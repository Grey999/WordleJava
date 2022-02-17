package wordle;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import java.awt.*;
public class InputView implements Observer{

    //Create method for the other flags (reread project paper)

    private Button yes;
    private WGController controller;
    private Button no;
    private Button newgame;
    private WGModel model;
    private JFrame frame;
    private TextArea textondisplay;


    //must handle: randomword, endgame screen
    //smaller panel in front of the game one
    //first frame visible at the beginning
    public InputView(WGModel model, WGController controller)
    {
        this.controller = controller;
        this.model = model;
        this.frame = new JFrame("Play Mode");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(200,200);
        frame.setResizable(false);
        frame.setVisible(true);

        displayView(0);

        model.addObserver(this);
        update(model,null);

    }

    public void displayView(int viewnumber)
    {
        this.yes  = new Button("Yes");
        this.no = new Button("No");
        switch (viewnumber)
        {
            case 0:
                yes.addActionListener((ActionEvent e) -> randomWord(true));
                no.addActionListener((ActionEvent e) -> randomWord(false));
                this.textondisplay = new TextArea("Do you want to have a random word ?");
                break;
            case 1:
                yes.addActionListener((ActionEvent e) -> debbugMode(true));
                no.addActionListener((ActionEvent e) -> debbugMode(false));
                this.textondisplay = new TextArea("Do you want to use the debbug mode ?");
                break;
            case 2:
                yes.addActionListener((ActionEvent e) -> displayError(true));
                no.addActionListener((ActionEvent e) -> displayError(false));
                this.textondisplay = new TextArea("Do you want to play with the error mode?");

        }

        textondisplay.setEditable(false);
        textondisplay.setVisible(true);
        yes.setVisible(true);
        no.setVisible(true);
        //place the button correctly
    }

    public void endgame()
    {
        this.textondisplay = new TextArea();
        if(model.isWin())
        {
            textondisplay.setText("You win ! \n Do you want to play again ?");
        }
        else
        {
            textondisplay.setText("You lose ! \n Do you want to play again ?");
        }
        this.newgame = new Button("New Game");
        newgame.setVisible(true);
        //place button and text
    }

    public void randomWord(boolean random)
    {
        controller.setRandom(random);
        displayView(1);
    }

    public void debbugMode(boolean debbug)
    {
        //TODO
        displayView(2);
    }

    public void displayError(boolean error)
    {
        //TODO
    }


    @Override
    public void update(Observable o, Object arg) {
        if(model.isNewgame()) //changer pour controller
        {
            endgame();
        }

    }
}
