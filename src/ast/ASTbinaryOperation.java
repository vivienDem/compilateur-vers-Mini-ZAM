package ast;

import interfaces.IASTbinaryOperation;
import interfaces.IASTexpression;
import interfaces.IASTvisitor;

public class ASTbinaryOperation extends ASToperation implements IASTbinaryOperation {
	private IASTexpression leftOperand;
	private IASTexpression rightOperand;
	
	public ASTbinaryOperation(String operator, IASTexpression leftOperand, IASTexpression rightOperand) {
		super(operator);
		this.leftOperand = leftOperand;
		this.rightOperand = rightOperand;
	}

	@Override
	public IASTexpression getLeftOperand() {
		return leftOperand;
	}

	@Override
	public IASTexpression getRightOperand() {
		return rightOperand;
	}

	@Override
	public <Result, Data, Anomaly extends Throwable> Result accept(IASTvisitor<Result, Data, Anomaly> visitor,
			Data data) throws Anomaly {
		return visitor.visit(this, data);
	}

}
