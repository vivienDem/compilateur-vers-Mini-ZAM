package interfaces;

public interface IASTfunctionDefinition extends IASTvisitable {
	String getName();
	IASTlocalVariable[] getVars();
	IASTsequence getBody();
}
