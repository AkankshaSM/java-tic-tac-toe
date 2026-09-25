package com.example.tictactoe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public final class Main {
    private Main() { }

    public static void main(String[] args) {
        try {
            run(new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8)),
                    new PrintWriter(System.out, true, StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.err.println("Unable to read input. Goodbye!");
        }
    }

    public static void run(BufferedReader input, PrintWriter output) throws IOException {
        output.println("Tic-Tac-Toe: three in a row, column, or diagonal wins. X goes first.");
        output.println("Type q or quit at any prompt to exit.");
        while (true) {
            output.println("1) Human vs computer (you are X)");
            output.println("2) Two players (Player 1 is X, Player 2 is O)");
            String mode = prompt(input, output, "Select mode: ");
            if (quit(mode)) break;
            if (!mode.equals("1") && !mode.equals("2")) {
                output.println("Please select 1 or 2.");
                continue;
            }
            boolean computer = mode.equals("1");
            Game game = new Game();
            display(game, output);
            while (!game.isOver()) {
                char mark = game.turn();
                int square;
                if (computer && mark == 'O') {
                    square = Computer.chooseMove(game);
                } else {
                    String move = prompt(input, output, label(mark, computer) + ", choose square 1-9: ");
                    if (quit(move)) {
                        output.println("Goodbye!");
                        output.flush();
                        return;
                    }
                    try {
                        square = Integer.parseInt(move);
                    } catch (NumberFormatException e) {
                        output.println("Please enter a number from 1 to 9.");
                        continue;
                    }
                }
                try {
                    game.play(square);
                } catch (IllegalArgumentException e) {
                    output.println(e.getMessage());
                    continue;
                }
                output.println(label(mark, computer) + " played square " + square + ".");
                display(game, output);
            }
            output.println(game.isDraw() ? "Draw!" : label(game.winner(), computer) + " wins!");
            output.println("Returning to mode menu for a new game.");
        }
        output.println("Goodbye!");
        output.flush();
    }

    private static String label(char mark, boolean computer) {
        return (computer ? (mark == 'X' ? "You" : "Computer")
                : (mark == 'X' ? "Player 1" : "Player 2")) + " (" + mark + ")";
    }

    private static String prompt(BufferedReader input, PrintWriter output, String text) throws IOException {
        output.print(text);
        output.flush();
        String line = input.readLine();
        return line == null ? null : line.strip();
    }

    private static boolean quit(String value) {
        return value == null || value.equalsIgnoreCase("q") || value.equalsIgnoreCase("quit");
    }

    private static void display(Game game, PrintWriter output) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                int square = row * 3 + col + 1;
                char mark = game.at(square);
                output.print(" " + (mark == ' ' ? Integer.toString(square) : Character.toString(mark)) + " ");
                if (col < 2) output.print("|");
            }
            output.println();
            if (row < 2) output.println("---+---+---");
        }
        output.flush();
    }
}
