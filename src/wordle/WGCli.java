package wordle;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class WGCli {
    //build the entire CLI version using only controller and model
    private static WGModel model;
    private static boolean endgame;
    public static void main(String[] args) throws FileNotFoundException {
        mainScreen();
        Scanner sc = new Scanner(System.in);
        while(!endgame) {
            initialiseWordle();
            while (model.getGuess() < 6 || !model.isNewgame()) {
                System.out.println("Enter you word:");
                prompt();
                model.setActualword(sc.next());
                if (model.getActualword().equals("display") && model.isDebbug()) {
                    System.out.println(model.getWord());
                    prompt();
                    model.setActualword(sc.next());
                }
                if (model.isMessagerror()) {
                    while (!isValidWord()) {
                        System.out.println("Invalid word. Try again");
                        prompt();
                        model.setActualword(sc.next());
                    }
                }
                model.change();
                applyColors();
            }
            newgame();
        }
        System.out.println("Thank you for playing with us!");
    }

    private static void applyColors()
    {
        for(int i = 0; i < 5; i++)
        {
            switch (model.getColors()[i]) {
                case 0 ->
                        //see for the change of colors
                        System.out.print(0);
                case 1 -> System.out.print(1);
                case 2 -> System.out.print(2);
                default -> System.out.print("Houston, got a problem");
            }
        }
    }

    private static boolean isValidWord() throws FileNotFoundException {
        File file = new File("C:\\Users\\colin\\Wordle\\src\\wordle\\common.txt");
        Scanner sc;
        sc = new Scanner(file);
        return isWordCorrect(sc);
    }

    private static boolean isWordCorrect(Scanner sc) throws FileNotFoundException {
        boolean found = false;
        while(!found && sc.hasNextLine())
        {
            found = model.getActualword().equals(sc.nextLine());
        }
        return found;
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
        System.out.println(" Welcome to Wordle ! \n Hope you enjoy your time with us. \n Are you ready Player One?");
        System.out.println(" Codded by Thanatos ");
        System.out.println(" This game present different option: \n First, you can choose to have a random word" +
                "or to use the pre-selected word (I'm not gonna give it to you :) ) \n" +
                " Then, you can choose to have a display mode: the selected word will be prompt on the console when you" +
                "tape \"display\" at the beginnig of a turn.\n" +
                " Finally, the last flag will ...\n");
        System.out.println(" Ready to crack the word ?\n");
    }

    private static void inputFlag()
    {
        System.out.println("Do you want to have a random word ?(y/n)");
        prompt();
        String input = getInput();
        setupflags(0,input);
        System.out.println("Do you want to see the word ? ");
        prompt();
        input = getInput();
        setupflags(1,input);
        System.out.println("Do you want to have an error message ?");
        prompt();
        input = getInput();
        setupflags(2,input);
    }

    private static String getInput()
    {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        while(!input.equals("y") && !input.equals("n"))
        {
            prompt();
            input = scanner.next();
        }
        return input;
    }

    private static void prompt()
    {
        System.out.print(">> ");
    }

    private static void setupflags(int number, String answer)
    {
        switch (number) {
            case 0 -> model.setRandomword(answer.equals("y"));
            case 1 -> model.setDebbug(answer.equals("y"));
            case 2 -> model.setMessagerror(answer.equals("y"));
            default -> {
            }
            //nothing
        }
    }

    private static void initialiseWordle() throws FileNotFoundException {
        model = new WGModel();
        inputFlag();
        model.initialise();
    }

    private static void newgame()
    {
        Scanner sc = new Scanner(System.in);
        if(model.isWin())
        {
            System.out.println("Congratulation ! You won !");
        }
        else
        {
            System.out.println("I'm sorry, you lost...");
        }
        System.out.println("Do you want to play again ?(y/n)");
        prompt();
        String input = sc.next();
        while(!input.equals("y") && !input.equals("n"))
        {
            input = sc.next();
        }
        endgame = input.equals("n");
    }


}
