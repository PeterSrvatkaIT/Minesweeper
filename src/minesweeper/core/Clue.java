package minesweeper.core;

/**
 * Clue tile.
 */
public class Clue  extends Tile {
    /** Value of the clue. */
    private final int value;
    
    /**
     * Constructor.
     * @param value  value of the clue
     */
    public Clue(int value) {
        this.value = value;
    }

    public int getValue() {             //ps001 getter pridany
        return value;
    }

    @Override       //ps021 vracia cislo CLUE - pocet bomb okolo
    public String toString() {
        return Integer.toString(value);
    }
}
