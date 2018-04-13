package com.rodrigosoares;

import java.util.StringTokenizer;

public class Expression {
    private String exp;
    private double result; //Saves the result of the math expression
    private Fila<String> pfNotation; //Saves the postfix notation

    //CONSTRUCTORS
    public Expression(String userInput) throws Exception{
        if(!isValid(userInput))
            throw new Exception("Operação inválida");
        else {
            this.exp = userInput.replaceAll("\\s", "");
            this.convert();
            this.calculate();
        }
    }

    //CLASS METHODS
    private void convert() throws Exception{       //Convert to postfix notation the expression
        StringTokenizer braker = new StringTokenizer (exp, "+-*/^()", true);
        Pilha<String>operators = new Pilha<>(exp.length());
        this.pfNotation = new Fila<>(exp.length());
        OperatorsTable table = new OperatorsTable();
        String token;

        while (braker.hasMoreTokens()) {           //Check if has any tokens left
            token = braker.nextToken();
            if (token.charAt(0) == '(')            //Token goes directly to the operators if it is '('
                operators.guarde(token);

            else if (isNumeric(token))             //Token goes directly to the operators if it is a number
                this.pfNotation.guarde(token);

            else if (token.charAt(0) == ')') {
                while (operators.getItem().charAt(0) != '(') {
                    //The algorithm send all operators to the pfNotation while '(' was not found
                    this.pfNotation.guarde(operators.getItem());
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
                        this.pfNotation.guarde(operators.getItem());
                        operators.retira();
                    }

                    operators.guarde(token);
                }
            }
        }

        while (!operators.vazia()){
            //After all tokens processed, if there is any operator left the algorithm send it to the pfNotation
            if(operators.getItem().charAt(0)=='(')
                throw new Exception("Operação inválida!");
            this.pfNotation.guarde(operators.getItem());
            operators.retira();
        }
    }

    private void calculate() throws Exception{ //Calculates the result of the postfix expression
        double v1, v2; //Value 1 and Value 2
        char op;       //Math operation
        Pilha<String> output = new Pilha<>(pfNotation.getQuantosElementos());

        while (!pfNotation.vazia()) { //The method ends only when the pfNotation is empty
            while (isNumeric(pfNotation.getItem())) {
                output.guarde(pfNotation.getItem());
                pfNotation.retira();
            }

            //Get the values of the next operation
            op=pfNotation.getItem().charAt(0);
            pfNotation.retira();
            v2=Double.parseDouble(output.getItem());
            output.retira();
            v1=Double.parseDouble(output.getItem());
            output.retira();

            //Save the value of the operation
            output.guarde(resultOf(v1,op,v2).toString());
        }

        //Save the result of the math expression
        result = Double.parseDouble(output.getItem());
    }

    private Double resultOf(double v1, char op, double v2) throws Exception{
        switch (op){
            case '+':
                return v1+v2;
            case '-':
                return v1-v2;
            case '*':
                return v1*v2;
            case '/':
                return v1/v2;
            case '^':
                return Math.pow(v1,v2);
        }

        throw new Exception("Operação inválida");
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

    private boolean isNumeric( String input ){
        try{
            Double.parseDouble( input );
            return true;
        }
        catch( Exception e){
            return false;
        }
    }

    public double getResult(){
        return this.result;
    }
}
