package ast;

import interfaces.IASTexpression;
import interfaces.IASTprint;
import interfaces.IASTvisitor;

public class ASTprint implements IASTprint {
	private IASTexpression value;

	public ASTprint(IASTexpression value) {
		super();
		this.value = value;
	}

	@Override
	public IASTexpression getValueToPrint() {
		return value; 
	}

	@Override
	public <Result, Data, Anomaly extends Throwable> Result accept(IASTvisitor<Result, Data, Anomaly> visitor,
			Data data) throws Anomaly {
		return visitor.visit(this, data);
	}

}
