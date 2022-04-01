package wordle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import java.awt.*;

import static wordle.WGModel.*;


public class WGView implements Observer
{
    private static final Dimension PANEL_SIZE = new Dimension(500,500);

    private final WGController controller;
    private JFrame frame;
    private JPanel panel;
    private GridView grid;
    private KeyboardView keyboard;


    public WGView(WGModel model, WGController controller) throws InterruptedException, FileNotFoundException {
        //Link the model, the controller and the view
        this.controller = controller;
        controller.setView(this);

        setFrame(new JFrame("Wordle"));
        getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //creation of grid and the keyboard
        setGrid(new GridView(this));
        setKeyboard(new KeyboardView(this));


        createControls();
        model.addObserver(this);
        update(model,null);
    }

    @Override
    public void update(Observable o, Object arg) {
        //update the grid and the keyboard
        if(!getController().isFirstflag()) {
            getGrid().changeBackgroundColor((getController().getGuess()-1)*5);
            getKeyboard().changeBackGroundColor();

            getController().setPlayerWord("");
        }
        getFrame().repaint();
    }

    private void createPanel() {
        setPanel(new JPanel());
        getPanel().setLayout(new BoxLayout(getPanel(), BoxLayout.Y_AXIS));
        getPanel().setBackground(Color.GRAY);
        getPanel().setSize(PANEL_SIZE);
        JLabel title = new JLabel("WORDLE", SwingConstants.CENTER);
        title.setBackground(Color.WHITE);
        title.setSize(new Dimension(40,40));
        getPanel().add(title);
        getPanel().add(getGrid().getPanel(), BorderLayout.NORTH);
        JPanel invisible = new JPanel();
        invisible.setSize(new Dimension(800,800));

        //Only if debbugflag is up
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


        invisible.setBackground(Color.GRAY);
        getPanel().add(invisible,BorderLayout.SOUTH);
        getPanel().add(getKeyboard().getPanel(), BorderLayout.SOUTH);
    }

    private void createControls() {
        Container contentPane = getFrame().getContentPane();
        contentPane.setBackground(Color.black);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));

        createPanel();
        contentPane.add(getPanel());


        getFrame().pack();
        getFrame().setSize(600,750);
        getFrame().setResizable(false);
        getFrame().setVisible(true);
    }

    protected boolean showErrorPannel() throws FileNotFoundException {
        //Only if the error flag is up
        if (!getController().isWordOnList())
            {
                JOptionPane.showMessageDialog( getFrame(), "Word not found",
                        "Error: Word not Found",JOptionPane.ERROR_MESSAGE);
                return false;
            }
            return true;
    }

    protected Color applyColor(int index)
    {
        //Switch who return the color depending on the state in Colors[]
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
}
