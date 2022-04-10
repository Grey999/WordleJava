package wordle;

import java.io.FileNotFoundException;

//Controller of the Model
//Call by WGView to enquire the model and ask for possible changes

public class WGController {
    private final WGModel model;
    private WGView view;

    protected  WGController(WGModel model) {
        this.model = model;
    }

    protected void initialise() throws FileNotFoundException {
        model.initialise();
    }
    protected void change() throws FileNotFoundException {
        model.change();
    }

    protected void setRandomFlag(boolean random) {
        model.setRandomflag(random);
    }

    protected int isWordAccept() throws FileNotFoundException {
        return model.isWordAccept();
    }

    protected void enableNewGame()
    {
        view.getNewgame().setVisible(true);
    }

    protected void setNewGame()
    {
        model.askForNewGame();
    }

    protected void setDebbugFlag(boolean debbug) {
        model.setDebbugflag(debbug);
    }

    protected void setErrorFlag(boolean error){
        model.setErrorflag(error);
    }

    protected String getPlayerword() {
        return model.getPlayerword();
    }

    protected void setPlayerword(String s)
    {
        model.setPlayerword(s);
    }

    protected int getGuess() {
        return model.getGuess();
    }

    protected boolean isFirstflag() {
        return model.isFirstflag();
    }

    public boolean isDebbugflag() {
        return model.isDebbugflag();
    }

    public String getWordtoguess() {
        return model.getWordtoguess();
    }


    public boolean isErrorflag() {
        return model.isErrorflag();
    }

    public int[] getColors() {
        return model.getColors();
    }

    public void setView(WGView wgView) {
        this.view = wgView;
    }

    public void setWordtoGuess() throws FileNotFoundException {
        model.setWordtoGuess();
    }

    public String getLastWord() {
        return model.getLastword();
    }
}
