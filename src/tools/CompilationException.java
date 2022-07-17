package tools;

import java.io.IOException;

public class CompilationException extends Exception {
	private static final long serialVersionUID = 1L;

	public CompilationException(IOException exc) {
		super(exc);
	}

	public CompilationException(String message) {
		super(message);
	}

}
