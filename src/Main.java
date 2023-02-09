/*
 * Developer: John Ketamine
 * Program: Watert
 *
 *
 * Dev Run Configuration: javac *.java && java Main easy
 * Jar Run Configuration: java -Xmx500M -jar Watert_v<version>.jar <easy, normal, hard>
 * Run Configuration: java Main <easy, normal, hard>
 * 
 *
 * Examples of Run Configuration:
 *       java Main
 *       java Main normal
 *       java -Xmx500M -jar Watert_v1_01.jar
 *       java -Xmx500M -jar Watert_v1_01.jar hard
 * 
 * 
 * NOTE: The "Dev Run Configuration" is to compile and 
 *       run the application for ease of use during
 *       development. If you have the compiled jar or
 *       classes, please use "Run Configuration."
 */

import java.util.Arrays;
import java.util.Scanner;
public class Main {
    // Class Variables
    static final Scanner input = new Scanner(System.in);
    static Difficulty difficulty;
    static Board b;

    public static void main(String... args) {
        if (args.length != 0) {
            if (!stringToDifficulty(args[0])) throw new IllegalArgumentException();
        } else {
            selectDifficulty(null);
        }

//        Board b = new Board(difficulty, false);
        b = new Board(difficulty, false);

        System.out.println(b.getBoardInfo() + "\n");
        System.out.println(b.getBoardAsString());
        movePlayer();

        do {
            clear();
            System.out.println(b.getPlayerStats());
            System.out.println(b.getBoardAsString());

            movePlayer();
        } while(!b.isGameOver() && !b.isGameComplete());
    }

    static boolean stringToDifficulty(String s) {
        switch(s.toLowerCase()) {
            case "easy":
                selectDifficulty(Difficulty.EASY);
                return true;
            case "normal":
                selectDifficulty(Difficulty.NORMAL);
                return true;
            case "hard":
                selectDifficulty(Difficulty.HARD);
                return true;
            default:
                return false;
        }
    }

    static void selectDifficulty(Difficulty d) {
        if (d != null) {
            difficulty = d;
            return;
        }

        String str;
        loop:
        while (true) {
            System.out.print("Easy, Normal, or Hard » ");
            str = input.nextLine();
            switch (str.toLowerCase()) {
                case "easy":
                    difficulty = Difficulty.EASY;
                    break loop;
                case "normal":
                    difficulty = Difficulty.NORMAL;
                    break loop;
                case "hard":
                    difficulty = Difficulty.HARD;
                    break loop;
                default:
                    System.out.println("Invalid Input");
                    break;
            }
        }
    }

    static void movePlayer() {
        String str;

        while (true) {
            try {
                System.out.print("Enter W, A, S, or D to move » " + Colors.YELLOW);
                str = input.nextLine().toLowerCase();
                System.out.print(Colors.RESET);

                if (str.equalsIgnoreCase("stop")) {
                    exit();
                } else if (Arrays.asList('w', 'a', 's', 'd').contains(str.charAt(0))) {
                    if (!b.movePlayer(str.charAt(0))) {
                        System.out.println("You can't go in this direction!");
                    } else {
                        break;
                    }
                } else {
                    System.out.println("Invalid Input");
                }
            } catch (Exception e) {
                System.out.println("You must input a character!");
            }

        }
    }

    static void enterToContinue() {
        System.out.println(Colors.YELLOW + "<Press 'Enter' to Continue>" + Colors.RESET);
        Main.input.nextLine();
    }

    static void exit() {
        System.exit(130);
    }

    static void clear() {
        System.out.println("\n\n\n\n\n\n\n");
        // System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
