grammar NameListWithParallelAssign;

stat     : list EOF | assign EOF ;
assign   : list '=' list ;
list     : '[' elements ']' ;
elements : element (',' element)* ;
element  : NAME '=' NAME
         | NAME
         | list
         ;
NAME     : ('a'..'z'|'A'..'Z')+ ;
