package parser;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import grammar.grammarCAListener;
import grammar.grammarCAParser.AlternativeContext;
import grammar.grammarCAParser.ArrayAssignContext;
import grammar.grammarCAParser.ArrayContext;
import grammar.grammarCAParser.ArrayLengthContext;
import grammar.grammarCAParser.BangContext;
import grammar.grammarCAParser.BeginContext;
import grammar.grammarCAParser.BinaryApplicationContext;
import grammar.grammarCAParser.BindingContext;
import grammar.grammarCAParser.BreakContext;
import grammar.grammarCAParser.ConstFalseContext;
import grammar.grammarCAParser.ConstIntContext;
import grammar.grammarCAParser.ConstTrueContext;
import grammar.grammarCAParser.ContinueContext;
import grammar.grammarCAParser.ExprContext;
import grammar.grammarCAParser.GlobalFunDefContext;
import grammar.grammarCAParser.InvocationContext;
import grammar.grammarCAParser.LocalArrayVariableContext;
import grammar.grammarCAParser.LocalVariableContext;
import grammar.grammarCAParser.LoopContext;
import grammar.grammarCAParser.PrintContext;
import grammar.grammarCAParser.ProgContext;
import grammar.grammarCAParser.ReferenceContext;
import grammar.grammarCAParser.ReturnContext;
import grammar.grammarCAParser.SeqContext;
import grammar.grammarCAParser.UnaryApplicationContext;
import grammar.grammarCAParser.VariableAssignContext;
import interfaces.IASTexpression;
import interfaces.IASTfactory;
import interfaces.IASTfunctionDefinition;
import interfaces.IASTlocalVariable;

public class Listener implements grammarCAListener {
	private IASTfactory factory;
	
	public Listener(IASTfactory factory) {
		this.factory = factory;
	}
	
	@Override public void enterEveryRule(ParserRuleContext arg0) {}
	@Override public void exitEveryRule(ParserRuleContext arg0) {}
	@Override public void visitErrorNode(ErrorNode arg0) {}
	@Override public void visitTerminal(TerminalNode arg0) {}
	@Override public void enterBinding(BindingContext ctx) {}
	@Override public void enterLoop(LoopContext ctx) {}
	@Override public void enterBinaryApplication(BinaryApplicationContext ctx) {}
	@Override public void enterAlternative(AlternativeContext ctx) {}
	@Override public void enterReference(ReferenceContext ctx) {}
	@Override public void enterBang(BangContext ctx) {}
	@Override public void enterLocalVariable(LocalVariableContext ctx) {}
	@Override public void enterVariableAssign(VariableAssignContext ctx) {}
	@Override public void enterConstFalse(ConstFalseContext ctx) {}
	@Override public void enterConstInt(ConstIntContext ctx) {}
	@Override public void enterProg(ProgContext ctx) {}
	@Override public void enterPrint(PrintContext ctx) {}
	@Override public void enterConstTrue(ConstTrueContext ctx) {}
	@Override public void enterBegin(BeginContext ctx) {}
	@Override public void enterSeq(SeqContext ctx) {}
	@Override public void enterUnaryApplication(UnaryApplicationContext ctx) {}
	@Override public void enterGlobalFunDef(GlobalFunDefContext ctx) {}
	@Override public void enterReturn(ReturnContext ctx) {}
	@Override public void enterInvocation(InvocationContext ctx) {}
	@Override public void enterContinue(ContinueContext ctx) {}
	@Override public void enterBreak(BreakContext ctx) {}
	@Override public void enterArrayAssign(ArrayAssignContext ctx) {}
	@Override public void enterArrayLength(ArrayLengthContext ctx) {}
	@Override public void enterArray(ArrayContext ctx) {}
	@Override public void enterLocalArrayVariable(LocalArrayVariableContext ctx) {}
	
	@Override
	public void exitBinding(BindingContext ctx) {
		ctx.node = factory.newBlock(ctx.var.getText(), ctx.val.node, ctx.body.node);	
	}

	@Override
	public void exitLoop(LoopContext ctx) {
		ctx.node = factory.newLoop(ctx.condition.node, ctx.consequence.node);
	}

	@Override
	public void exitBinaryApplication(BinaryApplicationContext ctx) {
		ctx.node = factory.newBinaryOperation(ctx.operator.getText(), ctx.operand1.node, ctx.operand2.node);
	}

	@Override
	public void exitAlternative(AlternativeContext ctx) {
		ctx.node = factory.newAlternative(ctx.condition.node, ctx.consequence.node, 
				ctx.alternant == null ? null : ctx.alternant.node);
	}

	@Override
	public void exitReference(ReferenceContext ctx) {
		ctx.node = factory.newRef(ctx.ref.node);
	}

	@Override
	public void exitBang(BangContext ctx) {
		ctx.node = factory.newBang(ctx.var.getText());
	}

	@Override
	public void exitLocalVariable(LocalVariableContext ctx) {
		ctx.node = factory.newLocalVariable(ctx.var.getText());
	}

	@Override
	public void exitVariableAssign(VariableAssignContext ctx) {
		ctx.node = factory.newVariableAssignation(ctx.var.getText(), ctx.val.node);
	}

	@Override
	public void exitConstFalse(ConstFalseContext ctx) {
		ctx.node = factory.newFalse();
	}

	@Override
	public void exitConstInt(ConstIntContext ctx) {
		ctx.node = factory.newInteger(Integer.parseInt(ctx.value.getText()));		
	}

	@Override
	public void exitProg(ProgContext ctx) {
		List<IASTfunctionDefinition> f = new ArrayList<>();
		for (GlobalFunDefContext d : ctx.defs) {
			f.add((IASTfunctionDefinition)d.node);
		}
		ctx.node = factory.newProgram(ctx.body.node, f.toArray(new IASTfunctionDefinition[0]));
	}

	@Override
	public void exitPrint(PrintContext ctx) {
		ctx.node = factory.newPrint(ctx.val.node);
	}

	@Override
	public void exitConstTrue(ConstTrueContext ctx) {
		ctx.node = factory.newTrue();
	}

	@Override
	public void exitBegin(BeginContext ctx) {
		ctx.node = factory.newBegin(ctx.sequence.node);		
	}

	@Override
	public void exitUnaryApplication(UnaryApplicationContext ctx) {
		ctx.node = factory.newUnaryOperation(ctx.operator.getText(), ctx.operand.node);
	}

	@Override
	public void exitSeq(SeqContext ctx) {
		ctx.node = factory.newSequence(ctx.instruction.node, ctx.sequence == null ? null : ctx.sequence.node);
	}

	@Override
	public void exitGlobalFunDef(GlobalFunDefContext ctx) {
		ctx.node = factory.newFunctionDefinition(ctx.name.getText(), toVariables(ctx.vars), ctx.body.node);
	}

	@Override
	public void exitReturn(ReturnContext ctx) {
		ctx.node = factory.newReturn(ctx.value.node);
	}

	@Override
	public void exitInvocation(InvocationContext ctx) {
		ctx.node = factory.newInvocation(ctx.fun.getText(), toExpressions(ctx.args));
	}
	
	private IASTexpression[] toExpressions(List<ExprContext> ctxs) {
		if (ctxs == null) return new IASTexpression[0];
		IASTexpression[] r = new IASTexpression[ctxs.size()];
		int pos = 0;
		for (ExprContext e : ctxs) {
			r[pos++] = e.node;
		}
		return r;
	}
	
	private IASTlocalVariable[] toVariables(List<Token> vars) {
		if (vars == null) return new IASTlocalVariable[0];
		IASTlocalVariable[] r = new IASTlocalVariable[vars.size()];
		int pos = 0;
		for (Token v : vars) {
			r[pos++] = (IASTlocalVariable) factory.newLocalVariable(v.getText());
		}
		return r;
	}

	@Override
	public void exitBreak(BreakContext ctx) {
		ctx.node = factory.newBreak();
	}

	@Override
	public void exitContinue(ContinueContext ctx) {
		ctx.node = factory.newContinue();
	}

	
	@Override
	public void exitArrayAssign(ArrayAssignContext ctx) {
		ctx.node = factory.newArrayAssignation(ctx.var.getText(), ctx.index.node, ctx.val.node);
	}

	@Override
	public void exitArrayLength(ArrayLengthContext ctx) {
		ctx.node = factory.newArrayLength(ctx.array.node);
	}

	@Override
	public void exitArray(ArrayContext ctx) {
		ctx.node = factory.newArray(this.toExpressions(ctx.content));
	}

	@Override
	public void exitLocalArrayVariable(LocalArrayVariableContext ctx) {
		ctx.node = factory.newLocalArrayVariable(ctx.var.getText(), ctx.index.node);
	}
}
