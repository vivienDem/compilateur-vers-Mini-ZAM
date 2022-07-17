package compiler.interfaces;

import interfaces.IASTexpression;
import interfaces.IASTvariable;
import tools.CompilationException;

public interface Environment {
	void push(String name);
	void push(String name, IASTexpression value);
	IASTvariable pop(int n);
	IASTvariable pop();
	void remove(String name);
	int getIndex(String name);
	int getEnvIndex(String name);
	void addtoEnv(String name);
	void clearEnv();
	void increaseSp();
	void decreaseSp();
	void increaseSp(int n);
	void decreaseSp(int n);
	void setSp(int sp);
	int getSp();
	String getFunctionLabel(String function_name);
	void addFunction(String function_name, String label) throws CompilationException;
	int getLast_function_nb_args();
	void setLast_function_nb_args(int last_function_nb_args);
	String getLoop_beginning_label();
	void setLoop_beginning_label(String loop_beginning_label);
	String getLoop_ending_label();
	void setLoop_ending_label(String loop_ending_label);
	void increase_nb_args_to_pop_before_return();
	void reset_nb_args_to_pop_before_return();
	int getNb_args_to_pop_before_return();
	void setRenaming(boolean renaming);
	
}
