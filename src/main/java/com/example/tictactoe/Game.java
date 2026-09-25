package com.example.tictactoe;

/** Rules independent of terminal I/O. Squares use 1-based numbering. */
public final class Game {
    private static final int[][] LINES = {
        {0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}
    };
    private final char[] board;
    private char turn;

    public Game() {
        board = new char[9];
        java.util.Arrays.fill(board, ' ');
        turn = 'X';
    }

    private Game(Game source) {
        board = source.board.clone();
        turn = source.turn;
    }

    public Game copy() { return new Game(this); }
    public char turn() { return turn; }
    public char at(int square) {
        checkSquare(square);
        return board[square - 1];
    }

    public char winner() {
        for (int[] line : LINES) {
            char mark = board[line[0]];
            if (mark != ' ' && mark == board[line[1]] && mark == board[line[2]]) return mark;
        }
        return ' ';
    }

    public boolean isDraw() {
        if (winner() != ' ') return false;
        for (char mark : board) if (mark == ' ') return false;
        return true;
    }

    public boolean isOver() { return winner() != ' ' || isDraw(); }

    public void play(int square) {
        if (isOver()) throw new IllegalStateException("The game has ended.");
        checkSquare(square);
        if (board[square - 1] != ' ') throw new IllegalArgumentException("That square is occupied.");
        board[square - 1] = turn;
        turn = turn == 'X' ? 'O' : 'X';
    }

    private static void checkSquare(int square) {
        if (square < 1 || square > 9) throw new IllegalArgumentException("Choose a square from 1 to 9.");
    }
}
