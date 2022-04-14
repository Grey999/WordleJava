package wordle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import java.awt.*;

//First frame to appear to the player
//Will ask the player for the flags they want to play with
//Then, will initialise the model, the controller and the view according to it

//At the end of the game, will display a message to say if the player win or lose
//Then will ask for a newgame or to exit

public class FirstView implements Observer{

    private static final Dimension PANEL_SIZE = new Dimension(250,250);
    private final JButton yes;
    private final JButton no;
    private final JButton newgame;
    private final JButton exit;


    private boolean randomflag;
    private boolean errorflag;
    private boolean debbugflag;
    public static boolean isnewgame;
    private JFrame frame;
    private JPanel panel;
    private final JLabel textondisplay;

    private WGModel model;
    private WGView view;


    //must handle: flags, endgame screen
    //first frame visible at the beginning
    public FirstView()
    {
        //Creation of the frame
        this.setFrame(new JFrame("Play Mode"));
        getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getFrame().setSize(300,300);
        getFrame().setResizable(false);
        getFrame().setLocationRelativeTo(null);
        getFrame().setVisible(true);

        //Creation of the final visual element
        this.newgame = new JButton("New Game");
        this.exit = new JButton("Exit");
        this.textondisplay = new JLabel("", SwingConstants.CENTER);
        textondisplay.setText("Do you want to have a random word ?");

        this.yes  = new JButton("Yes");
        this.no = new JButton("No");
        isnewgame = false;

        createPanel();
        frame.setContentPane(panel);
        frame.pack();


    }

    @Override
    public void update(Observable o, Object arg) {
        //update to see if the game is ended or have been asked to end
        if(model.isNewgame())
        {
            endGame();
        }

    }

    private void createPanel()
    {
        //Creation of the main panel
        this.panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        //Creation of the subpanels to contain the buttons
        JPanel panelnorth = new JPanel();
        panelnorth.setLayout(new BorderLayout());
        panelnorth.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        JPanel panelsouth = new JPanel();
        panelsouth.setLayout(new BorderLayout());
        panelsouth.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

        //Add the different visuals element to the subpanels
        panelnorth.add(textondisplay,BorderLayout.CENTER);
        panelnorth.setPreferredSize(PANEL_SIZE);

        panelsouth.add(new JPanel().add(yes), BorderLayout.WEST);
        panelsouth.add(new JPanel().add(no), BorderLayout.EAST);
        panelsouth.add(newgame,BorderLayout.NORTH);
        panelsouth.add(exit,BorderLayout.SOUTH);

        //Add the subpanels to the main panel
        panel.add(panelnorth, BorderLayout.NORTH);
        panel.add(panelsouth,BorderLayout.SOUTH);

        //Add Action to the buttons
        yes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setUpFlag(true);
            }
        });

        no.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setUpFlag(false);
            }
        });

        newgame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.getFrame().dispose();
                textondisplay.setText("Do you want to have a random word ?");
                yes.setVisible(true);
                no.setVisible(true);
                newgame.setVisible(false);
                exit.setVisible(false);
                getFrame().repaint();
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getFrame().dispose();
                view.getFrame().dispose();
            }
        });

        //Must be visible at the beginning of a game
        textondisplay.setVisible(true);
        yes.setVisible(true);
        no.setVisible(true);

        //Must be visible at the end of a game
        newgame.setVisible(false);
        exit.setVisible(false);

    }

    protected void setUpFlag(boolean bool)
    {
        //Call by yes and no button
        //Update the frame depending on the last sentence of textondisplay
        //Set the right flag according to the bool value
        //Will then change the sentence to the next before repainting the frame
        if (textondisplay.getText().equals("Do you want to have a random word ?")) {
            randomWord(bool);
            textondisplay.setText("Do you want to use the debbug mode ?");
            getFrame().repaint();
        } else if (textondisplay.getText().equals("Do you want to use the debbug mode ?")) {
            debbugMode(bool);
            textondisplay.setText("Do you want to use the error mode ?");
            getFrame().repaint();
        } else if (textondisplay.getText().equals("Do you want to use the error mode ?")) {
            try {
                displayError(bool);
            } catch (InterruptedException | FileNotFoundException ex) {
                ex.printStackTrace();
            }
            getFrame().repaint();
        }
    }

    private void randomWord(boolean random)
    {
        this.randomflag = random;
    }

    private void debbugMode(boolean debbug)
    {
        this.debbugflag = debbug;
    }

    private void displayError(boolean error) throws InterruptedException, FileNotFoundException {
        //Set the last flag before calling the beggining of the game
        this.errorflag = error;
        startGame();
    }

    protected void endFrame()
    {
        //Call to make the first frame disappear
        //Call before the beginning of a new game
        getFrame().setVisible(false);
        yes.setVisible(false);
        no.setVisible(false);
        textondisplay.setVisible(false);
        frame.repaint();
    }

    protected void startGame() throws InterruptedException, FileNotFoundException {
        endFrame();

        //Creation of the newgame
        //Instantiation of the model, the controller and the view
        model = new WGModel();
        WGController controller = new WGController(model);
        model.addObserver(this);
        controller.setErrorFlag(errorflag);
        controller.setRandomFlag(randomflag);
        controller.setDebbugFlag(debbugflag);
        controller.initialise();
        controller.setWordtoGuess();
        view = new WGView(model, controller);
    }

    private void endGame()
    {
        //Call when the game is ended or asked to be ended
        getFrame().setVisible(true);
        textondisplay.setVisible(true);
        if(model.isAsked())
        {
            //the game have been asked to end
            view.getFrame().setVisible(false);
            textondisplay.setText("Do you want to have a random word ?");
            yes.setVisible(true);
            no.setVisible(true);
            newgame.setVisible(false);
            getFrame().repaint();
        }
        else {
            //the player won or lost the game
            if (model.isWin()) {
                textondisplay.setText("You win ! \n Do you want to play again ?");
            } else {
                textondisplay.setText("You lose ! \n Do you want to play again ?");
            }
            //ask for a newgame or to exit the game
            newgame.setVisible(true);
            exit.setVisible(true);
            getFrame().repaint();
        }
    }


    //Getter and Setters
    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
}
