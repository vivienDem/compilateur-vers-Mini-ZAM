grammar grammarCA;

@header {
	package grammar;  	
}

prog returns [interfaces.IASTprogram node]
	: (defs+=globalFunDef ';'?)* body =  seq  EOF
	;
	
instr returns [interfaces.IASTinstruction node]
	: 'begin' sequence=seq 'end' # Begin
	| 'return' value=expr # Return 
	| 'print' val=expr # Print
	| 'let' var=IDENT '=' val=expr 'in' body=instr # Binding
	| var=IDENT'['index=expr']' ':=' val=expr # ArrayAssign
	| var=IDENT ':=' val=expr # VariableAssign
	| 'if' condition=expr 'then' consequence=instr ('else' alternant=instr)?  # Alternative
	| 'while' condition=expr 'do' consequence=seq 'done'  # Loop
	| 'break' # Break
	| 'continue' # Continue
	;

seq returns [interfaces.IASTsequence node]
	: instruction=instr 
	|  instruction=instr  ';' sequence=seq
	;
	
expr returns [interfaces.IASTexpression node]
	: fun=IDENT '(' args+=expr? (',' args+=expr)* ')' # Invocation
	| value=INT # ConstInt
	| 'true' # ConstTrue
	| 'false' # ConstFalse
	| var=IDENT '[' index=expr ']' # LocalArrayVariable
	| var=IDENT # LocalVariable
	| '(length' array=expr')' # ArrayLength
	| '{'content+=expr? (',' content+=expr)*'}' #Array
	| operator='not'  '('operand=expr')' # UnaryApplication
	| operator='not'  operand=expr # UnaryApplication
	| '('operand1=expr operator=('*' | '/' | '+' | '-' | '<' | '<=' | '>' | '>=' | '==' | '!=')  operand2=expr')' # BinaryApplication
	| operand1=expr operator=('*' | '/' )  operand2=expr # BinaryApplication
	| operand1=expr operator=('+' | '-')  operand2=expr  # BinaryApplication
	| operand1=expr operator=('<' | '<=' | '>' | '>=')  operand2=expr # BinaryApplication
	| operand1=expr operator=('==' | '!=')  operand2=expr  # BinaryApplication
	| '(ref' ref=expr ')' # Reference
	| '(!' var=IDENT ')' # Bang
	;
	
	globalFunDef returns [interfaces.IASTfunctionDefinition node]
    : 'fun' name=IDENT '(' vars+=IDENT? (',' vars+=IDENT)* ')'
        body=seq
    ;
	
	
	
	
	IDENT : [a-zA-Z_] [a-zA-Z0-9_]* ;
	INT : [0-9]+ ;
	SPACE : [ \t\r\n]+ -> skip;
	SKIP : 'skip' ';'? -> skip;
	LINE_COMMENT : '//' (~[\r\n])* -> skip;
	COMMENT : '/*' ('*' ~[/] | ~[*])* '*/' -> skip;
	