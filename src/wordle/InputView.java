package wordle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import java.awt.*;
public class InputView implements Observer{

    private static final Dimension PANEL_SIZE = new Dimension(250,250);
    private final JButton yes;
    private final JButton no;
    private final JButton newgame;


    private boolean random;
    private boolean error;
    private boolean debbug;
    public static boolean isnewgame;
    private JFrame frame;
    private JPanel panel;
    private final JLabel textondisplay;

    private WGModel model;
    private WGView view;


    //must handle: flags, endgame screen
    //smaller panel in front of the game one
    //first frame visible at the beginning
    public InputView()
    {
        this.setFrame(new JFrame("Play Mode"));
        getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getFrame().setSize(300,300);
        getFrame().setResizable(false);
        getFrame().setLocationRelativeTo(null);
        getFrame().setVisible(true);

        this.newgame = new JButton("New Game");
        this.textondisplay = new JLabel("", SwingConstants.CENTER);
        textondisplay.setText("Do you want to have a random word ?");

        this.yes  = new JButton("Yes");
        this.no = new JButton("No");
        isnewgame = false;

        createPanel();
        frame.setContentPane(panel);
        frame.pack();


    }

    private void createPanel()
    {
        this.panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JPanel panelnorth = new JPanel();
        panelnorth.setLayout(new BorderLayout());
        panelnorth.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        JPanel panelsouth = new JPanel();
        panelsouth.setLayout(new BorderLayout());
        panelsouth.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

        panel.add(panelnorth, BorderLayout.NORTH);
        panel.add(panelsouth,BorderLayout.SOUTH);
        panelsouth.add(new JPanel().add(yes), BorderLayout.WEST);
        panelsouth.add(new JPanel().add(no), BorderLayout.EAST);


        panelnorth.add(textondisplay,BorderLayout.CENTER);

        panelsouth.add(newgame,BorderLayout.CENTER);
        newgame.setVisible(false);

        panelnorth.setPreferredSize(PANEL_SIZE);

        textondisplay.setVisible(true);
        yes.setVisible(true);
        no.setVisible(true);

        yes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textondisplay.getText().equals("Do you want to have a random word ?")) {
                    randomWord(true);
                    textondisplay.setText("Do you want to use the debbug mode ?");
                    getFrame().repaint();
                } else if (textondisplay.getText().equals("Do you want to use the debbug mode ?")) {
                    debbugMode(true);
                    textondisplay.setText("Do you want to use the error mode ?");
                    getFrame().repaint();
                } else if (textondisplay.getText().equals("Do you want to use the error mode ?")) {
                    try {
                        displayError(true);
                    } catch (InterruptedException | FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    getFrame().repaint();
                }
            }
        });

        no.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textondisplay.getText().equals("Do you want to have a random word ?")) {
                    randomWord(false);
                    textondisplay.setText("Do you want to use the debbug mode ?");
                    getFrame().repaint();
                } else if (textondisplay.getText().equals("Do you want to use the debbug mode ?")) {
                    debbugMode(false);
                    textondisplay.setText("Do you want to use the error mode ?");
                    getFrame().repaint();
                } else if (textondisplay.getText().equals("Do you want to use the error mode ?")) {
                    try {
                        displayError(false);
                    } catch (InterruptedException | FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    getFrame().repaint();
                }
            }
        });

        newgame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.getPanel().setVisible(false);
                textondisplay.setText("Do you want to have a random word ?");
                yes.setVisible(true);
                no.setVisible(true);
                newgame.setVisible(false);
                getFrame().repaint();
            }
        });

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
        this.random = random;
    }

    private void debbugMode(boolean debbug)
    {
        this.debbug = debbug;
    }

    private void displayError(boolean error) throws InterruptedException, FileNotFoundException {
        this.error = error;
        getFrame().setVisible(false);
        yes.setVisible(false);
        no.setVisible(false);
        textondisplay.setVisible(false);
        frame.repaint();
        startGame();
    }

    public void startGame() throws InterruptedException, FileNotFoundException {
        model = new WGModel();
        WGController controller = new WGController(model);
        model.addObserver(this);
        controller.setErrorFlag(error);
        controller.setRandomFlag(random);
        controller.setDebbugFlag(debbug);
        controller.initialise();
        view = new WGView(model, controller);
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public void update(Observable o, Object arg) {
        if(model.isNewgame())
        {
            endgame();
        }

    }
}
