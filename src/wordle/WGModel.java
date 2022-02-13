package wordle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Observable;


public class WGModel extends Observable {
    private boolean validword;
    private boolean display;
    private boolean randomword;
    private boolean newgame;

    private String word;
    private int[] colors;
    private String actualword;
    private int guess;


    public void addObserver(WGView wgView) {
    }

    public void initialise() throws FileNotFoundException {
        setValidword(false);
        setDisplay(false);
        setRandomword(false);
        //change randomword
        //Button
        setNewgame(false);
        File file = new File("words.txt");
        Scanner sc = new Scanner(file);
        int number = 0;
        if(randomword) {
            number = (int) Math.random() * 10657;
        }
        else
        {
            number = 1;
        }
        for (int i = 0; i < number; i++) {
            setWord(sc.nextLine());
        }
        setGuess(0);
        setChanged();
        notifyObservers();
    }

    public boolean iswordcorrect(Scanner scanner, Scanner sc) throws FileNotFoundException {
        setActualword(scanner.next());
        return getActualword().equals(sc.nextLine()) && sc.hasNextLine();
    }

    public void change() throws FileNotFoundException {
        while(!isValidword()) {
            //textfield
            //trouver comment récupérer input texfield
            //ne pas oublier de virer Scanner scanner dans iswordcorrec()
            Scanner scanner = new Scanner(System.in);
            File file = new File("common.txt");
            Scanner sc = new Scanner(file);
            setValidword(iswordcorrect(scanner, sc));
            while (!isValidword())
            {
                setValidword(iswordcorrect(scanner,sc));
            }
        }
        if(getActualword().equals(getWord()))
        {
            setNewgame(true);
        }
        else
        {
            ChangeColors();
            setGuess(getGuess() + 1);
            if(getGuess() == 6)
            {
                //GAME OVER
                setNewgame(true);
            }
        }
        setDisplay(true);
        setChanged();
        notifyObservers();

    }

    public void ChangeColors()
    {
        colors = new int[5];
        for (int c = 0; c < 5; c++)
        {
            colors[c] = 0;
        }
        for (int i = 0; i < 5; i++) {
            if(actualword.indexOf(i) == word.indexOf(i))
            {
                colors[i] = 1;
            }
            else {
                for (int j = i + 1; j < 5; j++) {
                    if (actualword.indexOf(i) == word.indexOf(j)) {
                        colors[i] = 2;
                    }
                }
            }
        }
    }
    public int getColors(int colum, int line) { return colors[colum+line];}

    public boolean isValidword() {
        return validword;
    }

    public void setValidword(boolean validword) {
        this.validword = validword;
    }

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
}
