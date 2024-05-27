package gui;
import hexGrid.Node;

import java.awt.*;

public class MyGridLayout extends GridLayout {
    Node node;
    public MyGridLayout(int rows, int cols, int hgap, int vgap, Node n) {
        super(rows, cols, hgap, vgap);
        node = n;
    }

    // A függvényben írt változtatásokon kívül a width, height, rows, cols, hgap, vgap változókat
    // private elérhetőségük miatt a Getter függvényeikre kellett cserélni.
    @Override
    public void layoutContainer(Container parent) {
        synchronized (parent.getTreeLock()) {
            Insets insets = parent.getInsets();
            int ncomponents = parent.getComponentCount();
            int nrows = getRows();
            int ncols = getColumns();
            boolean ltr = parent.getComponentOrientation().isLeftToRight();

            //offset értékének beállítása
            int offsetX = 0;
            if(node.getSize().width != 0)
                offsetX = node.getSize().width/2;
            else
                offsetX = node.getPreferredSize().width/2;


            if (ncomponents == 0) {
                return;
            }
            if (nrows > 0) {
                ncols = (ncomponents + nrows - 1) / nrows;
            } else {
                nrows = (ncomponents + ncols - 1) / ncols;
            }
            // 4370316. To position components in the center we should:
            // 1. get an amount of extra space within Container
            // 2. incorporate half of that value to the left/top position
            // Note that we use trancating division for widthOnComponent
            // The reminder goes to extraWidthAvailable
            int totalGapsWidth = (ncols - 1) * getHgap();
            int widthWOInsets = parent.getWidth() - (insets.left + insets.right);
            int widthOnComponent = (widthWOInsets - totalGapsWidth) / ncols;
            int extraWidthAvailable = (widthWOInsets - (widthOnComponent * ncols + totalGapsWidth)) / 2;

            int totalGapsHeight = (nrows - 1) * getVgap();
            int heightWOInsets = parent.getHeight() - (insets.top + insets.bottom);
            int heightOnComponent = (heightWOInsets - totalGapsHeight) / nrows;
            int extraHeightAvailable = (heightWOInsets - (heightOnComponent * nrows + totalGapsHeight)) / 2;
            if (ltr) {
                for (int c = 0, x = insets.left + extraWidthAvailable; c < ncols ; c++, x += widthOnComponent + getHgap()) {
                    for (int r = 0, y = insets.top + extraHeightAvailable; r < nrows ; r++, y += heightOnComponent + getVgap()) {
                        int i = r * ncols + c;
                        if (i < ncomponents) {
                            //ha páratlan sorban vagyunk, akkor az offsettel eltoljuk a node-ot
                            if(r%2 != 0)
                                parent.getComponent(i).setBounds(x+offsetX, y, widthOnComponent, heightOnComponent);
                            else
                                parent.getComponent(i).setBounds(x, y, widthOnComponent, heightOnComponent);
                        }
                    }
                }
            } else {
                for (int c = 0, x = (parent.getWidth() - insets.right - widthOnComponent) - extraWidthAvailable; c < ncols ; c++, x -= widthOnComponent + getHgap()) {
                    for (int r = 0, y = insets.top + extraHeightAvailable; r < nrows ; r++, y += heightOnComponent + getVgap()) {
                        int i = r * ncols + c;
                        //ha páratlan sorban vagyunk, akkor az offsettel eltoljuk a node-ot
                        if(r%2 != 0) {
                            parent.getComponent(i).setBounds(x+offsetX, y, widthOnComponent, heightOnComponent);
                        }
                        else {
                            parent.getComponent(i).setBounds(x, y, widthOnComponent, heightOnComponent);
                        }
                    }
                }
            }
        }
    }

}
