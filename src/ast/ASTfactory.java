package ast;

import interfaces.IASTarray;
import interfaces.IASTarrayAssignation;
import interfaces.IASTarrayLength;
import interfaces.IASTbegin;
import interfaces.IASTbinaryOperation;
import interfaces.IASTblock;
import interfaces.IASTbreak;
import interfaces.IASTconstant;
import interfaces.IASTcontinue;
import interfaces.IASTexpression;
import interfaces.IASTfactory;
import interfaces.IASTfunctionDefinition;
import interfaces.IASTinstruction;
import interfaces.IASTinvocation;
import interfaces.IASTlocalArrayVariable;
import interfaces.IASTlocalVariable;
import interfaces.IASTprogram;
import interfaces.IASTreturn;
import interfaces.IASTsequence;
import interfaces.IASTunaryOperation;

public class ASTfactory implements IASTfactory {

	@Override
	public IASTbinaryOperation newBinaryOperation(String op, IASTexpression leftOperand, IASTexpression rightOperand) {
		return new ASTbinaryOperation(op, leftOperand, rightOperand);
	}

	@Override
	public IASTunaryOperation newUnaryOperation(String op, IASTexpression operand) {
		return new ASTunaryOperation(op, operand);
	}

	@Override
	public IASTblock newBlock(String var, IASTexpression value, IASTinstruction body) {
		return new ASTblock(var, value, body);
	}

	@Override
	public IASTconstant newTrue() {
		return new ASTtrue();
	}

	@Override
	public IASTconstant newFalse() {
		return new ASTfalse();
	}

	@Override
	public IASTconstant newInteger(int value) {
		return new ASTinteger(value);
	}

	@Override
	public IASTinstruction newPrint(IASTexpression value) {
		return new ASTprint(value);
	}

	@Override
	public IASTsequence newSequence(IASTinstruction instruction, IASTsequence sequence) {
		return new ASTsequence(instruction, sequence);
	}

	@Override
	public IASTinstruction newVariableAssignation(String var, IASTexpression value) {
		return new ASTvariableAssignation(var, value);
	}

	@Override
	public IASTinstruction newAlternative(IASTexpression condition, IASTinstruction consequence,
			IASTinstruction alternant) {
		return new ASTalternative(condition, consequence, alternant);
	}

	@Override
	public IASTinstruction newLoop(IASTexpression condition, IASTsequence body) {
		return new ASTloop(condition, body);
	}

	@Override
	public IASTexpression newRef(IASTexpression ref) {
		return new ASTref(ref);
	}

	@Override
	public IASTexpression newBang(String var) {
		return new ASTbang(var);
	}

	@Override
	public IASTbegin newBegin(IASTsequence sequence) {
		return new ASTbegin(sequence);
	}

	@Override
	public IASTexpression newLocalVariable(String name) {
		return new ASTlocalVariable(name);
	}

	@Override
	public IASTprogram newProgram(IASTsequence sequence, IASTfunctionDefinition[] functions) {
		return new ASTprogram(sequence, functions);
	}

	@Override
	public IASTreturn newReturn(IASTexpression value) {
		return new ASTreturn(value);
	}

	@Override
	public IASTinvocation newInvocation(String fun, IASTexpression[] args) {
		return new ASTinvocation(fun, args);
	}

	@Override
	public IASTfunctionDefinition newFunctionDefinition(String name, IASTlocalVariable[] args, IASTsequence body) {
		return new ASTfunctionDefinition(name, args, body);
	}

	@Override
	public IASTcontinue newContinue() {
		return new ASTcontinue();
	}

	@Override
	public IASTbreak newBreak() {
		return new ASTbreak();
	}

	@Override
	public IASTarrayAssignation newArrayAssignation(String var, IASTexpression index, IASTexpression value) {
		return new ASTarrayAssignation(var, index, value);
	}

	@Override
	public IASTarrayLength newArrayLength(IASTexpression array) {
		return new ASTarrayLength(array);
	}

	@Override
	public IASTlocalArrayVariable newLocalArrayVariable(String var, IASTexpression index) {
		return new ASTlocalArrayVariable(var, index);
	}

	@Override
	public IASTarray newArray(IASTexpression[] content) {
		return new ASTarray(content);
	}
}
