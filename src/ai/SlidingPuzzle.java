package ai;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sac.graph.GraphState;

public class SlidingPuzzle extends sac.graph.GraphStateImpl{
    public static final int n=3;
    private byte[][] board;
    public static final int UP=0;
    public static final int DOWN=1;
    public static final int RIGHT=2;
    public static final int LEFT=3;
    private int zeroRow; //The row position of 0
    private int zeroColumn; //The column position of 0
    private int misplacedPuzzlePieceNumber= 0;
    private int distance = 0;


    public SlidingPuzzle(){
        this.board = new byte[n][n];
        this.zeroRow=0;
        this.zeroColumn=0;
        byte k=0;
        for (int i=0; i<n; i++){
            for (int j=0; j<n; j++){
                board[i][j] = k++;
            }
        }
    }

    public SlidingPuzzle(SlidingPuzzle parent) {
        this.board = new byte[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				this.board[i][j] = parent.board[i][j];
			}
		}
    }

    @Override
    public String toString(){
        StringBuilder txt = new StringBuilder();
        for (int i=0; i<n; i++){
            for (int j=0; j<n; j++){
                txt.append(board[i][j]);
                txt.append(",");
            }
            txt.append("\n");
        }
        return txt.toString();
    }

     public boolean move(int direction){
        switch(direction){
            case DOWN:
            if (zeroRow<n-1){
                board[zeroRow][zeroColumn]=board[zeroRow+1][zeroColumn];
                board[zeroRow+1][zeroColumn]=0;
                zeroRow++;
                return true;
            }
            return false;

            case UP:
            if (zeroRow>0){
                board[zeroRow][zeroColumn]=board[zeroRow-1][zeroColumn];
                board[zeroRow-1][zeroColumn]=0;
                zeroRow--;
                return true;
            }
            return false;

            case LEFT:
            if (zeroColumn>0){
                board[zeroRow][zeroColumn]=board[zeroRow][zeroColumn-1];
                board[zeroRow][zeroColumn-1]=0;
                zeroColumn--;
                return true;
            }
            return false;

            case RIGHT:
            if(zeroColumn<n-1){
                board[zeroRow][zeroColumn]=board[zeroRow][zeroColumn+1];
                board[zeroRow][zeroColumn+1]=0;
                zeroColumn++;
                return true;
            }
            return false;
        }
        refreshPuzzlePieces();
        return false;
     }

    private void refreshPuzzlePieces() {
        refreshMisplacedPuzzlePieces();
        refreshDistance();
    }


    private void refreshDistance() {
        distance=0;
        int k=0;
        byte[] boardCopy = new byte[n*n];

        //Copies the values of the board
        for (int i = 0; i < n; ++i)
            for (int j = 0; j < n; ++j) {
                boardCopy[k] = board[i][j];
                k++;
            }
        
        //The sum of the distances of the tiles from their goal positions
        for (int i=1; i<n*n; i++){
            distance = distance + calculateDistance((byte)i, boardCopy);
        }
    }

    //Checks the place of the tiles and if they are not at the place where they should be,  
    private void refreshMisplacedPuzzlePieces() {
        misplacedPuzzlePieceNumber = 0;
        //One in the first frame or two in the second frame..
        byte value=0;
        for (int i=0; i<n; ++i) {
            for (int j=0; j<n; ++j) {
                if (board[i][j] != value){
                    misplacedPuzzlePieceNumber++;
                }
                value++;
            }
        }
    }

    //Calculates the distance of the tile from its goal position
    private int calculateDistance(byte tile, byte[] board) {
        int position = findPuzzlePiece(tile, board);
        if (tile != position)
            if (position>tile){
                return position-tile;
            }
            else {
                return tile-position;
            }
        else 
            return 0;
    }

    public int getMisplacedPuzzlePieceNumber() {
        return misplacedPuzzlePieceNumber;
    }

    public int getDistance() {
        return distance;
    }

    //Finds the position of the puzzle piece
    private int findPuzzlePiece (byte puzzlePiece, byte[] board) {
        for (int i = 0; i < n*n; ++i)
            if (board[i] == puzzlePiece)
                return i;
        return -1;
    }

    @Override
    public boolean isSolution() {
        return misplacedPuzzlePieceNumber == 0 && distance == 0;
    }

    @Override
    public int hashCode() {
        byte[] linearBoard = new byte[n*n];
        int value = 0;
        for (int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                linearBoard[value++] = board[i][j];
            }
        }
        return Arrays.hashCode(linearBoard);
    }
    
    @Override
    public List<GraphState> generateChildren() {
        List<GraphState> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            SlidingPuzzle child = new SlidingPuzzle(this);
            //Moves move = Moves.values()[i];
            i
            if (child.move(move)){
                list.add(child);
            }
        }
        return list;
    }
}
