package wordle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import java.awt.*;
import static wordle.WGModel.*;

//The view of the game
//Will be created with a GridView and a KeyBoardView

public class WGView implements Observer
{
    //Declaration of the constant
    private static final Dimension PANEL_SIZE = new Dimension(500,500);

    //Declaration of the variables
    private final WGController controller;
    private JFrame frame;
    private JPanel panel;
    private JButton newgame;

    //GridView and KeyBoardView
    private GridView grid;
    private KeyboardView keyboard;


    public WGView(WGModel model, WGController controller) throws InterruptedException, FileNotFoundException {
        //Link the model, the controller and the view
        this.controller = controller;
        controller.setView(this);
        model.addObserver(this);


        //Creation of the Frame
        setFrame(new JFrame("Wordle"));
        getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getFrame().setFocusable(false);

        //creation of grid and the keyboard
        setGrid(new GridView(this));
        setKeyboard(new KeyboardView(this));

        //Creation of the panels and the visual elements
        createControls();

        //update of the view
        update(model,null);
    }

    @Override
    public void update(Observable o, Object arg) {
        //update the grid and the keyboard before updating the frame
        //only do it after the first model change
        if(!getController().isFirstflag()) {
            getGrid().changeBackgroundColor((getController().getGuess()-1)*5);
            getKeyboard().changeBackGroundColor();
            getController().enableNewGame();
        }
        getFrame().repaint();
    }

    private void createPanel() {
        //create the panel and place the visual element
        setPanel(new JPanel());
        getPanel().setLayout(new BoxLayout(getPanel(), BoxLayout.Y_AXIS));
        getPanel().setBackground(Color.GRAY);
        getPanel().setSize(PANEL_SIZE);
        getPanel().setFocusable(false);
        JLabel title = new JLabel("WORDLE", SwingConstants.CENTER);
        title.setBackground(Color.WHITE);
        title.setSize(new Dimension(40,40));
        getPanel().add(title);
        getPanel().add(getGrid().getPanel(), BorderLayout.NORTH);
        JPanel invisible = new JPanel();
        invisible.setSize(new Dimension(800,800));

        //Creation of the button dispose on the invisible panel
        //Creation of the display button
        // Only if debbugflag is up
        if(getController().isDebbugflag())
        {
            JButton display = new JButton("display");
            display.setFocusable(false);
            display.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(getFrame(), getController().getWordtoguess(),
                            "Word to find", JOptionPane.INFORMATION_MESSAGE);
                }
            });
            invisible.add(display);
        }
        //Creation of the newgame button
        //will appear after the first change of the model
        //allow to ask for a newgame during the current game
        setNewgame(new JButton("newgame"));
        getNewgame().setFocusable(false);
        getNewgame().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getFrame().setVisible(false);
                getController().setNewGame();

            }
        });
        getNewgame().setVisible(false);
        invisible.add(getNewgame());


        invisible.setBackground(Color.GRAY);
        getPanel().add(invisible,BorderLayout.SOUTH);
        getPanel().add(getKeyboard().getPanel(), BorderLayout.SOUTH);
    }

    private void createControls() {
        //Creation of the container
        Container contentPane = getFrame().getContentPane();
        contentPane.setBackground(Color.black);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));

        //Method to create the panel
        createPanel();
        contentPane.add(getPanel());


        getFrame().pack();
        getFrame().setSize(600,750);
        getFrame().setResizable(false);
        getFrame().setVisible(true);
    }

    protected void showErrorPannel() throws FileNotFoundException {
        //Only if the error flag is up
        //Display a message to say the word on try is not on the list
        JOptionPane.showMessageDialog( getFrame(), "Word not found",
                "Error: Word not Found",JOptionPane.ERROR_MESSAGE);
    }

    protected Color applyColor(int index)
    {
        //Switch who return the color depending on the state in Colors[] of WGModel
        //Use the constants in WGModel to return the right color
        //Use in GridView and KeyBoardView in changeBackGroundColor()
        switch (getController().getColors()[index]) {
            case RED -> {
                return Color.RED;
            }
            case GREEN -> {
                return Color.GREEN;
            }
            case ORANGE -> {
                return Color.ORANGE;
            }
            default -> {
                //not supposed to happen
                return Color.GRAY;
            }
        }
    }


    //Getters and Setters
    public WGController getController(){return controller;}

    public GridView getGrid() {
        return grid;
    }

    public void setGrid(GridView grid) {
        this.grid = grid;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public KeyboardView getKeyboard() {
        return keyboard;
    }

    public void setKeyboard(KeyboardView keyboard) {
        this.keyboard = keyboard;
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public JButton getNewgame() {
        return newgame;
    }

    public void setNewgame(JButton newgame) {
        this.newgame = newgame;
    }
}
