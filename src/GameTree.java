
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *
 * @author HP
 */
public class GameTree {
    
    private Board board;
    private char you;
    private int score = 0;
    
    GameTree(){}
    
    public GameTree(Board b,char s) {
        board = b;
        you = s;
    }

    public GameTree chooseMove(int alpha,int beta,int depth) {
        GameTree best = new GameTree();
        GameTree reply;
        if(board.isOver()) {
            if(board.isWin()) {
                if(board.getSide() == you) score = Integer.MAX_VALUE-(1000-depth);
                else score = Integer.MIN_VALUE+(1000-depth);
            }
            else score = 0;
            return this;
        }
        if(depth <= 0) {
            evaluate();
            return this;
        }
        if(board.getSide() == you) best.setScore(alpha);
        else best.setScore(beta);
        int x = board.getBoard().length;
        int y = board.getBoard()[0].length;
        List<GameTree> valid = new ArrayList<>();
        for(int i=0; i<x; i++) {
            for(int j=0; j<y; j++) {
                if(board.isValidMove(i,j)) {
                    Board temp = new Board(board);
                    temp.put(i,j);
                    valid.add(new GameTree(temp,you));
                }
            }
        }
        Random ran = new Random();
        best.setBoard(valid.get(ran.nextInt(valid.size())).getBoard());
        for(GameTree move : valid) {
            reply = move.chooseMove(alpha,beta,depth-1);
            if(board.getSide() == you && reply.getScore() > best.getScore()) {
                best.setBoard(move.getBoard());
                best.setScore(reply.getScore());
                alpha = reply.getScore();
                if(alpha >= Integer.MAX_VALUE-(1000-depth)-1) return best;
            }
            else if(board.getSide() != you && reply.getScore() < best.getScore()) {
                best.setBoard(move.getBoard());
                best.setScore(reply.getScore());
                beta = reply.getScore();
                if(beta <= Integer.MIN_VALUE+(1000-depth)+1) return best;
            }
            if(alpha >= beta) return best;
        }
        return best;
    }
    
    private void evaluate() {
        char opp = Board.getOpposite(you);
        char[][] board = this.board.getBoard();
        
        for(int i=0; i<5; i++) {
            char[] tmp = {board[0][i],board[1][i],board[2][i],board[3][i]};
            
            if(Arrays.equals(tmp,genArray(you,you,you,' '))) score+= 50;
            if(Arrays.equals(tmp,genArray(you,you,' ',you))) score+= 50;
            if(Arrays.equals(tmp,genArray(you,' ',you,you))) score+= 50;
            if(Arrays.equals(tmp,genArray(' ',you,you,you))) score+= 50;
            
            if(Arrays.equals(tmp,genArray(opp,opp,opp,' '))) score-= 50;
            if(Arrays.equals(tmp,genArray(opp,opp,' ',opp))) score-= 50;
            if(Arrays.equals(tmp,genArray(opp,' ',opp,opp))) score-= 50;
            if(Arrays.equals(tmp,genArray(' ',opp,opp,opp))) score-= 50;
            
            if(Arrays.equals(tmp,genArray(you,you,' ',' '))) score+= 10;
            if(Arrays.equals(tmp,genArray(you,' ',you,' '))) score+= 10;
            if(Arrays.equals(tmp,genArray(you,' ',' ',you))) score+= 10;
            if(Arrays.equals(tmp,genArray(' ',you,you,' '))) score+= 10;
            if(Arrays.equals(tmp,genArray(' ',you,' ',you))) score+= 10;
            if(Arrays.equals(tmp,genArray(' ',' ',you,you))) score+= 10;
            
            if(Arrays.equals(tmp,genArray(opp,opp,' ',' '))) score-= 10;
            if(Arrays.equals(tmp,genArray(opp,' ',opp,' '))) score-= 10;
            if(Arrays.equals(tmp,genArray(opp,' ',' ',opp))) score-= 10;
            if(Arrays.equals(tmp,genArray(' ',opp,opp,' '))) score-= 10;
            if(Arrays.equals(tmp,genArray(' ',opp,' ',opp))) score-= 10;
            if(Arrays.equals(tmp,genArray(' ',' ',opp,opp))) score-= 10;
        }
        
        for(int i=0; i<4; i++) {
            char[] tmp = {board[i][0],board[i][1],board[i][2],board[i][3],board[i][4]};
            
            if(Arrays.equals(tmp,genArray(' ',you,you,you,' '))) score+= 100;
            if(Arrays.equals(tmp,genArray(' ',opp,opp,opp,' '))) score-= 100;
            
            if(Arrays.equals(tmp,genArray(' ',you,you,' ',' '))) score+= 20;
            if(Arrays.equals(tmp,genArray(' ',' ',you,you,' '))) score+= 20;
            if(Arrays.equals(tmp,genArray(' ',you,' ',you,' '))) score+= 20;
            
            if(Arrays.equals(tmp,genArray(' ',opp,opp,' ',' '))) score-= 20;
            if(Arrays.equals(tmp,genArray(' ',' ',opp,opp,' '))) score-= 20;
            if(Arrays.equals(tmp,genArray(' ',opp,' ',opp,' '))) score+= 20;
            
            if(Arrays.equals(tmp,genArray(you,you,you,' ',' '))) score+= 50;
            if(Arrays.equals(tmp,genArray(you,you,' ',you,' '))) score+= 50;
            if(Arrays.equals(tmp,genArray(you,you,' ',' ',you))) score+= 10;
            if(Arrays.equals(tmp,genArray(' ',you,you,' ',you))) score+= 50;
            if(Arrays.equals(tmp,genArray(' ',' ',you,you,you))) score+= 50;
            
            if(Arrays.equals(tmp,genArray(opp,opp,opp,' ',' '))) score-= 50;
            if(Arrays.equals(tmp,genArray(opp,opp,' ',opp,' '))) score-= 50;
            if(Arrays.equals(tmp,genArray(opp,opp,' ',' ',opp))) score-= 10;
            if(Arrays.equals(tmp,genArray(' ',opp,opp,' ',opp))) score-= 50;
            if(Arrays.equals(tmp,genArray(' ',' ',opp,opp,opp))) score-= 50;
            
            if(Arrays.equals(tmp,genArray(you,you,' ',' ',' '))) score+= 10;
            if(Arrays.equals(tmp,genArray(you,' ',you,' ',' '))) score+= 10;
            if(Arrays.equals(tmp,genArray(you,' ',' ',you,' '))) score+= 10;
            if(Arrays.equals(tmp,genArray(you,' ',' ',' ',you))) score+= 2;
            if(Arrays.equals(tmp,genArray(' ',you,' ',' ',you))) score+= 10;
            if(Arrays.equals(tmp,genArray(' ',' ',you,' ',you))) score+= 10;
            if(Arrays.equals(tmp,genArray(' ',' ',' ',you,you))) score+= 10;
            
            if(Arrays.equals(tmp,genArray(opp,opp,' ',' ',' '))) score-= 10;
            if(Arrays.equals(tmp,genArray(opp,' ',opp,' ',' '))) score-= 10;
            if(Arrays.equals(tmp,genArray(opp,' ',' ',opp,' '))) score-= 10;
            if(Arrays.equals(tmp,genArray(opp,' ',' ',' ',opp))) score-= 2;
            if(Arrays.equals(tmp,genArray(' ',opp,' ',' ',opp))) score-= 10;
            if(Arrays.equals(tmp,genArray(' ',' ',opp,' ',opp))) score-= 10;
            if(Arrays.equals(tmp,genArray(' ',' ',' ',opp,opp))) score-= 10;
            
            if(Arrays.equals(tmp,genArray(opp,you,you,' ',' '))) score+= 10;
            if(Arrays.equals(tmp,genArray(opp,you,' ',you,' '))) score+= 10;
            if(Arrays.equals(tmp,genArray(opp,you,' ',' ',you))) score+= 10;
            if(Arrays.equals(tmp,genArray(opp,' ',you,you,' '))) score+= 10;
            if(Arrays.equals(tmp,genArray(opp,' ',you,' ',you))) score+= 10;
            if(Arrays.equals(tmp,genArray(opp,' ',' ',you,you))) score+= 10;
            
            if(Arrays.equals(tmp,genArray(you,opp,opp,' ',' '))) score-= 10;
            if(Arrays.equals(tmp,genArray(you,opp,' ',opp,' '))) score-= 10;
            if(Arrays.equals(tmp,genArray(you,opp,' ',' ',opp))) score-= 10;
            if(Arrays.equals(tmp,genArray(you,' ',opp,opp,' '))) score-= 10;
            if(Arrays.equals(tmp,genArray(you,' ',opp,' ',opp))) score-= 10;
            if(Arrays.equals(tmp,genArray(you,' ',' ',opp,opp))) score-= 10;
            
            if(Arrays.equals(tmp,genArray(you,you,' ',' ',opp))) score+= 10;
            if(Arrays.equals(tmp,genArray(you,' ',you,' ',opp))) score+= 10;
            if(Arrays.equals(tmp,genArray(you,' ',' ',you,opp))) score+= 10;
            if(Arrays.equals(tmp,genArray(' ',you,you,' ',opp))) score+= 10;
            if(Arrays.equals(tmp,genArray(' ',you,' ',you,opp))) score+= 10;
            if(Arrays.equals(tmp,genArray(' ',' ',you,you,opp))) score+= 10;
            
            if(Arrays.equals(tmp,genArray(opp,opp,' ',' ',you))) score-= 10;
            if(Arrays.equals(tmp,genArray(opp,' ',opp,' ',you))) score-= 10;
            if(Arrays.equals(tmp,genArray(opp,' ',' ',opp,you))) score-= 10;
            if(Arrays.equals(tmp,genArray(' ',opp,opp,' ',you))) score-= 10;
            if(Arrays.equals(tmp,genArray(' ',opp,' ',opp,you))) score-= 10;
            if(Arrays.equals(tmp,genArray(' ',' ',opp,opp,you))) score-= 10;
            
            if(Arrays.equals(tmp,genArray(opp,you,you,you,' '))) score+= 50;
            if(Arrays.equals(tmp,genArray(opp,you,you,' ',you))) score+= 50;
            if(Arrays.equals(tmp,genArray(opp,you,' ',you,you))) score+= 50;
            if(Arrays.equals(tmp,genArray(opp,' ',you,you,you))) score+= 50;
            
            if(Arrays.equals(tmp,genArray(you,opp,opp,opp,' '))) score-= 50;
            if(Arrays.equals(tmp,genArray(you,opp,opp,' ',opp))) score-= 50;
            if(Arrays.equals(tmp,genArray(you,opp,' ',opp,opp))) score-= 50;
            if(Arrays.equals(tmp,genArray(you,' ',opp,opp,opp))) score-= 50;
        }
        
        {
            char[] tmp = {board[0][0],board[1][1],board[2][2],board[3][3]};
            
            if(Arrays.equals(tmp,genArray(you,you,you,' '))) score+= 50;
            if(Arrays.equals(tmp,genArray(you,you,' ',you))) score+= 50;
            if(Arrays.equals(tmp,genArray(you,' ',you,you))) score+= 50;
            if(Arrays.equals(tmp,genArray(' ',you,you,you))) score+= 50;
            
            if(Arrays.equals(tmp,genArray(opp,opp,opp,' '))) score-= 50;
            if(Arrays.equals(tmp,genArray(opp,opp,' ',opp))) score-= 50;
            if(Arrays.equals(tmp,genArray(opp,' ',opp,opp))) score-= 50;
            if(Arrays.equals(tmp,genArray(' ',opp,opp,opp))) score-= 50;
            
            if(Arrays.equals(tmp,genArray(you,you,' ',' '))) score+= 10;
            if(Arrays.equals(tmp,genArray(you,' ',you,' '))) score+= 10;
            if(Arrays.equals(tmp,genArray(you,' ',' ',you))) score+= 10;
            if(Arrays.equals(tmp,genArray(' ',you,you,' '))) score+= 10;
            if(Arrays.equals(tmp,genArray(' ',you,' ',you))) score+= 10;
            if(Arrays.equals(tmp,genArray(' ',' ',you,you))) score+= 10;
            
            if(Arrays.equals(tmp,genArray(opp,opp,' ',' '))) score-= 10;
            if(Arrays.equals(tmp,genArray(opp,' ',opp,' '))) score-= 10;
            if(Arrays.equals(tmp,genArray(opp,' ',' ',opp))) score-= 10;
            if(Arrays.equals(tmp,genArray(' ',opp,opp,' '))) score-= 10;
            if(Arrays.equals(tmp,genArray(' ',opp,' ',opp))) score-= 10;
            if(Arrays.equals(tmp,genArray(' ',' ',opp,opp))) score-= 10;
        }
        
        {
            char[] tmp = {board[0][1],board[1][2],board[2][3],board[3][4]};
            
            if(Arrays.equals(tmp,genArray(you,you,you,' '))) score+= 50;
            if(Arrays.equals(tmp,genArray(you,you,' ',you))) score+= 50;
            if(Arrays.equals(tmp,genArray(you,' ',you,you))) score+= 50;
            if(Arrays.equals(tmp,genArray(' ',you,you,you))) score+= 50;
            
            if(Arrays.equals(tmp,genArray(opp,opp,opp,' '))) score-= 50;
            if(Arrays.equals(tmp,genArray(opp,opp,' ',opp))) score-= 50;
            if(Arrays.equals(tmp,genArray(opp,' ',opp,opp))) score-= 50;
            if(Arrays.equals(tmp,genArray(' ',opp,opp,opp))) score-= 50;
            
            if(Arrays.equals(tmp,genArray(you,you,' ',' '))) score+= 10;
            if(Arrays.equals(tmp,genArray(you,' ',you,' '))) score+= 10;
            if(Arrays.equals(tmp,genArray(you,' ',' ',you))) score+= 10;
            if(Arrays.equals(tmp,genArray(' ',you,you,' '))) score+= 10;
            if(Arrays.equals(tmp,genArray(' ',you,' ',you))) score+= 10;
            if(Arrays.equals(tmp,genArray(' ',' ',you,you))) score+= 10;
            
            if(Arrays.equals(tmp,genArray(opp,opp,' ',' '))) score-= 10;
            if(Arrays.equals(tmp,genArray(opp,' ',opp,' '))) score-= 10;
            if(Arrays.equals(tmp,genArray(opp,' ',' ',opp))) score-= 10;
            if(Arrays.equals(tmp,genArray(' ',opp,opp,' '))) score-= 10;
            if(Arrays.equals(tmp,genArray(' ',opp,' ',opp))) score-= 10;
            if(Arrays.equals(tmp,genArray(' ',' ',opp,opp))) score-= 10;
        }
        
        {
            char[] tmp = {board[0][4],board[1][3],board[2][2],board[3][1]};
            
            if(Arrays.equals(tmp,genArray(you,you,you,' '))) score+= 50;
            if(Arrays.equals(tmp,genArray(you,you,' ',you))) score+= 50;
            if(Arrays.equals(tmp,genArray(you,' ',you,you))) score+= 50;
            if(Arrays.equals(tmp,genArray(' ',you,you,you))) score+= 50;
            
            if(Arrays.equals(tmp,genArray(opp,opp,opp,' '))) score-= 50;
            if(Arrays.equals(tmp,genArray(opp,opp,' ',opp))) score-= 50;
            if(Arrays.equals(tmp,genArray(opp,' ',opp,opp))) score-= 50;
            if(Arrays.equals(tmp,genArray(' ',opp,opp,opp))) score-= 50;
            
            if(Arrays.equals(tmp,genArray(you,you,' ',' '))) score+= 10;
            if(Arrays.equals(tmp,genArray(you,' ',you,' '))) score+= 10;
            if(Arrays.equals(tmp,genArray(you,' ',' ',you))) score+= 10;
            if(Arrays.equals(tmp,genArray(' ',you,you,' '))) score+= 10;
            if(Arrays.equals(tmp,genArray(' ',you,' ',you))) score+= 10;
            if(Arrays.equals(tmp,genArray(' ',' ',you,you))) score+= 10;
            
            if(Arrays.equals(tmp,genArray(opp,opp,' ',' '))) score-= 10;
            if(Arrays.equals(tmp,genArray(opp,' ',opp,' '))) score-= 10;
            if(Arrays.equals(tmp,genArray(opp,' ',' ',opp))) score-= 10;
            if(Arrays.equals(tmp,genArray(' ',opp,opp,' '))) score-= 10;
            if(Arrays.equals(tmp,genArray(' ',opp,' ',opp))) score-= 10;
            if(Arrays.equals(tmp,genArray(' ',' ',opp,opp))) score-= 10;
        }
        
        {
            char[] tmp = {board[0][3],board[1][2],board[2][1],board[3][0]};
            
            if(Arrays.equals(tmp,genArray(you,you,you,' '))) score+= 50;
            if(Arrays.equals(tmp,genArray(you,you,' ',you))) score+= 50;
            if(Arrays.equals(tmp,genArray(you,' ',you,you))) score+= 50;
            if(Arrays.equals(tmp,genArray(' ',you,you,you))) score+= 50;
            
            if(Arrays.equals(tmp,genArray(opp,opp,opp,' '))) score-= 50;
            if(Arrays.equals(tmp,genArray(opp,opp,' ',opp))) score-= 50;
            if(Arrays.equals(tmp,genArray(opp,' ',opp,opp))) score-= 50;
            if(Arrays.equals(tmp,genArray(' ',opp,opp,opp))) score-= 50;
            
            if(Arrays.equals(tmp,genArray(you,you,' ',' '))) score+= 10;
            if(Arrays.equals(tmp,genArray(you,' ',you,' '))) score+= 10;
            if(Arrays.equals(tmp,genArray(you,' ',' ',you))) score+= 10;
            if(Arrays.equals(tmp,genArray(' ',you,you,' '))) score+= 10;
            if(Arrays.equals(tmp,genArray(' ',you,' ',you))) score+= 10;
            if(Arrays.equals(tmp,genArray(' ',' ',you,you))) score+= 10;
            
            if(Arrays.equals(tmp,genArray(opp,opp,' ',' '))) score-= 10;
            if(Arrays.equals(tmp,genArray(opp,' ',opp,' '))) score-= 10;
            if(Arrays.equals(tmp,genArray(opp,' ',' ',opp))) score-= 10;
            if(Arrays.equals(tmp,genArray(' ',opp,opp,' '))) score-= 10;
            if(Arrays.equals(tmp,genArray(' ',opp,' ',opp))) score-= 10;
            if(Arrays.equals(tmp,genArray(' ',' ',opp,opp))) score-= 10;
        }
    }
    
    private static char[] genArray(char... c) {
        return c;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public char getYou() {
        return you;
    }

    public void setYou(char you) {
        this.you = you;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
