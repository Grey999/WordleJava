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



    public JPanel getPanel()
    {
        return gridpanel;
    }


    protected void changeLabel(int index, String letter)
    {
        letterscase[index].setText(letter);
    }


    public void changeBackgroundColor(int colum)
    {
        for(int i = 0; i < view.getController().getColors().length; i++) {
            switch (view.getController().getColors()[i]) {
                case RED -> letterscase[colum + i].setBackground(Color.RED);
                case GREEN -> letterscase[colum + i].setBackground(Color.GREEN);
                case ORANGE -> letterscase[colum + i].setBackground(Color.ORANGE);
                default -> letterscase[colum + i].setBackground(Color.GRAY);
            }
        }

    }
}
