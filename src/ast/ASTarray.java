package ast;

import interfaces.IASTarray;
import interfaces.IASTexpression;
import interfaces.IASTvisitor;

public class ASTarray implements IASTarray {
	private IASTexpression[] content;

	public ASTarray(IASTexpression[] content) {
		super();
		this.content = content;
	}

	@Override
	public <Result, Data, Anomaly extends Throwable> Result accept(IASTvisitor<Result, Data, Anomaly> visitor,
			Data data) throws Anomaly {
		return visitor.visit(this, data);
	}

	@Override
	public IASTexpression[] getContent() {
		return content;
	}

	@Override
	public int getLength() {
		return content.length;
	}

}
