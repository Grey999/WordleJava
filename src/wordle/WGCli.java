package wordle;

import java.io.FileNotFoundException;
import java.util.Scanner;

import static wordle.WGModel.*;

//Main Program for the CLI Version
//Use WGModel
public class WGCli {
    //Declaration of the model
    private static WGModel model;

    //Boolean use to ask for a newgame
    private static boolean endgame;

    //Array which contains the state of each letter
    //Each number correspond to a color, as describe on the model
    //this int[] will be use to display the letter used or not by the player
    //and their status on the game:
    //  -Not used
    //  -Used, belong to the word and is on the correct spot
    //  -Used, belong to the word and is not on the correct spot
    //  -Used but doesn't belong to the word
    protected static int[] letters;

    //String use to display the colors on the CMD
    protected static final String COLOR_RESET = "\033[0m";
    protected static final String COLOR_RED = "\033[0;31m";
    protected static final String COLOR_GREEN = "\033[0;32m";
    protected static final String COLOR_YELLOW = "\033[0;33m";
    protected static final String COLOR_RED_BOLD = "\033[1;31m";
    protected static final String COLOR_GREEN_BOLD = "\033[1;32m";
    protected static final String COLOR_YELLOW_BOLD = "\033[1;33m";
    protected static final String COLOR_YELLOW_BRIGHT = "\033[0;93m";



    //Main Method
    //Handle the game and the possibility to replay
    public static void main(String[] args) throws FileNotFoundException {
        //declaration letters
        letters = new int[26];
        mainScreen();
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
                            //Not supposed to happen
                        }
                    }
                    //ask for a new input
                    takeInput(sc);
                    wordcorrect = model.isWordAccept();
                }
                //the model change
                model.change();
                //display the change of the model by displaying
                // the word on try and the letters
                // with the correct colors
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
        //Display at the beginning of the game
        System.out.print(COLOR_YELLOW);
        System.out.println("""

                ██╗    ██╗ ██████╗ ██████╗ ██████╗ ██╗     ███████╗
                ██║    ██║██╔═══██╗██╔══██╗██╔══██╗██║     ██╔════╝
                ██║ █╗ ██║██║   ██║██████╔╝██║  ██║██║     █████╗ \s
                ██║███╗██║██║   ██║██╔══██╗██║  ██║██║     ██╔══╝ \s
                ╚███╔███╔╝╚██████╔╝██║  ██║██████╔╝███████╗███████╗
                 ╚══╝╚══╝  ╚═════╝ ╚═╝  ╚═╝╚═════╝ ╚══════╝╚══════╝
                """);
        System.out.print(COLOR_RESET);
        System.out.println("""
                 Welcome to Wordle !
                 Hope you enjoy your time with us.
                 Are you ready Player One ?
                 
                 This game present different option:\s
                 First, you can choose to have a random word or to use the pre-selected word (I'm not gonna give it to you :) )\s
                 Then, you can choose to have a display mode: the selected word will be prompt on the console when you tape "display" as an input.
                 Finally, the last flag will display an error message and will let you give another word if you input is not in the wordle database. 
                 Choose wisely.
                 
                 Ready to crack the word ?
                 
                 Coded by Thanatos
                """);
    }

    private static void prompt()
    {
        //display the prompt
        //will be used every time we ask for an input
        System.out.print(">> ");
    }

    private static String getInput()
    {
        //use to ask for the flags
        //handle only 'y' and 'n' as an answer
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
        //set the flags necessary for the model
        switch (number) {
            case 0 -> model.setRandomflag(answer.equals("y"));
            case 1 -> model.setDebbugflag(answer.equals("y"));
            case 2 -> model.setErrorflag(answer.equals("y"));
            default -> {
                //shouldn't happen because of the getInput()
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
        System.out.println("Do you want to see the word ?(y/n) ");
        prompt();
        input = getInput();
        setUpFlags(1,input);
        System.out.println("Do you want to have an error message ?(y/n)");
        prompt();
        input = getInput();
        setUpFlags(2,input);
    }

    private static void initialiseWordle() throws FileNotFoundException {
        //create a new WGModel
        model = new WGModel();
        //call for the flag to be set
        inputFlags();
        //call the initialisation of wordle
        model.initialise();
        //set the word depending on the state of the randomflag
        model.setWordtoGuess();
    }

    private static void takeInput(Scanner sc)
    {
        //Loop to take a valid input
        //use when asking for a word
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
        //change the state of the letters
        //depend of the letters in the lastword
        //look for the correct spot on the letters (in alphabetic order)
        //then associate the int in colors[] depending on the index
        int current;
        for(int i = 0; i < model.getLastword().length(); i++)
        {
            //give us the index in letters
            current = (model.getLastword().charAt(i) - 'a')%26;
            if(letters[current] == GRAY)
            {
                if(model.getColors()[i] != GRAY) {
                    letters[current] = model.getColors()[i];
                }
            }
            else {
                if (letters[current] == ORANGE) {
                    if (model.getColors()[i] == GREEN) {
                        //need to be change from ORANGE to GREEN because the player
                        //find the right spot for the letter
                        letters[current] = model.getColors()[i];
                    }
                }
            }
        }
    }

    private static void displayTheLetter(int color)
    {
        //color = GRAY or GREEN or RED or ORANGE (constant in the WGModel)
        //Look for the correct letters depending on the category we want
        //use in displayLetters()
        char current;
        for(int i =0; i < letters.length; i++)
        {
            if(letters[i] == color)
            {
                current = (char)(i+65);
                System.out.print(current);
            }
        }
    }

    private static void applyColors()
    {
        //use to display the word with the correct colors
        for(int i = 0; i < 5; i++)
        {
            //look for the right String to print before the letter
            switch (model.getColors()[i]) {
                case RED -> {
                    System.out.print(COLOR_RED_BOLD);
                }
                case GREEN -> {
                    System.out.print(COLOR_GREEN_BOLD);
                }
                case ORANGE -> {
                    System.out.print(COLOR_YELLOW_BOLD);
                }
                default -> System.out.print("not suppose to happen");
            }
            //print the letter of the lastword
            System.out.print(model.getLastword().charAt(i));
            //use to reset the colors to normal
            System.out.print(COLOR_RESET);
        }
        System.out.println();
    }

    private static void displayLetters()
    {
        //will display the letter corresponding to each category
        System.out.println("The letter you haven't use yet: ");
        displayTheLetter(GRAY);
        System.out.println();
        System.out.println("The letters that belong to the word and are in the right place:");
        System.out.print(COLOR_GREEN);
        displayTheLetter(GREEN);
        System.out.println();
        System.out.print(COLOR_RESET);
        System.out.println("The letters that belong to the word but are not in the right place: ");
        System.out.print(COLOR_YELLOW);
        displayTheLetter(ORANGE);
        System.out.println();
        System.out.print(COLOR_RESET);
        System.out.println("The letter that doesn't belong tot the word");
        System.out.print(COLOR_RED);
        displayTheLetter(RED);
        System.out.println();
        System.out.print(COLOR_RESET);
    }

    private static void newgame()
    {
        //Display the endgame screen and ask for a new game
        for(int i = 0; i < 4; i++)
        {
            System.out.println();
        }
        System.out.println();
        Scanner sc = new Scanner(System.in);
        if(model.isWin())
        {
            System.out.println("Congratulation ! You won !");
            System.out.print(COLOR_YELLOW_BRIGHT);
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
            System.out.print(COLOR_RED);
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
        System.out.print(COLOR_RESET);
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
