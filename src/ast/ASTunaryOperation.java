package ast;

import interfaces.IASTexpression;
import interfaces.IASTunaryOperation;
import interfaces.IASTvisitor;

public class ASTunaryOperation extends ASToperation implements IASTunaryOperation {
	private IASTexpression operand;
	
	public ASTunaryOperation(String operator, IASTexpression operand) {
		super(operator);
		this.operand = operand;
	}

	@Override
	public IASTexpression getOperand() {
		return operand;
	}

	@Override
	public <Result, Data, Anomaly extends Throwable> Result accept(IASTvisitor<Result, Data, Anomaly> visitor,
			Data data) throws Anomaly {
		return visitor.visit(this, data);
	}

}
