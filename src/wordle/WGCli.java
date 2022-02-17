package wordle;

import java.io.FileNotFoundException;

public class WGCli {
    //build the entire CLI version using only controller and model
    public static void main(String[] args) throws FileNotFoundException {
        //TODO
        //ASCII model + rules
        mainScreen();
        //initialiseWordle();
        //boucle en fonction des guess
    }

    public static void mainScreen()
    {
        System.out.println("\n" +
                "██╗    ██╗ ██████╗ ██████╗ ██████╗ ██╗     ███████╗\n" +
                "██║    ██║██╔═══██╗██╔══██╗██╔══██╗██║     ██╔════╝\n" +
                "██║ █╗ ██║██║   ██║██████╔╝██║  ██║██║     █████╗  \n" +
                "██║███╗██║██║   ██║██╔══██╗██║  ██║██║     ██╔══╝  \n" +
                "╚███╔███╔╝╚██████╔╝██║  ██║██████╔╝███████╗███████╗\n" +
                " ╚══╝╚══╝  ╚═════╝ ╚═╝  ╚═╝╚═════╝ ╚══════╝╚══════╝\n" +
                "                                                   \n");
        System.out.println(" Welcome to Wordle ! \n Hope you enjoy your time with us. \n Are you ready to crack the word ?");
        System.out.println(); //Add rules + Code for the error
    }

    public void prompt()
    {
        //TODO
    }

    public static void initialiseWordle() throws FileNotFoundException {
        WGModel model = new WGModel();
        //setup flag
    }


}
