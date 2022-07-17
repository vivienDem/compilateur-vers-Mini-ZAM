package interfaces;

public interface IASTlocalArrayVariable extends IASTexpression {
	String getVar();
	IASTexpression getIndex();
}
