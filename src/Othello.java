import java.util.Scanner;

/**
 * Othello Class
 * <p>
 * This is the main class for the Othello game
 */
public class Othello {

    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        GameBoard gameBoard = new GameBoard();
        gameBoard.setup();

        System.out.println(gameBoard);
        char currentPiece = gameBoard.BLACK_PIECE;

        while(gameBoard.getIsSetup()) {
            System.out.println(String.format("Enter coordinates for [%s]:", currentPiece));
            String[] coordinates = input.nextLine().split(" ");
            int x = Integer.parseInt(coordinates[0]);
            int y = Integer.parseInt(coordinates[1]);

            if(gameBoard.addPiece(x, y, currentPiece)) {
                currentPiece = gameBoard.oppositePiece(currentPiece);
                System.out.println(gameBoard);
            }
            else {
                System.out.println(String.format("Invalid move x:%s y:%s for %s", coordinates[0], coordinates[1], currentPiece));
            }
        }
    }
}
