package ast;

import interfaces.IASTblock;
import interfaces.IASTexpression;
import interfaces.IASTinstruction;
import interfaces.IASTvisitor;

public class ASTblock implements IASTblock {
	private String var;
	private IASTexpression val;
	private IASTinstruction body;
	
	public ASTblock(String var, IASTexpression val, IASTinstruction body) {
		super();
		this.var = var;
		this.val = val;
		this.body = body;
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
	public IASTinstruction getBody() {
		return body;
	}

	@Override
	public <Result, Data, Anomaly extends Throwable> Result accept(IASTvisitor<Result, Data, Anomaly> visitor,
			Data data) throws Anomaly {
		return visitor.visit(this, data);
	}

}
