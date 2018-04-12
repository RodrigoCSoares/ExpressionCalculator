package com.rodrigosoares;

import java.util.StringTokenizer;

public class Expression {
    private String exp;
    private float result;

    //CONSTRUCTORS
    public Expression(String userInput) throws Exception{
        if(!isValid(userInput))
            throw new Exception("Operação inválida");
        else {
            this.exp = userInput.replaceAll("\\s", "");
            this.calculate();
        }
    }

    //CLASS METHODS
    private void calculate() throws Exception{ //Calculates the expression
        StringTokenizer braker = new StringTokenizer (exp, "+-*/^()", true);
        Pilha<String> operators = new Pilha<>(exp.length());
        Fila<String> output = new Fila<>(exp.length());
        OperatorsTable table = new OperatorsTable();
        String token;

        while (braker.hasMoreTokens()) { //Check if has any tokens left
            token = braker.nextToken();
            if (token.charAt(0) == '(') //Token goes directly to the operators if it is '('
                operators.guarde(token);
            else if (isInteger(token)) //Token goes directly to the operators if it is a number
                output.guarde(token);
            else if (token.charAt(0) == ')') {
                while (operators.getItem().charAt(0) != '(') {
                    //The algorithm send all operators to the output while '(' was not found
                    output.guarde(operators.getItem());
                    operators.retira();
                    if (operators.vazia()) throw new Exception("Operação inválida!");
                }
                operators.retira();
            }
            else if (isOperator(token.charAt(0))) {
                if(operators.vazia())
                    //If operators is empty there is nothing to check, so token goes to operators
                    operators.guarde(token);
                else {
                    while (!operators.vazia() && table.check(token.charAt(0), operators.getItem().charAt(0))) {
                        //Before put a new token at operators the algorithm check if the last token and
                        //the new one is true at the table of operators precedence
                        output.guarde(operators.getItem());
                        operators.retira();
                    }
                    operators.guarde(token);
                }
            }
        }
        while (!operators.vazia()){
            //After all tokens processed, if there is any operator left the algorithm send it to the output
            if(operators.getItem().charAt(0)=='(')
                throw new Exception("Operação inválida!");
            output.guarde(operators.getItem());
            operators.retira();
        }
    }

    private boolean isOperator(Character c){ //Checks if c is a valid math operator
        if(!(Character.toString(c).matches("[0-9]") ||
                c.equals('+') || c.equals('-') ||
                c.equals('*') || c.equals('/') ||
                c.equals('^') || c.equals('(') || c.equals(')')))
            return false;
        return true;
    }

    private boolean isValid(String input){ //Checks if the expression is valid
        input=input.replaceAll("\\s","");
        if(input.length()==0)
            return false;

        for(int i=0; i<input.length(); i++){
            Character c = input.charAt(i);
            if(!isOperator(c))
                return false;
        }

        return true;
    }

    private boolean isInteger( String input )
    {
        try{
            Integer.parseInt( input );
            return true;
        }
        catch( Exception e){
            return false;
        }
    }

    public float getResult(){
        return this.result;
    }
}
