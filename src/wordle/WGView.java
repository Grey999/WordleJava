package wordle;
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


    private JPanel errorpannel;
    private JTextArea errormessage;


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

        //need to be created before the keyboard
        //will display error message for the words
        createErrorPannel();


        setKeyboard(new KeyboardView(this));


        createControls();
        this.model.addObserver(this);
        update(this.model,null);
    }

    public Dimension getPanelSize(){
        return PANEL_SIZE;
    }

    private void createControls() {
        Container contentPane = getFrame().getContentPane();
        contentPane.setBackground(Color.black);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));

        createPanel();
        contentPane.add(panel);
        contentPane.add(getErrorpannel());
        getErrorpannel().setVisible(false);

        getFrame().pack();
        getFrame().setSize(1000,1000);
        getFrame().setResizable(false);
        getFrame().setVisible(true);
    }

    private void createErrorPannel()
    {
        setErrorpannel(new JPanel());
        getErrorpannel().setLayout(new BoxLayout(getErrorpannel(), BoxLayout.Y_AXIS));
        getErrorpannel().setBackground(Color.BLACK);
        setErrormessage(new JTextArea());
        getErrorpannel().add(getErrormessage());
    }


    private void createPanel() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.GRAY);
        JLabel title = new JLabel("WORDLE", SwingConstants.CENTER);
        title.setBackground(Color.WHITE);
        title.setSize(new Dimension(40,40));
        panel.add(title);
        panel.add(getGrid().getPanel(), BorderLayout.NORTH);
        JPanel invisible = new JPanel();
        invisible.setSize(new Dimension(800,800));
        invisible.setBackground(Color.GRAY);
        panel.add(invisible,BorderLayout.SOUTH);
        panel.add(getKeyboard().getPanel(), BorderLayout.SOUTH);
    }

    public boolean showerrorpannel() throws FileNotFoundException {
        //long delay = 5;
        //TimeUnit time = TimeUnit.SECONDS;
        if (getModel().getActualword().length() != 5)
        {
            getErrorpannel().setVisible(true);
            getErrormessage().setText("Word too short");
            //time.sleep(delay);
            return false;
        }
        else {
            if (!getModel().isValidWord())
            {
                getErrorpannel().setVisible(true);
                getErrormessage().setText("Word not found");
                //time.sleep(delay);
                return false;
            }
            return true;
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("appel de update view");
        if(!getModel().getActualword().equals("")) {
            getGrid().changeBackgroundColor((getModel().getGuess()-1)*5);
            getKeyboard().changeBackgroundColor();
            getModel().setActualword("");
        }
        getFrame().repaint();
    }
    
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

    public JPanel getErrorpannel() {
        return errorpannel;
    }

    public void setErrorpannel(JPanel errorpannel) {
        this.errorpannel = errorpannel;
    }

    public JTextArea getErrormessage() {
        return errormessage;
    }

    public void setErrormessage(JTextArea errormessage) {
        this.errormessage = errormessage;
    }
}
