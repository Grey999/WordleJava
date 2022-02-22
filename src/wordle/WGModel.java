package wordle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Observable;


public class WGModel extends Observable {
    private boolean validword;
    private boolean display;

    //FLAGS
    private boolean randomword;
    private boolean debbug;
    private boolean messagerror;

    private boolean newgame;
    private boolean win;

    //word to guess
    private String word;
    private int[] colors;

    //word on try
    private String actualword;
    private int guess;


    public void addObserver(WGView wgView) {
    }

    protected void initialise() throws FileNotFoundException {
        File file = new File("words.txt");
        Scanner sc = new Scanner(file);
        int number;
        if(randomword) {
            number = (int) (Math.random() * 10657);
        }
        else
        {
            number = 42;
        }
        for (int i = 0; i < number; i++) {
            setWord(sc.nextLine());
        }
        colors = new int[5];
        setActualword("");
        setGuess(0);
        setChanged();
        notifyObservers();
    }



    protected void change() throws FileNotFoundException {
       //actual word send by the controller
        if(getActualword().equals(getWord()))
        {
            setNewgame(true);
            setWin(true);
        }
        else
        {
            changeColors();
            setGuess(getGuess() + 1);
            if(getGuess() == 6)
            {
                setWin(false);
                setNewgame(true);
            }
        }
        setDisplay(true);
        setChanged();
        notifyObservers();

    }

    private void changeColors()
    {
        setColors(new int[5]);
        for (int c = 0; c < 5; c++)
        {
            getColors()[c] = 0;
        }
        for (int i = 0; i < 5; i++) {
            if(actualword.indexOf(i) == word.indexOf(i))
            {
                getColors()[i] = 1;
            }
            else {
                for (int j = i + 1; j < 5; j++) {
                    if (actualword.indexOf(i) == word.indexOf(j)) {
                        getColors()[i] = 2;
                    }
                }
            }
        }
    }
    public int getColors(int colum, int line) { return getColors()[colum+line];}

    public boolean isValidword() {
        return validword;
    }

    public void setValidword(boolean validword) {
        this.validword = validword;
    }

    public boolean isWin(){return win; }

    public void setWin(boolean win){this.win = win; }

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    public boolean isRandomword() {
        return randomword;
    }

    public void setRandomword(boolean randomword) {
        this.randomword = randomword;
    }

    public boolean isNewgame() {
        return newgame;
    }

    public void setNewgame(boolean newgame) {
        this.newgame = newgame;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getActualword() {
        return actualword;
    }

    public void setActualword(String actualword) {
        this.actualword = actualword;
    }

    public int getGuess() {
        return guess;
    }

    public void setGuess(int guess) {
        this.guess = guess;
    }

    public boolean isDebbug() {
        return debbug;
    }

    public void setDebbug(boolean debbug) {
        this.debbug = debbug;
    }

    public boolean isMessagerror() {
        return messagerror;
    }

    public void setMessagerror(boolean messagerror) {
        this.messagerror = messagerror;
    }

    public int[] getColors() {
        return colors;
    }

    public void setColors(int[] colors) {
        this.colors = colors;
    }
}
