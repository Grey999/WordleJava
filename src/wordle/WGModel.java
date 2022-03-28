package wordle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.Observable;


public class WGModel extends Observable {
    static final double GUESS=6;

    //FLAGS
    private boolean randomword;
    private boolean debbug;
    private boolean messagerror;

    final int Red = 0;
    final int Green = 1;
    final int Orange = 2;

    private boolean newgame;
    private boolean win;

    //word to guess
    private String word;
    private int[] colors;

    //word on try
    private String actualword;
    private int guess;

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
        sc.close();
        colors = new int[5];
        setActualword("");
        setGuess(0);
        setChanged();
        notifyObservers();
    }



    protected void change() throws FileNotFoundException {
       //actual word send by the controller
        System.out.println("change is called");
        if(getActualword().equals(getWord()))
        {
            setNewgame(true);
            setWin(true);
            System.out.println("The game is over");
            setChanged();
            notifyObservers();
        }
        else
        {
            changeColors();
            //Debbugage
            for(int i = 0; i < getColors().length; i++)
            {
                System.out.println(getActualword().charAt(i)+" "+getColors()[i]);
            }
            setGuess(getGuess() + 1);
            System.out.println(getGuess());
            if(getGuess() == GUESS)
            {
                setWin(false);
                setNewgame(true);
            }
            setChanged();
            notifyObservers();
        }
    }

    protected boolean isValidWord() throws FileNotFoundException {
        File file = new File("common.txt");
        Scanner sc = new Scanner(file);
        boolean found = isWordCorrect(sc);
        if(!found)
        {
            file = new File("words.txt");
            sc = new Scanner(file);
            found = isWordCorrect(sc);
        }
        sc.close();
        return found;
    }

    private  boolean isWordCorrect(Scanner sc) throws FileNotFoundException {
        boolean found = false;
        while(!found && sc.hasNextLine())
        {
            found = getActualword().equals(sc.nextLine());
        }
        sc.close();
        return found;
    }

    private void changeColors()
    {
        setColors(new int[5]);
        for (int c = 0; c < 5; c++)
        {
            getColors()[c] = Red;
        }
        for (int i = 0; i < 5; i++) {
            if(actualword.charAt(i) == word.charAt(i))
            {
                getColors()[i] = Green;
            }
            else {
                for (int j = i + 1; j < 5; j++) {
                    if (actualword.charAt(i) == word.charAt(j)) {
                        getColors()[i] = Orange;
                    }
                }
            }
        }
    }
    public int getColors(int colum) { return getColors()[colum];}

    public boolean isWin(){return win; }

    public void setWin(boolean win){this.win = win; }

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
