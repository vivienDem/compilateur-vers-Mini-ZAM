package interfaces;

public interface IASTarrayAssignation extends IASTinstruction {
	String getVar();
	IASTexpression getIndex();
	IASTexpression getValue();
}
