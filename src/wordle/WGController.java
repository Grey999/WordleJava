package wordle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class WGController {
    private final WGModel model;
    private WGView view;

    public WGController(WGModel model) {
        this.model = model;
    }

    protected void setView(WGView  view) {
        this.view = view;
    }

    protected boolean isValidWord() throws FileNotFoundException {
        return model.isValidWord();
    }

    protected WGView getView(){return view;}

    protected void change() throws FileNotFoundException {
        model.change();
    }

    protected void setRandom(boolean random) {model.setRandomword(random);}
    protected void setDebbug(boolean debbug) {model.setDebbug(debbug);}
    protected void setError(boolean error){model.setMessagerror(error);}
    protected void setColors()
    {
        int[]colors = new int[5];
        model.setColors(colors);}

    protected void initialise() throws FileNotFoundException {
        model.initialise();
    }
}
