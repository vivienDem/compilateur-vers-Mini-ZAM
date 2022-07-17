package ast;

import interfaces.IASTtrue;
import interfaces.IASTvisitor;

public class ASTtrue implements IASTtrue {

	@Override
	public boolean getValue() {
		return true;
	}

	@Override
	public <Result, Data, Anomaly extends Throwable> Result accept(IASTvisitor<Result, Data, Anomaly> visitor,
			Data data) throws Anomaly {
		return visitor.visit(this, data);
	}

}
