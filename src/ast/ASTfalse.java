package ast;

import interfaces.IASTfalse;
import interfaces.IASTvisitor;

public class ASTfalse implements IASTfalse {

	@Override
	public boolean getValue() {
		return false;
	}

	@Override
	public <Result, Data, Anomaly extends Throwable> Result accept(IASTvisitor<Result, Data, Anomaly> visitor,
			Data data) throws Anomaly {
		return visitor.visit(this, data);
	}

}
