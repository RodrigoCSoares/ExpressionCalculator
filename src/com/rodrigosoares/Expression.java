package com.rodrigosoares;

import java.util.StringTokenizer;

import static java.lang.Double.valueOf;

public class Expression implements Comparable {
    private String expression;
    private double result; //Saves the result of the math expression
    private Fila<String> pfNotation; //Saves the postfix notation

    //CONSTRUCTORS
    public Expression(String userInput) throws Exception{
        this.setExpression(userInput);
    }

    public Expression(Expression model) throws Exception{
        if (model == null)
            throw new Exception("Modelo inválido!");

        this.expression = model.expression;
        this.result = model.result;
        this.pfNotation = model.pfNotation;
    }

    //CLASS METHODS
    private void convert() throws Exception{       //Convert to postfix notation the expression
        try {
            StringTokenizer braker = new StringTokenizer(expression, "+-*/^()", true);
            Pilha<String> operators = new Pilha<>(expression.length());
            this.pfNotation = new Fila<>(expression.length());
            String token;

            while (braker.hasMoreTokens()) {           //Check if has any tokens left
                token = braker.nextToken();
                if (token.charAt(0) == '(')            //Token goes directly to the operators if it is '('
                    operators.guarde(token);

                else if (isNumeric(token))             //Token goes directly to the operators if it is a number
                    this.pfNotation.guarde(token);

                else if (token.charAt(0) == ')') {
                    //if (operators.vazia()) throw new Exception("Operação inválida");

                    while (operators.getItem().charAt(0) != '(') {
                        //The algorithm send all operators to the pfNotation while '(' was not found
                        this.pfNotation.guarde(operators.getItem());
                        operators.retira();
                        //if (operators.vazia()) throw new Exception("Operação inválida!");
                    }

                    operators.retira();
                } else if (isOperator(token.charAt(0))) {
                    if (operators.vazia()) {
                        //If operators is empty there is nothing to check, so token goes to operators
                        if (pfNotation.vazia()) throw new Exception("Operação inválida!");
                        operators.guarde(token);
                    } else {
                        while (!operators.vazia() && OperatorsTable.check(token.charAt(0), operators.getItem().charAt(0))) {
                            //Before put a new token at operators the algorithm check if the last token and
                            //the new one is true at the table of operators precedence
                            this.pfNotation.guarde(operators.getItem());
                            operators.retira();
                        }

                        operators.guarde(token);
                    }
                }
            }

            while (!operators.vazia()) {
                //After all tokens processed, if there is any operator left the algorithm send it to the pfNotation
                if (operators.getItem().charAt(0) == '(')
                    throw new Exception("Operação inválida!");
                this.pfNotation.guarde(operators.getItem());
                operators.retira();
            }
        }catch (Exception e){
            throw new Exception("Operação inválida!");
        }
    }

    private void calculate() throws Exception{ //Calculates the result of the postfix expression
        try {
            double v1, v2; //Value 1 and Value 2
            char op;       //Math operation
            Pilha<String> output = new Pilha<>(pfNotation.getQuantosElementos());

            while (!pfNotation.vazia()) { //The method ends only when the pfNotation is empty
                while (isNumeric(pfNotation.getItem())) {
                    output.guarde(pfNotation.getItem());
                    pfNotation.retira();
                }

                //Get the values of the next operation
                if (output.getQuantosElementos() >= 2 || pfNotation.getQuantosElementos() > 1) {
                    op = pfNotation.getItem().charAt(0);
                    pfNotation.retira();
                    v2 = Double.parseDouble(output.getItem());
                    output.retira();
                    v1 = Double.parseDouble(output.getItem());
                    output.retira();
                } else
                    throw new Exception("Operação inválida!");

                //Save the value of the operation
                output.guarde(resultOf(v1, op, v2).toString());
            }

            //Save the result of the math expression
            result = Double.parseDouble(output.getItem());
        }catch (Exception e){
            throw new Exception("Operação inválida!");
        }
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

    //SETTER AND GETTERS
    public void setExpression(String userInput) throws Exception{
        if(!isValid(userInput))
            throw new Exception("Operação inválida");
        else {
            this.expression = userInput.replaceAll("\\s", "");
            this.convert();
            this.calculate();
        }
    }

    public double getResult(){
        return this.result;
    }

    //REQUIRED METHODS
    public boolean equals (Expression otherExpression){
        if(this.result==otherExpression.result)
            return true;
        else
            return false;
    }

    public String toString(){
        return this.expression;
    }

    public int hashCode(){
        int ret = 9;

        ret = ret*7 + this.expression.hashCode();
        ret = ret*7 + valueOf(this.result).hashCode();
        ret = ret*7 + pfNotation.hashCode();

        return ret;
    }

    public int compareTo(Object o) {//Tirar dúvida com o professor sobre como comparar com um Object
        return 0;
    }
}
