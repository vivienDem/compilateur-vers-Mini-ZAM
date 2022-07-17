package ast;

import interfaces.IASTexpression;
import interfaces.IASTloop;
import interfaces.IASTsequence;
import interfaces.IASTvisitor;

public class ASTloop implements IASTloop {
	private IASTexpression condition;
	private IASTsequence body;

	public ASTloop(IASTexpression condition, IASTsequence body) {
		super();
		this.condition = condition;
		this.body = body;
	}

	@Override
	public IASTexpression getCondition() {
		return condition;
	}

	@Override
	public IASTsequence getBody() {
		return body;
	}

	@Override
	public <Result, Data, Anomaly extends Throwable> Result accept(IASTvisitor<Result, Data, Anomaly> visitor,
			Data data) throws Anomaly {
		return visitor.visit(this, data);
	}

}
