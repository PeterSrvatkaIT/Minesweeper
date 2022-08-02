package minesweeper;

import minesweeper.consoleui.ConsoleUI;
import minesweeper.core.Field;


/**
 * Main application class.
 */
public class Minesweeper {
    /**
     * User interface.
     */
    private ConsoleUI userInterface;

    /**
     * Constructor.
     */
    private Minesweeper() {
        userInterface = new ConsoleUI();

        Field field = new Field(9, 9, 10);
        userInterface.newGameStarted(field);
    }

    private static long startTime;

    /**
     * Main method.
     *
     * @param args arguments
     */
    public void main(String[] args) {
        startTime = System.currentTimeMillis();
        new Minesweeper();
        this.getPlayingSeconds();
    }

    public int getPlayingSeconds() {
        return (int) ((System.currentTimeMillis() - startTime) / 1000);
    }

}