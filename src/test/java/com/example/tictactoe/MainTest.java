package com.example.tictactoe;

import java.io.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    private String run(String script) throws IOException {
        StringWriter text = new StringWriter();
        Main.run(new BufferedReader(new StringReader(script)), new PrintWriter(text));
        return text.toString();
    }

    @Test void twoPlayerWinAndReplayMenu() throws IOException {
        String output = run("2\n1\n4\n2\n5\n3\nq\n");
        assertTrue(output.contains("Player 1 (X) wins!"));
        assertTrue(output.contains("Returning to mode menu"));
        assertTrue(output.contains("Goodbye!"));
    }

    @Test void computerRespondsAndInputErrorsAreRecoverable() throws IOException {
        String output = run("bad\n1\nno\n10\n1\n1\nq\n");
        assertTrue(output.contains("Please select 1 or 2."));
        assertTrue(output.contains("Please enter a number"));
        assertTrue(output.contains("Choose a square from 1 to 9."));
        assertTrue(output.contains("That square is occupied."));
        assertTrue(output.contains("Computer (O) played square 5."));
        assertTrue(output.contains("Goodbye!"));
    }

    @Test void eofAndQuitAreGraceful() throws IOException {
        for (String input : new String[]{"", " QUIT \n", "2\n", "1\nq\n"}) {
            assertTrue(run(input).contains("Goodbye!"));
        }
    }

    @Test void drawIsReported() throws IOException {
        assertTrue(run("2\n1\n2\n3\n5\n4\n6\n8\n7\n9\nq\n").contains("Draw!"));
    }
}
