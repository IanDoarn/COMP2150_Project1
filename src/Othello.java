import java.util.Scanner;

/**
 * Othello Class
 * <p>
 * This is the main class for the Othello game
 */
public class Othello {

    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        GameBoard game = new GameBoard(4);
        game.setup();

        System.out.println(game);
        char currentPiece = game.BLACK_PIECE;

        do {
            System.out.println(String.format("Black(%s): %s\t:\tWhite(%s): %s",
                    game.BLACK_PIECE, game.getCountBlackPieces(),
                    game.WHITE_PIECE, game.getCountWhitePieces()
                    ));
            System.out.println(String.format("Enter coordinates for [%s]:", currentPiece));
            String[] coordinates = input.nextLine().split(" ");
            int x = Integer.parseInt(coordinates[0]);
            int y = Integer.parseInt(coordinates[1]);

            if (game.addPiece(x, y, currentPiece)) {
                System.out.println(game);
                currentPiece = game.oppositePiece(currentPiece);
                if (!game.gameState(currentPiece)) {
                    if (game.getIsSetup())
                        System.out.println(String.format("%s has no possible moves!", currentPiece));
                    currentPiece = game.oppositePiece(currentPiece);
                }
            } else {
                System.out.println(String.format("Invalid move x:%s y:%s for %s", coordinates[0], coordinates[1], currentPiece));
            }
        } while (game.getIsSetup());

        System.out.println(game);
        System.out.println("Winner! " + game.getWinner());
    }
}