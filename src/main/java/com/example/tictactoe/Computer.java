package com.example.tictactoe;

/** Exhaustive minimax; equal-scoring moves use the lowest square number. */
public final class Computer {
    private Computer() { }

    public static int chooseMove(Game game) {
        if (game.isOver()) throw new IllegalStateException("The game has ended.");
        char mark = game.turn();
        int bestScore = Integer.MIN_VALUE;
        int bestMove = -1;
        for (int square = 1; square <= 9; square++) {
            if (game.at(square) != ' ') continue;
            Game next = game.copy();
            next.play(square);
            int score = score(next, mark, 1);
            if (score > bestScore) {
                bestScore = score;
                bestMove = square;
            }
        }
        return bestMove;
    }

    private static int score(Game game, char computer, int depth) {
        if (game.winner() != ' ') return game.winner() == computer ? 10 - depth : depth - 10;
        if (game.isDraw()) return 0;
        boolean maximize = game.turn() == computer;
        int best = maximize ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (int square = 1; square <= 9; square++) {
            if (game.at(square) != ' ') continue;
            Game next = game.copy();
            next.play(square);
            int value = score(next, computer, depth + 1);
            best = maximize ? Math.max(best, value) : Math.min(best, value);
        }
        return best;
    }
}
