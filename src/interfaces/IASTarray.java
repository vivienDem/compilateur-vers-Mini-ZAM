package interfaces;

public interface IASTarray extends IASTexpression {
	IASTexpression[] getContent();
	int getLength();
}
