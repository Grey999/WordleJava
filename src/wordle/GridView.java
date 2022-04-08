package wordle;

import javax.swing.*;
import java.awt.*;

import static wordle.WGModel.*;

public class GridView {
    private final JLabel[] letterscase;
    private final JPanel gridpanel;
    private final WGView view;
    public GridView(WGView view)
    {
        this.view = view;
        letterscase = new JLabel[30];
        gridpanel = new JPanel();
        gridpanel.setLayout(new GridLayout(6,5));
        gridpanel.setPreferredSize(new Dimension(2000,2000));
        gridpanel.setBackground(Color.GRAY);
        addLabelsView();
    }

    private void addLabelsView()
    {
        //Create the Visual Grid
        GridBagConstraints gbc = new GridBagConstraints();
        int colum = 0;
        for(int i = 0; i < letterscase.length; i++)
        {
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
        letterscase[index].setText(letter);
    }


    public void changeBackgroundColor(int colum)
    {
        //Call by the view update to change the color, regarding the state of the letter on the word
        for(int i = 0; i < view.getController().getColors().length; i++) {
            letterscase[colum+i].setBackground(view.applyColor(i));
        }

    }

    public JPanel getPanel()
    {
        return gridpanel;
    }
}
