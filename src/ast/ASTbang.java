package ast;

import interfaces.IASTbang;
import interfaces.IASTvisitor;

public class ASTbang implements IASTbang {
	private String var;
	
	public ASTbang(String var) {
		super();
		this.var = var;
	}

	@Override
	public String getVar() {
		return var;
	}

	@Override
	public <Result, Data, Anomaly extends Throwable> Result accept(IASTvisitor<Result, Data, Anomaly> visitor,
			Data data) throws Anomaly {
		return visitor.visit(this, data);
	}

}
