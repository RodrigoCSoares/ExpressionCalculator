package com.rodrigosoares;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            //Recebendo a expressão + retirando os whitespaces
            //Scanner scanner = new Scanner(System.in);
            //Expression expression = new Expression(scanner.nextLine());
            OperatorsTable table = new OperatorsTable();

            System.out.println(table.toString());

            System.out.println("+ e *: "+table.check('+','*'));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
