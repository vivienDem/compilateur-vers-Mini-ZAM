package ast;

import interfaces.IASTlocalVariable;
import interfaces.IASTvisitor;

public class ASTlocalVariable implements IASTlocalVariable {
	private String name;

	public ASTlocalVariable(String name) {
		super();
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public <Result, Data, Anomaly extends Throwable> Result accept(IASTvisitor<Result, Data, Anomaly> visitor,
			Data data) throws Anomaly {
		return visitor.visit(this, data);
	}
	
}
