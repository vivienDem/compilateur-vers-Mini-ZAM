package interfaces;

public interface IASTsequence extends IASTvisitable {
	IASTinstruction getInstruction();
	IASTsequence getNextSequence();
}
