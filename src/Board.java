
/**
 *
 * @author HP
 */
public class Board{
    private char[][] board;
    private char side = 'X';
    private boolean win = false;
    
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
