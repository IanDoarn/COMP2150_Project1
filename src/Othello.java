// import java.util.Scanner;

import java.util.ArrayList;

/**
 * Othello Class
 * <p>
 * This is the main class for the Othello game
 */
public class Othello {

    public static void main(String[] args) throws Exception {
        ArrayList tArray = new ArrayList();
        tArray.add(new Tile(new int[] {0, 0},'W'));
        System.out.println(tArray.get(0));

        // Scanner input = new Scanner(System.in);
//        GameBoard gameBoard = new GameBoard();
//
//        gameBoard.setup();
//
//        // System.out.println(gameBoard);
//
//        gameBoard.addPiece(0, 0, gameBoard.WHITE_PIECE);
//        gameBoard.addPiece(0, 7, gameBoard.WHITE_PIECE);
//        gameBoard.addPiece(7, 0, gameBoard.WHITE_PIECE);
//        gameBoard.addPiece(7, 7, gameBoard.WHITE_PIECE);
//
//        System.out.println(gameBoard);
    }
}
