package gui;

import actionListeners.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class TitleScreenPanel extends JPanel {
    GUI GUI;

    public TitleScreenPanel(GUI gui){
        super();
        setLayout(new GridLayout(5, 1, 0, 30));
        setBorder(BorderFactory.createEmptyBorder(100, 400, 50, 400));
        GUI = gui;

        setBackground(Color.black);

        JLabel title = new JLabel("Pathfinding Visualization");
        title.setFont(new Font("Serif", Font.BOLD, 50));
        title.setForeground(Color.white);
        title.setHorizontalAlignment(JLabel.CENTER);


        add(title);

        JButton continueButton = new JButton("Continue");
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUI.titleScreenPanel.setVisible(false);
                GUI.splitPane.setVisible(true);
            }
        });
        continueButton.setEnabled(false);
        add(continueButton);

        JButton startButton = new JButton("New Configuration");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                continueButton.setEnabled(true);
                ClearActionListener clearActionListener = new ClearActionListener(GUI);
                clearActionListener.actionPerformed(e);
                GUI.titleScreenPanel.setVisible(false);
                GUI.splitPane.setVisible(true);
            }
        });
        add(startButton);

//        JButton loadButton = new JButton("Load Configuration");
//        loadButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                LoadActionListener loadActionListener = new LoadActionListener(GUI);
//                loadActionListener.actionPerformed(e);
//                GUI.titleScreenPanel.setVisible(false);
//                GUI.splitPane.setVisible(true);
//            }
//        });
//        add(loadButton);

//        JButton howTOButton = new JButton("How to use");
//        //howTOButton.addActionListener(new HowToActionListener());
//        add(howTOButton);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        add(exitButton);
    }

}
