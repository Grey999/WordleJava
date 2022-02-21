package wordle;

import java.io.FileNotFoundException;

public class WGController {
    private WGModel model;
    private WGView view;

    public WGController(WGModel model) {
        this.model = model;
    }

    protected void setView(WGView  view) {
        this.view = view;
    }

    protected void change() throws FileNotFoundException {
        model.change();
    }

    protected void setRandom(boolean random)
    {
        model.setRandomword(random);
    }
    protected void setDebbug(boolean debbug){model.setDebbug(debbug);}
    protected void setError(boolean error){model.setMessagerror(error);}
    protected void setColors()
    {
        int[]colors = new int[5];
        for(int i = 0; i < colors.length; i++)
        {
            colors[i] = 0;
        }
        model.setColors(colors);}

    protected void initialise() throws FileNotFoundException {
        model.initialise();
    }
}
