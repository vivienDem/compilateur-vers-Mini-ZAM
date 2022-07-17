package ast;

import interfaces.IASTexpression;
import interfaces.IASTref;
import interfaces.IASTvisitor;

public class ASTref implements IASTref {
	private IASTexpression ref;
	
	public ASTref(IASTexpression ref) {
		super();
		this.ref = ref;
	}

	@Override
	public IASTexpression getRef() {
		return ref;
	}

	@Override
	public <Result, Data, Anomaly extends Throwable> Result accept(IASTvisitor<Result, Data, Anomaly> visitor,
			Data data) throws Anomaly {
		return visitor.visit(this, data);
	}

}
