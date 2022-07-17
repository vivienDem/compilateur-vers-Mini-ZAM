package ast;

import interfaces.IASTalternative;
import interfaces.IASTexpression;
import interfaces.IASTinstruction;
import interfaces.IASTvisitor;

public class ASTalternative implements IASTalternative {
	private IASTexpression condition;
	private IASTinstruction consequence;
	private IASTinstruction alternant;
	
	public ASTalternative(IASTexpression condition, IASTinstruction consequence, IASTinstruction alternant) {
		super();
		this.condition = condition;
		this.consequence = consequence;
		this.alternant = alternant;
	}

	@Override
	public IASTexpression getCondition() {
		return condition;
	}

	@Override
	public IASTinstruction getConsequence() {
		return consequence;
	}

	@Override
	public IASTinstruction getAlternant() {
		return alternant;
	}

	@Override
	public <Result, Data, Anomaly extends Throwable> Result accept(IASTvisitor<Result, Data, Anomaly> visitor,
			Data data) throws Anomaly {
		return visitor.visit(this, data);
	}

}
