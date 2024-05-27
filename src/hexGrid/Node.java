/**
 * Represents a hexagonal grid node with associated properties and functionality.
 */

package hexGrid;

import gui.Sound;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a hexagonal grid node with associated properties and functionality.
 */
public class Node extends JButton{
	int x;
	int y;
	private ArrayList<Node> neighbours;
	private AlgState algState;
	private Type type;
	private Color color;
	private Node pathParent;
	private boolean partOfPath = false;

	Polygon polygon;
	int n = 6;//hatszög
	double angle = 2*Math.PI/n;

	/**
	 * Constructs a Node with specified coordinates, type, and algorithmic state.
	 *
	 * @param Cx The x-coordinate of the node.
	 * @param Cy The y-coordinate of the node.
	 * @param t  The type of the node.
	 * @param s  The algorithmic state of the node.
	 */
	public Node(int Cx, int Cy, Type t, AlgState s) {
		super();
		x = Cx;
		y = Cy;
		type = t;
		algState = s;
		neighbours = new ArrayList<>();
		partOfPath = false;
		Dimension size = getPreferredSize();
		this.setPreferredSize(new Dimension(80,80));
		size.width = Math.min(size.width, size.height);
		size.height = Math.min(size.width, size.height);

		setPreferredSize(size);
		setContentAreaFilled(false);
	}

	/**
	 * Creates the hexagonal polygon for the node.
	 *
	 * @return The hexagonal polygon.
	 */
	protected Polygon createPolygon(){
		int [] xArray = new int[n];
		int[] yArray = new int[n];
		int x0 = getSize().width/2;
		int y0 = getSize().height/2;
		for(int i=0; i<n; i++) {
			xArray[i] = x0 + (int)Math.round(((double) getWidth() /2)*Math.cos(angle*i + angle/2)); //a +angle/2 azért kell, hogy jó irányba álljon a hatszög
			yArray[i] = y0 + (int)Math.round(((double) getHeight() /2)*Math.sin(angle*i + angle/2));
		}
		polygon = new Polygon(xArray, yArray, n);
		return polygon;
	}

	//hatszög határának megrajzolása
	protected void paintBorder(Graphics g) {
		g.setColor(Color.black);
		Polygon polygon = createPolygon();
		g.drawPolygon(polygon);
	}

	//hatszög kitöltése (hogy )
	protected void paintComponent(Graphics g) {
		// Node színének állítása (kattintáskor is)
		if (getModel().isArmed())
			g.setColor(color.darker());
		else g.setColor(color);

		polygon = createPolygon();
		g.fillPolygon(polygon);
		super.paintComponent(g);
	}

	//hatszög hitboxának állítása
	@Override
	public boolean contains(int x, int y){
		polygon = createPolygon();
		return polygon.contains(x, y);
	}

	/**
	 * Gets the type of the node.
	 *
	 * @return The type of the node.
	 */
	public Type getType() {
		return type;
	}

	/**
	 * Sets the type of the node and updates its color accordingly.
	 *
	 * @param t The new type of the node.
	 */
	public void setType(Type t) {
		type = t;
		setColor();
	}

	/**
	 * Sets the algorithmic state of the node.
	 *
	 * @param s The new algorithmic state.
	 */
	public void setAlgState(AlgState s) {
		algState = s;
	}

	/**
	 * Gets the algorithmic state of the node.
	 *
	 * @return The algorithmic state of the node.
	 */
	public AlgState getAlgState() {
		return algState;
	}

	/**
	 * Adds a neighboring node to the list of neighbors.
	 *
	 * @param n The neighboring node.
	 */
	public void addNeighbour(Node n){
		neighbours.add(n);
	}

	/**
	 * Deletes all neighbors of the node.
	 */
	public void deleteNeighbours(){ neighbours.clear(); }

	/**
	 * Gets the list of neighboring nodes.
	 *
	 * @return The list of neighboring nodes.
	 */
	public ArrayList<Node> getNeighbours() {
		return neighbours;
	}

	/**
	 * Sets the color of the node based on its type.
	 */
	public void setColor(){
		Color baseColor = switch (type) {
            case available -> Color.gray;
            case sourceNode -> Color.green;
			case destNode -> Color.red;
            case wall -> Color.black;
        };
        color = baseColor;
		repaint();
	}

	/**
	 * Sets the color of the node to a specified color.
	 *
	 * @param c The new color of the node.
	 */
	public void setColor(Color c){
		if(partOfPath) return;
		color = c;
		repaint();
	}

	/**
	 * Gets the current color of the node.
	 *
	 * @return The current color of the node.
	 */
	public Color getColor(){
		return color;
	}

	/**
	 * Sets the path parent of the node.
	 *
	 * @param p The new path parent node.
	 */
	public void setPathParent(Node p){
		pathParent = p;
	}

	/**
	 * Gets the path parent of the node.
	 *
	 * @return The path parent of the node.
	 */
	public Node getPathParent(){
		return pathParent;
	}

	/**
	 * Sets whether the node is part of the path or not.
	 *
	 * @param b True if the node is part of the path, false otherwise.
	 */
	public void setPartOfPath(boolean b){
		partOfPath = b;
	}

	/**
	 * Checks if the node is part of the path.
	 *
	 * @return True if the node is part of a path, false otherwise.
	 */
	public boolean isPartOfPath(){
		return partOfPath;
	}

}