package interfaces;

public interface IASTfactory {
	IASTbinaryOperation newBinaryOperation (String op, IASTexpression leftOperand, IASTexpression rightOperand);
	IASTunaryOperation newUnaryOperation (String op, IASTexpression operand);
	IASTblock newBlock (String var, IASTexpression value, IASTinstruction body);
	IASTbegin newBegin (IASTsequence sequence);
	IASTconstant newTrue();
	IASTconstant newFalse();
	IASTconstant newInteger (int value);
	IASTinstruction newPrint (IASTexpression value);
	IASTsequence newSequence(IASTinstruction instruction, IASTsequence sequence);
	IASTinstruction newVariableAssignation (String var, IASTexpression value);
	IASTinstruction newAlternative (IASTexpression condition, IASTinstruction consequence, IASTinstruction alternant);
	IASTinstruction newLoop (IASTexpression condition, IASTsequence body);
	IASTexpression newRef (IASTexpression ref);
	IASTexpression newBang (String var);
	IASTexpression newLocalVariable(String name);
	IASTprogram newProgram(IASTsequence sequence, IASTfunctionDefinition[] functions);
	IASTreturn newReturn(IASTexpression value);
	IASTinvocation newInvocation(String fun, IASTexpression[] args);
	IASTfunctionDefinition newFunctionDefinition(String name, IASTlocalVariable[] args, IASTsequence body);
	IASTcontinue newContinue();
	IASTbreak newBreak();
	IASTarrayAssignation newArrayAssignation(String var, IASTexpression index, IASTexpression value);
	IASTarrayLength newArrayLength(IASTexpression array);
	IASTlocalArrayVariable newLocalArrayVariable(String var, IASTexpression index);
	IASTarray newArray(IASTexpression[] content);
}
