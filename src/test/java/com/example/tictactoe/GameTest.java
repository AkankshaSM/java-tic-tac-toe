package com.example.tictactoe;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game moves(int... squares) {
        Game game = new Game();
        for (int square : squares) game.play(square);
        return game;
    }

    @Test void initialStateAndValidation() {
        Game game = new Game();
        assertEquals('X', game.turn());
        assertFalse(game.isOver());
        assertThrows(IllegalArgumentException.class, () -> game.play(0));
        assertThrows(IllegalArgumentException.class, () -> game.play(10));
        game.play(5);
        assertEquals('O', game.turn());
        assertThrows(IllegalArgumentException.class, () -> game.play(5));
        assertEquals('O', game.turn());
        assertEquals('X', game.at(5));
    }

    @Test void allEightWinningLines() {
        int[][] lines = {{1,2,3},{4,5,6},{7,8,9},{1,4,7},{2,5,8},{3,6,9},{1,5,9},{3,5,7}};
        for (int[] line : lines) {
            Game game = new Game();
            int[] other = java.util.stream.IntStream.rangeClosed(1, 9)
                    .filter(s -> s != line[0] && s != line[1] && s != line[2]).toArray();
            game.play(line[0]); game.play(other[0]);
            game.play(line[1]); game.play(other[1]); game.play(line[2]);
            assertEquals('X', game.winner());
            assertTrue(game.isOver());
            assertFalse(game.isDraw());
            assertThrows(IllegalStateException.class, () -> game.play(other[2]));
        }
    }

    @Test void oCanWinAndFullBoardCanDraw() {
        assertEquals('O', moves(1,4,2,5,9,6).winner());
        Game draw = moves(1,2,3,5,4,6,8,7,9);
        assertTrue(draw.isDraw());
        assertTrue(draw.isOver());
        assertEquals(' ', draw.winner());
    }

    @Test void computerWinsOrBlocksWithoutMutatingInput() {
        Game win = moves(1,4,2,5,9);
        assertEquals(6, Computer.chooseMove(win));
        assertEquals(' ', win.at(6));
        assertEquals('O', win.turn());
        assertEquals(3, Computer.chooseMove(moves(1,5,2)));
        assertThrows(IllegalStateException.class, () -> Computer.chooseMove(moves(1,4,2,5,3)));
    }

    @Test void computerNeverLosesAgainstAnyHumanMoveSequence() {
        explore(new Game());
    }

    private void explore(Game game) {
        assertNotEquals('X', game.winner(), "Computer must not lose");
        if (game.isOver()) return;
        if (game.turn() == 'O') {
            Game next = game.copy();
            next.play(Computer.chooseMove(game));
            explore(next);
        } else {
            for (int square = 1; square <= 9; square++) {
                if (game.at(square) != ' ') continue;
                Game next = game.copy();
                next.play(square);
                explore(next);
            }
        }
    }
}
