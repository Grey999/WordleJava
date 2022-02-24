package wordle;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        try {
                            createAndShowGUI();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }
    public static void createAndShowGUI() throws FileNotFoundException {
        WGModel model = new WGModel();
        WGController controller = new WGController(model);
        controller.initialise();
        InputView inputView = new InputView(model, controller);
    }
}
