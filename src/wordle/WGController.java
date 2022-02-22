package wordle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class WGController {
    private WGModel model;
    private WGView view;

    public WGController(WGModel model) {
        this.model = model;
    }

    protected void setView(WGView  view) {
        this.view = view;
    }

    private boolean isWordCorrect(Scanner scanner, Scanner sc) throws FileNotFoundException {
        boolean found = false;
        while(!found && sc.hasNextLine())
        {
            found = model.getActualword().equals(sc.nextLine());
        }
        return found;
    }

    protected void isValidWord() throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        File file = new File("C:\\Users\\colin\\Wordle\\src\\wordle\\common.txt");
        Scanner sc = null;
        sc = new Scanner(file);
        if(isWordCorrect(scanner, sc))
        {
            model.change();
        }
        else
        {
            //view
        }

    }

    protected WGView getView(){return view;}

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
        model.setColors(colors);}

    protected void initialise() throws FileNotFoundException {
        model.initialise();
    }
}
