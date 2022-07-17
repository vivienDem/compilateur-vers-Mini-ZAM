package ast;

import interfaces.IASTinstruction;
import interfaces.IASTsequence;
import interfaces.IASTvisitor;

public class ASTsequence implements IASTsequence {
	private IASTinstruction instruction;
	IASTsequence nextSequence;

	public ASTsequence(IASTinstruction instruction, IASTsequence nextSequence) {
		super();
		this.instruction = instruction;
		this.nextSequence = nextSequence;
	}

	@Override
	public IASTinstruction getInstruction() {
		return instruction;
	}

	@Override
	public IASTsequence getNextSequence() {
		return nextSequence;
	}

	@Override
	public <Result, Data, Anomaly extends Throwable> Result accept(IASTvisitor<Result, Data, Anomaly> visitor,
			Data data) throws Anomaly {
		return visitor.visit(this, data);
	}

}
