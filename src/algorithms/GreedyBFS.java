package algorithms;

import gui.GUI;
import hexGrid.*;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class GreedyBFS extends Algorithm {

    public GreedyBFS(GUI g, Color c, int d){
        super(g, c, d);
        setName("GreedyBFS");
    }

    public void implementedAlgorithm() throws InterruptedException, LineUnavailableException {
        source = findSource(GUI);
        destination = findDestination(GUI);

        //DFS implementálása (ha egy sor mögött komment áll, az a nem az algoritmus működése, hanem a felhasználóbarátabb élmény miatt szükséges)
        Node currentNode;
        LinkedList<Node> queue = new LinkedList<>();
        source.setAlgState(AlgState.explored);
        queue.add(source);

        while (!queue.isEmpty()) {
            Collections.sort(queue, new Comparator<Node>() {
                @Override
                public int compare(Node o1, Node o2) {
                    return Double.compare(getDistanceX(o1, destination) + getDistanceY(o1, destination), getDistance(o2, destination) + getDistanceY(o2, destination));
                }
            });
            currentNode = queue.poll();
            if (currentNode == destination) 								//early stopping
                return;
            if(currentNode != source && currentNode != destination)			//source színét ne állítsuk
                currentNode.setColor(color.darker().darker());				//felfedezett node-ok színének állítása
            //playSound(currentNode, destination);                            //hang
            publish();														//GUI frissítése
            Thread.sleep(delay);											//késleltetés
            for (Node neighbor : currentNode.getNeighbours()) {
                if(neighbor.getAlgState() == AlgState.unexplored) {
                    neighbor.setAlgState(AlgState.explored);
                    neighbor.setPathParent(currentNode);
                    queue.add(neighbor);
                }
            }
        }

        cannotReachDestination();                                           //ha a destination nem érhető el
    }

    public Algorithm resetObject() {
        return new GreedyBFS(GUI, color, delay);
    }

}
