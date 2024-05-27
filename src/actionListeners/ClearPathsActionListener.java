package actionListeners;

import hexGrid.AlgState;
import hexGrid.Node;
import gui.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClearPathsActionListener implements ActionListener {
    GUI GUI;
    public ClearPathsActionListener(GUI g){
        GUI = g;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (Node[] nodes : GUI.gridPanel.getGrid())
            for (Node node : nodes) {
                node.setAlgState(AlgState.unexplored);
                node.setPartOfPath(false);
                node.setColor();
            }
    }
}
