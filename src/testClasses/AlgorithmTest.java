package testClasses;

import algorithms.Algorithm;
import algorithms.DFS;
import gui.GUI;
import hexGrid.AlgState;
import hexGrid.Node;
import hexGrid.Type;
import org.junit.Assert;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class AlgorithmTest {

    @Test
    public void deleteNeighbours() {
        Node[][] grid = new Node[10][10];
        for(int i = 0; i < grid.length; i++)
            for(int j = 0; j < grid[0].length; j++)
                grid[i][j] = new Node(i, j, hexGrid.Type.available, hexGrid.AlgState.unexplored);

        GUI GUI = new GUI(grid, 0, 0);
        Algorithm algo = new DFS(GUI, Color.blue, 10);
        algo.deleteNeighbours();

        boolean areThereNeighbours = false;
        for (Node[] nodes : grid)
            for (Node node : nodes)
                if (node.getNeighbours().size() != 0)
                    areThereNeighbours = true;

        Assert.assertFalse(areThereNeighbours);
    }

    @Test
    public void findDestination() {
        Node[][] grid = new Node[10][10];
        for(int i = 0; i < grid.length; i++)
            for(int j = 0; j < grid[0].length; j++)
                grid[i][j] = new Node(i, j, hexGrid.Type.available, hexGrid.AlgState.unexplored);
        grid[grid.length-1][grid[0].length-1].setType(Type.destNode);

        GUI GUI = new GUI(grid, 0, 0);
        Algorithm algo = new DFS(GUI, Color.blue, 10);

        Node dest = algo.findDestination(GUI);

        Assert.assertEquals(dest, grid[grid.length-1][grid[0].length-1]);
    }

    @Test
    public void findSource() {
        Node[][] grid = new Node[10][10];
        for(int i = 0; i < grid.length; i++)
            for(int j = 0; j < grid[0].length; j++)
                grid[i][j] = new Node(i, j, hexGrid.Type.available, hexGrid.AlgState.unexplored);
        grid[0][0].setType(Type.sourceNode);

        GUI GUI = new GUI(grid, 0, 0);
        Algorithm algo = new DFS(GUI, Color.blue, 10);

        Node source = algo.findSource(GUI);

        Assert.assertEquals(source, grid[0][0]);
    }

    @Test
    public void findSourceFail() {
        Node[][] grid = new Node[10][10];
        for(int i = 0; i < grid.length; i++)
            for(int j = 0; j < grid[0].length; j++)
                grid[i][j] = new Node(i, j, hexGrid.Type.available, hexGrid.AlgState.unexplored);

        GUI GUI = new GUI(grid, 0, 0);
        Algorithm algo = new DFS(GUI, Color.blue, 10);

        Node source = algo.findDestination(GUI);

        Assert.assertEquals(source, null);
    }

    @Test
    public void resetAlgStates() {
        Node[][] grid = new Node[10][10];
        for(int i = 0; i < grid.length; i++)
            for(int j = 0; j < grid[0].length; j++)
                grid[i][j] = new Node(i, j, hexGrid.Type.available, hexGrid.AlgState.explored);

        GUI GUI = new GUI(grid, 0, 0);
        Algorithm algo = new DFS(GUI, Color.blue, 10);
        algo.resetAlgStates();

        boolean areThereExploredNodes = false;
        for (Node[] nodes : grid)
            for (Node node : nodes)
                if (node.getAlgState() == AlgState.explored)
                    areThereExploredNodes = true;

        Assert.assertFalse(areThereExploredNodes);
    }

}