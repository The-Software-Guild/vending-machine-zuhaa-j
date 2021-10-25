package com.mthree.c130.zuhaa.ui;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserIOConsoleImpl implements UserIO {


    Scanner input = new Scanner(System.in);

    public void print(String message) {
        System.out.println(message);
    }

    public String readString(String prompt) {
        Scanner userInput = new Scanner(System.in);
        System.out.println(prompt);
        String response = userInput.nextLine();
        return response;
    }

    public int readInt(String prompt) {
        Boolean hasErrors = true;
        int intOutput = 0;
        do {
            try {
                System.out.println(prompt);
                intOutput = input.nextInt();
                input.nextLine();
                hasErrors = false;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Try again.");
                input.nextLine();
            }
        } while (hasErrors);
        return intOutput;
    }

    public int readInt(String prompt, int min, int max) {
        int num = 0;
        Boolean valid = false;
        while (!valid) {
            try {
                System.out.println(prompt);
                num = input.nextInt();
                input.nextLine();
                if (num < min || num > max) {
                    System.out.println("Number out of range! Try again.");
                } else {
                    valid = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Try again.");
                input.nextLine();
            }
        }
        return num;
    }

    public double readDouble(String prompt) {
        System.out.println(prompt);
        return input.nextDouble();
    }

    public double readDouble(String prompt, double min, double max) {
        double num = 0;
        Boolean valid = false;
        while (!valid) {
            System.out.println(prompt);
            num = input.nextDouble();
            if (num < min || num > max) {
                System.out.println("Number out of range! Try again.");
            } else {
                valid = true;
            }
        }
        return num;
    }

    public float readFloat(String prompt) {
        System.out.println(prompt);
        return input.nextFloat();
    }

    public float readFloat(String prompt, float min, float max) {
        float num = 0;
        Boolean valid = false;
        while (!valid) {
            System.out.println(prompt);
            num = input.nextFloat();
            if (num < min || num > max) {
                System.out.println("Number out of range! Try again.");
            } else {
                valid = true;
            }
        }
        return num;
    }

    public long readLong(String prompt) {
        System.out.println(prompt);
        return input.nextLong();
    }

    public long readLong(String prompt, long min, long max) {
        long num = 0;
        Boolean valid = false;
        while (!valid) {
            System.out.println(prompt);
            num = input.nextLong();
            if (num < min || num > max) {
                System.out.println("Number out of range! Try again.");
            } else {
                valid = true;
            }
        }
        return num;
    }

    public LocalDate readDate(String prompt) {
        System.out.println(prompt);
        LocalDate date = LocalDate.parse(input.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return date;
    }

    public BigDecimal readBigDecimal(String prompt) {
        Scanner userInputBigDecimal = new Scanner(System.in);
        BigDecimal num = BigDecimal.ZERO;
        Boolean valid = false;
        while (!valid) {
            try {
                System.out.println(prompt);
                num = new BigDecimal(userInputBigDecimal.nextLine());
                valid = true;
            } catch (Exception e) {
                System.out.println("Invalid input. Try again.");
                System.out.println(prompt);
                userInputBigDecimal.nextLine();
            }
        }

        while (num.compareTo(BigDecimal.ZERO) < 0) {
            System.out.println("Number must be positive. Try again.");
            num = new BigDecimal(userInputBigDecimal.nextLine());
        }
        return num;
    }
}
