package actionListeners;

import gui.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClearActionListener implements ActionListener {
    GUI GUI;

    public ClearActionListener(GUI g){
        GUI = g;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GUI.resizeGrid(GUI.gridPanel.getGrid().length);

        int offsetX = GUI.gridPanel.getGrid()[0][0].getSize().width/2;
        GUI.gridPanel.setBounds(GUI.gridPanel.getX(), GUI.gridPanel.getY(), GUI.gridPanel.getSize().width+offsetX, GUI.gridPanel.getSize().height);
        GUI.gridPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30+offsetX));
    }
}
