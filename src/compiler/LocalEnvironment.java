package compiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ast.ASTvariable;
import compiler.interfaces.Environment;
import interfaces.IASTexpression;
import interfaces.IASTvariable;
import tools.CompilationException;

public class LocalEnvironment implements Environment {
	private ArrayList<IASTvariable> stack;
	private ArrayList<IASTvariable> env;
	private Map<String, String> functions_location;
	private int sp = 0;
	private String loop_beginning_label, loop_ending_label;
	private int last_function_nb_args;
	private int nb_args_to_pop_before_return;
	private boolean renaming;
	private ArrayList<String> toRename;

	public LocalEnvironment() {
		this(new ArrayList<IASTvariable>());
	}

	public LocalEnvironment (ArrayList<IASTvariable> stack) {
		this(stack, new ArrayList<IASTvariable>(), new HashMap<String, String>(), 0, null, null
				, 0, 1, new ArrayList<String>(), false);
	}

	public LocalEnvironment(ArrayList<IASTvariable> stack, ArrayList<IASTvariable> env,
			Map<String, String> functions_location, int sp, String loop_beginning_label, String loop_ending_label,
			int last_function_nb_args, int nb_args_to_pop_before_return, ArrayList<String> toRename, boolean renaming) {
		this.stack = stack;
		this.env = env;
		this.functions_location = functions_location;
		this.sp = sp;
		this.loop_beginning_label = loop_beginning_label;
		this.loop_ending_label = loop_ending_label;
		this.last_function_nb_args = last_function_nb_args;
		this.nb_args_to_pop_before_return = nb_args_to_pop_before_return;
		this.renaming = renaming;
		this.toRename = toRename;
		functions_location = new HashMap<String, String>();
	}

	@Override
	public void push(String name) {
		if (renaming) {
			toRename.add(name);
			name = "let" + name;
		}
		IASTvariable var = new ASTvariable(name, null);
		stack.add(0, var);
	}

	@Override
	public void push(String name, IASTexpression value) {
		if (renaming) {
			toRename.add(name);
			name = "let" + name;
		}
		IASTvariable var = new ASTvariable(name, value);
		stack.add(0, var);
	}

	@Override
	public IASTvariable pop(int n) {
		IASTvariable var = null;
		for (int i = 0; i < n; i++) {
			var = stack.remove(stack.size() - 1);
		}
		return var;
	}

	@Override
	public IASTvariable pop() {
		return pop(1);
	}

	@Override
	public LocalEnvironment clone() {
		return new LocalEnvironment((ArrayList<IASTvariable>) stack.clone(), (ArrayList<IASTvariable>) env.clone(),
				functions_location, sp, loop_beginning_label, loop_ending_label, last_function_nb_args,
				nb_args_to_pop_before_return, toRename, renaming);
	}

	@Override
	public int getIndex(String name) {
		if (toRename.contains(name)) {
			name = "let" + name;
		}
		int result = 0;
		for (IASTvariable var : stack) {
			if (var.getName().equals(name)) {
				return result + sp;
			}
			result++;
		}
		return -1;
	}

	@Override
	public void remove(String name) {
		int index = 0;
		if (renaming) {
			name = "let" + name;
		}
		for (IASTvariable var : stack) {
			if (var.getName().equals(name)) {
				break;
			}
			index++;
		}
		stack.remove(index);
	}

	@Override
	public int getEnvIndex(String name) {
		if (toRename.contains(name)) {
			name = "let" + name;
		}
		int result = 1;
		for (IASTvariable var : env) {
			if (var.getName().equals(name)) {
				return result;
			}
			result++;
		}
		return -1;
	}

	@Override
	public void addtoEnv(String name) {
		IASTvariable var = new ASTvariable(name, null);
		env.add(var);
	}

	@Override
	public void clearEnv() {
		env.clear();
	}

	@Override
	public void increaseSp() {
		sp++;
	}

	@Override
	public void decreaseSp() {
		sp--;
	}

	@Override
	public void increaseSp(int n) {
		sp += n;
	}

	@Override
	public void decreaseSp(int n) {
		sp -= n;
	}

	@Override
	public void setSp(int sp) {
		this.sp = sp;
	}

	@Override
	public int getSp() {
		return sp;
	}

	@Override
	public String getFunctionLabel(String function_name) {
		return functions_location.get(function_name);
	}

	@Override
	public void addFunction(String function_name, String label) throws CompilationException {
		if (this.functions_location.containsKey(function_name))
			throw new CompilationException("Function " + function_name + " is already defined");
		this.functions_location.put(function_name, label);
	}

	@Override
	public int getLast_function_nb_args() {
		return last_function_nb_args;
	}

	@Override
	public void setLast_function_nb_args(int last_function_nb_args) {
		this.last_function_nb_args = last_function_nb_args;
	}

	@Override
	public String getLoop_beginning_label() {
		return loop_beginning_label;
	}

	@Override
	public void setLoop_beginning_label(String loop_beginning_label) {
		this.loop_beginning_label = loop_beginning_label;
	}

	@Override
	public String getLoop_ending_label() {
		return loop_ending_label;
	}

	@Override
	public void setLoop_ending_label(String loop_ending_label) {
		this.loop_ending_label = loop_ending_label;
	}

	@Override
	public void increase_nb_args_to_pop_before_return() {
		this.nb_args_to_pop_before_return++;
	}

	@Override
	public void reset_nb_args_to_pop_before_return() {
		this.nb_args_to_pop_before_return = 1;
	}

	@Override
	public int getNb_args_to_pop_before_return() {
		return this.nb_args_to_pop_before_return;
	}

	@Override
	public void setRenaming(boolean renaming) {
		this.renaming = renaming;
	}

}
