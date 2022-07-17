package ast;

import interfaces.IASTexpression;
import interfaces.IASTvariableAssignation;
import interfaces.IASTvisitor;

public class ASTvariableAssignation implements IASTvariableAssignation {
	private String var;
	private IASTexpression val;

	public ASTvariableAssignation(String var, IASTexpression val) {
		super();
		this.var = var;
		this.val = val;
	}

	@Override
	public String getVar() {
		return var;
	}

	@Override
	public IASTexpression getVal() {
		return val;
	}

	@Override
	public <Result, Data, Anomaly extends Throwable> Result accept(IASTvisitor<Result, Data, Anomaly> visitor,
			Data data) throws Anomaly {
		return visitor.visit(this, data);
	}
	
}
