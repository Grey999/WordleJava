package wordle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.Observable;


public class WGModel extends Observable {
    //Number of permitted guess
    static final double GUESS=6;

    //FLAGS
    //randomflag = have a random word
    //debbugflag = the word to guess can be see during the game
    //errorflag = the word on try must be on the list of the game, an error message will be display if not
    //firstflag = use by the update of WGView to know if it can be updated (not necessary for the CLI Version)
    private boolean randomflag;
    private boolean debbugflag;
    private boolean errorflag;
    private boolean firstflag;

    //Constant for the colors
    //Will be placed in the colors and read by the WGView
    static final int RED = 1;
    static final int GREEN = 2;
    static final int ORANGE = 3;
    static final int GRAY = 0;

    //Boolean that control the beginning of a new game
    //newgame indicates a game will begin
    //asked indicates that the precedent game hasn't finished
    //win indicates that the present game has finished
    // win = the game has been won
    // !win = the game has not been won
    private boolean newgame;
    private boolean asked;
    private boolean win;

    //word to guess and the number of guess associated
    //the game stop when:
    //  wordtoguess.equals(playerword)
    //  guess == GUESS
    private String wordtoguess;
    private int guess;

    //playerword: word on try by the player
    //colors: vectors which associated the colors to the letters
    //lastword: contain the last playerword, use by the WGView to display the right information
    private String playerword;
    private String lastword;
    private int[] colors;


    public WGModel() {
        //Call only to create the object
    }

    protected void initialise()  {
        //call to initialise the game
        //create the first element necessary for the beginning of the game:
        //  colors = vectors associated to the word: contain int which indicate the color to the view
        //  playerword = must be initialise at "" at the beginning of the game
        //  guess = must be initialise at 0 at the beginning of the game
        //  lastword = will be used later on the change() and by the update() of the WGView
        setFirstflag(true);
        colors = new int[5];
        setPlayerword("");
        setGuess(0);
        setLastword("");

        //call the update methods of the views
        assert getPlayerword().equals(""): "the player word hasn't been created";
        assert getGuess() == 0: "the number of guess is different from 0";
        assert getLastword().equals(""): "the last word hasn't been created";


        //Notify the views
        setChanged();
        notifyObservers();
    }

    protected void setWordtoGuess() throws FileNotFoundException
    {
        //use by the WGCli or the Controller to set the wordtoguess
        //depends on the randomflag
        File file = new File("words.txt");
        assert file.exists(): "the file words.txt doesn't exist";

        Scanner sc = new Scanner(file);
        int number;
        if(randomflag) {
            //random index
            number = (int) (Math.random() * 10657);
        }
        else
        {
            //wordtoguess = absit
            number = 42;
        }
        for (int i = 0; i < number; i++) {
            setWordtoguess(sc.nextLine());
        }
        sc.close();
    }

    protected int isWordAccept() throws FileNotFoundException {
        //Handle the possible mistake of the input
        //return a different error code depending on the nature of the mistake
        //will be read by the WGCli or by the Controller
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
        assert (getPlayerword().length() == 5): "the playerword is not of length 5 and it hasn't been handled";
        assert isWordOnList(): "the word is not on the list and it hasn't been handled";
        return 0;
    }

    protected boolean isWordOnList() throws FileNotFoundException {
        //verify is the actual word belong to the lists of word
        //use by isWordAccept()
        File file = new File("common.txt");
        assert file.exists(): "the file common.txt doesn't exist";
        Scanner sc = new Scanner(file);
        boolean found = checkList(sc);
        if(!found)
        {
            file = new File("words.txt");
            assert file.exists(): "the file words.txt doesn't exist";
            sc = new Scanner(file);
            found = checkList(sc);
        }
        return found;
    }

    private  boolean checkList(Scanner sc) throws FileNotFoundException {
        //go through the list to found the word
        //use by isWordOnList()
        boolean found = false;
        while(!found && sc.hasNextLine())
        {
            found = getPlayerword().equals(sc.nextLine());
        }
        sc.close();
        return found;
    }


    protected void askForNewGame() {
        //Use by the Controller to ask for a newgame during a play
        //will change the FirstView and the WGView
        assert !newgame: "the flag newgame shouldn't be at true";
        assert !win: "the flag win shouldn't be at true";
        assert !asked: "the flag asked shouldn't be at true";

        setNewgame(true);
        setAsked(true);
        setChanged();
        notifyObservers();

    }

    protected void change() throws FileNotFoundException {
        //update the game depending on the current playerword
        //will update the flags win and newgame depending on:
        //  guess == GUESS
        //  wordtoguess.equals(playerword)
        //if not, continue the game
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

    private void changeColors()
    {
        //Change the number on the colors array
        //This array will be used by the classes to display the correct information to the player
        //necessary for both WGCli and WGView
        setColors(new int[5]);
        assert !getWordtoguess().equals(""): "the word to guess hasn't been assigned";
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
        assert guess >= 0: "the guess can't be negative";
        assert guess <= GUESS: "the guess can't be superior to GUESS";
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
        assert this.lastword.equals(getPlayerword()) || this.lastword.equals(""):
                "the lastword doesn't equal to the current playerword or to an empty string";
    }

    public boolean isAsked() {
        return asked;
    }

    public void setAsked(boolean asked) {
        this.asked = asked;
    }
}
