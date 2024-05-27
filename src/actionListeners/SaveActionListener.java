package actionListeners;

import gui.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SaveActionListener implements ActionListener {
    GUI GUI;
    public SaveActionListener(GUI g){
        GUI = g;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String filename = JOptionPane.showInputDialog(GUI, "The current configuration will be saved in a file named: ", "Enter filename", JOptionPane.INFORMATION_MESSAGE);
        if(filename == null) // ha a cancel gombra kattintunk
            return;
        if(filename.isEmpty()) {
            JOptionPane.showMessageDialog(GUI, "The filename you've entered is invalid. Try again!", "Invalid filename", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            FileOutputStream fs = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fs);
            out.writeObject(GUI.gridPanel);
            out.close();
            fs.close();
            JOptionPane.showMessageDialog(GUI, "Configuration successfully saved!", "Successful save", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(GUI, "An unexpected error occured. Try again!", "Error", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(ex);
        }
    }
}
