package com.rodrigosoares;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            //Getting the expression
            Scanner scanner = new Scanner(System.in);
            Expression expression = new Expression(scanner.nextLine());
            Expression expression1 = new Expression(expression);

            if(expression.equals(expression1))
                System.out.println(expression.getResult()+"\n"+expression.hashCode());


        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
