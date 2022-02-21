package wordle;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import java.awt.*;
public class InputView implements Observer{

    //Create method for the other flags (reread project paper)
    private static final Dimension PANEL_SIZE = new Dimension(350,350);
    private JButton yes;
    private JButton no;
    private JButton newgame;

    private WGController controller;

    private WGModel model;
    private JFrame frame;
    private JPanel panel;
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
        frame.setSize(400,400);
        frame.setResizable(false);
        frame.setVisible(true);

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
        createPanel();
        contentPane.add(panel);

        displayView(0);

        model.addObserver(this);
        update(model,null);

    }

    public void createPanel()
    {
        panel = new JPanel();
        panel.setLayout(new GridLayout(4,2));

        textondisplay.setEditable(false);
        panel.add(yes);
        panel.add(no);
        panel.add(newgame);
        panel.add(textondisplay);
        //place button

        panel.setPreferredSize(PANEL_SIZE);

    }

    public void displayView(int viewnumber)
    {
        this.yes  = new JButton("Yes");
        this.no = new JButton("No");
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
        textondisplay.setVisible(true);
        yes.setVisible(true);
        no.setVisible(true);
        frame.repaint();
    }

    public void endgame()
    {
        frame.setVisible(true);
        textondisplay.setVisible(true);
        this.textondisplay = new TextArea();
        if(model.isWin())
        {
            textondisplay.setText("You win ! \n Do you want to play again ?");
        }
        else
        {
            textondisplay.setText("You lose ! \n Do you want to play again ?");
        }
        this.newgame = new JButton("New Game");
        newgame.setVisible(true);
        //place button and text
        frame.repaint();
    }

    public void randomWord(boolean random)
    {
        controller.setRandom(random);
        displayView(1);
    }

    public void debbugMode(boolean debbug)
    {
        controller.setDebbug(debbug);
        displayView(2);
    }

    public void displayError(boolean error)
    {
        controller.setError(error);
        frame.setVisible(false);
        yes.setVisible(false);
        no.setVisible(false);
        textondisplay.setVisible(false);
    }


    @Override
    public void update(Observable o, Object arg) {
        if(model.isNewgame()) //changer pour controller
        {
            endgame();
        }

    }
}
