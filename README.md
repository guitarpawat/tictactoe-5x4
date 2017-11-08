# About this Project
This project is the part of the Abstract Data Type and Problem Solving at Kasetsart University.

## How does it work?
This project is a Tic Tag Toe game with 5 rows and 4 columns. To win the game, you must place four of your marks in a horizontal, vertical, or diagonal row. There are four modes in the game, Player vs Player, Player vs Computer (Player first), Computer vs Player (Computer first) and, Watch computer play. I created a AI for calculate the computer move by using a game tree with minimax and alpha-beta algorithm. At the first move of each computer, they will randomly put their symbol to the board. The second to the third  round, it will calculate the possible of next 7 symbols to choose the best move. At the fourth calculate the next 8 symbols, the fifth round look for next 9 symbols and sixth round afterward, they look to the end of the game.

### Scoring
There are simple rules, if it is positive score, your side wins. If it is negative, you lose. And zero means draw. There are two parts of the scoring, absolutely win and evaluate method.

If you will win the board, the calculate result score is `Integer.MAX_VALUE-(1000-depth).` If the tree search makes a dig deeper, you will win slower, this score thinking method will motivate the AI to finish early. Same as the losing score, `Integer.MIN_VALUE+(1000-depth)`, AI will be prevent doing thing to lose faster for more chance to win or draw if opponent makes a mistake.

Because the estimated all possible ways of this game is 20! so, it is impossible for home PC or notebook to calculate the best of all possible ways. They can only calculate for a certain depth and approximately calculate the score by evaluate method. This is some of sketchily scoring rules.
* 2 same symbols horizontal, vertical, or diagonal row and others are the blank. Get ±10.
* 3 same symbols horizontal, vertical, or diagonal row and others are the blank. Get ±50.
* 3 of 5 same symbols vertical row in the center and others are the blank. Get ±100.
* There are some special rules that had not list in here.

### Bugs or Limitations
Because the speed limit of the computer, it cannot compute perfectly and I cannot find the perfect rule for evaluate method so, this AI cannot do the best, most of all game are draw, not win the game but, it does not having any losing history. So, there is currently no perfect for winning strategy for this 5x4 board Tic Tag Toe game known.

## Classes
There are 3 classes in this project.
1. `TicTacToe` – The UI class of this project, it will receive input from the user and show the board result.
1. `Board` – Contains board information. It can be created a custom row and column number and check if it is winning, allow and deny putting their symbol and get the current playing side. Also, evaluate the score of the board and choose next move.
1. `StdDraw` – The drawing driver of this project.

## Compiling
Compile to class file:

`javac TicTacToe.java StdDraw.java Board.java GameTree.java`

Run the program:

`java TicTacToe`
