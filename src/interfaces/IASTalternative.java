package interfaces;

public interface IASTalternative extends IASTinstruction {
	IASTexpression getCondition();
	IASTinstruction getConsequence();
	IASTinstruction getAlternant();
}
