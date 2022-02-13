package wordle;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import java.awt.*;
public class InputView implements Observer{
    TextField input;
    Button yes;
    Button no;
    WGModel model;
    public InputView(WGModel model)
    {
        this.model = model;
        JFrame frame = new JFrame("");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

        model.addObserver(this);
        update(model,null);

    }

    public void initialiseviex()
    {
        //TODO
    }

    public void endgame()
    {
        //TODO
    }

    //regarder comment créer les buttons
    //ce qui doit être visible à l'initialisation ou pas

    @Override
    public void update(Observable o, Object arg) {

    }
}
