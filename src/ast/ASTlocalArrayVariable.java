package ast;

import interfaces.IASTexpression;
import interfaces.IASTlocalArrayVariable;
import interfaces.IASTvisitor;

public class ASTlocalArrayVariable implements IASTlocalArrayVariable {
	private String var;
	private IASTexpression index;

	public ASTlocalArrayVariable(String var, IASTexpression index) {
		super();
		this.var = var;
		this.index = index;
	}

	@Override
	public <Result, Data, Anomaly extends Throwable> Result accept(IASTvisitor<Result, Data, Anomaly> visitor,
			Data data) throws Anomaly {
		return visitor.visit(this, data);
	}

	@Override
	public String getVar() {
		return var;
	}

	@Override
	public IASTexpression getIndex() {
		return index;
	}

}
