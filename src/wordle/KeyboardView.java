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
        keygrid.setOpaque(true);
        addkeytogrid();
        //find a way for the placement (keyboard java GUI)
        keyboardpanel.add(keygrid);

    }

    public void createkeys(String label)
    {
        JButton key = new JButton(label);
        //condition size for enter button
        key.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
        key.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        key.setBackground(Color.WHITE);
        key.setOpaque(true);
    }

    public void addkeytogrid()
    {
        int length = keyboard.length;
        for(int i = 0; i < length; i++)
        {
            keygrid.add(getkeyboard()[i]);
        }
    }

    public JButton[] getkeyboard()
    {
        return keyboard;
    }
}
