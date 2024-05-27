package testClasses;

import gui.GUI;
import hexGrid.Node;
import org.junit.Test;

import static org.junit.Assert.*;

public class GUITest {

    @Test
    public void resizeGrid() {
        Node[][] grid = new Node[10][10];
        for(int i = 0; i < grid.length; i++)
            for(int j = 0; j < grid[0].length; j++)
                grid[i][j] = new Node(i, j, hexGrid.Type.available, hexGrid.AlgState.unexplored);

        GUI GUI = new GUI(grid, 0, 0);
        GUI.resizeGrid(20);

        assertEquals(GUI.gridPanel.getGrid().length, 20);
    }

    @Test
    public void reconstruct() {
        Node[][] grid = new Node[10][10];
        for(int i = 0; i < grid.length; i++)
            for(int j = 0; j < grid[0].length; j++)
                grid[i][j] = new Node(i, j, hexGrid.Type.available, hexGrid.AlgState.unexplored);

        Node[][] grid2 = grid;

        GUI GUI = new GUI(grid, 0, 0);
        GUI.reconstruct(grid2);

        assertEquals(GUI.gridPanel.getGrid(), grid);
    }
}