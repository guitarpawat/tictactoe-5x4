import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *
 * @author HP
 */
public class Board{
    private char[][] board;
    private char side = 'X';
    private boolean win = false;
    private int score = 0;
    
    public Board() {
        this(4,5);
    }
    
    public Board(int x,int y) {
        board = new char[x][y];
        for(int i=0; i<x; i++) {
            for(int j=0; j<y; j++) {
                board[i][j] = ' ';
            }
        }
    }
    
    public Board(char[][] b,char side) {
        this.side = side;
        setBoard(b);
    }

    public Board(Board b) {
        board = b.getBoard();
        side = b.getSide();
        checkWin();
    }
    
    public boolean put(int x,int y) {
        if(isValidMove(x,y)) {
            board[x][y] = side;
            checkWin();
            if(!win) setOpposite();
            return true;
        }
        return false;
    }
    
    public boolean isValidMove(int x,int y) {
        return !isOver() && board[x][y]==' ';
    }
    
    public void chooseMove(char you,int depth) {
        if(!isOver()) {
            Board b = chooseMove(Integer.MIN_VALUE,Integer.MAX_VALUE,you,depth);
            board = b.getBoard();
            checkWin();
            if(!win) setOpposite();
        }
    }
    
    public Board chooseMove(int alpha,int beta,char you,int depth) {
        Board best = new Board();
        Board reply;
        if(isOver()) {
            if(isWin()) {
                if(getSide() == you) score = Integer.MAX_VALUE-(1000-depth);
                else score = Integer.MIN_VALUE+(1000-depth);
            }
            else score = 0;
            return this;
        }
        if(depth <= 0) {
            evaluate(you);
            return this;
        }
        if(getSide() == you) best.setScore(alpha);
        else best.setScore(beta);
        int x = getBoard().length;
        int y = getBoard()[0].length;
        List<Board> valid = new ArrayList<>();
        for(int i=0; i<x; i++) {
            for(int j=0; j<y; j++) {
                if(isValidMove(i,j)) {
                    Board temp = new Board(board,side);
                    temp.put(i,j);
                    valid.add(temp);
                }
            }
        }
        Random ran = new Random();
        best.setBoard(valid.get(ran.nextInt(valid.size())).getBoard());
        for(Board move : valid) {
            reply = move.chooseMove(alpha,beta,you,depth-1);
            if(getSide() == you && reply.getScore() > best.getScore()) {
                best.setBoard(move.getBoard());
                best.setScore(reply.getScore());
                alpha = reply.getScore();
                if(alpha >= Integer.MAX_VALUE-(1000-depth)-1) return best;
            }
            else if(getSide() != you && reply.getScore() < best.getScore()) {
                best.setBoard(move.getBoard());
                best.setScore(reply.getScore());
                beta = reply.getScore();
                if(beta <= Integer.MIN_VALUE+(1000-depth)+1) return best;
            }
            if(alpha >= beta) return best;
        }
        return best;
    }
    
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
    private static char[] genArray(char... c) {
        return c;
    }
    
    private void evaluate(char you) {
        char opp = Board.getOpposite(you);
        
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
    
    public char[][] getBoard() {
        char[][] ret = new char[board.length][];
        for(int i=0; i<board.length; i++) {
            ret[i] = board[i].clone();
        }
        return ret;
    }
    
    public void setBoard(char[][] b) {
        if(board != null) {
            if(b.length!=board.length || b[0].length!=board[0].length)
                throw new IllegalArgumentException("Invalid Array Size!");
        }
        else {
            board = new char[b.length][];
        }
        for(int i=0; i<b.length; i++) {
            board[i] = b[i].clone();
        }
    }
    
    public boolean isWin() {
        return win;
    }
    
    public char getSide() {
        return side;
    }
    
    public boolean isOver() {
        return isFull() || win;
    }
    
    public boolean isFull() {
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[x].length; y++) {
                if (board[x][y] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(obj.getClass() != obj.getClass()) return false;
        Board other = (Board) obj;
        char[][] arr = other.getBoard();
        if(arr.length != board.length || arr[0].length!= board[0].length) return false;
        for(int i=0; i<arr.length; i++) {
            for(int j=0; j<arr[i].length; j++) {
                if(arr[i][j] != board[i][j]) return false;
            }
        }
        return true;
    }
    
    private void checkWin() {
        for(int x=0; x<board.length; x++) {
            for(int y=0; y<board[x].length; y++) {
                if(board[x][y] != ' '){
                    int pts = 0;
                    if(board[x][0] == board[x][y]) {
                        for(int i = 0;i < board[x].length-1;i++) {
                            if(board[x][i] == board[x][y]) pts++;
                        }
                    }
                    else {
                        for(int i = 1;i < board[x].length;i++) {
                            if(board[x][i] == board[x][y]) pts++;
                        }
                    }
                    if (pts >= 4) {
                        win = true;
                        return;
                    }
                    pts = 0;
                    for (int i = 0; i < board.length; i++) {
                        if (board[x][y] == board[i][y]) {
                            pts++;
                        }
                    }
                    if (pts >= 4) {
                        win = true;
                        return;
                    }
                    pts = 0;
                    for (int i = 1; x - i >= 0 && y - i >= 0; i++) {
                        if (board[x][y] == board[x - i][y - i]) {
                            pts++;
                        }
                    }
                    for (int i = 1; x + i < board.length && y + i < board[x].length; i++) {
                        if (board[x][y] == board[x + i][y + i]) {
                            pts++;
                        }
                    }
                    if (pts >= 3) {
                        win = true;
                        return;
                    }
                    pts = 0;
                    for (int i = 1; x + i < board.length && y - i >= 0; i++) {
                        if (board[x][y] == board[x + i][y - i]) {
                            pts++;
                        }
                    }
                    for (int i = 1; x - i >= 0 && y + i < board[x].length; i++) {
                        if (board[x][y] == board[x - i][y + i]) {
                            pts++;
                        }
                    }
                    if (pts >= 3) {
                        win = true;
                        return;
                    }
                    win = false;
                }
            }
        }
    }
    
    private void setOpposite() {
        side = getOpposite(side);
    }
    
    public static char getOpposite(char c) {
        if(c == 'X') return 'O';
        if(c == 'O') return 'X';
        throw new IllegalArgumentException();
    }
}
