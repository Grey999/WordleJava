package wordle;

import java.io.FileNotFoundException;

public class WGController {
    //is that really all ???
    //ask the teacher
    private WGModel model;
    private WGView view;

    public WGController(WGModel model) {
        this.model = model;
    }

    public void setView(WGView  view) {
        this.view = view;
    }

    public void change() throws FileNotFoundException {
        model.change();
    }

    public void setRandom(boolean random)
    {
        model.setRandomword(random);
    }
    public void setDebbug(boolean debbug){model.setDebug(debbug);}
    public void setError(boolean error){model.setMessagerror(error);}

    public void initialise() throws FileNotFoundException {
        model.initialise();
    }
}
