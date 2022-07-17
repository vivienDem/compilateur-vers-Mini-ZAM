package ast;

import interfaces.IASTfunctionDefinition;
import interfaces.IASTlocalVariable;
import interfaces.IASTsequence;
import interfaces.IASTvisitor;

public class ASTfunctionDefinition implements IASTfunctionDefinition {
	private String name;
	private IASTlocalVariable[] vars;
	private IASTsequence body;

	public ASTfunctionDefinition(String name, IASTlocalVariable[] vars, IASTsequence body) {
		super();
		this.name = name;
		this.vars = vars;
		this.body = body;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public IASTlocalVariable[] getVars() {
		return vars;
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
