package testClasses;

import hexGrid.AlgState;
import hexGrid.Node;
import hexGrid.Type;
import org.junit.Assert;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class NodeTest {

    @org.junit.Test
    public void setColor() {
        Node node = new Node(0,0, Type.available, AlgState.unexplored);
        node.setColor();
        Assert.assertEquals(node.getColor(), Color.gray);
    }

    @Test
    public void SetColorWithArg() {
        Node node = new Node(0,0, Type.available, AlgState.unexplored);
        node.setColor(Color.red);
        Assert.assertEquals(node.getColor(), Color.red);
    }

    @Test
    public void getPathParent() {
        Node node = new Node(0,0, Type.available, AlgState.unexplored);
        Assert.assertEquals(node.getPathParent(), null);
    }

    @Test
    public void setPathParent() {
        Node node = new Node(0,0, Type.available, AlgState.unexplored);
        Node parent = new Node(1,1, Type.available, AlgState.unexplored);
        node.setPathParent(parent);
        Assert.assertEquals(node.getPathParent(), parent);
    }

    @Test
    public void setPartOfPath() {
        Node node = new Node(0,0, Type.available, AlgState.unexplored);
        node.setPartOfPath(true);
        assertTrue(node.isPartOfPath());
    }
}