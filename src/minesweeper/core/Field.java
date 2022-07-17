package minesweeper.core;

import java.util.Random;

/**
 * Field represents playing field and game logic.
 */
public class Field {
    /**
     * Playing field tiles.
     */
    private final Tile[][] tiles;

    /**
     * Field row count. Rows are indexed from 0 to (rowCount - 1).
     */
    private final int rowCount;

    /**
     * Column count. Columns are indexed from 0 to (columnCount - 1).
     */
    private final int columnCount;

    /**
     * Mine count.
     */
    private final int mineCount;

    /**
     * Game state.
     */
    private GameState state = GameState.PLAYING;

    /**
     * Constructor.
     *
     * @param rowCount    row count
     * @param columnCount column count
     * @param mineCount   mine count
     */
    public Field(int rowCount, int columnCount, int mineCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.mineCount = mineCount;
        tiles = new Tile[rowCount][columnCount];

        //generate the field content
        generate();
    }

    /**
     * Opens tile at specified indeces.
     *
     * @param row    row number
     * @param column column number
     */
    public void openTile(int row, int column) {
        Tile tile = tiles[row][column];
        if (tile.getState() == Tile.State.CLOSED) {
            tile.setState(Tile.State.OPEN);
            if (tile instanceof Mine) {
                state = GameState.FAILED;
                return;
            }

            if (isSolved()) {
                state = GameState.SOLVED;
                return;
            }
        }
    }

    /**
     * Marks tile at specified indeces.
     *
     * @param row    row number
     * @param column column number
     */
    public void markTile(int row, int column) {     //ps004 vytroril som podmienky pre status tile OPEN/MARKED,CLOSED
//        throw new UnsupportedOperationException("Method markTile not yet implemented");
        if (getTile(row, column).getState() == Tile.State.OPEN) {
            return;
        }
        if (getTile(row, column).getState() == Tile.State.MARKED) {
            getTile(row, column).setState(Tile.State.CLOSED);
        } else {
            getTile(row, column).setState(Tile.State.MARKED);
        }

    }

    /**
     * Generates playing field.
     */
    private void generate() {       //ps005 vygenerujem polia s minami, zatial nevykreslujem
        //       throw new UnsupportedOperationException("Method generate not yet implemented");
        //  if((rowCount*columnCount)<mineCount) {mineCount==rowCount*columnCount};    //ps005 ak nastaveny pocet min je vacsi ako pole, zmeni pocet min na velkost pola
        Random r = new Random();
        for (int i = 0; i < mineCount; ) {
            int randrow = r.nextInt(rowCount);
            int randcolumn = r.nextInt(columnCount);
            if (tiles[randrow][randcolumn] == null) {
                tiles[randrow][randcolumn] = new Mine();
                i++;
            }
        }
        for (int x = 0; x < rowCount; x++) {                //ps006 oznaci prazdne dlazdice mnozstvom okolitych min
            for (int y = 0; y < columnCount; y++) {
                if (tiles[x][y] == null) {
                    tiles[x][y] = new Clue(this.countAdjacentMines(x, y));
                }
            }
        }


    }

    /**
     * Returns true if game is solved, false otherwise.
     *
     * @return true if game is solved, false otherwise
     */
    private boolean isSolved() {            //ps009 predtym spravim metodu public void searchForOpenClue
                                            //ps011 metoda spravena
        //   throw new UnsupportedOperationException("Method isSolved not yet implemented");
        if (this.getColumnCount()*this.getRowCount() - this.getMineCount()==getNumber(Tile.State.OPEN)) {
            return true;
        }
        else {
            return false;
        }
    }


    /**
     * Returns number of adjacent mines for a tile at specified position in the field.
     *
     * @param row    row number.
     * @param column column number.
     * @return number of adjacent mines.
     */
    private int countAdjacentMines(int row, int column) {
        int count = 0;
        for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
            int actRow = row + rowOffset;
            if (actRow >= 0 && actRow < rowCount) {
                for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
                    int actColumn = column + columnOffset;
                    if (actColumn >= 0 && actColumn < columnCount) {
                        if (tiles[actRow][actColumn] instanceof Mine) {
                            count++;
                        }
                    }
                }
            }
        }

        return count;

    }

    public int getRowCount() {  //ps002 getter pridany
        return rowCount;
    }

    public int getColumnCount() {       //ps002 getter pridany
        return columnCount;
    }

    public int getMineCount() {     //ps002 getter pridany
        return mineCount;
    }

    public GameState getState() {       //ps002 getter pridany
        return state;

    }

    public Tile getTile(int row, int column) {    //ps003 getter pridany pre array [y] [x]
        return tiles[row][column];
    }

    public void searchForOpenClue() {      //ps009 doplnit, aby vratila prazdne polia po odkryti

    }

    int getNumber(Tile.State status) {       //ps010 zisti pocet tiles v stave MARKED/CLOSED/OPEN
        int count = 0;
        for (Tile[] tArray : this.tiles) {
            for (Tile t : tArray) {
                if (t.getState() == status) {
                    count++;
                }

            }
        }
        return count;
    }


}


