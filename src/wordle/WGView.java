package wordle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import java.awt.*;




public class WGView implements Observer
{
    private static final Dimension PANEL_SIZE = new Dimension(500,500);

    private final WGModel model;
    private final WGController controller;
    private JFrame frame;
    private JPanel panel;
    private GridView grid;
    private KeyboardView keyboard;

    private JButton display;



    public WGView(WGModel model, WGController controller) throws InterruptedException, FileNotFoundException {
        //Link the model, the controller and the view
        this.model = model;
        this.controller = controller;
        controller.setView(this);


        //creation of the Frame
        setFrame(new JFrame("Wordle"));
        getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //creation of grid
        setGrid(new GridView(this));


        setKeyboard(new KeyboardView(this));


        createControls();
        this.model.addObserver(this);
        update(this.model,null);
    }

    @Override
    public void update(Observable o, Object arg) {
        if(!getModel().getPlayerword().equals("")) {
            getGrid().changeBackgroundColor((getModel().getGuess()-1)*5);
            getKeyboard().changeBackgroundColor();
            getModel().setPlayerword("");
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
        display = new JButton("display");
        display.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getModel().isDebbugflag())
                {
                    JOptionPane.showMessageDialog( getFrame(), getModel().getWordtoguess(),
                            "Word to find",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        invisible.add(display);
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

    public boolean showErrorPannel() throws FileNotFoundException {
        if (!getModel().isWordOnList())
            {
                JOptionPane.showMessageDialog( getFrame(), "Word not found",
                        "Error: Word not Found",JOptionPane.ERROR_MESSAGE);
                return false;
            }
            return true;
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

    public WGModel getModel() {
        return model;
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }
}
