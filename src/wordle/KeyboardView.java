package wordle;



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.*;

import static wordle.WGModel.*;

public class KeyboardView {
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
        keyboardpanel.setBackground(Color.GRAY);
        String qwerty = "QWERTYUIOPASDFGHJKL⌫ZXCVBNM";
        for(int i = 0; i < qwerty.length(); i++)
        {
            createKeys(String.valueOf(qwerty.charAt(i)), i);
        }
        createKeys("Enter",27);

        keyboard[0].addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                switch (e.getKeyChar())
                {
                    case 10 ->
                            {
                                EnterKey();
                            }
                    case 8 ->
                            {
                                BackSpaceKey();
                            }
                    default -> {
                        if((e.getKeyChar() >= 97 && e.getKeyChar() <= 122))
                        {
                            LetterKey(String.valueOf(e.getKeyChar()));
                        }

                    }
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        keyboardpanel.setLayout(new BoxLayout(keyboardpanel, BoxLayout.Y_AXIS));
        keyboardpanel.setPreferredSize(new Dimension(1980,850));
        keygridfirst = new JPanel();
        keygridsecond = new JPanel();
        keygridthird = new JPanel();


        keygridfirst.setLayout(new BoxLayout(keygridfirst, BoxLayout.X_AXIS));
        keygridfirst.setOpaque(true);
        keygridfirst.setBackground(Color.GRAY);
        keygridsecond.setLayout(new BoxLayout(keygridsecond, BoxLayout.X_AXIS));
        keygridsecond.setOpaque(true);
        keygridsecond.setBackground(Color.GRAY);
        keygridthird.setLayout(new BoxLayout(keygridthird, BoxLayout.X_AXIS));
        keygridthird.setOpaque(true);
        keygridthird.setBackground(Color.GRAY);

        Container contentPane = view.getFrame().getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
        contentPane.setMaximumSize(new Dimension(400,400));
        contentPane.add(keyboardpanel);
        addKeyToGrid();


        keyboardpanel.add(keygridfirst);
        keyboardpanel.add(keygridsecond);
        keyboardpanel.add(keygridthird);


    }

    public void EnterKey()
    {
        if (view.getModel().getActualword().length() == 5) {
            try {
                if (view.showerrorpannel()) {
                    if (view.getModel().isMessagerror()) {
                        try {
                            if (view.showerrorpannel()) {
                                view.getController().change();

                            }
                        } catch (FileNotFoundException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        try {
                            view.getController().change();
                        } catch (FileNotFoundException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }

        }
    }

    public void BackSpaceKey()
    {
        System.out.println("Appel BackSpace");
        if (view.getModel().getActualword().length() != 0)
        {
            view.getGrid().changeLabel(view.getModel().getActualword().length() + (view.getModel().getGuess()*5)-1, "");
            view.getModel().setActualword(removeLastChar(view.getModel().getActualword()));
        }
    }

    public void LetterKey(String label)
    {
        if (view.getModel().getActualword().length() < 5)
        {
            //review condition (problem on second try)
            view.getGrid().changeLabel(view.getModel().getActualword().length() + (view.getModel().getGuess()*5),label.toUpperCase(Locale.ROOT));
            view.getModel().setActualword(view.getModel().getActualword() + label.toLowerCase(Locale.ROOT));
        }
    }

    private void createKeys(String label, int i) throws InterruptedException, FileNotFoundException {
        //review conditions for the keyboard
        JButton key = new JButton();
        key.setText(label);

        key.setFont(new Font(Font.MONOSPACED, Font.BOLD, 40));

        key.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
        key.setSize(new Dimension(8,8));
        key.setBackground(Color.WHITE);
        key.setOpaque(true);
        switch (label) {
            case "Enter" ->
                key.addActionListener((ActionEvent e) -> {EnterKey();});
            case "⌫" ->
                    key.addActionListener((ActionEvent e) -> { BackSpaceKey();});
            default -> key.addActionListener((ActionEvent e) -> {LetterKey(label);});
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
            if(i <10) {
                keygridfirst.add(keyboard[i]);
            }
            if(i >= 10 && i < 19)
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


    public void changeBackgroundColor()
    {
        JButton key = null;
        boolean found = false;
        int index;
        int state;
        String letter;
        for(int i = 0; i < view.getModel().getColors().length;i++) {
            index = 0;
            state = view.getModel().getColors()[i];
            letter = String.valueOf(view.getModel().getActualword().charAt(i));
            letter = letter.toUpperCase(Locale.ROOT);
            while (!found && index < keyboard.length) {
                if (keyboard[index].getText().equals(letter)) {
                    key = keyboard[index];
                    found = true;
                }
                index++;
            }
            switch (state) {
                case Red -> {
                        assert key != null;
                        key.setBackground(Color.RED);
                    }
                case Green -> {
                        assert key != null;
                        key.setBackground(Color.GREEN);
                    }
                case Orange -> {
                        assert key != null;
                        key.setBackground(Color.ORANGE);
                    }

                    default -> {
                        assert key != null;
                        key.setBackground(Color.GRAY);
                    }
            }
            found = false;
        }
    }
}
