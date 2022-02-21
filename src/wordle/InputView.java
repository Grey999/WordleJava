package wordle;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import java.awt.*;
public class InputView implements Observer{

    //Create method for the other flags (reread project paper)
    private static final Dimension PANEL_SIZE = new Dimension(500,500);
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
        this.setFrame(new JFrame("Play Mode"));
        getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getFrame().pack();
        getFrame().setSize(450,450);
        getFrame().setResizable(false);
        getFrame().setVisible(true);

        Container contentPane = getFrame().getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));

        this.yes  = new JButton("Yes");
        this.no = new JButton("No");
        this.newgame = new JButton("New Game");
        this.textondisplay = new TextArea();
        createPanel();
        contentPane.add(panel);

        model.addObserver(this);

        displayView(0);

        update(model,null);

    }

    private void createPanel()
    {
        panel = new JPanel();
        panel.setLayout(new GridLayout(4,2));

        textondisplay.setEditable(false);
        panel.add(yes);
        panel.add(no);
        panel.add(newgame);
        newgame.setVisible(false);
        panel.add(textondisplay);
        //place button

        panel.setPreferredSize(PANEL_SIZE);

    }

    private void displayView(int viewnumber)
    {

        switch (viewnumber)
        {
            case 0:
                yes.addActionListener((ActionEvent e) -> randomWord(true));
                no.addActionListener((ActionEvent e) -> randomWord(false));
                this.textondisplay.setText("Do you want to have a random word ?");
                break;
            case 1:
                yes.addActionListener((ActionEvent e) -> debbugMode(true));
                no.addActionListener((ActionEvent e) -> debbugMode(false));
                this.textondisplay.setText("Do you want to use the debbug mode ?");
                break;
            case 2:
                this.textondisplay.setText("Do you want to play with the error mode?");
                yes.addActionListener((ActionEvent e) -> displayError(true));
                no.addActionListener((ActionEvent e) -> displayError(false));
                break;
            default:
                //never happen

        }
        textondisplay.setVisible(true);
        yes.setVisible(true);
        no.setVisible(true);
        getFrame().repaint();
    }

    private void endgame()
    {
        getFrame().setVisible(true);
        textondisplay.setVisible(true);
        if(model.isWin())
        {
            textondisplay.setText("You win ! \n Do you want to play again ?");
        }
        else
        {
            textondisplay.setText("You lose ! \n Do you want to play again ?");
        }
        newgame.setVisible(true);
        //place button and text
        getFrame().repaint();
    }

    private void randomWord(boolean random)
    {
        controller.setRandom(random);
        displayView(1);
    }

    private void debbugMode(boolean debbug)
    {
        controller.setDebbug(debbug);
        displayView(2);
    }

    private void displayError(boolean error)
    {
        controller.setError(error);
        getFrame().setVisible(false);
        yes.setVisible(false);
        no.setVisible(false);
        textondisplay.setVisible(false);
        startGame();
    }

    public void startGame()
    {
        WGView view = new WGView(model, controller);
    }


    @Override
    public void update(Observable o, Object arg) {
        if(model.isNewgame()) //changer pour controller
        {
            endgame();
        }

    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
}
