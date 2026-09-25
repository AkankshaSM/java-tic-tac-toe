# Java Tic-Tac-Toe

Java 17 command-line game with **human vs computer** and **local two-player** modes. No production dependencies. The computer uses deterministic exhaustive minimax, choosing a forced win when possible and otherwise avoiding defeat with optimal play.

## Build, test, and play
Requires JDK 17 and Maven 3.6.3 or newer on PATH. First build needs internet access for Maven dependencies.

```sh
git clone https://github.com/AkankshaSM/java-tic-tac-toe.git
cd java-tic-tac-toe
mvn clean package
java -jar target/java-tic-tac-toe.jar
```

Run tests separately:
```sh
mvn test
```

`mvn clean package` also runs tests. Reports: `target/surefire-reports/`.

## Controls
- Select **1** for human vs computer: you are X, computer is O.
- Select **2** for two people sharing the terminal: Player 1 is X, Player 2 is O.
- Enter a square number **1–9**, then Enter, to place your mark.
- Invalid or occupied-square input does not consume your turn.
- Type **q** or **quit** at any prompt to exit; EOF also exits gracefully.
- After a win or draw, the mode menu returns so you can play again.

```text
 1 | 2 | 3
---+---+---
 4 | 5 | 6
---+---+---
 7 | 8 | 9
```

## Rules researched online
Standard Tic-Tac-Toe uses a 3×3 grid and two players marking X and O. Players alternate placing one mark in an empty square. Three matching marks horizontally, vertically, or diagonally win immediately. A full board without a winning line is a draw. This implementation uses X first.

Sources:
- [Tic Tac Toe Rules: A Complete Guide](https://lost-boy-entertainment.com/blogs/news/tic-tac-toe-rules) — grid, empty-square moves, alternating turns, winning directions, draws, and usual X-first convention.
- [National Museum of Mathematics: Alyssa Choi, Tic-Tac-Toe](https://momath.org/wp-content/uploads/2021/08/Alyssa-Choi-Tic-Tac-Toe.pdf) — introductory standard 3×3 rules and optimal-play draws. Only the standard game is implemented, not the paper's later variants.

## Design and tests
See [DESIGN.md](DESIGN.md) and [PLAN.md](PLAN.md). Deterministic JUnit 5 tests cover all eight winning lines, O victory, draw, invalid moves, terminal states, computer tactics, every possible human reply against the computer policy, and scripted CLI interactions for both modes.

## Execution status
Java/Maven execution was unavailable in the authoring environment. No passing build or test result is claimed; use the commands above to validate locally. Saved files are verified by reading them back from GitHub.
