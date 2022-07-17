package ast;

import interfaces.IASTinteger;
import interfaces.IASTvisitor;

public class ASTinteger implements IASTinteger {
	private int value;
	
	public ASTinteger(int value) {
		super();
		this.value = value;
	}

	@Override
	public int getValue() {
		return value;
	}

	@Override
	public <Result, Data, Anomaly extends Throwable> Result accept(IASTvisitor<Result, Data, Anomaly> visitor,
			Data data) throws Anomaly {
		return visitor.visit(this, data);
	}

}
