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

        createFirstView(0);

        model.addObserver(this);
        update(model,null);

    }

    public void createFirstView(int viewnumber)
    {
        //make a switch using view number
        //just change the textondisplay in function of it
        //and modify the method call by yes and no
        //initialise with two buttons
        this.yes  = new Button("Yes");
        this.no = new Button("No");
        yes.addActionListener((ActionEvent e) -> randomWord(true));
        no.addActionListener((ActionEvent e) -> randomWord(false));
        this.textondisplay = new TextArea("Do you want to have a random word ?");
        textondisplay.setEditable(false);
        textondisplay.setVisible(true);
        yes.setVisible(true);
        no.setVisible(true);
        //place the button correctly
    }

    public void endgame()
    {
        //how to link model to this function ?
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
        //call next view
    }

    public void debbugMode(boolean debbug)
    {
        //TODO
    }

    public void displayError(boolean error)
    {
        //TODO
    }


    @Override
    public void update(Observable o, Object arg) {
        if(model.isNewgame())
        {
            endgame();
        }

    }
}
