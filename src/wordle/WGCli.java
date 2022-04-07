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
                int wordcorrect = model.isWordAccept();
                while(wordcorrect != 0)
                {
                    switch (wordcorrect) {
                        case 1 -> System.out.println("Error: only take words of 5 letters. Try again");
                        case 2 -> System.out.println("Error: the game only accept letters.");
                        case 3 -> System.out.println("Error: the word isn't accept by the game. Try again");
                        default -> {
                        }
                    }
                    takeInput(sc);
                    wordcorrect = model.isWordAccept();
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

    private static void mainScreen()
    {
        //Display the beggining of the game
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

    private static void prompt()
    {
        //display the prompt
        System.out.print(">> ");
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

    private static void inputFlags()
    {
        //call the method setUpFlags for the three flags
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

    private static void initialiseWordle() throws FileNotFoundException {
        model = new WGModel();
        inputFlags();
        model.initialise();
        model.setWordtoGuess();
    }

    private static void takeInput(Scanner sc)
    {
        //Loop to take a valid input
        prompt();
        model.setPlayerword(sc.next());
        boolean rightinput = isnotSpecialInput();
        while(!rightinput)
        {
            prompt();
            model.setPlayerword(sc.next());
            rightinput = isnotSpecialInput();
        }
    }

    private static boolean isnotSpecialInput()
    {
        //handle the flag debbug and display a reminder of the rules
        if (model.getPlayerword().equals("display") && model.isDebbugflag()) {
            System.out.println(model.getWordtoguess());
            return false;
        }
        if(model.getPlayerword().equals("help"))
        {
            System.out.println("Help of Wordle: \n" +
                    "Rules: You got "+ GUESS+ " to found the right word. A word is composed of 5 letters. \n"+
                    "You can decide of different flags at the begging of the game: You can have" +
                    "a random word or the decided word, have an error message if the world doesn't belong" +
                    "to the lists of possible words and the possibility to see the given word at any moment of " +
                    "the game.\n" +
                    "If you want to see the word, type 'display'.\n");
            return false;
        }
        return true;
    }

    private static void changeLetters()
    {
        int current;
        for(int i = 0; i < model.getLastword().length(); i++)
        {
            current = (model.getLastword().charAt(i) - 'a')%26;
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

    private static void displayTheLetter(int value)
    {
        //Look for the correct letters depending on the category we want
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

    private static void applyColors()
    {
        for(int i = 0; i < 5; i++)
        {
            switch (model.getColors()[i]) {
                case RED -> {
                    System.out.print(CColor.RED_BOLD);
                }
                case GREEN -> {
                    System.out.print(CColor.GREEN_BOLD);
                }
                case ORANGE -> {
                    System.out.print(CColor.YELLOW_BOLD);
                }
                default -> System.out.print("not suppose to happen");
            }
            System.out.print(model.getLastword().charAt(i));
            System.out.print(CColor.RESET);
        }
        System.out.println();
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

    private static void newgame()
    {
        //Display the endgame screen and ask for a new game
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
