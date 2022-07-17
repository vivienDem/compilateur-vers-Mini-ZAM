package ast;

import interfaces.IASTarrayAssignation;
import interfaces.IASTexpression;
import interfaces.IASTvisitor;

public class ASTarrayAssignation implements IASTarrayAssignation {
	private String var;
	private IASTexpression index;
	private IASTexpression value;

	public ASTarrayAssignation(String var, IASTexpression index, IASTexpression value) {
		super();
		this.var = var;
		this.index = index;
		this.value = value;
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

	@Override
	public IASTexpression getValue() {
		return value;
	}

}
