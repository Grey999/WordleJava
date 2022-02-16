package wordle;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import java.awt.*;
public class InputView implements Observer{
    private Button yes;
    private Button no;
    private Button newgame;
    private WGModel model;
    private JFrame frame;
    private TextArea textondisplay;


    //must handle: randomword, endgame screen
    //smaller panel in front of the game one
    //first frame visible at the beginning
    public InputView(WGModel model)
    {
        this.model = model;
        this.frame = new JFrame("Play Mode");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(200,200);
        frame.setResizable(false);
        frame.setVisible(true);

        initialiseview();

        model.addObserver(this);
        update(model,null);

    }

    public void initialiseview()
    {
        //initialise with two buttons
        this.yes  = new Button("Yes");
        this.no = new Button("No");
        yes.addActionListener((ActionEvent e) -> randomword(true));
        no.addActionListener((ActionEvent e) -> randomword(false));
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

    public void randomword(boolean random)
    {
        model.setRandomword(random);
        frame.setVisible(false);
        yes.setVisible(false);
        no.setVisible(false);
        textondisplay.setVisible(false);
    }


    @Override
    public void update(Observable o, Object arg) {
        if(model.isNewgame())
        {
            endgame();
        }

    }
}
