/**
 * Represents a panel displaying controls and options on the side of the pathfinding visualization.
 */
package gui;

import actionListeners.*;
import algorithms.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

public class SidePanel extends JPanel {
    private GUI GUI;
    public LinkedList<Algorithm> algorithms;

    /**
     * Constructs a SidePanel with the specified GUI and array of algorithms.
     *
     * @param gui  The main GUI instance.
     * @param algs An array of algorithms to be displayed and executed.
     */
    SidePanel(GUI gui, Algorithm[] algs){
        super();
        GUI = gui;
        algorithms = new LinkedList<Algorithm>(List.of(algs));
        setVisible(true);
        setMinimumSize(new Dimension(100, 100));
        setLayout(new GridLayout(0, 2, 10, 50)); //Ezt inkább GridBagLayout-ra kéne cserélni

        String[] algNames = new String[algorithms.size() + 1];
        algNames[0] = "Select an algorithm";
        for (int i = 0; i < algorithms.size(); i++)
            algNames[i+1] = algorithms.get(i).getName();

        JComboBox algorithmComboBox = new JComboBox(algNames);
        JButton runButton = new JButton("Run Algorithm");
        runButton.addActionListener(new RunActionListener(GUI, algorithms, algorithmComboBox));

        JButton clearPathsButton = new JButton("Clear Paths");
        clearPathsButton.addActionListener(new ClearPathsActionListener(GUI));

        //setmargins-szal hogy jobban nézzen ki
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new SaveActionListener(GUI));

        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(new LoadActionListener(GUI));


        JButton clearConfButton = new JButton("Clear");
        clearConfButton.addActionListener(new ClearActionListener(GUI));

        //Grid méretének változtatása
        JButton changeSizeButton = new JButton("Change Grid Size");
        JTextField changeSizeTextField = new JTextField("20");
        changeSizeTextField.setHorizontalAlignment(JTextField.CENTER);
        changeSizeButton.addActionListener(new ChangeSizeActionListener(GUI, changeSizeTextField));

        JTextField changeSpeedTextField = new JTextField("20");
        changeSpeedTextField.setHorizontalAlignment(JTextField.CENTER);
        JButton changeSpeedButton = new JButton("Change Speed");
        changeSpeedButton.addActionListener(new ChangeSpeedActionListener(GUI, changeSpeedTextField));

        add(algorithmComboBox);
        add(runButton);
        add(changeSpeedTextField);
        add(changeSpeedButton);

        JLabel clearPathsLabel = new JLabel("Clear Paths", SwingConstants.CENTER);
        clearPathsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        clearPathsLabel.setForeground(Color.white);
        add(clearPathsLabel);
        add(clearPathsButton);

        JLabel saveLabel = new JLabel("Save Configuration", SwingConstants.CENTER);
        saveLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        saveLabel.setForeground(Color.white);
        add(saveLabel);
        add(saveButton);

        JLabel loadLabel = new JLabel("Load Configuration", SwingConstants.CENTER);
        loadLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        loadLabel.setForeground(Color.white);
        add(loadLabel);
        add(loadButton);

        JLabel clearConfLabel = new JLabel("Clear Configuration", SwingConstants.CENTER);
        clearConfLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        clearConfLabel.setForeground(Color.white);
        add(clearConfLabel);
        add(clearConfButton);

        add(changeSizeTextField);
        add(changeSizeButton);

        JButton backButton = new JButton("Back to Menu");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUI.splitPane.setVisible(false);
                GUI.titleScreenPanel.setVisible(true);
            }
        });
        add(backButton);
    }


}
