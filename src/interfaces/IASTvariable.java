package interfaces;

public interface IASTvariable {
	IASTexpression getValue();
	String getName();
	void setValue(IASTexpression value);
}
