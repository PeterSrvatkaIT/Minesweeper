package minesweeper.consoleui;

import minesweeper.UserInterface;
import minesweeper.core.Field;
import minesweeper.core.GameState;
import minesweeper.core.Tile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Console user interface.
 */
public class ConsoleUI implements UserInterface {
    /**
     * Playing field.
     */
    private Field field;
    Pattern pattern1 = Pattern.compile("([OM]{1})([A-Za-z]{1})([0-9]{1,2})");   //ps012 dostane vstup O/M a suradnice y a x

    /**
     * Input reader.
     */
    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Reads line of text from the reader.
     *
     * @return line as a string
     */
    private String readLine() {
        try {
            return input.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Starts the game.
     *
     * @param field field of mines and clues
     */
    @Override
    public void newGameStarted(Field field) {               //ps015 zaciatok hry, ukoncenie hry, pokracovanie hry
        this.field = field;
        do {
            update();
            processInput();
            //throw new UnsupportedOperationException("Resolve the game state - winning or loosing condition.");
            if (field.getState()== GameState.FAILED) {
                System.out.println("Mina vybuchla, koniec hry, prehral si");
                break;
            }
            if (field.getState()==GameState.SOLVED) {
                System.out.println("Vyhral si"); //ps016 doplnit ponuknut novy hru
                break;
            }
        } while (true);
        System.exit(0);
    }

    /**
     * Updates user interface - prints the field.
     */
    @Override
    public void update() {
        int dlzkaStlpcov;
        // ps006 doplnit podmienku na pocet miest v stlpcoch
        System.out.printf("%3s", "");            //ps006 vypise pocet stlpcov
        //   throw new UnsupportedOperationException("Method update not yet implemented");
        for (int y = 0; y < field.getColumnCount(); y++) {
            System.out.printf("%3s", y);
        }
        System.out.println();
        for (int y = 0; y < field.getRowCount(); y++) {
            System.out.printf("%3s", ((char) (y + 65)));  //ps007 oznacovanie riadkov A-Z
            for (int x = 0; x < field.getRowCount(); x++) {
                //
                if (this.field.getTile(y, x).getState() == Tile.State.OPEN) {
                    System.out.printf("%3s", this.field.getTile(x, y).toString());
                }
                if (this.field.getTile(y, x).getState() == Tile.State.MARKED) {
                    System.out.printf("%3s", "M");
                }
                if (this.field.getTile(y, x).getState() == Tile.State.CLOSED) {
                    System.out.printf("%3s", "-");
                }

            }
            System.out.println();
        }
    }


    /**
     * Processes user input.
     * Reads line from console and does the action on a playing field according to input string.
     */
    private void processInput() {           //ps011 zadavam vstup od uzivatela na ziadanu operaciu
        //  throw new UnsupportedOperationException("Method processInput not yet implemented");
        System.out.println("Zadaj vstup Vstup_riadok_stlpec");
        String gameInput = readLine();
        Matcher matcher1 = pattern1.matcher(gameInput);
        try {
            handleInput(gameInput);                 //ps013 xxx
        } catch (WrongFormatException e) {
            System.out.println(e.getMessage()); //ps014 vytiahne spravu
            processInput();             //ps015 vrati hodnoty
         return;
        }
        if (matcher1.find()) {          //ps019 ochrani matcherO
            System.out.print("// PlayerInput: " + matcher1.group(0));
            System.out.print(" | group1: " + matcher1.group(1));
            System.out.print(" | group2: " + matcher1.group(2));
            System.out.println(" | group3: " + matcher1.group(3));
        } else {
            System.out.println("NO MATCH");
        }
        //vykona operaciu
        if(pattern1.matcher(gameInput).matches()) {
            doOperation(matcher1.group(1).charAt(0), matcher1.group(2).charAt(0), Integer.parseInt(matcher1.group(3)));
        }
    }

    private void doOperation(char charAt, char charAt1, int parseInt)  {

        int osYRowInt = charAt1 - 65;

        // M - oznacenie dlzadice
        if (charAt == 'M') {
            field.markTile(osYRowInt,parseInt);  //ps017 zmeni MARKED/UNMARKED

        }


        if (charAt == 'O') {        //ps018 odkryje dlazdice
            if (field.getTile(osYRowInt, parseInt).getState() == Tile.State.MARKED) {
                System.out.println("!!! Nie je mozne odkryt dlazdicu v stave MARKED");
                return;
            } else {
                field.getTile(osYRowInt, parseInt).setState(Tile.State.OPEN);
            }
// just a note, ignore this note, trying to save on git.hub
                    }

        System.out.println("Vykonal som pozadovanu operaciu");
    }

    void handleInput(String playerInput) throws WrongFormatException { //ps012 vnori exception pri nespravnom vstupe
        if (!pattern1.matcher(playerInput).matches()) {
            throw new WrongFormatException("Zadaj spravny vstup");

        }


    }
}
