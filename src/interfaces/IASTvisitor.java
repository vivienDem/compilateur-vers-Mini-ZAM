package interfaces;

public interface IASTvisitor<Result, Data, Anomaly extends Throwable> {
	Result visit(IASTalternative iast, Data data) throws Anomaly;
	Result visit(IASTbang iast, Data data) throws Anomaly;
	Result visit(IASTbegin iast, Data data) throws Anomaly;
	Result visit(IASTbinaryOperation iast, Data data) throws Anomaly;
	Result visit(IASTblock iast, Data data) throws Anomaly;
	Result visit(IASTtrue iast, Data data) throws Anomaly;
	Result visit(IASTfalse iast, Data data) throws Anomaly;
	Result visit(IASTinteger iast, Data data) throws Anomaly;
	Result visit(IASTlocalVariable iast, Data data) throws Anomaly;
	Result visit(IASTloop iast, Data data) throws Anomaly;
	Result visit(IASTprint iast, Data data) throws Anomaly; 
	Result visit(IASTprogram iast, Data data) throws Anomaly;
	Result visit(IASTref iast, Data data) throws Anomaly;
	Result visit(IASTsequence iast, Data data) throws Anomaly;
	Result visit(IASTunaryOperation iast, Data data) throws Anomaly;
	Result visit(IASTvariableAssignation iast, Data data) throws Anomaly;
	Result visit(IASTreturn iast, Data data) throws Anomaly;
	Result visit(IASTinvocation iast, Data data) throws Anomaly;
	Result visit(IASTfunctionDefinition iast, Data data) throws Anomaly;
	Result visit(IASTcontinue iast, Data data) throws Anomaly;
	Result visit(IASTbreak iast, Data data) throws Anomaly;
	Result visit(IASTarrayAssignation iast, Data data) throws Anomaly;
	Result visit(IASTarrayLength iast, Data data) throws Anomaly;
	Result visit(IASTlocalArrayVariable iast, Data data) throws Anomaly;
	Result visit(IASTarray iast, Data data) throws Anomaly;
}
