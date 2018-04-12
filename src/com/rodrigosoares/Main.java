package com.rodrigosoares;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            //Recebendo a expressão
            Scanner scanner = new Scanner(System.in);
            Expression expression = new Expression(scanner.nextLine());

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
