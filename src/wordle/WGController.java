package wordle;

import java.io.FileNotFoundException;

public class WGController {
    private final WGModel model;

    protected void initialise() throws FileNotFoundException {
        model.initialise();
    }
    protected void change() throws FileNotFoundException {
        model.change();
    }

    protected void setRandomFlag(boolean random) {model.setRandomflag(random);}
    protected void setDebbugFlag(boolean debbug) {model.setDebbugflag(debbug);}
    protected void setErrorFlag(boolean error){model.setErrorflag(error);}
    protected  WGController(WGModel model) {
        this.model = model;
    }
    protected void setView(WGView  view) {
    }

    public String getPlayerword() {
        return model.getPlayerword();
    }

    public void setPlayerword(String s)
    {
        model.setPlayerword(s);
    }

    public int getGuess() {
        return model.getGuess();
    }

    public void setFirstflag(boolean b) {
        model.setFirstflag(b);
    }

    public boolean isFirstflag() {
        return model.isFirstflag();
    }

    public void setPlayerWord(String s) {
        model.setPlayerword(s);
    }

    public boolean isDebbugflag() {
        return model.isDebbugflag();
    }

    public String getWordtoguess() {
        return model.getWordtoguess();
    }

    public boolean isWordOnList() throws FileNotFoundException {
        return model.isWordOnList();
    }

    public boolean isErrorflag() {
        return model.isErrorflag();
    }

    public int[] getColors() {
        return model.getColors();
    }
}
