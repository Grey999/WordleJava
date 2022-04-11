package wordle;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.Observable;


public class WGModel extends Observable {
    //Number of permitted guess
    static final double GUESS=6;

    //FLAGS
    private boolean randomflag;
    private boolean debbugflag;
    private boolean errorflag;
    private boolean firstflag;

    //Constant for the colors
    static final int RED = 1;
    static final int GREEN = 2;
    static final int ORANGE = 3;
    static final int GRAY = 0;

    //Boolean that control the beginning of a new game
    private boolean newgame;
    private boolean asked;
    private boolean win;

    //word to guess and the number of guess associated
    private String wordtoguess;
    private int guess;

    //word on try and the colors associated
    private String playerword;
    private String lastword;
    private int[] colors;


    public WGModel()
    {

    }

    protected void initialise()  {
        //call when creating a WGModel Object
        //Will initialise the game
        setFirstflag(true);
        colors = new int[5];
        setPlayerword("");
        setGuess(0);
        setLastword("");

        //call the update methods of the views
        assert !getPlayerword().equals(""): "the player word hasn't been created";
        assert getGuess() != 0: "the number of guess is different from 0";
        assert !getLastword().equals(""): "the last word hasn't been created";

        setChanged();
        notifyObservers();
    }

    protected void setWordtoGuess() throws FileNotFoundException
    {
        File file = new File("words.txt");
        assert !file.exists(): "the file words.txt doesn't exist";

        Scanner sc = new Scanner(file);
        int number;
        if(randomflag) {
            number = (int) (Math.random() * 10657);
        }
        else
        {
            number = 42;
        }
        for (int i = 0; i < number; i++) {
            setWordtoguess(sc.nextLine());
        }
        sc.close();
    }

    protected int isWordAccept() throws FileNotFoundException {
        //Handle the possible mistake of the input
        if(getPlayerword().length() != 5)
        {
            return 1;
        }
        for(int i = 0; i < getPlayerword().length(); i++)
        {
            int current = getPlayerword().charAt(i);
            if(current < 65 || current > 91 && current < 97 || current > 123)
            {
                return 2;
            }
        }
        if (isErrorflag()) {
            if (!isWordOnList()) {
                return 3;
            }
        }
        return 0;
    }


    protected void askForNewGame() {
        setNewgame(true);
        setAsked(true);
        setChanged();
        notifyObservers();

    }

    protected void change() throws FileNotFoundException {
       //update the game depending on the current actualword
        setFirstflag(false);
        changeColors();
        setGuess(getGuess() + 1);
        if(getPlayerword().equals(getWordtoguess()))
        {
            //Player won the game
            setWin(true);
            setNewgame(true);
        }
        else
        {
            //See if the game is over
            if(getGuess() == GUESS)
            {
                setWin(false);
                setNewgame(true);
            }
        }

        setLastword(getPlayerword());
        setPlayerword("");

        //Update the views
        setChanged();
        notifyObservers();
    }

    protected boolean isWordOnList() throws FileNotFoundException {
        //verify is the actual word belong to the lists of word
        File file = new File("common.txt");
        assert !file.exists(): "the file common.txt doesn't exist";
        Scanner sc = new Scanner(file);
        boolean found = checkList(sc);
        if(!found)
        {
            file = new File("words.txt");
            assert !file.exists(): "the file words.txt doesn't exist";
            sc = new Scanner(file);
            found = checkList(sc);
        }
        return found;
    }

    private  boolean checkList(Scanner sc) throws FileNotFoundException {
        boolean found = false;
        while(!found && sc.hasNextLine())
        {
            found = getPlayerword().equals(sc.nextLine());
        }
        sc.close();
        return found;
    }

    private void changeColors()
    {
        //Change the number on the colors array
        //This array will be used by the classes to display the correct
        //information to the player
        setColors(new int[5]);
        assert getWordtoguess().equals(""): "the word to guess hasn't been assigned";
        assert Arrays.equals(getColors(), new int[5]) : "the colors have not been clean";
        for (int c = 0; c < 5; c++)
        {
            getColors()[c] = RED;
        }
        for (int i = 0; i < 5; i++) {
            if(playerword.charAt(i) == wordtoguess.charAt(i))
            {
                getColors()[i] = GREEN;
            }
            else {
                for (int j = 0; j < 5; j++) {
                    if (playerword.charAt(i) == wordtoguess.charAt(j) && getColors()[i] == RED) {
                        getColors()[i] = ORANGE;
                    }
                }
            }
        }
        assert !Arrays.equals(getColors(), new int[5]) : "the colors have not changed";
    }


    //Getter and Setters for the variables
    public boolean isWin()
    {
        return win;
    }

    public void setWin(boolean win)
    {
        this.win = win;
    }

    public void setRandomflag(boolean randomflag)
    {
        this.randomflag = randomflag;
    }

    public boolean isNewgame() {
        return newgame;
    }

    public void setNewgame(boolean newgame) {
        this.newgame = newgame;
    }

    public String getWordtoguess() {
        return wordtoguess;
    }

    public void setWordtoguess(String wordtoguess) {
        this.wordtoguess = wordtoguess;
    }

    public String getPlayerword() {
        return playerword;
    }

    public void setPlayerword(String playerword) {
        this.playerword = playerword;
    }

    public int getGuess() {
        return guess;
    }

    public void setGuess(int guess) {
        this.guess = guess;
    }

    public boolean isDebbugflag() {
        return debbugflag;
    }

    public void setDebbugflag(boolean debbugflag) {
        this.debbugflag = debbugflag;
    }

    public boolean isErrorflag() {
        return errorflag;
    }

    public void setErrorflag(boolean errorflag) {
        this.errorflag = errorflag;
    }

    public int[] getColors() {
        return colors;
    }

    public void setColors(int[] colors) {
        this.colors = colors;
    }

    public boolean isFirstflag() {
        return firstflag;
    }

    public void setFirstflag(boolean firstflag) {
        this.firstflag = firstflag;
    }

    public String getLastword() {
        return lastword;
    }

    public void setLastword(String lastword) {
        this.lastword = lastword;
    }


    public boolean isAsked() {
        return asked;
    }

    public void setAsked(boolean asked) {
        this.asked = asked;
    }
}
