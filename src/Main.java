import algorithms.Algorithm;
import hexGrid.*;
import gui.*;
import algorithms.*;

import javax.swing.*;


public class Main {
    static int size = 20;

    /**
     * Létrehoz egy rácsot a megadott mérettel és beállítja az alapértelmezett kezdeti állapotokat.
     *
     * @param s a grid mérete (hosszúság és szélesség egyaránt
     * @return Egy új grid (Node[][])
     */
    public static Node[][] createGrid(int s){
        Node[][] grid = new Node[s][s];
        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid.length; j++){
                grid[i][j] = new Node(i, j, Type.available, AlgState.unexplored);
                if (i == grid.length-1 && j == grid.length-1)
                    grid[i][j].setType(Type.destNode);
            }
        }
        grid[0][0].setType(Type.sourceNode);
        return grid;
    }

    public static void main(String[] args) {
        //Alap tábla létrehozása
        Node[][] grid = createGrid(size);



        GUI GUI = new GUI(grid, 0, 0);
        GUI.setVisible(true);
    }
}