package wordle;
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


    public WGView(WGModel model, WGController controller) throws InterruptedException {
        this.model = model;
        this.controller = controller;
        setGrid(new GridView(this));
        setFrame(new JFrame("Wordle"));
        getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setErrorpannel(new JPanel());
        getErrorpannel().setLayout(new BoxLayout(getErrorpannel(), BoxLayout.Y_AXIS));
        getErrorpannel().setBackground(Color.BLACK);
        setErrormessage(new JTextArea());
        getErrorpannel().add(getErrormessage());
        setKeyboard(new KeyboardView(this));
        createControls();
        controller.setView(this);
        model.addObserver(this);
        update(model,null);
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


    private void createPanel() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.black);
        JLabel title = new JLabel("WORDLE", SwingConstants.CENTER);
        title.setBackground(Color.WHITE);
        title.setSize(new Dimension(40,40));
        panel.add(title);
        panel.add(getGrid().getPanel());
        panel.add(getKeyboard().getPanel());
    }

    public void update(Observable o, Object arg) {

        if(model.getActualword() != null && model.getActualword().length() == 5) {
            for (int i = 0; i < 5; i++) {
                getGrid().changeBackgroundColor(i, getModel().getGuess(), getModel().getColors(i, getModel().getGuess()));
            }
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
