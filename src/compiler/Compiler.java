package compiler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import interfaces.IASTalternative;
import interfaces.IASTarray;
import interfaces.IASTarrayAssignation;
import interfaces.IASTarrayLength;
import interfaces.IASTbang;
import interfaces.IASTbegin;
import interfaces.IASTbinaryOperation;
import interfaces.IASTblock;
import interfaces.IASTbreak;
import interfaces.IASTcontinue;
import interfaces.IASTexpression;
import interfaces.IASTfactory;
import interfaces.IASTfalse;
import interfaces.IASTfunctionDefinition;
import interfaces.IASTinteger;
import interfaces.IASTinvocation;
import interfaces.IASTlocalArrayVariable;
import interfaces.IASTlocalVariable;
import interfaces.IASTloop;
import interfaces.IASTprint;
import interfaces.IASTprogram;
import interfaces.IASTref;
import interfaces.IASTreturn;
import interfaces.IASTsequence;
import interfaces.IASTtrue;
import interfaces.IASTunaryOperation;
import interfaces.IASTvariableAssignation;
import interfaces.IASTvisitor;
import tools.CompilationException;

public class Compiler implements IASTvisitor<Void, LocalEnvironment, CompilationException> {
	private Writer out;
	private int label, closure;
	private IASTfactory factory;

	public Compiler(IASTfactory factory) {
		this.label = 0;
		this.closure = 0;
		this.factory = factory;
	}

	public String compile(IASTprogram program) throws CompilationException {
		StringWriter sw = new StringWriter();
		try {
			out = new BufferedWriter(sw);
			LocalEnvironment env = new LocalEnvironment();
			visit(program, env);
			out.flush();
		} catch (IOException exc) {
			throw new CompilationException(exc);
		}
		return normalize(sw.toString());
	}

	public String normalize(String code) {
		Map<String, String> labelsToReplace = new HashMap<String, String>();
		String[] lines = code.split("\n");
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < lines.length - 1; i++) {
			if (lines[i].matches("L(.)*:(.)*") && lines[i + 1].matches("L(.)*:(.)*")) {
				labelsToReplace.put(lines[i + 1].substring(0, lines[i + 1].length() - 2),
						lines[i].substring(0, lines[i].length() - 2));
			} else {
				result.append(lines[i]);
				result.append("\n");
			}

		}
		result.append(lines[lines.length - 1]);
		for (Entry<String, String> entry : labelsToReplace.entrySet()) {
			Pattern p = Pattern.compile(entry.getValue() + "\\b");
			Matcher m = p.matcher(result);
			result = new StringBuilder(m.replaceAll(entry.getKey()));
		}
		return result.toString();
	}

	public String generateLabel() {
		String result = "L" + label;
		label++;
		return result;
	}

	public String generateClosureName() {
		String result = "C" + closure;
		closure++;
		return result;
	}

	public void emit(String s) throws CompilationException {
		try {
			out.append(s);
		} catch (IOException e) {
			throw new CompilationException(e);
		}
	}

	public void emit(char c) throws CompilationException {
		try {
			out.append(c);
		} catch (IOException e) {
			throw new CompilationException(e);
		}
	}

	public void emit(int i) throws CompilationException {
		try {
			out.append(Integer.toString(i));
		} catch (IOException e) {
			throw new CompilationException(e);
		}
	}

	@Override
	public Void visit(IASTalternative iast, LocalEnvironment data) throws CompilationException {
		String label_else = this.generateLabel();
		String label_end_if = this.generateLabel();
		iast.getCondition().accept(this, data);
		emit("BRANCHIFNOT " + label_else + "\n");
		iast.getConsequence().accept(this, data);
		emit("BRANCH " + label_end_if + "\n");
		emit(label_else + ": \n");
		if (iast.getAlternant() != null)
			iast.getAlternant().accept(this, data);
		emit(label_end_if + ": \n");
		return null;
	}

	@Override
	public Void visit(IASTbang iast, LocalEnvironment env) throws CompilationException {
		int index = env.getIndex(iast.getVar());
		if (index == -1)
			throw new CompilationException("Variable " + iast.getVar() + " not in scope");
		emit("ACC ");
		emit(index);
		emit("\n");
		emit("GETFIELD 0\n");
		return null;
	}

	@Override
	public Void visit(IASTbegin iast, LocalEnvironment data) throws CompilationException {
		iast.getSequence().accept(this, data);
		return null;
	}

	@Override
	public Void visit(IASTbinaryOperation iast, LocalEnvironment env) throws CompilationException {
		String operator = iast.getOperator();
		iast.getRightOperand().accept(this, env);
		emit("PUSH\n");
		LocalEnvironment nenv = env.clone();
		nenv.increaseSp();
		iast.getLeftOperand().accept(this, nenv);
		emit("PRIM " + operator + "\n");
		return null;
	}

	@Override
	public Void visit(IASTblock iast, LocalEnvironment env) throws CompilationException {
		LocalEnvironment nenv = env.clone();
		LocalEnvironment nenv2 = env.clone();
		nenv.setSp(0);
		iast.getVal().accept(this, nenv);
		emit("PUSH\n");
		nenv2.setRenaming(true);
		nenv2.push(iast.getVar());
		nenv2.increase_nb_args_to_pop_before_return();
		iast.getBody().accept(this, nenv2);
		emit("POP\n");
		nenv2.pop();
		nenv2.setRenaming(false);
		return null;
	}

	@Override
	public Void visit(IASTinteger iast, LocalEnvironment data) throws CompilationException {
		emit("CONST ");
		emit(iast.getValue());
		emit("\n");
		return null;
	}

	@Override
	public Void visit(IASTlocalVariable iast, LocalEnvironment env) throws CompilationException {
		int index = env.getEnvIndex(iast.getName());
		if (index == -1) {
			index = env.getIndex(iast.getName());
			if (index == -1) {
				throw new CompilationException("Variable " + iast.getName() + " not in scope");
			}
			emit("ACC ");
			emit(index);
			emit("\n");
			return null;
		}
		emit("ENVACC ");
		emit(index);
		emit("\n");
		return null;
	}

	@Override
	public Void visit(IASTloop iast, LocalEnvironment env) throws CompilationException {
		String loop_label = this.generateLabel();
		String end_of_loop = this.generateLabel();
		emit(loop_label + ":\n");
		env.setLoop_beginning_label(loop_label);
		env.setLoop_ending_label(end_of_loop);
		iast.getCondition().accept(this, env);
		emit("BRANCHIFNOT " + end_of_loop + "\n");
		iast.getBody().accept(this, env);
		emit("BRANCH " + loop_label + "\n");
		emit(end_of_loop + ":\n");
		env.setLoop_beginning_label(null);
		env.setLoop_ending_label(null);
		return null;
	}

	@Override
	public Void visit(IASTprint iast, LocalEnvironment data) throws CompilationException {
		iast.getValueToPrint().accept(this, data);
		emit("PRIM print\n");
		return null;
	}

	@Override
	public Void visit(IASTprogram iast, LocalEnvironment env) throws CompilationException {
		String begin = this.generateLabel();
		emit("BRANCH " + begin + "\n");
		IASTfunctionDefinition[] functions = iast.getFunctions();
		for (int i = 0; i < functions.length; i++) {
			functions[i].accept(this, env);
		}
		emit(begin + ": \n");
		iast.getBody().accept(this, env);
		emit("STOP\n");
		return null;
	}

	@Override
	public Void visit(IASTref iast, LocalEnvironment env) throws CompilationException {
		iast.getRef().accept(this, env);
		emit("MAKEBLOCK 1\n");
		return null;
	}

	@Override
	public Void visit(IASTsequence iast, LocalEnvironment data) throws CompilationException {
		iast.getInstruction().accept(this, data);
		IASTsequence next = iast.getNextSequence();
		if (next != null)
			next.accept(this, data);
		return null;
	}

	@Override
	public Void visit(IASTunaryOperation iast, LocalEnvironment env) throws CompilationException {
		String operator = iast.getOperator();
		iast.getOperand().accept(this, env);
		emit("PRIM " + operator + "\n");
		return null;
	}

	@Override
	public Void visit(IASTvariableAssignation iast, LocalEnvironment env) throws CompilationException {
		int index = env.getIndex(iast.getVar());
		if (index == -1) {
			/*if (!(iast.getVal() instanceof IASTref) && !(iast.getVal() instanceof IASTarray)) {
				throw new CompilationException("Invalid initialisation : " + iast.getVar()
						+ " must be initialized with a reference or an array");
			}*/
			env.setSp(0);
			iast.getVal().accept(this, env);
			emit("PUSH\n");
			env.push(iast.getVar());
			return null;
		}
		iast.getVal().accept(this, env);
		emit("PUSH\n");
		emit("ACC ");
		emit(index + 1);
		emit("\n");
		emit("SETFIELD 0\n");
		emit("ASSIGN ");
		emit(index);
		emit("\n");
		return null;
	}

	@Override
	public Void visit(IASTtrue iast, LocalEnvironment data) throws CompilationException {
		emit("CONST ");
		emit(1);
		emit("\n");
		return null;
	}

	@Override
	public Void visit(IASTfalse iast, LocalEnvironment data) throws CompilationException {
		emit("CONST ");
		emit(0);
		emit("\n");
		return null;
	}

	@Override
	public Void visit(IASTreturn iast, LocalEnvironment env) throws CompilationException {
		iast.getValue().accept(this, env);
		emit("RETURN ");
		emit(env.getNb_args_to_pop_before_return());
		emit("\n");
		env.reset_nb_args_to_pop_before_return();
		return null;
	}

	@Override
	public Void visit(IASTinvocation iast, LocalEnvironment env) throws CompilationException {
		int sp = env.getSp();
		IASTexpression[] args = iast.getArgs();
		String function_label = env.getFunctionLabel(iast.getFun());
		if (function_label == null)
			throw new CompilationException("Fonction " + iast.getFun() + " is undefined");
		
		for (int i = args.length - 1; i >= 1; i--) {
		args[i].accept(this, env);
		env.increaseSp();
		emit("PUSH\n");
		}
		if (args.length > 0)
			args[0].accept(this, env);
		emit("CLOSURE " + function_label + ", " + args.length + "\n");
		emit("PUSH\n");
		String closure_name = this.generateClosureName();
		env.push(closure_name);

		// Apply ne semble fonctionner qu'avec n > 0 donc on ajoute une variable
		visit((IASTinteger) factory.newInteger(1664), env);
		emit("PUSH\n");

		emit("ACC ");
		emit(env.getIndex(closure_name) - env.getSp() + 1);
		emit("\n");

		emit("APPLY ");
		emit(1);
		emit("\n");
		emit("POP\n");
		env.remove(closure_name);
		env.setSp(sp);
		return null;
	}

	@Override
	public Void visit(IASTfunctionDefinition iast, LocalEnvironment env) throws CompilationException {
		String function_label = this.generateLabel();
		emit(function_label + ": \n");
		env.addFunction(iast.getName(), function_label);
		env.setLast_function_nb_args(iast.getVars().length);
		IASTlocalVariable[] vars = iast.getVars();
		for (int i = 0; i < vars.length; i++) {
			env.addtoEnv(vars[i].getName());
		}
		iast.getBody().accept(this, env);
		env.clearEnv();
		return null;
	}

	@Override
	public Void visit(IASTcontinue iast, LocalEnvironment env) throws CompilationException {
		String label = env.getLoop_beginning_label();
		if (label == null)
			throw new CompilationException("continue out of loop");
		emit("BRANCH " + label + "\n");
		return null;
	}

	@Override
	public Void visit(IASTbreak iast, LocalEnvironment env) throws CompilationException {
		String label = env.getLoop_ending_label();
		if (label == null)
			throw new CompilationException("break out of loop");
		emit("BRANCH " + label + "\n");
		return null;
	}

	@Override
	public Void visit(IASTarrayAssignation iast, LocalEnvironment env) throws CompilationException {
		int index = env.getIndex(iast.getVar());
		if (index == -1) {
			index = env.getEnvIndex(iast.getVar());
			if (index == -1) {
				throw new CompilationException("Array " + iast.getVar() + " may be out of scope or undefined");
			}
			iast.getValue().accept(this, env);
			emit("PUSH\n");
			env.increaseSp();
			iast.getIndex().accept(this, env);
			emit("PUSH\n");
			emit("ENVACC ");
			emit(index);
			emit("\n");
			emit("SETVECTITEM\n");
			env.decreaseSp();
			return null;
		}
		iast.getValue().accept(this, env);
		emit("PUSH\n");
		env.increaseSp();
		iast.getIndex().accept(this, env);
		emit("PUSH\n");
		emit("ACC ");
		emit(index + 2);
		emit("\n");
		emit("SETVECTITEM\n");
		env.decreaseSp();
		return null;
	}

	@Override
	public Void visit(IASTarrayLength iast, LocalEnvironment env) throws CompilationException {
		IASTexpression array = iast.getArray();
		array.accept(this, env);
		emit("VECTLENGTH\n");
		return null;
	}

	@Override
	public Void visit(IASTlocalArrayVariable iast, LocalEnvironment env) throws CompilationException {
		int index = env.getEnvIndex(iast.getVar());

		if (index == -1) {
			index = env.getIndex(iast.getVar());
			if (index == -1) {
				throw new CompilationException("Variable " + iast.getVar() + " not in scope");
			}
			iast.getIndex().accept(this, env);
			emit("PUSH\n");
			env.increaseSp();
			emit("ACC ");
			emit(env.getIndex(iast.getVar()));
			emit("\n");
			emit("GETVECTITEM\n");
			env.decreaseSp();
			return null;
		}
		iast.getIndex().accept(this, env);
		emit("PUSH\n");
		env.increaseSp();
		emit("ENVACC ");
		emit(env.getEnvIndex(iast.getVar()));
		emit("\n");
		emit("GETVECTITEM\n");
		env.decreaseSp();
		return null;
	}

	@Override
	public Void visit(IASTarray iast, LocalEnvironment env) throws CompilationException {
		IASTexpression[] content = iast.getContent();
		int sp = env.getSp();
		int length = iast.getLength();
		for (int i = length - 1; i >= 1; i--) {
			content[i].accept(this, env);
			emit("PUSH\n");
			env.increaseSp();
		}
		if (length > 0)
			content[0].accept(this, env);
		emit("MAKEBLOCK ");
		emit(length);
		emit("\n");
		env.setSp(sp);
		return null;
	}

}
