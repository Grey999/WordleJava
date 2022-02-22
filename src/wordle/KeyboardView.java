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
            createKeys(String.valueOf(qwerty.charAt(i)), i);
        }
        createKeys("Enter",27);

        keyboardpanel.setLayout(new BoxLayout(keyboardpanel, BoxLayout.Y_AXIS));
        keyboardpanel.setPreferredSize(new Dimension(650,160));
        keygrid = new JPanel();
        keygrid.setLayout(new GridBagLayout());
        keygrid.setOpaque(true);

        Container contentPane = view.getFrame().getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
        contentPane.add(keyboardpanel);
        keyboardpanel.add(keygrid);

        addKeyToGrid();


    }

    public JPanel getPanel()
    {
        return keyboardpanel;
    }

    private void createKeys(String label, int i)
    {
        //review conditions for the keyboard
        JButton key = new JButton(label);
        if(label == "Enter")
        {
            key.setFont(new Font(Font.MONOSPACED, Font.BOLD, 40));
        }
        else {
            key.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
        }
        key.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        key.setBackground(Color.WHITE);
        key.setOpaque(true);
        switch (label)
        {
            case "Enter":
                if (!view.getModel().getActualword().equals("") && view.getModel().getActualword().length() < 5) {
                    key.addActionListener((ActionEvent e) -> {
                        //add error line
                        //sleep for two second
                    });
                }
                else {
                    if (!view.getModel().getActualword().equals("") && view.getModel().getActualword().length() == 5) {
                        key.addActionListener((ActionEvent e) -> {
                            try {
                                view.getController().change();
                            } catch (FileNotFoundException ex) {
                                ex.printStackTrace();
                            }
                        });
                    }
                }
                break;
            case "⌫":
                key.addActionListener((ActionEvent e) -> {
                    if(!view.getModel().getActualword().equals("") && view.getModel().getActualword().length() > 0)
                    {
                        view.getModel().setActualword(removeLastChar(view.getModel().getActualword()));
                        view.getGrid().changeLabel(view.getModel().getActualword().length(),
                                view.getModel().getGuess(), "");
                    }

                });
                break;
            default:
                key.addActionListener((ActionEvent e) -> {
                    if(!view.getModel().getActualword().equals("") && view.getModel().getActualword().length() < 5)
                    {
                        view.getModel().setActualword(view.getModel().getActualword() + label.toLowerCase(Locale.ROOT));
                        view.getGrid().changeLabel(view.getModel().getActualword().length(),
                                view.getModel().getGuess(), label.toUpperCase(Locale.ROOT));
                    }
                });
        }
        keyboard[i] = key;


    }

    private String removeLastChar(String s)
    {
        //returns the string after removing the last character
        return s.substring(0, s.length() - 1);
    }

    private void addKeyToGrid()
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
