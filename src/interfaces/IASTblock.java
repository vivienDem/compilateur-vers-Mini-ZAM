package interfaces;

public interface IASTblock extends IASTinstruction {
	String getVar();
	IASTexpression getVal();
	IASTinstruction getBody();
}
