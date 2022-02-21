package wordle;

import java.io.FileNotFoundException;

public class WGCli {
    //build the entire CLI version using only controller and model
    private static WGModel model;
    public static void main(String[] args) throws FileNotFoundException {
        //ASCII model + rules
        mainScreen();
        initialiseWordle();
        //boucle en fonction des guess
    }

    private static void mainScreen()
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
        System.out.println(" Codded by Thanatos ");
        System.out.println("This game present different option:");
    }

    private void prompt()
    {
        //TODO
        //The input line
    }

    private void setupflags(int number, String answer)
    {
        //TODO
    }

    private static void initialiseWordle() throws FileNotFoundException {
        model = new WGModel();
        //setup flag
    }


}
