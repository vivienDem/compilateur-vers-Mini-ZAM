package ast;

import interfaces.IASTfunctionDefinition;
import interfaces.IASTprogram;
import interfaces.IASTsequence;
import interfaces.IASTvisitor;

public class ASTprogram implements IASTprogram {
	private IASTsequence body;
	private IASTfunctionDefinition[] functions;
	
	public ASTprogram(IASTsequence body, IASTfunctionDefinition[] functions) {
		super();
		this.body = body;
		this.functions = functions;
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

	@Override
	public IASTfunctionDefinition[] getFunctions() {
		return functions;
	}

}
