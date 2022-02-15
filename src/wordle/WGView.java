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
    //private JPanel inputframe;
    private GridView grid;
    private KeyboardView keyboard;


    public WGView(WGModel model, WGController controller)
    {
        this.model = model;
        this.controller = controller;
        grid = new GridView(this);
        keyboard = new KeyboardView(this);
        createControls();
        controller.setView(this);

        //createControls() ???
        //createPanel() ??

        model.addObserver(this);
        update(model,null);
    }

    public Dimension getPanelSize(){
        return PANEL_SIZE;
    }

    private void createControls() {
        frame = new JFrame("Wordle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container contentPane = frame.getContentPane();
        contentPane.setBackground(Color.black);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
        createPanel();
        contentPane.add(panel);

        //revoir si inputframe nécessaire
        /*
        Container contentPane2 = inputframe.getContentPane();
        contentPane2.setBackground(Color.black);
        contentPane2.setLayout(new BoxLayout(contentPane2, BoxLayout.X_AXIS));
        createInputControl();
        contentPane2.add(inputframe);

         */


        frame.pack();
        frame.setSize(500,500);
        frame.setResizable(false);
        //revoir visible ou non
        frame.setVisible(true);
        //inputframe.setVisible(true);
    }


    private void createPanel() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.black);
        //voir méthode Frame
        //panel.add(grid.getBasePanel());
        //panel.add(keyboard.getBasePanel());
    }

    public void update(Observable o, Object arg) {
        for(int i = 0; i < 6; i++)
        {
            grid.changebackgroundcolor(i,model.getGuess(),model.getColors(i,model.getGuess()));
        }
        frame.repaint();
    }
}
