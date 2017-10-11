import java.util.ArrayList;
import java.util.Arrays;

/**
 * GameBoard Class
 * <p>
 * This is the main board of the game
 */
public class GameBoard {

    final char BLANK_PIECE = '_';
    final char WHITE_PIECE = 'W';
    final char BLACK_PIECE = 'B';
    private Tile[][] boardTiles;
    private boolean isSetup = false;
    private int MAX_X = 8;
    private int MAX_Y = 8;

    // These are always 2
    private int countBlackPieces = 2;
    private int countWhitePieces = 2;
    private int countBlankPieces = MAX_X * MAX_Y - 4;

    private Tile[] blankPieces;

    private String winner = "";

    GameBoard() {
        //Fill board with tiles
        boardTiles = new Tile[MAX_X][MAX_Y];
    }

    GameBoard(int dimensions) throws Exception {
        // Give GameBoard custom size
        // dimensions -> rows & columns

        // Check given dimensions are valid
        if (dimensions % 2 == 0) {
            // Set custom game board size
            MAX_X = dimensions;
            MAX_Y = dimensions;

            countBlankPieces = MAX_X * MAX_Y + 4;

            //Fill board with tiles
            boardTiles = new Tile[MAX_X][MAX_Y];
        } else {
            // Given dimensions are not valid
            throw new Exception(String.format("Invalid board size [%s, %s]", dimensions, dimensions));
        }
    }

    int getCountBlackPieces() {
        return countBlackPieces;
    }

    int getCountWhitePieces() {
        return countWhitePieces;
    }

    String getWinner() {
        return this.winner;
    }

    private void setWinner(String winner) {
        this.winner = winner;
    }

    boolean getIsSetup() {
        return isSetup;
    }

    void setup() {

        for (int i = 0; i < boardTiles.length; i++) {
            for (int j = 0; j < boardTiles[i].length; j++) {
                boardTiles[i][j] = new Tile(new int[]{i, j});
            }
        }

        // Set up middle of board
        // with:  W B
        //        B W

        boardTiles[(MAX_X / 2) - 1][(MAX_Y / 2) - 1].setState(WHITE_PIECE);
        boardTiles[(MAX_X / 2)][(MAX_Y / 2)].setState(WHITE_PIECE);
        boardTiles[(MAX_X / 2) - 1][(MAX_Y / 2)].setState(BLACK_PIECE);
        boardTiles[(MAX_X / 2)][(MAX_Y / 2) - 1].setState(BLACK_PIECE);

        isSetup = true;
    }

    private void reset() {
        isSetup = false;
    }

    boolean gameState(char currentPiece) {
        // Get the games state

        // Count pieces on board
        countPieces();

        if(countBlankPieces == 0) {
            if (countBlackPieces > countWhitePieces)
                setWinner(String.valueOf(BLACK_PIECE));
            else if (countBlackPieces < countWhitePieces)
                setWinner(String.valueOf(WHITE_PIECE));
            else if(countBlackPieces == countWhitePieces)
                setWinner("Tie");

            reset();
        }
        return hasMoves(currentPiece);
    }

    private void countPieces() {
        int cBlack = 0;
        int cWhite = 0;
        int cBlank = 0;

        for (Tile[] row : boardTiles)
            for (Tile cell : row)
                switch (cell.getState()) {
                    case BLACK_PIECE:
                        cBlack++;
                    case WHITE_PIECE:
                        cWhite++;
                    default:
                        cBlank++;
                }
        countBlackPieces = cBlack;
        countWhitePieces = cWhite;
        countBlankPieces = cBlank;
    }

    private boolean hasMoves(char piece) {
        for(Tile[] row : boardTiles) {
            for(Tile tile : row) {
                if(tile.getState() == BLANK_PIECE) {
                    int x = tile.getX();
                    int y = tile.getY();
                    if (verifyPlacement(new Tile(new int[] {x, y}, piece), false))
                        return true;
                }
            }
        }
        return false;
    }

    char oppositePiece(char piece) {
        return piece == WHITE_PIECE ? BLACK_PIECE : WHITE_PIECE;
    }

    boolean addPiece(int y, int x, char newPiece) {

        // No you can't remove a piece that's cheating
        if (newPiece == BLANK_PIECE) {
            System.out.println("You can not remove a piece.");
        }

        if (boardTiles[x][y].getState() == BLANK_PIECE) {
            Tile newTile = new Tile(new int[]{x, y}, newPiece);
            while (verifyPlacement(newTile, true)) {
                boardTiles[x][y] = newTile;
            }
            return boardTiles[x][y] == newTile;
        }
        return false;
    }

    private boolean verifyPlacement(Tile tile, boolean flip) {
        if (verifyUpperBounds(tile, flip))
            return true;
        else if (verifyUpperRightBounds(tile, flip))
            return true;
        else if (verifyUpperLeftBounds(tile, flip))
            return true;
        else if (verifyBottomBounds(tile, flip))
            return true;
        else if (verifyBottomRightBounds(tile, flip))
            return true;
        else if (verifyBottomLeftBounds(tile, flip))
            return true;
        else if (verifyLeftBounds(tile, flip))
            return true;
        else if (verifyRightBounds(tile, flip))
            return true;
        else
            return false;
    }

    private Tile getTile(String direction, Tile t) {
        switch (direction.toLowerCase()) {
            case "right":
                return rightTile(t.getX(), t.getY());
            case "left":
                return leftTile(t.getX(), t.getY());
            case "upper":
                return upperTile(t.getX(), t.getY());
            case "bottom":
                return bottomTile(t.getX(), t.getY());
            case "upperright":
                return upperRightTile(t.getX(), t.getY());
            case "bottomright":
                return bottomRightTile(t.getX(), t.getY());
            case "upperleft":
                return upperLeftTile(t.getX(), t.getY());
            case "bottomleft":
                return bottomLeftTile(t.getX(), t.getY());
            default:
                break;
        }
        return null;
    }

    private boolean scanTiles(int x, int y, char piece, String direction, boolean flip) {
        ArrayList<Tile> tArray = new ArrayList<>();
        tArray.add(new Tile(new int[]{x, y}, piece));
        Tile tile = getTile(direction, boardTiles[x][y]);

        while (tile != null) {
            if (tile.getState() == BLANK_PIECE) {
                break;
            } else if (tile.getState() == piece) {
                tArray.add(tile);
                break;
            } else {
                tArray.add(tile);
                tile = getTile(direction, tile);
            }
        }

        if (tArray.size() >= 3) {
            if (tArray.get(0).getState() == piece && tArray.get(tArray.size() - 1).getState() == piece) {
                for (Tile t : tArray) {
                    if (oppositePiece(t.getState()) == piece && flip)
                        t.flip();
                }
                return true;
            }
            return false;
        }
        return false;
    }


    private boolean verifyRightBounds(Tile tile, boolean flip) {
        Tile ntile = rightTile(tile.getX(), tile.getY());
        if (!(ntile == null) && !(ntile.getState() == BLANK_PIECE) && !(ntile.getState() == tile.getState()))
            return scanTiles(tile.getX(), tile.getY(), tile.getState(), "right", flip);
        return false;
    }

    private boolean verifyLeftBounds(Tile tile, boolean flip) {
        Tile ntile = leftTile(tile.getX(), tile.getY());
        if (!(ntile == null) && !(ntile.getState() == BLANK_PIECE) && !(ntile.getState() == tile.getState()))
            return scanTiles(tile.getX(), tile.getY(), tile.getState(), "left", flip);
        return false;
    }

    private boolean verifyUpperBounds(Tile tile, boolean flip) {
        Tile ntile = upperTile(tile.getX(), tile.getY());
        if (!(ntile == null) && !(ntile.getState() == BLANK_PIECE) && !(ntile.getState() == tile.getState()))
            return scanTiles(tile.getX(), tile.getY(), tile.getState(), "upper", flip);
        return false;
    }

    private boolean verifyBottomBounds(Tile tile, boolean flip) {
        Tile ntile = bottomTile(tile.getX(), tile.getY());
        if (!(ntile == null) && !(ntile.getState() == BLANK_PIECE) && !(ntile.getState() == tile.getState()))
            return scanTiles(tile.getX(), tile.getY(), tile.getState(), "bottom", flip);
        return false;
    }

    private boolean verifyUpperRightBounds(Tile tile, boolean flip) {
        Tile ntile = upperRightTile(tile.getX(), tile.getY());
        if (!(ntile == null) && !(ntile.getState() == BLANK_PIECE) && !(ntile.getState() == tile.getState()))
            return scanTiles(tile.getX(), tile.getY(), tile.getState(), "upperright", flip);
        return false;
    }

    private boolean verifyUpperLeftBounds(Tile tile, boolean flip) {
        Tile ntile = upperLeftTile(tile.getX(), tile.getY());
        if (!(ntile == null) && !(ntile.getState() == BLANK_PIECE) && !(ntile.getState() == tile.getState()))
            return scanTiles(tile.getX(), tile.getY(), tile.getState(), "upperleft", flip);
        return false;
    }

    private boolean verifyBottomRightBounds(Tile tile, boolean flip) {
        Tile ntile = bottomRightTile(tile.getX(), tile.getY());
        if (!(ntile == null) && !(ntile.getState() == BLANK_PIECE) && !(ntile.getState() == tile.getState()))
            return scanTiles(tile.getX(), tile.getY(), tile.getState(), "bottomright", flip);
        return false;
    }

    private boolean verifyBottomLeftBounds(Tile tile, boolean flip) {
        Tile ntile = bottomLeftTile(tile.getX(), tile.getY());
        if (!(ntile == null) && !(ntile.getState() == BLANK_PIECE) && !(ntile.getState() == tile.getState()))
            return scanTiles(tile.getX(), tile.getY(), tile.getState(), "bottomleft", flip);
        return false;
    }

    private Tile rightTile(int x, int y) {
        if (!(y + 1 > MAX_Y - 1))
            return boardTiles[x][y + 1];
        return null;
    }

    private Tile leftTile(int x, int y) {
        if (!(y - 1 < 0))
            return boardTiles[x][y - 1];
        return null;
    }

    private Tile bottomTile(int x, int y) {
        if (!(x + 1 > MAX_X - 1))
            return boardTiles[x + 1][y];
        return null;
    }

    private Tile upperTile(int x, int y) {
        if (!(x - 1 < 0))
            return boardTiles[x - 1][y];
        return null;
    }

    private Tile upperLeftTile(int x, int y) {
        if (!(x - 1 < 0) && !(y - 1 < 0))
            return boardTiles[x - 1][y - 1];
        return null;
    }

    private Tile upperRightTile(int x, int y) {
        if (!(x - 1 < 0) && !(y + 1 > MAX_Y - 1))
            return boardTiles[x - 1][y + 1];
        return null;
    }

    private Tile bottomLeftTile(int x, int y) {
        if (!(x + 1 > MAX_X - 1) && !(y - 1 < 0))
            return boardTiles[x + 1][y - 1];
        return null;
    }

    private Tile bottomRightTile(int x, int y) {
        if (!(x + 1 > MAX_X - 1) && !(y + 1 > MAX_Y - 1))
            return boardTiles[x + 1][y + 1];
        return null;
    }

    @Override
    public String toString() {

        // Build visual representation of the board
        StringBuilder board = new StringBuilder();

        if (isSetup) {
            int rowNumber = 0;
            char gridIntersection = '+';
            String padding = " ";
            char[] delimiter = new char[padding.length() + 2];
            char gridDelimiter = '-';
            Arrays.fill(delimiter, gridDelimiter);

            board.append(String.format("   %s", padding));

            for (int i = 0; i < MAX_X; ++i)
                board.append(String.format("%s  %s", i, padding));

            board.append("\n");

            for (Tile[] row : boardTiles) {
                board.append(String.format(" %s%s", padding, gridIntersection));

                for (int i = 0; i < MAX_X; i++) {
                    board.append(new String(delimiter));
                    board.append(gridIntersection);
                }

                board.append("\n");
                board.append(rowNumber);
                board.append(padding);

                for (Tile tile : row) {
                    board.append('|');
                    board.append(String.format("%s%s ", padding, tile));
                }

                rowNumber++;
                board.append("|\n");
            }

            board.append(String.format(" %s%s", padding, gridIntersection));

            for (int i = 0; i < MAX_X; i++) {
                board.append(new String(delimiter));
                board.append(gridIntersection);
            }
            return board.toString();
        }

        System.out.println("Board has not been setup.");
        return null;
    }
}
