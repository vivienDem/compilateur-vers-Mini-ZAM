package interfaces;

public interface IASTbinaryOperation extends IASToperation {
	IASTexpression getLeftOperand();
	IASTexpression getRightOperand();
}
