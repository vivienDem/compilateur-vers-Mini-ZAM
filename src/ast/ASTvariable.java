package ast;

import interfaces.IASTexpression;
import interfaces.IASTvariable;

public class ASTvariable implements IASTvariable {
	private IASTexpression value;
	private String name;
	private int size;
	
	public ASTvariable(String name, IASTexpression value) {
		this(name, value, 0);
	}
	
	public ASTvariable(String name, IASTexpression value, int size) {
		this.name = name;
		this.value = value;
		this.size = size;
	}

	@Override
	public IASTexpression getValue() {
		return value;
	}

	@Override
	public String getName() {
		return name;
	}
	@Override
	public void setValue(IASTexpression value) {
		this.value = value;
	}
}
