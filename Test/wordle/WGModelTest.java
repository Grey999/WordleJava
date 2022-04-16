package wordle;

import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class WGModelTest {
    WGModel model;
    ArrayList<String> list;

    @Before
    public void initialise()
    {
        model = new WGModel();
        model.initialise();

        //will help test for the third test
        //The word belongs to the list and we will add the last
        //during the third game depending on the testing
        list = new ArrayList<>();
        list.add("robin");
        list.add("horse");
        list.add("array");
        list.add("tyler");
        list.add("blush");
    }

    @Test
    public void FirstTest () throws FileNotFoundException {
        //Test the initialisation of the game
        //Test if the word is different from the hardcoded word
        assertEquals(model.getPlayerword(),"");
        assertTrue(model.isFirstflag());
        assertEquals(model.getGuess(),0);
        assertEquals(model.getLastword(), "");
        model.setRandomflag(true);

        model.setWordtoGuess();

        assertNotEquals(model.getWordtoguess(), "absit");
    }

    @Test
    public void SecondTest() throws FileNotFoundException {
        //Test if the possible mistake for a word are handled
        //Test if the word that doesn't belong to the list are not accepted
        //by the game
        //Test if the word that belong to the list are accepted by the game
        int code ;
        model.setRandomflag(false);
        model.setErrorflag(true);
        model.setWordtoGuess();
        model.setPlayerword("err");
        code = model.isWordAccept();
        assertNotEquals(0, code);

        model.setPlayerword("12345");
        code = model.isWordAccept();
        assertNotEquals(0,code);

        model.setPlayerword("zelda");
        code = model.isWordAccept();
        assertNotEquals(0,code);

        model.setPlayerword("hazel");
        code = model.isWordAccept();
        assertEquals(0,code);

    }

    @Test
    public void ThirdTest() throws FileNotFoundException {
        //Test with two game:
        //The first is supposed to be lost
        model.setRandomflag(false);
        model.setWordtoGuess();
        list.add("evade");
        model.setPlayerword(list.get(0));
        int[] oldcolors = model.getColors();
        int oldguess = model.getGuess();

        model.change();

        assertNotEquals(oldcolors, model.getColors());
        assertFalse(model.isWin());
        assertNotEquals(oldguess, model.getGuess());
        for(int i = model.getGuess(); i < WGModel.GUESS; i++)
        {
            model.setPlayerword(list.get(i));
            model.change();
        }
        assertFalse(model.isWin());
        assertTrue(model.isNewgame());

        //The second game is supposed to be win
        model.initialise();
        list.remove(5);
        model.setRandomflag(false);
        model.setWordtoGuess();
        list.add("absit");
        model.setPlayerword(list.get(0));
        oldcolors = model.getColors();
        oldguess = model.getGuess();

        model.change();

        assertNotEquals(oldcolors, model.getColors());
        assertFalse(model.isWin());
        assertNotEquals(oldguess, model.getGuess());
        for(int i = model.getGuess(); i < WGModel.GUESS-1; i++)
        {
            model.setPlayerword(list.get(i));
            model.change();
        }
        model.setPlayerword(list.get(5));
        model.change();
        assertTrue(model.isWin());
        assertTrue(model.isNewgame());
    }
}