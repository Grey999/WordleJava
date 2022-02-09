package wordle;

public class Main {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        createAndShowGUI();
                    }
                }
        );
    }
    public static void createAndShowGUI() {
        WGModel model = new WGModel();
        WGController controller = new WGController(model);
        WGView view = new WGView(model, controller);
}
}
