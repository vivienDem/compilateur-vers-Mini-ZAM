package parser;


import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import grammar.grammarCALexer;
import grammar.grammarCAParser;
import interfaces.IASTfactory;
import interfaces.IASTprogram;
import tools.ParseException;

public class Parser {
	private IASTfactory factory;
	private Input input;

	public Parser(IASTfactory factory) {
		super();
		this.factory = factory;
	}
	
	public Parser(IASTfactory factory, Input input) {
		super();
		this.factory = factory;
		this.input = input;
	}

	public void setInput(Input input) {
		this.input = input;
	}
	
	public IASTprogram getProgram() throws ParseException {
		try {
			ANTLRInputStream in = new ANTLRInputStream(input.getText());
			grammarCALexer lexer = new grammarCALexer(in);
			CommonTokenStream tokens =	new CommonTokenStream(lexer);
			grammarCAParser parser = new grammarCAParser(tokens);
			grammarCAParser.ProgContext tree = parser.prog();
			ParseTreeWalker walker = new ParseTreeWalker();
			Listener extractor = new Listener(factory);
			walker.walk(extractor, tree);
			return tree.node;
		}
		catch (Exception e) {
			throw new ParseException(e);
		}
	}
}
