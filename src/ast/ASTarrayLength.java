package ast;

import interfaces.IASTarrayLength;
import interfaces.IASTexpression;
import interfaces.IASTvisitor;

public class ASTarrayLength implements IASTarrayLength {
	private IASTexpression array;

	public ASTarrayLength(IASTexpression array) {
		super();
		this.array = array;
	}

	@Override
	public <Result, Data, Anomaly extends Throwable> Result accept(IASTvisitor<Result, Data, Anomaly> visitor,
			Data data) throws Anomaly {
		return visitor.visit(this, data);
	}

	@Override
	public IASTexpression getArray() {
		return array;
	}

}
