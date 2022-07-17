package ast;

import interfaces.IASTbreak;
import interfaces.IASTvisitor;

public class ASTbreak implements IASTbreak {

	@Override
	public <Result, Data, Anomaly extends Throwable> Result accept(IASTvisitor<Result, Data, Anomaly> visitor,
			Data data) throws Anomaly {
		return visitor.visit(this, data);
	}

}
