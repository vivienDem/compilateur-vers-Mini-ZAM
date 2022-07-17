package interfaces;

public interface IASTvariableAssignation extends IASTinstruction {
	String getVar();
	IASTexpression getVal();
}
