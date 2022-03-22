package wordle;



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class KeyboardView implements WordleComponent, KeyListener{
    private final JButton[] keyboard;
    private final JPanel keyboardpanel;
    private final JPanel keygridfirst;
    private final JPanel keygridsecond;
    private final JPanel keygridthird;
    private final WGView view;

    public KeyboardView(WGView view) throws InterruptedException, FileNotFoundException {
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




    private void createKeys(String label, int i) throws InterruptedException, FileNotFoundException {
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
        switch (label) {
            case "Enter" ->
                key.addActionListener((ActionEvent e) ->
                    {
                        try {
                            if(!view.showerrorpannel())
                            {
                                if (view.getModel().isMessagerror())
                                {
                                    try {
                                        if (!view.showerrorpannel())
                                        {
                                            view.getModel().change();
                                            view.update(view.getModel(), null);
                                        }
                                    } catch (FileNotFoundException ex) {
                                        ex.printStackTrace();
                                    }
                                }
                                else
                                {
                                    try
                                    {
                                        view.getModel().change();
                                        view.update(view.getModel(), null);
                                    } catch (FileNotFoundException ex)
                                    {
                                        ex.printStackTrace();
                                    }
                                }
                            }
                        } catch (FileNotFoundException ex) {
                            ex.printStackTrace();
                        }

                    });
            case "⌫" -> key.addActionListener((ActionEvent e) -> {
                if (view.getModel().getActualword().length() > 0)
                {
                    //problem backspace
                    view.getModel().setActualword(removeLastChar(view.getModel().getActualword()));
                    view.getGrid().changeLabel(view.getModel().getActualword().length()+1,
                            view.getModel().getGuess(), "");
                }

            });
            default -> key.addActionListener((ActionEvent e) -> {
                if (view.getModel().getActualword().length() < 5)
                {
                    //doesn't seem to change the number of Guess...
                    //Maybe recreate the key ???
                    //weird...
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

    @Override
    public void changeBackgroundColor(int colum, int line, int state) {
        switch (state) {
            case 0 -> keyboard[colum + line].setBackground(Color.DARK_GRAY);
            case 1 -> keyboard[colum + line].setBackground(Color.ORANGE);
            case 2 -> keyboard[colum + line].setBackground(Color.GREEN);
            default -> keyboard[colum + line].setBackground(Color.GRAY);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (KeyEvent.getKeyText(e.getKeyCode()))
        {
            case "Enter" -> System.out.println("Something");
            case "BackSpace" -> System.out.println("BackSpce");
            case "" -> {
                //find how to see a letter
                if (view.getModel().getActualword().length() < 5) {
                    view.getModel().setActualword(view.getModel().getActualword() + String.valueOf(e.getKeyChar()));
                    view.getGrid().changeLabel(view.getModel().getActualword().length(),
                        view.getModel().getGuess(), String.valueOf(e.getKeyChar()));
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
