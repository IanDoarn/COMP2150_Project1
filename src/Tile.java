/**
 * Tile Class
 * <p>
 * This is a subclass of GameBoard
 * Tile represents a space on the board
 * in which a piece can be placed
 * </p>
 */
public class Tile extends GameBoard {

    private char state;
    private int[] position;

    void setState(char piece) {
        state = piece;
    }

    char getState() {
        return state;
    }

    int getX() { return this.position[0]; }
    int getY() { return this.position[1]; }

    Tile(int[] position) {
        this.position = position;
        state = BLANK_PIECE;
    }

    Tile(int[] position, char piece) {
        this.position = position;
        state = piece;
    }

    void flip() {
        switch(state) {
            case WHITE_PIECE:
                setState(BLACK_PIECE);
                break;
            case BLACK_PIECE:
                setState(WHITE_PIECE);
                break;
            default: break;
        }
    }

    @Override
    public String toString() {
        return String.format("%s", state);
    }
}
