package wordle;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import java.awt.*;
public class InputView implements Observer{

    //Create method for the other flags (reread project paper)
    private static final Dimension PANEL_SIZE = new Dimension(600,600);
    private final JButton yes;
    private final JButton no;
    private final JButton newgame;

    private final WGController controller;

    private final WGModel model;
    private JFrame frame;
    private JPanel panel;
    private final JLabel textondisplay;


    //must handle: randomword, endgame screen
    //smaller panel in front of the game one
    //first frame visible at the beginning
    public InputView(WGModel model, WGController controller)
    {
        this.controller = controller;
        this.model = model;
        this.setFrame(new JFrame("Play Mode"));
        getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getFrame().setSize(700,700);
        getFrame().setResizable(false);
        getFrame().setLocationRelativeTo(null);
        getFrame().setVisible(true);

        this.yes  = new JButton("Yes");
        this.no = new JButton("No");
        this.newgame = new JButton("New Game");
        this.textondisplay = new JLabel();
        createPanel();
        frame.setContentPane(panel);
        frame.pack();

        model.addObserver(this);

        displayView(0);

        update(model,null);

    }

    private void createPanel()
    {
        this.panel = new JPanel();
        JPanel panelnorth = new JPanel();
        panelnorth.setLayout(new BorderLayout());
        panelnorth.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JPanel panelsouth = new JPanel();
        panelsouth.setLayout(new BorderLayout());
        panelsouth.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        panel.add(panelnorth, BorderLayout.CENTER);
        panel.add(panelsouth,BorderLayout.SOUTH);


        panelnorth.add(textondisplay,BorderLayout.CENTER);


        panelsouth.add(yes, BorderLayout.WEST);


        panelsouth.add(no,BorderLayout.EAST);


        panelsouth.add(newgame,BorderLayout.CENTER);
        newgame.setVisible(false);

        panelnorth.setPreferredSize(PANEL_SIZE);

    }

    private void displayView(int viewnumber)
    {

        textondisplay.setVisible(true);
        yes.setVisible(true);
        no.setVisible(true);
        switch (viewnumber) {
            case 0 -> {
                yes.addActionListener((ActionEvent e) -> randomWord(true));
                no.addActionListener((ActionEvent e) -> randomWord(false));
                this.textondisplay.setText("Do you want to have a random word ?");
                getFrame().repaint();
            }
            case 1 -> {
                yes.addActionListener((ActionEvent e) -> debbugMode(true));
                no.addActionListener((ActionEvent e) -> debbugMode(false));
                this.textondisplay.setText("Do you want to use the debbug mode ?");
                getFrame().repaint();
            }
            case 2 -> {
                yes.addActionListener((ActionEvent e) -> displayError(true));
                no.addActionListener((ActionEvent e) -> displayError(false));
                this.textondisplay.setText("Do you want to use the error mode ?");
                getFrame().repaint();
            }
            default -> {
                this.textondisplay.setText("Problem");
            }


        }

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
