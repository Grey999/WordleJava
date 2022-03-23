package wordle;

import javax.swing.*;
import java.awt.*;

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
        gridpanel.setPreferredSize(new Dimension(400,400));
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
            letterscase[i].setMaximumSize(new Dimension(8,8));
            letterscase[i].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,2,true));
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


    protected void changeLabel(int colum, int line, String letter)
    {
        if(line != 0)
        {
            line = line -1;
        }

        letterscase[colum+line].setText(letter);

    }


    public void changeBackgroundColor(int colum, int line, int state)
    {
        if(line != 0)
        {
            line = line -1;
        }
        switch (state) {
            case 0 -> letterscase[colum + line].setBackground(Color.DARK_GRAY);
            case 1 -> letterscase[colum + line].setBackground(Color.ORANGE);
            case 2 -> letterscase[colum + line].setBackground(Color.GREEN);
            default -> letterscase[colum + line].setBackground(Color.GRAY);
        }
        view.getController().setColors();

    }
}
