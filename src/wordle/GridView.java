package wordle;

import javax.swing.*;
import java.awt.*;

public class GridView {
    private JLabel[] letterscase;
    private JPanel gridpanel;
    public GridView(WGView view)
    {
        letterscase = new JLabel[28];
        gridpanel = new JPanel();
        gridpanel.setLayout(new GridLayout(6,5));
        gridpanel.setPreferredSize(new Dimension(400,400));
        addlabelsview();
    }

    public void addlabelsview()
    {
        for(int i = 0; i < letterscase.length; i++)
        {
            letterscase[i] = new JLabel("",SwingConstants.CENTER);
            letterscase[i].setBackground(Color.GRAY);
            letterscase[i].setMaximumSize(new Dimension(10,10));
            letterscase[i].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,2,true));
            letterscase[i].setOpaque(true);
            gridpanel.add(letterscase[i]);
        }
    }
    //Trouver comment changer label de la case en fonction du mot entrée
    public void changelabel(int colum, int line, String letter)
    {
        //TODO
    }

    public void changebackgroundcolor(int colum, int line, int state)
    {
        switch (state)
        {
            case 0:
                letterscase[colum+line].setBackground(Color.DARK_GRAY);
                break;
            case 1:
                letterscase[colum+line].setBackground(Color.ORANGE);
                break;
            case 2:
                letterscase[colum+line].setBackground(Color.GREEN);
                break;
            default:
                letterscase[colum+line].setBackground(Color.GRAY);
        }
    }
}
