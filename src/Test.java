
import java.awt.Font;



/** The three-in-a-row game for two human players. */
public class Test {
    
    private static boolean computerMove = false;
    private static Board board = new Board();
    private static int count = 0;

    /**
     * Draws the state of board, including instructions or a game over message.
     */
    public static void draw() {
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.line(0.5, -0.5, 0.5, 4.5);
        StdDraw.line(1.5, -0.5, 1.5, 4.5);
        StdDraw.line(2.5, -0.5, 2.5, 4.5);
        StdDraw.line(-0.5, 0.5, 3.5, 0.5);
        StdDraw.line(-0.5, 1.5, 3.5, 1.5);
        StdDraw.line(-0.5, 2.5, 3.5, 2.5);
        StdDraw.line(-0.5, 3.5, 3.5, 3.5);
        char[][] boardArray = board.getBoard();
        for (int x = 0; x < boardArray.length; x++) {
            for (int y = 0; y < boardArray[x].length; y++) {
                StdDraw.text(x, y, boardArray[x][y] + "");
            }
        }
        StdDraw.setPenColor(StdDraw.YELLOW);
        if(board.isOver()) {
            if(board.isWin()) StdDraw.text(1.5, -0.5, "The winner is "+board.getSide()+"!");
            else StdDraw.text(1.5, -0.5, "Draw Game!");
        }
        else StdDraw.text(1.5, -0.5, board.getSide() + " to play. Click in a square.");
        StdDraw.show(0);
        StdDraw.setPenColor(StdDraw.WHITE);
    }

    /**
     * Handles a mouse click, placing player's mark in the square on which the
     * user clicks.
     */
    public static void handleMouseClick() {
        if(computerMove) {
            if(count <= 1) {
//                int x,y;
//                do {
//                    Random ran = new Random();
//                    x = ran.nextInt(4);
//                    y = ran.nextInt(5);
//                }while(!board.put(x,y));
            }
            else {
                if(count <= 3) {
                    board.chooseMove(board.getSide(),7);
                }
                else if(count <= 4) {
                    board.chooseMove(board.getSide(),8);
                }
                else if(count <= 5) {
                    board.chooseMove(board.getSide(),9);
                }
                else {
                    board.chooseMove(board.getSide(),999);
                }
            }
        }
        else {
            while (!StdDraw.mousePressed()) {
            // Wait for mouse press
            }
        
            double x = Math.round(StdDraw.mouseX());
            double y = Math.round(StdDraw.mouseY());
            while (StdDraw.mousePressed()) {
                // Wait for mouse release
            }
            int a = (int) x;
            int b = (int) y;
            board.put(a,b);
        }
    }

    

    /**
     * Plays the game.
     */
    public static void main(String[] args) {
        init();
        int round = 1;
        int draw=0,X=0,O=0;
        for(int i=0; i<4; i++){
            for(int j=0; j<5; j++) {
                for(int m=0; m<4; m++) {
                    for(int n=0; n<5; n++) {
                        System.out.printf("(X[%d,%d] , Y[%d,%d]) -> ",i,j,m,n);
                        if(i==m && j==n) {
                            System.out.println("Round "+round+" : Can't place.");
                            round++;
                            continue;
                        }
                        board = new Board();
                        board.put(i,j);
                        board.put(m,n);
                        startGame(0);
                        count++;
                        if(board.isWin()) {
                            if(board.getSide() == 'X') {
                                X++;
                                System.out.println("Round "+round+" : X Win.");
                            }
                            else {
                                O++;
                                System.out.println("Round "+round+" : O Win.");
                            }
                            char[][] result = board.getBoard();
                            for(int y=4; y>=0; y--) {
                                System.out.print("| ");
                                for(int x=0; x<4; x++) {
                                    System.out.print(result[x][y]+" | ");
                                }
                                System.out.println();
                            }
                        }
                        else {
                            draw++;
                            System.out.println("Round "+round+" : Draw.");
                        }
                        count = 0;
            //            try {
            //                Thread.sleep(1500L);
            //            } catch (InterruptedException ex) {}
                        round++;
                    }
                }
            }
        }
        System.out.printf("Result[D:X:O] = [%d:%d:%d]\n",draw,X,O);
    }
    
    public static int getMode() {
        while (!StdDraw.mousePressed()) {
            // Wait for mouse press
        }
        double y = Math.round(StdDraw.mouseY());
        while (StdDraw.mousePressed()) {
            // Wait for mouse release
        }
        return (int) y;
    }
    
    public static void init() {
        StdDraw.setFont(new Font("sans serif",Font.BOLD,30));
        StdDraw.setXscale(-0.5, 3.5);
        StdDraw.setYscale(-0.5, 4.5);
        StdDraw.setPenColor(StdDraw.BOOK_BLUE);
        StdDraw.filledRectangle(1.5,4,2,0.4);
        StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
        StdDraw.text(1.5,4,"Tic Tac Toe Game");
        StdDraw.setPenColor();
        StdDraw.rectangle(1.5,3,2,0.4);
        StdDraw.text(1.5,3,"Player vs Player");
        StdDraw.rectangle(1.5,2,2,0.4);
        StdDraw.text(1.5,2,"Player vs Computer");
        StdDraw.rectangle(1.5,1,2,0.4);
        StdDraw.text(1.5,1,"Computer vs Player");
        StdDraw.rectangle(1.5,0,2,0.4);
        StdDraw.text(1.5,0,"Watch Computer Play!");
    }
    
    public static void startGame(int mode) {
        StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
        draw();
        while (!board.isOver()) {
            if(board.getSide() == 'X') count++;
             if(mode == 0) {
                 computerMove = true;
//                if(count >= 8) {
//                    try {
//                    Thread.sleep(250L);
//                    } catch (InterruptedException ex) {}
//                }
             }
             if(mode == 1) {
                 computerMove = board.getSide() == 'X';
             }
             if(mode == 2) {
                 computerMove = board.getSide() == 'O';
             }
            handleMouseClick();
            draw();
        }
    }
    
}