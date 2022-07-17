package ast;

import interfaces.IASTexpression;
import interfaces.IASTinvocation;
import interfaces.IASTvisitor;

public class ASTinvocation implements IASTinvocation {
	private String fun;
	private IASTexpression[] args;

	public ASTinvocation(String fun, IASTexpression[] args) {
		super();
		this.fun = fun;
		this.args = args;
	}

	@Override
	public <Result, Data, Anomaly extends Throwable> Result accept(IASTvisitor<Result, Data, Anomaly> visitor,
			Data data) throws Anomaly {
		return visitor.visit(this, data);
	}

	@Override
	public IASTexpression[] getArgs() {
		return args;
	}

	@Override
	public String getFun() {
		return fun;
	}

}
