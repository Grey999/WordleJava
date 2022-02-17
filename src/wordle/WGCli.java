package wordle;

import java.io.FileNotFoundException;

public class WGCli {
    //build the entire CLI version using only controller and model
    public static void main(String[] args) throws FileNotFoundException {
        //TODO
        //ASCII model + rules
        initialiseWordle();
        //is that all ?
    }

    public void mainScreen()
    {
        System.out.print(" _        _  _        _\n" +
                "\\ \\      / /\\ \\      / /\n" +
                " \\ \\    / /  \\ \\    / /\n" +
                "  \\ \\  / /    \\ \\  / /\n" +
                "   \\_\\/_/      \\_\\/_/");
    }

    public static void initialiseWordle() throws FileNotFoundException {
        WGModel model = new WGModel();
        WGController controller = new WGController(model);
        controller.initialise();
    }


}
