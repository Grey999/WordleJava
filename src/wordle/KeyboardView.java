package wordle;



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.*;


//Visual and nonvisual KeyBoard
//Handle the creation and visual aspect of the keyboard
//Handle the events link to a key being pressed
public class KeyboardView {

    private final JButton[] keyboard;
    private final JPanel keyboardpanel;
    private  JPanel keygridfirst;
    private  JPanel keygridsecond;
    private  JPanel keygridthird;
    private final WGView view;

    public KeyboardView(WGView view) throws InterruptedException, FileNotFoundException {
        //link to the viw
        this.view = view;

        //instantion of the different element necessary
        keyboard = new JButton[28];
        keyboardpanel = new JPanel();
        keyboardpanel.setBackground(Color.GRAY);
        keyboardpanel.setFocusable(false);
        String keyslabel = "QWERTYUIOPASDFGHJKL⌫ZXCVBNM";

        //Creation of the keys with the character stock on keyslabel
        for(int i = 0; i < keyslabel.length(); i++)
        {
            createKeys(String.valueOf(keyslabel.charAt(i)), i);
        }
        //Creation of the Enter key
        createKeys("Enter",27);

        //Use to detect the physical keyboard

        KeyListener keyListener = new KeyListener()
        {
            @Override
            public void keyTyped(KeyEvent e) {
                switch (e.getKeyChar())
                {
                    case 10 -> {
                        EnterKey();
                    }
                    case 8 -> BackSpaceKey();
                    default -> {
                        //detect if it's a "letter" key
                        //reduce the risk of wrong input
                        //don't let the player use special character or number
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
        };
        for (JButton jButton : keyboard) {
            jButton.addKeyListener(keyListener);
        }

        //Creation of the panel and the visual element
        CreateControl();


    }

    protected void CreateControl()
    {
        Container contentPane = view.getFrame().getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
        contentPane.setMaximumSize(new Dimension(400,400));

        CreatePanel();

        contentPane.add(keyboardpanel);
        addKeyToGrid();
    }

    protected void CreatePanel()
    {
        //Creation of the different panels and the visual elements
        keyboardpanel.setLayout(new BoxLayout(keyboardpanel, BoxLayout.Y_AXIS));
        keyboardpanel.setPreferredSize(new Dimension(1980,850));

        //use to dispose the rows of keys on the keyboardpanel
        keygridfirst = new JPanel();
        keygridsecond = new JPanel();
        keygridthird = new JPanel();


        keygridfirst.setLayout(new BoxLayout(keygridfirst, BoxLayout.X_AXIS));
        keygridfirst.setOpaque(true);
        keygridfirst.setBackground(Color.GRAY);
        keygridfirst.setFocusable(false);

        keygridsecond.setLayout(new BoxLayout(keygridsecond, BoxLayout.X_AXIS));
        keygridsecond.setOpaque(true);
        keygridsecond.setBackground(Color.GRAY);
        keygridsecond.setFocusable(false);

        keygridthird.setLayout(new BoxLayout(keygridthird, BoxLayout.X_AXIS));
        keygridthird.setOpaque(true);
        keygridthird.setBackground(Color.GRAY);
        keygridthird.setFocusable(false);

        keyboardpanel.add(keygridfirst);
        keyboardpanel.add(keygridsecond);
        keyboardpanel.add(keygridthird);
    }

    protected void EnterKey(){
        //Use when Enter is pressed on the physical and GUI keyboard
        //Will call for isWordAccept to see if the word is accept
        int code = 0;
        try {
            code = view.getController().isWordAccept();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (code != 0) {
            //if the word is not accepted and the error flag is up
            if (view.getController().isErrorflag() && code == 3) {
                try {
                    view.showErrorPannel();

                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        }
        else
        {
            //if the word is accepted or the error mode is not up
            try {
                view.getController().change();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }


    protected void BackSpaceKey()
    {
        //Use when BackSpace is pressed on the physical and GUI keyboard
        //will change the last character label by ""
        //except if there is no character
        //will also call for a change in the PlayerWord
        if (view.getController().getPlayerword().length() != 0)
        {
            view.getGrid().changeLabel(view.getController().getPlayerword().length() + (view.getController().getGuess()*5)-1, "");
            view.getController().setPlayerword(removeLastChar(view.getController().getPlayerword()));
        }
    }

    protected void LetterKey(String label)
    {
        //Use when a letter is pressed on the physical and GUI keyboard
        //will change the label on the grid for the label on the key
        //will also call for a change in the PlayerWord
        if (view.getController().getPlayerword().length() < 5)
        {
            view.getGrid().changeLabel(view.getController().getPlayerword().length() + (view.getController().getGuess()*5),label.toUpperCase(Locale.ROOT));
            view.getController().setPlayerword(view.getController().getPlayerword() + label.toLowerCase(Locale.ROOT));
        }
    }

    private void createKeys(String label, int i) throws InterruptedException, FileNotFoundException {
        //Create the different keys of the visual keyboard
        //setup the size, label and function associated on the button based on the label

        JButton key = new JButton();
        key.setText(label);

        key.setFont(new Font(Font.MONOSPACED, Font.BOLD, 40));

        key.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
        key.setSize(new Dimension(8,8));
        key.setBackground(Color.WHITE);
        key.setOpaque(true);
        switch (label) {
            case "Enter" ->
                key.addActionListener((ActionEvent e) ->
                {
                        EnterKey();
                });
            case "⌫" ->
                    key.addActionListener((ActionEvent e) ->
                    {
                        BackSpaceKey();
                    });
            default -> key.addActionListener((ActionEvent e) ->
            {
                LetterKey(label);
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
        //add the key to the good rows
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


    protected void changeBackGroundColor()
    {
        //change the Background Color of the letter depending on the state of the letter in Colors[]
        JButton key = null;
        boolean found = false;
        int index;
        String letter;
        for(int i = 0; i < view.getController().getColors().length;i++) {
            index = 0;
            letter = String.valueOf(view.getController().getLastWord().charAt(i));
            letter = letter.toUpperCase(Locale.ROOT);
            while (!found && index < keyboard.length) {
                if (keyboard[index].getText().equals(letter)) {
                    key = keyboard[index];
                    found = true;
                }
                index++;
            }
            assert key != null;
            Color color = view.applyColor(i);
            if(key.getBackground() == Color.WHITE || (key.getBackground() == Color.ORANGE && color == Color.GREEN))
            {
                key.setBackground(color);
            }

            found = false;
        }
    }

    public JPanel getPanel()
    {
        return keyboardpanel;
    }
}
