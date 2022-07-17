package interfaces;

public interface IASTinvocation extends IASTexpression {
	String getFun();
	IASTexpression[] getArgs();
}
