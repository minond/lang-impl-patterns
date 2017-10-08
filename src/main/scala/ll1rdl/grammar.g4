grammar NestedNameGrammar;

list     : '[' element ']' ;
elements : element (',' element)* ;
element  : NAME | list ;
NAME     : ('a'..'z'|'A'..'Z')+ ;
