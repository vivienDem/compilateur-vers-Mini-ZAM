package main;

import java.io.FileWriter;
import java.io.IOException;

import ast.ASTfactory;
import compiler.Compiler;
import interfaces.IASTfactory;
import interfaces.IASTprogram;
import parser.Input;
import parser.Parser;
import tools.CompilationException;
import tools.ParseException;

public class Main {

	public static void main(String[] args) {
		if (args.length < 1) {
			System.err.println("usage : java -jar compiler.jar <input file> <output file>");
			System.exit(1);
		}
		try {
			Input input = new Input(args[0]);
			IASTfactory factory = new ASTfactory();
			Parser parser = new Parser(factory, input);
			IASTprogram program = parser.getProgram();
			Compiler compiler = new Compiler(factory);
			String code = compiler.compile(program);
			FileWriter fw = null;
			if (args.length >= 2) {
				try {
					fw = new FileWriter(args[1]);
					fw.write(code);
				}
				catch (IOException e) {
					System.err.println("An error occured during compilation : " + e.getMessage());
				    }
				finally {
					fw.close();
				}
			}
			
			else {
				System.out.println(code);
			}
			
		} catch (IOException | ParseException | CompilationException e) {
			System.err.println("An error occured during compilation : " + e.getMessage());
			System.exit(2);
		}
	}

}
