package wordle;

import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class WGModelTest {

    @Test
    public void initialise()
    {
        WGModel model = new WGModel();
        model.initialise();
        assertEquals(model.getPlayerword(),"");
        assertTrue(model.isFirstflag());
        assertEquals(model.getGuess(),0);
        assertEquals(model.getLastword(), "");
    }

    @Test
    public void setRandomWord()
    {
        WGModel model = new WGModel();
        model.initialise();
        model.setRandomflag(true);
        try {
            model.setWordtoGuess();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assertNotEquals(model.getWordtoguess(), "absit");
        String lastry = model.getWordtoguess();
        try {
            model.setWordtoGuess();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assertNotEquals(model.getWordtoguess(), lastry);
    }

    @Test
    public void setFixedWord()
    {
        WGModel model = new WGModel();
        model.initialise();
        model.setRandomflag(false);
        try {
            model.setWordtoGuess();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assertEquals(model.getWordtoguess(), "absit");
    }

    @Test
    public void changeTrue()
    {

    }

    @Test
    public void changeFalse()
    {

    }
}