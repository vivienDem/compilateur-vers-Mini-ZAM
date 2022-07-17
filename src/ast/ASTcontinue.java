package ast;

import interfaces.IASTcontinue;
import interfaces.IASTvisitor;

public class ASTcontinue implements IASTcontinue {

	@Override
	public <Result, Data, Anomaly extends Throwable> Result accept(IASTvisitor<Result, Data, Anomaly> visitor,
			Data data) throws Anomaly {
		return visitor.visit(this, data);
	}

}
