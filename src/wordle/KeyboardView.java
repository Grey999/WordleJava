package wordle;

import jdk.jshell.spi.ExecutionControl;

import javax.swing.*;
import java.awt.*;
import java.util.Dictionary;
import java.util.Hashtable;

public class KeyboardView {
    private JButton[] keyboard;
    private JPanel keyboardpanel;
    private JPanel keygrid;
    public KeyboardView(WGView view)
    {
        keyboard = new JButton[28];
        String qwerty = "QWERTYUIOPASDFGHJKL⌫ZXCVBNM";
        for(int i = 0; i < qwerty.length(); i++)
        {
            createkeys(String.valueOf(qwerty.charAt(i)));
        }
        createkeys("Enter");

        keyboardpanel.setLayout(new BoxLayout(keyboardpanel, BoxLayout.Y_AXIS)); // cree le layout pour le gros panel
        keyboardpanel.setPreferredSize(new Dimension(650,160));
        keygrid = new JPanel();
        keygrid.setLayout(new GridBagLayout());
        //continuer


    }

    public void createkeys(String label)
    {
        JButton key = new JButton(label);
        key.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
        key.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        key.setBackground(Color.WHITE);
        key.setOpaque(true);
    }
}
