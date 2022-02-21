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


    public WGView(WGModel model, WGController controller)
    {
        this.model = model;
        this.controller = controller;
        setGrid(new GridView(this));


        setFrame(new JFrame("Wordle"));
        getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

        getFrame().pack();
        getFrame().setSize(500,500);
        getFrame().setResizable(false);
        getFrame().setVisible(true);
    }


    private void createPanel() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.black);
        //voir méthode Frame
        panel.add(getGrid().getPanel());
        panel.add(getKeyboard().getPanel());
    }

    public void update(Observable o, Object arg) {
        for(int i = 0; i < 6; i++)
        {
            getGrid().changeBackgroundColor(i, getModel().getGuess(), getModel().getColors(i, getModel().getGuess()));
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
}
