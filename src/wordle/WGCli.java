package wordle;

import java.io.FileNotFoundException;
import java.util.Scanner;

import static wordle.WGModel.*;

public class WGCli {
    //Declaration
    private static WGModel model;
    private static boolean endgame;
    protected static int[] letters;


    //Main Method
    public static void main(String[] args) throws FileNotFoundException {
        //declaration variable and rules of the game
        mainScreen();
        letters = new int[26];
        Scanner sc = new Scanner(System.in);
        //first loop to play as many game as you want
        while(!endgame) {
            //initialisation of the model
            initialiseWordle();
            //second loop to play a game
            while (model.getGuess() < 6 && !model.isNewgame()) {
                System.out.println("Enter you word:");
                takeInput(sc);
                boolean wordcorrect = isWordAccept();
                while(!wordcorrect)
                {
                    takeInput(sc);
                    wordcorrect = isWordAccept();
                }
                model.change();
                applyColors();
                changeLetters();
                displayLetters();
            }
            newgame();
        }
        sc.close();
        System.out.println("Thank you for playing with us!");
    }


    private static boolean isWordAccept() throws FileNotFoundException {
        //Handle the possible mistake of the input
        if(model.getPlayerword().length() != 5)
        {
            System.out.println("Error: only take words of 5 letters. Try again");
            return false;
        }
        for(int i = 0; i < model.getPlayerword().length(); i++)
        {
            int current = model.getPlayerword().charAt(i);
            if(current < 65 || current > 91 && current < 97 || current > 123)
            {
                System.out.println("Error: the game only accept letters.");
                return false;
            }
        }
        if (model.isErrorflag()) {
            if (!model.isWordOnList()) {
                System.out.println("Error: the word isn't accept by the game. Try again");
                return false;
            }
        }
        return true;
    }

    private static void takeInput(Scanner sc)
    {
        //Loop to take a valid input
        prompt();
        model.setPlayerword(sc.next());
        boolean rightinput = isSpecialInput();
        while(!rightinput)
        {
            prompt();
            model.setPlayerword(sc.next());
            rightinput = isSpecialInput();
        }
    }

    private static boolean isSpecialInput()
    {
        if (model.getPlayerword().equals("display") && model.isDebbugflag()) {
            System.out.println(model.getWordtoguess());
            return false;
        }
        if(model.getPlayerword().equals("help"))
        {
            //TODO
            System.out.println("Write the help of the game");
            return false;
        }
        return true;
    }

    private static void displayLetters()
    {
        System.out.println("The letter you haven't use yet: ");
        displayTheLetter(0);
        System.out.println();
        System.out.println("The letters that belong to the word and are in the right place:");
        System.out.print(CColor.GREEN);
        displayTheLetter(GREEN);
        System.out.println();
        System.out.print(CColor.RESET);
        System.out.println("The letters that belong to the word but are not in the right place: ");
        System.out.print(CColor.YELLOW);
        displayTheLetter(ORANGE);
        System.out.println();
        System.out.print(CColor.RESET);
        System.out.println("The letter that doesn't belong tot the word");
        System.out.print(CColor.RED);
        displayTheLetter(RED);
        System.out.println();
        System.out.print(CColor.RESET);
    }


    private static void displayTheLetter(int value)
    {
        //Look for the correct letters depeding on the category we want
        char current;
        for(int i =0; i < letters.length; i++)
        {
            if(letters[i] == value)
            {
                current = (char)(i+65);
                System.out.print(current);
            }
        }
    }

    private static void changeLetters()
    {
        int current;
        for(int i = 0; i < model.getPlayerword().length(); i++)
        {
            current = (model.getPlayerword().charAt(i) - 'a')%26;
            if(letters[current] == 0)
            {
                if(model.getColors()[i] != 0) {
                    letters[current] = model.getColors()[i];
                }
                else
                {
                    letters[current] = 3;
                }
            }
        }
    }

    private static void applyColors()
    {
        for(int i = 0; i < 5; i++)
        {
            switch (model.getColors()[i]) {
                case RED -> {
                    System.out.print(CColor.RED_BOLD);
                    System.out.print(model.getPlayerword().charAt(i));
                    System.out.print(CColor.RESET);
                }
                case GREEN -> {
                    System.out.print(CColor.GREEN_BOLD);
                    System.out.print(model.getPlayerword().charAt(i));
                    System.out.print(CColor.RESET);
                }
                case ORANGE -> {
                    System.out.print(CColor.YELLOW_BOLD);
                    System.out.print(model.getPlayerword().charAt(i));
                    System.out.print(CColor.RESET);
                }
                default -> System.out.print("not suppose to happen");
            }
        }
        System.out.println();
    }


    private static void mainScreen()
    {
        System.out.print(CColor.YELLOW);
        System.out.println("""

                ██╗    ██╗ ██████╗ ██████╗ ██████╗ ██╗     ███████╗
                ██║    ██║██╔═══██╗██╔══██╗██╔══██╗██║     ██╔════╝
                ██║ █╗ ██║██║   ██║██████╔╝██║  ██║██║     █████╗ \s
                ██║███╗██║██║   ██║██╔══██╗██║  ██║██║     ██╔══╝ \s
                ╚███╔███╔╝╚██████╔╝██║  ██║██████╔╝███████╗███████╗
                 ╚══╝╚══╝  ╚═════╝ ╚═╝  ╚═╝╚═════╝ ╚══════╝╚══════╝
                """);
        System.out.print(CColor.RESET);
        System.out.println(" Welcome to Wordle ! \n Hope you enjoy your time with us. \n Are you ready Player One ?");
        System.out.println(" Coded by Thanatos ");
        System.out.println(" This game present different option: \n First, you can choose to have a random word" +
                " or to use the pre-selected word (I'm not gonna give it to you :) ) \n" +
                " Then, you can choose to have a display mode: the selected word will be prompt on the console when you" +
                " tape \"display\" at the beginnig of a turn.\n" +
                " Finally, the last flag will display an error message and will let you give another word if you input is not" +
                " in the wordle database. Choose wisely.\n");
        System.out.println(" Ready to crack the word ?\n");
    }

    private static void inputFlags()
    {
        System.out.println("Do you want to have a random word ?(y/n)");
        prompt();
        String input = getInput();
        setUpFlags(0,input);
        System.out.println("Do you want to see the word ? ");
        prompt();
        input = getInput();
        setUpFlags(1,input);
        System.out.println("Do you want to have an error message ?");
        prompt();
        input = getInput();
        setUpFlags(2,input);
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

    private static void setUpFlags(int number, String answer)
    {
        switch (number) {
            case 0 -> model.setRandomflag(answer.equals("y"));
            case 1 -> model.setDebbugflag(answer.equals("y"));
            case 2 -> model.setErrorflag(answer.equals("y"));
            default -> {
                System.err.println("Not supposed to happen");
            }
        }
    }

    private static void initialiseWordle() throws FileNotFoundException {
        model = new WGModel();
        inputFlags();
        model.initialise();
    }

    private static void newgame()
    {
        System.out.println();
        System.out.println();
        System.out.println();
        Scanner sc = new Scanner(System.in);
        if(model.isWin())
        {
            System.out.println("Congratulation ! You won !");
            System.out.print(CColor.YELLOW_BRIGHT);
            System.out.println("""
                                                 .''.      \s
                          .''.      .        *''*    :_\\/_:     .\s
                         :_\\/_:   _\\(/_  .:.*_\\/_*   : /\\ :  .'.:.'.
                     .''.: /\\ :   ./)\\   ':'* /\\ * :  '..'.  -=:o:=-
                    :_\\/_:'.:::.    ' *''*    * '.\\'/.' _\\(/_'.':'.'
                    : /\\ : :::::     *_\\/_*     -= o =-  /)\\    '  *
                     '..'  ':::'     * /\\ *     .'/.\\'.   '
                         *            *..*         :
                          *
                           *""".indent(1));
        }
        else
        {
            System.out.println("I'm sorry, you lost...");
            System.out.print(CColor.RED);
            System.out.println("""
                    ┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼
                    ┼┼┼┼███▀▀▀██┼ ███▀▀▀███┼ ███▀█▄█▀███┼ ██▀▀▀┼┼┼┼
                    ┼┼┼┼██┼┼┼┼██┼ ██┼┼┼┼┼██┼ ██┼┼┼█┼┼┼██┼ ██┼┼┼┼┼┼┼
                    ┼┼┼┼██┼┼┼▄▄▄┼ ██▄▄▄▄▄██┼ ██┼┼┼▀┼┼┼██┼ ██▀▀▀┼┼┼┼
                    ┼┼┼┼██┼┼┼┼██┼ ██┼┼┼┼┼██┼ ██┼┼┼┼┼┼┼██┼ ██┼┼┼┼┼┼┼
                    ┼┼┼┼███▄▄▄██┼ ██┼┼┼┼┼██┼ ██┼┼┼┼┼┼┼██┼ ██▄▄▄┼┼┼┼
                    ┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼
                    ┼┼┼┼███▀▀▀███┼ ▀███┼┼██▀┼ ██▀▀▀┼ ██▀▀▀▀██▄┼┼┼┼┼
                    ┼┼┼┼██┼┼┼┼┼██┼ ┼┼██┼┼██┼┼ ██┼┼┼┼ ██┼┼┼┼┼██┼┼┼┼┼
                    ┼┼┼┼██┼┼┼┼┼██┼ ┼┼██┼┼██┼┼ ██▀▀▀┼ ██▄▄▄▄▄▀▀┼┼┼┼┼
                    ┼┼┼┼██┼┼┼┼┼██┼ ┼┼██┼┼█▀┼┼ ██┼┼┼┼ ██┼┼┼┼┼██┼┼┼┼┼
                    ┼┼┼┼███▄▄▄███┼ ┼┼─▀█▀┼┼─┼ ██▄▄▄┼ ██┼┼┼┼┼██▄┼┼┼┼
                    ┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼
                    ┼┼┼┼┼┼┼┼┼┼┼┼┼┼██┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼██┼┼┼┼┼┼┼┼┼┼┼┼┼┼
                    ┼┼┼┼┼┼┼┼┼┼┼┼████▄┼┼┼▄▄▄▄▄▄▄┼┼┼▄████┼┼┼┼┼┼┼┼┼┼┼┼
                    ┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼▀▀█▄█████████▄█▀▀┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼
                    ┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼█████████████┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼
                    ┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼██▀▀▀███▀▀▀██┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼
                    ┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼██┼┼┼███┼┼┼██┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼
                    ┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼█████▀▄▀█████┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼
                    ┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼███████████┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼
                    ┼┼┼┼┼┼┼┼┼┼┼┼┼┼▄▄▄██┼┼█▀█▀█┼┼██▄▄▄┼┼┼┼┼┼┼┼┼┼┼┼┼┼
                    ┼┼┼┼┼┼┼┼┼┼┼┼┼┼▀▀██┼┼┼┼┼┼┼┼┼┼┼██▀▀┼┼┼┼┼┼┼┼┼┼┼┼┼┼
                    ┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼▀▀┼┼┼┼┼┼┼┼┼┼┼▀▀┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼
                    ┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼
                    """);
        }
        System.out.print(CColor.RESET);
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
