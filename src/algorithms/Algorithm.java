/**
 * An abstract class representing an algorithm for pathfinding on a hexagonal grid.
 * This class extends SwingWorker for background execution and GUI interaction.
 */

package algorithms;
import gui.GUI;
import gui.Sound;
import hexGrid.*;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Abstract class for implementing pathfinding algorithms.
 */
public abstract class Algorithm extends SwingWorker<Void, Void>{
    private String name;
    protected Color color;
    protected GUI GUI;
    protected Node source;
    protected Node destination;
    protected int delay;

    /**
     * Constructor for the Algorithm class.
     *
     * @param GUI   The GUI object associated with the algorithm.
     * @param c     The color representing the algorithm.
     * @param d     The delay in milliseconds for visualization.
     */
    Algorithm(GUI GUI, Color c, int d){
        color = c;
        this.GUI = GUI;
        delay = d;
    }

    /**
     * Runs the algorithm and disables the GUI during execution.
     *
     * @throws InterruptedException if the algorithm execution is interrupted.
     */
    public void runAlgorithm() throws InterruptedException {
        GUI.setEnabled(false);
        execute();
    }

    @Override
    protected Void doInBackground() throws Exception {
        implementedAlgorithm();
        drawFinalPath(destination);
        return null;
    }

    /**
     * Abstract method for implementing the specific pathfinding algorithm.
     *
     * @throws InterruptedException       if the algorithm execution is interrupted.
     * @throws LineUnavailableException   if an audio line cannot be opened.
     */
    public abstract void implementedAlgorithm() throws InterruptedException, LineUnavailableException;

    /**
     * Deletes the neighbors of all nodes in the grid.
     */
    public void deleteNeighbours(){
        Node[][] grid = GUI.gridPanel.getGrid();
        for (Node[] nodes : grid)
            for (Node node : nodes)
                node.deleteNeighbours();
    }

    @Override
    protected void process(List<Void> chunks) {
        GUI.gridPanel.repaint();
    }

    /**
     * Finds the source node in the grid.
     *
     * @param g The GUI object containing the grid.
     * @return The source node.
     */
    public Node findSource(GUI g) {
        Node[][] grid = GUI.gridPanel.getGrid();
        for (Node[] nodes : grid)
            for (Node node : nodes)
                if (node.getType() == Type.sourceNode)
                    return node;
        return null;
    }

    /**
     * Finds the destination node in the grid.
     *
     * @param g The GUI object containing the grid.
     * @return The destination node.
     */
    public Node findDestination(GUI g) {
        Node[][] grid = GUI.gridPanel.getGrid();
        for (Node[] nodes : grid)
            for (Node node : nodes)
                if (node.getType() == Type.destNode)
                    return node;
        return null;
    }

    //AlgState-ek és színek visszaállítása
    /**
     * Resets the algorithm states and colors of the nodes in the grid.
     */
    public void resetAlgStates() {
        for (Node[] nodes : GUI.gridPanel.getGrid())
            for (Node node : nodes) {    //source és dest színét ne állítsuk
                node.setAlgState(AlgState.unexplored);
                if(!node.isPartOfPath()) node.setColor();
            }
    }

    /**
     * Draws the final path on the grid from destination to source.
     *
     * @param destination The destination node.
     * @throws InterruptedException      if the drawing process is interrupted.
     * @throws LineUnavailableException  if an audio line cannot be opened.
     */
    public void drawFinalPath(Node destination) throws InterruptedException, LineUnavailableException {
        Node currentNode = destination;
        while (currentNode != null) {
            if(currentNode == source || currentNode == destination)     //source és dest színét ne állítsuk
                currentNode.setColor();
            else {
                currentNode.setColor(color);
                currentNode.setPartOfPath(true);
            }
            //playSound(currentNode, source);
            Thread.sleep(delay);
            currentNode = currentNode.getPathParent();
        }
    }

    /**
     * Executes actions to be performed when the algorithm is done.
     */
    public void done(){
        resetAlgStates();
        resetPathParents();
        deleteNeighbours();
        GUI.setEnabled(true);
    }

    /**
     * Resets the path parents of all nodes in the grid.
     */
    public void resetPathParents() {
        for (Node[] nodes : GUI.gridPanel.getGrid())
            for (Node node : nodes)
                node.setPathParent(null);
    }

    /**
     * Abstract method to reset the Algorithm object.
     *
     * @return A new instance of the Algorithm.
     */
    public abstract Algorithm resetObject();

    /**
     * Gets the name of the algorithm.
     *
     * @return The name of the algorithm.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the algorithm.
     *
     * @param name The name of the algorithm.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the delay in milliseconds for visualization.
     *
     * @param ms The delay in milliseconds.
     */
    public void setDelay(int ms){
        delay = ms;
    }

    /**
     * Calculates the Euclidean distance between two nodes.
     *
     * @param cur The current node.
     * @param des The destination node.
     * @return The Euclidean distance between the nodes.
     */
    public double getDistance(Node cur, Node des){
        return Math.sqrt(Math.pow(cur.getX()*100 - des.getX()*100, 2) + Math.pow(cur.getY()*100 - des.getY()*100, 2));
    }

    /**
     * Calculates the absolute horizontal distance between two nodes.
     *
     * @param cur The current node.
     * @param des The destination node.
     * @return The absolute horizontal distance between the nodes.
     */
    public double getDistanceX(Node cur, Node des){
        return Math.abs(cur.getX()*100 - des.getX()*100);
    }

    /**
     * Calculates the absolute vertical distance between two nodes.
     *
     * @param cur The current node.
     * @param des The destination node.
     * @return The absolute vertical distance between the nodes.
     */
    public double getDistanceY(Node cur, Node des){
        return Math.abs(cur.getY()*100 - des.getY()*100);
    }

    /**
     * Plays a sound based on the distance between two nodes.
     *
     * @param cur The current node.
     * @param des The destination node.
     * @throws LineUnavailableException if an audio line cannot be opened.
     */
    public void playSound(Node cur, Node des) throws LineUnavailableException {
        double distance = Math.sqrt(Math.pow(cur.getX()*100 - des.getX()*100, 2) + Math.pow(cur.getY()*100 - des.getY()*100, 2));

        int frequency = (int) (440 + (1/distance) * 1000000000);
        Sound.beep(frequency, 5);
//        Sound sound = new Sound(frequency, 5);
//        sound.start();
    }

    public void cannotReachDestination() throws InterruptedException {
        JOptionPane.showMessageDialog(GUI, "The source and destination nodes are not in one component!", "Destination cannot be reached", JOptionPane.INFORMATION_MESSAGE);
    }

}
