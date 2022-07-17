package interfaces;

public interface IASTprogram extends IASTvisitable {
	IASTsequence getBody();
	IASTfunctionDefinition[] getFunctions();
}
