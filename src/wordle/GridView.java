package wordle;

import javax.swing.*;
import java.awt.*;

import static wordle.WGModel.*;

//Handle the grid on the game
//Will change the letter according to the KeyBoardView
//Will change the colors according to WGModel


public class GridView {
    private final JLabel[] letterscase;
    private final JPanel gridpanel;
    private final WGView view;
    public GridView(WGView view)
    {
        //link to the view
        this.view = view;

        //Instantiation of the necessary variables
        letterscase = new JLabel[30];
        gridpanel = new JPanel();
        gridpanel.setLayout(new GridLayout(6,5));
        gridpanel.setPreferredSize(new Dimension(2000,2000));
        gridpanel.setBackground(Color.GRAY);

        //Creation of the visual grid
        addLabelsView();
    }

    private void addLabelsView()
    {
        //Create the Visual Grid
        GridBagConstraints gbc = new GridBagConstraints();
        int colum = 0;
        for(int i = 0; i < letterscase.length; i++)
        {
            //Create all the cases in the grid
            gbc.gridx = i;
            gbc.gridy = colum;
            letterscase[i] = new JLabel("",SwingConstants.CENTER);
            letterscase[i].setBackground(Color.WHITE);
            letterscase[i].setMaximumSize(new Dimension(6,6));
            letterscase[i].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,1,true));
            letterscase[i].setOpaque(true);
            letterscase[i].setHorizontalAlignment(SwingConstants.CENTER);
            letterscase[i].setText("");
            gridpanel.add(letterscase[i], gbc);
            if(i%6 == 0)
            {
                colum++;
            }
        }
    }


    protected void changeLabel(int index, String letter)
    {
        //Call by the Keyboard to change the letter on the case
        //Could change to a letter or to an empty space (with the BackSpace key)
        letterscase[index].setText(letter);
    }


    public void changeBackgroundColor(int colum)
    {
        //Call by the view update to change the color, regarding the state of the letter
        // in int[]Colors of WGModel
        for(int i = 0; i < view.getController().getColors().length; i++) {
            letterscase[colum+i].setBackground(view.applyColor(i));
        }

    }

    public JPanel getPanel()
    {
        return gridpanel;
    }
}
