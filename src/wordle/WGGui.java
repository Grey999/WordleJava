package wordle;

import java.io.FileNotFoundException;

//Main Class for GUI version
//Use FirstView to start the game
public class WGGui {

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
        FirstView firstView = new FirstView();
    }
}
