package actionListeners;

import gui.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeSizeActionListener implements ActionListener {
    GUI GUI;
    JTextField changeSizeTextField;

    public ChangeSizeActionListener(GUI g, JTextField cstf){
        GUI = g;
        changeSizeTextField = cstf;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(Integer.parseInt(changeSizeTextField.getText()) > 2)
            GUI.resizeGrid(Integer.parseInt(changeSizeTextField.getText()));
    }

}
