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
    private void calculate() throws Exception{
        StringTokenizer braker = new StringTokenizer (exp, "+-*/^()", true);
        Pilha<String> operators = new Pilha<>(exp.length());
        Fila<String> output = new Fila<>(exp.length());
        String token = braker.nextToken();

        if(token=="(")
            operators.guarde(token);
        else if (token.matches("[0-9]"))
                output.guarde(token);
        else if (isOperator(token.charAt(0))){

        }

    }

    private boolean isOperator(Character c){
        if(!(Character.toString(c).matches("[0-9]") ||
                c.equals('+') || c.equals('-') ||
                c.equals('*') || c.equals('/') ||
                c.equals('^') || c.equals('(') || c.equals(')')))
            return false;
        return true;
    }

    private boolean isValid(String input){
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

    public float getResult(){
        return this.result;
    }
}
