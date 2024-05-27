/**
 * Represents a panel displaying the grid of nodes for the pathfinding visualization.
 */
package gui;

import hexGrid.Node;
import hexGrid.Type;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;

public class GridPanel extends JPanel implements Serializable {
    private Node[][] grid;
    private int hgap;
    private int vgap;

    //egér nyomvatartásának érzékeléséhez
    static boolean leftMouseKeyPressed = false;
    static boolean rightMouseKeyPressed = false;

    //start és end node-ok mozgatásához
    EditState editState = EditState.changeWalls;

    /**
     * Constructs a GridPanel with the specified grid, horizontal gap, and vertical gap.
     *
     * @param g    The initial grid of nodes.
     * @param hgap The horizontal gap between nodes.
     * @param vgap The vertical gap between nodes.
     */
    GridPanel(Node[][] g, int hgap, int vgap){
        super();
        grid = g;

        setVisible(true);

        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid[0].length; j++){
                add(grid[i][j]);
                grid[i][j].setColor();

                final int fi = i;
                final int fj = j;
                grid[i][j].addActionListener(new ActionListener() { //kattintás érzékelése
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Node modification based on edit state
                        if(editState == EditState.changeWalls)
                            switch (grid[fi][fj].getType()) {
                                case available:
                                    grid[fi][fj].setType(Type.wall);
                                break;
                            case wall:
                                 grid[fi][fj].setType(Type.available);
                                break;
                            case sourceNode:
                                grid[fi][fj].setType(Type.available);
                                editState= EditState.placeStart;
                                break;
                            case destNode:
                                grid[fi][fj].setType(Type.available);
                                editState= EditState.placeEnd;
                                break;
                            }
                        else if(editState == EditState.placeStart) {
                            if(grid[fi][fj].getType() == Type.available){
                                grid[fi][fj].setType(Type.sourceNode);
                                editState = EditState.changeWalls;
                            }
                        }
                        else if(editState == EditState.placeEnd) {
                            if(grid[fi][fj].getType() == Type.available){
                                grid[fi][fj].setType(Type.destNode);
                                editState = EditState.changeWalls;
                            }
                        }
                        grid[fi][fj].setColor();
                    }
                });
                grid[i][j].addMouseListener(new MouseAdapter() { //hosszan nyomás érzékelése
                    public void mousePressed(MouseEvent evt) {
                        if(evt.getButton() == MouseEvent.BUTTON1)
                            leftMouseKeyPressed = true;
                        else if(evt.getButton() == MouseEvent.BUTTON3)
                            rightMouseKeyPressed = true;
                    }
                    public void mouseReleased(MouseEvent evt) {
                        if(evt.getButton() == MouseEvent.BUTTON1)
                            leftMouseKeyPressed = false;
                        else if(evt.getButton() == MouseEvent.BUTTON3)
                            rightMouseKeyPressed = false;
                    }
                    public void mouseEntered(MouseEvent evt) {
                        // Node modification based on mouse movement and button state
                        if(leftMouseKeyPressed)
                            if(grid[fi][fj].getType() == Type.available) {
                                grid[fi][fj].setType(Type.wall);
                                grid[fi][fj].setColor();
                            }
                        if(rightMouseKeyPressed)
                            if(grid[fi][fj].getType() == Type.wall) {
                                grid[fi][fj].setType(Type.available);
                                grid[fi][fj].setColor();
                            }
                    }
                });
            }
        }

        setLayout(new MyGridLayout(grid.length, grid[0].length, hgap, vgap, grid[0][0]));
    }

    /**
     * Sets the neighbors for each node in the grid.
     */
    public void setNeighbours(){
        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid[0].length; j++){
                if(grid[i][j].getType() == Type.wall) continue; //falaknak nem kell szomszédot adni
                if(j > 0 && grid[i][j-1].getType() != Type.wall) grid[i][j].addNeighbour(grid[i][j-1]); //bal oldali
                if(j < grid[0].length-1 && grid[i][j+1].getType() != Type.wall) grid[i][j].addNeighbour(grid[i][j+1]); //jobb oldali
                if (i%2 == 0){ //páros sorok (0-tól számolva)
                    if(i > 0 && j > 0 && grid[i-1][j-1].getType() != Type.wall) grid[i][j].addNeighbour(grid[i-1][j-1]); //bal felső
                    if(i > 0 && grid[i-1][j].getType() != Type.wall) grid[i][j].addNeighbour(grid[i-1][j]); //jobb felső (páros sorban minden j értékre van)
                    if(i < grid.length-1 && j > 0 && grid[i+1][j-1].getType() != Type.wall) grid[i][j].addNeighbour(grid[i+1][j-1]); //bal alsó
                    if(i < grid.length-1 && grid[i+1][j].getType() != Type.wall) grid[i][j].addNeighbour(grid[i+1][j]); //jobb alsó
                } else{ //páratlan sorok
                    if(grid[i-1][j].getType() != Type.wall) grid[i][j].addNeighbour(grid[i-1][j]); //bal felső (páratlan sorban mindig van)
                    if(j < grid[0].length-1 && grid[i-1][j+1].getType() != Type.wall) grid[i][j].addNeighbour(grid[i-1][j+1]); //jobb felső
                    if(i < grid.length-1 && grid[i+1][j].getType() != Type.wall) grid[i][j].addNeighbour(grid[i+1][j]); //bal alsó
                    if(i < grid.length-1 && j < grid[0].length-1 && grid[i+1][j+1].getType() != Type.wall) grid[i][j].addNeighbour(grid[i+1][j+1]); //jobb alsó
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Az eltolás és a pálya tényleges méretének beállítása (size() renderelés előtt (0,0)-t ad vissza), ezért ezt az extra kódot is bele kell írni a paintComponent()-be
        int offsetX = getGrid()[0][0].getSize().width/2;
        setBounds(getX(), getY(), getSize().width+offsetX, getSize().height);
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30+offsetX));
    }

    /**
     * Gets the grid of nodes displayed in the panel.
     *
     * @return The grid of nodes.
     */
    public Node[][] getGrid(){
        return grid;
    }

    /**
     * Sets the grid of nodes for the panel.
     *
     * @param g The new grid of nodes.
     */
    public void setGrid(Node[][] g){
    	grid = g;
    }

    /**
     * Sets the color of a specific node in the grid.
     *
     * @param i The row index of the node.
     * @param j The column index of the node.
     * @param c The color to set for the node.
     */
    public void setNodeColor(int i, int j, Color c){
    	grid[i][j].setColor(c);
    }

    /**
     * Gets the horizontal gap between nodes.
     *
     * @return The horizontal gap.
     */
    public int getHgap() {
        return hgap;
    }

    /**
     * Gets the vertical gap between nodes.
     *
     * @return The vertical gap.
     */
    public int getVgap() {
        return vgap;
    }
}
