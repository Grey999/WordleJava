package wordle;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.util.Locale;

public class KeyboardView {
    private final JButton[] keyboard;
    private final JPanel keyboardpanel;
    private final JPanel keygridfirst;
    private final JPanel keygridsecond;
    private final JPanel keygridthird;
    private final WGView view;

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
        keygridfirst = new JPanel();
        keygridsecond = new JPanel();
        keygridthird = new JPanel();


        keygridfirst.setLayout(new GridBagLayout());
        keygridfirst.setOpaque(true);
        keygridsecond.setLayout(new GridBagLayout());
        keygridsecond.setOpaque(true);
        keygridthird.setLayout(new GridBagLayout());
        keygridthird.setOpaque(true);

        Container contentPane = view.getFrame().getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
        contentPane.add(keyboardpanel);
        addKeyToGrid();


        keyboardpanel.add(keygridfirst);
        keyboardpanel.add(keygridsecond);
        keyboardpanel.add(keygridthird);


    }



    private void createKeys(String label, int i)
    {
        //review conditions for the keyboard
        JButton key = new JButton(label);
        if(label.equals("Enter"))
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
                if (view.getModel().getActualword().length() == 5) {
                    key.addActionListener((ActionEvent e) -> {
                        if (view.getModel().isMessagerror()) {
                            try {
                                if (!view.getModel().isValidWord()) {
                                    //TODO
                                }
                                else
                                {
                                    try {
                                        view.getModel().change();
                                    } catch (FileNotFoundException ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            } catch (FileNotFoundException ex) {
                                ex.printStackTrace();
                            }
                        }
                        else {
                            try {
                                view.getModel().change();
                            } catch (FileNotFoundException ex) {
                                ex.printStackTrace();
                            }
                        }
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
        for (int i = 0; i < length; i++) {
            if(i < 10) {
                keygridfirst.add(keyboard[i]);
            }
            if(i > 10 && i < 19)
            {
                keygridsecond.add(keyboard[i]);
            }
            if(i >= 19)
            {
                keygridthird.add(keyboard[i]);
            }
        }
    }

    public JButton[] getKeyBoard()
    {
        return keyboard;
    }
    public JPanel getPanel()
    {
        return keyboardpanel;
    }
}
