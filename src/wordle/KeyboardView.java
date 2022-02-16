package wordle;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.util.Locale;

public class KeyboardView {
    private JButton[] keyboard;
    private JPanel keyboardpanel;
    private JPanel keygrid;;
    private WGView view;

    public KeyboardView(WGView view)
    {
        this.view = view;
        keyboard = new JButton[28];
        keyboardpanel = new JPanel();
        String qwerty = "QWERTYUIOPASDFGHJKL⌫ZXCVBNM";
        for(int i = 0; i < qwerty.length(); i++)
        {
            createKeys(String.valueOf(qwerty.charAt(i)));
        }
        createKeys("Enter");

        keyboardpanel.setLayout(new BoxLayout(keyboardpanel, BoxLayout.Y_AXIS)); // cree le layout pour le gros panel
        keyboardpanel.setPreferredSize(new Dimension(650,160));
        keygrid = new JPanel();
        keygrid.setLayout(new GridBagLayout());
        keygrid.setOpaque(true);
        addKeyToGrid();
        //find a way for the placement (keyboard java GUI)
        keyboardpanel.add(keygrid);

    }

    public void createKeys(String label)
    {
        JButton key = new JButton(label);
        //condition size for enter button
        key.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
        key.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        key.setBackground(Color.WHITE);
        key.setOpaque(true);
        switch (label)
        {
            case "Enter":
                if(view.model.getActualword().length() == 5) {
                    key.addActionListener((ActionEvent e) -> {
                        try {
                            view.getController().change();
                        } catch (FileNotFoundException ex) {
                            ex.printStackTrace();
                        }
                    });
                }
                break;
            case "⌫":
                key.addActionListener((ActionEvent e) -> {view.model.setActualword(view.model.getActualword()+label.toLowerCase(Locale.ROOT));});
                break;
            default:
                key.addActionListener((ActionEvent e) -> {view.model.setActualword(removeLastChar(view.model.getActualword()));});
        }


    }

    public String removeLastChar(String s)
    {
        //returns the string after removing the last character
        return s.substring(0, s.length() - 1);
    }

    public void addKeyToGrid()
    {
        int length = keyboard.length;
        for(int i = 0; i < length; i++) {
            keygrid.add(keyboard[i]);
        }
    }



    public JButton[] getKeyBoard()
    {
        return keyboard;
    }
}
