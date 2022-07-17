package ast;

import interfaces.IASToperation;

public abstract class ASToperation implements IASToperation {
	private String operator;

	public ASToperation(String operator) {
		super();
		if (operator.equals("==")) {
			this.operator = "=";
		}
		else {
			this.operator = operator;
		}
	}

	@Override
	public String getOperator() {
		return operator;
	}

}
