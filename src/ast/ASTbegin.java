package ast;

import interfaces.IASTbegin;
import interfaces.IASTsequence;
import interfaces.IASTvisitor;

public class ASTbegin implements IASTbegin {
	private IASTsequence sequence;

	public ASTbegin(IASTsequence sequence) {
		super();
		this.sequence = sequence;
	}

	@Override
	public IASTsequence getSequence() {
		return sequence;
	}

	@Override
	public <Result, Data, Anomaly extends Throwable> Result accept(IASTvisitor<Result, Data, Anomaly> visitor,
			Data data) throws Anomaly {
		return visitor.visit(this, data);
	}

}
