package wordle;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class WGCli {
    //build the entire CLI version using only controller and model
    private static WGModel model;
    private static boolean endgame;
    protected static int[] letters;
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
                takeinput(sc);
                boolean wordcorrect = wordaccept();
                while(!wordcorrect)
                {
                    takeinput(sc);
                    wordcorrect = wordaccept();
                }
                model.change();
                applyColors();
                changeletters();
                displayletters();
            }
            newgame();
        }
        sc.close();
        System.out.println("Thank you for playing with us!");
    }

    private static boolean wordaccept() throws FileNotFoundException {
        if(model.getActualword().length() != 5)
        {
            System.out.println("Error: only take words of 5 letters. Try again");
            return false;
        }
        for(int i = 0; i < model.getActualword().length(); i++)
        {
            int current = model.getActualword().charAt(i);
            if(current < 65 || current > 91 && current < 97 || current > 123)
            {
                System.out.println("Error: the game only accept letters.");
                return false;
            }
        }
        if (model.isMessagerror()) {
            if (!model.isValidWord()) {
                System.out.println("Error: the word isn't accept by the game. Try again");
                return false;
            }
        }
        return true;
    }

    private static void takeinput(Scanner sc)
    {
        prompt();
        model.setActualword(sc.next());
        boolean rightinput = verifyInput();
        while(!rightinput)
        {
            prompt();
            model.setActualword(sc.next());
            rightinput = verifyInput();
        }
    }

    private static boolean verifyInput()
    {
        if (model.getActualword().equals("display") && model.isDebbug()) {
            System.out.println(model.getWord());
            return false;
        }
        if(model.getActualword().equals("help"))
        {
            System.out.println("Write the help of the game");
            return false;
        }
        return true;
    }

    private static void displayletters()
    {
        System.out.println("The letter you haven't use yet: ");
        displaytheletter(0);
        System.out.println();
        System.out.println("The letters that belong to the word and are in the right place:");
        System.out.print(CColor.GREEN);
        displaytheletter(1);
        System.out.println();
        System.out.print(CColor.RESET);
        System.out.println("The letters that belong to the word but are not in the right place: ");
        System.out.print(CColor.YELLOW);
        displaytheletter(2);
        System.out.println();
        System.out.print(CColor.RESET);
        System.out.println("The letter that doesn't belong tot the word");
        System.out.print(CColor.RED);
        displaytheletter(3);
        System.out.println();
        System.out.print(CColor.RESET);
    }

    //review this part
    private static void displaytheletter(int value)
    {
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

    private static void changeletters()
    {
        int current;
        for(int i = 0; i < model.getActualword().length(); i++)
        {
            current = (model.getActualword().charAt(i) - 'a')%26;
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
                case 0 -> {
                    System.out.print(CColor.RED_BOLD);
                    System.out.print(model.getActualword().charAt(i));
                    System.out.print(CColor.RESET);
                }
                case 1 -> {
                    System.out.print(CColor.GREEN_BOLD);
                    System.out.print(model.getActualword().charAt(i));
                    System.out.print(CColor.RESET);
                }
                case 2 -> {
                    System.out.print(CColor.YELLOW_BOLD);
                    System.out.print(model.getActualword().charAt(i));
                    System.out.print(CColor.RESET);
                }
                default -> System.out.print("Houston, got a problem");
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
            System.out.println("                              .''.       \n" +
                    "       .''.      .        *''*    :_\\/_:     . \n" +
                    "      :_\\/_:   _\\(/_  .:.*_\\/_*   : /\\ :  .'.:.'.\n" +
                    "  .''.: /\\ :   ./)\\   ':'* /\\ * :  '..'.  -=:o:=-\n" +
                    " :_\\/_:'.:::.    ' *''*    * '.\\'/.' _\\(/_'.':'.'\n" +
                    " : /\\ : :::::     *_\\/_*     -= o =-  /)\\    '  *\n" +
                    "  '..'  ':::'     * /\\ *     .'/.\\'.   '\n" +
                    "      *            *..*         :\n" +
                    "       *\n" +
                    "        *");
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
