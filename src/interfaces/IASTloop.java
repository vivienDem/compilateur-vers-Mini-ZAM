package interfaces;

public interface IASTloop extends IASTinstruction {
	IASTexpression getCondition();
	IASTsequence getBody();
}
