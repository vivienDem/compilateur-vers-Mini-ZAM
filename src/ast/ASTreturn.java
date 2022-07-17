package ast;

import interfaces.IASTexpression;
import interfaces.IASTreturn;
import interfaces.IASTvisitor;

public class ASTreturn implements IASTreturn {
	private IASTexpression value;

	public ASTreturn(IASTexpression value) {
		super();
		this.value = value;
	}

	@Override
	public <Result, Data, Anomaly extends Throwable> Result accept(IASTvisitor<Result, Data, Anomaly> visitor,
			Data data) throws Anomaly {
		return visitor.visit(this, data);
	}

	@Override
	public IASTexpression getValue() {
		return value;
	}

}
