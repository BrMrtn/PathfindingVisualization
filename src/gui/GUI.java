/**
 * Represents the graphical user interface for the pathfinding visualization.
 */

package gui;

import algorithms.Algorithm;
import algorithms.*;
import hexGrid.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {
    private static Node[][] grid;
    public SidePanel sidePanel;
    public GridPanel gridPanel;
    public JSplitPane splitPane;
    public TitleScreenPanel titleScreenPanel;
    public JLayeredPane layeredPane;
    private int gridHGap;
    private int gridVGap;

    GridBagConstraints sidePanelConstraint;
    GridBagConstraints gridPanelConstraint;

    /**
     * Constructs the GUI for the pathfinding visualization.
     *
     * @param g     The initial grid of nodes.
     * @param hgap  The horizontal gap for the grid layout.
     * @param vgap  The vertical gap for the grid layout.
     */
    public GUI(Node[][] g, int hgap, int vgap){
        super("Pathfinding Visualization");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        grid = g;
        setVisible(true);
        setSize(1400, 800);
        setLayout(new GridLayout(1, 1));

        Algorithm[] algorithms = {new DFS(this, Color.blue, 20),
                new BFS(this, Color.magenta, 20),
                new GreedyBFS(this, Color.cyan, 20),
                new BestFirstSearch(this, Color.yellow, 20)};

        gridPanel = new GridPanel(grid, hgap, vgap);
        gridPanel.setVisible(true);
        gridPanel.setBackground(Color.black);
        sidePanel = new SidePanel(this, algorithms);
        sidePanel.setVisible(true);
        sidePanel.setBackground(Color.black);

        sidePanel.setPreferredSize(new Dimension(300, this.getHeight()));
        sidePanel.setMinimumSize(new Dimension(300, this.getHeight()));
        sidePanel.setMaximumSize(new Dimension(300, this.getHeight()));

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sidePanel, gridPanel);
        splitPane.setDividerLocation(300);
        splitPane.setResizeWeight(0);
        splitPane.setOpaque(true);
        splitPane.setVisible(true);
        splitPane.setBounds(0, 0, this.getWidth(), this.getHeight());

        layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, this.getWidth(), this.getHeight());
        layeredPane.add(splitPane, JLayeredPane.DEFAULT_LAYER);

        titleScreenPanel = new TitleScreenPanel(this);
        titleScreenPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
        titleScreenPanel.setVisible(true);
        layeredPane.add(titleScreenPanel, JLayeredPane.DRAG_LAYER);
        layeredPane.setVisible(true);

        add(layeredPane);

        int offsetX = grid[0][0].getSize().width/2;
        splitPane.setBounds(getX(), getY(), getSize().width+offsetX, getSize().height);
        splitPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 30+offsetX));

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                layeredPane.setBounds(0, 0, getWidth(), getHeight());
                splitPane.setBounds(0, 0, getWidth(), getHeight());
                titleScreenPanel.setBounds(0, 0, getWidth(), getHeight());
                sidePanel.setPreferredSize(new Dimension(300, getHeight()));
                sidePanel.setMinimumSize(new Dimension(300, getHeight()));
                sidePanel.setMaximumSize(new Dimension(300, getHeight()));

                invalidate();
                validate();
                repaint();
            }
        });
    }

    /**
     * Sets the horizontal and vertical gaps for the grid layout.
     *
     * @param hgap The horizontal gap.
     * @param vgap The vertical gap.
     */
    public void setGaps(int hgap, int vgap) {
        gridHGap = hgap;
        gridVGap = vgap;
        gridPanel.setLayout(new MyGridLayout(grid.length, grid[0].length, hgap, vgap, grid[0][0]));
    }

    /**
     * Resizes the grid to a specified size and reconstructs the GUI accordingly.
     *
     * @param s The new size of the grid.
     */
    public void resizeGrid(int s){
        Node[][] g = new Node[s][s];
        for (int i = 0; i < g.length; i++){
            for (int j = 0; j < g.length; j++){
                g[i][j] = new Node(i, j, hexGrid.Type.available, AlgState.unexplored);
                if (i == g.length-1 && j == g.length-1)
                    g[i][j].setType(hexGrid.Type.destNode);
            }
        }
        g[0][0].setType(hexGrid.Type.sourceNode);
        grid = g;
        reconstruct(g);
    }

    /**
     * Reconstructs the GUI with a new grid configuration.
     *
     * @param g The new grid configuration.
     */
    public void reconstruct(Node[][] g){
        layeredPane.remove(splitPane);
        grid = g;

        gridPanel = new GridPanel(grid, gridHGap, gridVGap);
        gridPanel.setVisible(true);
        gridPanel.setBackground(Color.black);

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sidePanel, gridPanel);
        splitPane.setDividerLocation(300);
        splitPane.setResizeWeight(0);
        splitPane.setBounds(0, 0, this.getWidth(), this.getHeight());
        splitPane.setVisible(true);

        layeredPane.setBounds(0, 0, this.getWidth(), this.getHeight());
        layeredPane.add(splitPane, JLayeredPane.DEFAULT_LAYER);

        int offsetX = grid[0][0].getSize().width/2;
        splitPane.setBounds(getX(), getY(), getSize().width+offsetX, getSize().height);
        splitPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 30+offsetX));

        invalidate();
        validate();
        repaint();
    }

}