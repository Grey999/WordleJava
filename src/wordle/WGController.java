package wordle;

import java.io.FileNotFoundException;

public class WGController {
    private final WGModel model;
    private WGView view;

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
        this.view = view;
    }
}
