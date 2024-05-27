package actionListeners;

import gui.*;
import hexGrid.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class LoadActionListener implements ActionListener {
    GUI GUI;

    public LoadActionListener(GUI g) {
        GUI = g;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String filename = JOptionPane.showInputDialog(GUI, "Enter the name of the configuration you want to load in: ", "Enter filename", JOptionPane.INFORMATION_MESSAGE);
        if(filename == null) // ha a cancel gombra kattintunk
            return;
        if(filename.isEmpty()) {
            JOptionPane.showMessageDialog(GUI, "The filename you've entered is invalid. Try again!", "Invalid filename", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            FileInputStream fs = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fs);

            GUI.reconstruct(((GridPanel) in.readObject()).getGrid());

            JOptionPane.showMessageDialog(GUI, "Configuration successfully loaded!", "Loaded successfully", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(GUI, "An unexpected error occured. Try again!", "Error", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(ex);
        }
    }
}
