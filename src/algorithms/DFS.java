package algorithms;

import gui.GUI;
import gui.Sound;
import hexGrid.*;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class DFS extends Algorithm {

	public DFS(GUI g, Color c, int d){
		super(g, c, d);
		setName("DFS");
	}

	public void implementedAlgorithm() throws InterruptedException, LineUnavailableException {
		source = findSource(GUI);
		destination = findDestination(GUI);

		//DFS implementálása (ha egy sor mögött komment áll, az a nem az algoritmus működése, hanem a felhasználóbarátabb élmény miatt szükséges)
		Node currentNode;
		Stack<Node> stack = new Stack<>();
		stack.push(source);


		while (!stack.isEmpty()) {
			currentNode = stack.pop();
			if (currentNode.getAlgState() == AlgState.unexplored) {
				currentNode.setAlgState(AlgState.explored);


				if(currentNode != source && currentNode != destination)			//source színét ne állítsuk
					currentNode.setColor(color.darker().darker());				//felfedezett node-ok színének állítása
				publish();														//GUI frissítése
				//playSound(currentNode, destination);							//hang
				Thread.sleep(delay);											//késleltetés

				for (Node neighbor : currentNode.getNeighbours()) {
					if(neighbor.getAlgState() == AlgState.unexplored) {
						stack.push(neighbor);
						neighbor.setPathParent(currentNode);
					}
				}
			}
			if (currentNode == destination) 									//early stopping
				return;
		}

		cannotReachDestination();                                           	//ha a destination nem érhető el
	}

	public Algorithm resetObject() {
        return new DFS(GUI, color, delay);
	}

}
