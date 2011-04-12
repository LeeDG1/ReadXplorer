/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cebitec.vamp.thumbnail;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Collection;
import org.netbeans.api.visual.layout.Layout;
import org.netbeans.api.visual.widget.Widget;

/**
 * Creates a custom layout to display all Widgets in a GridLayout used by the scene.
 * @author dkramer
 */
public class ThumbGridLayout implements Layout {

    private int columns;
    private int rows;

    public ThumbGridLayout(int columns) {
        this.columns = columns;        
    }

    @Override
    public void layout(Widget widget) {
        Collection<Widget> children = widget.getChildren();
        int posX = 0;
        int posY = 0;
        int col = 0;
        int maxHeight = 0;
        int maxWidth = 0;
        //Calculates maxHeigth, maxWidth for all Children
        for (Widget child : children) {
            Rectangle prefferedBounds = child.getPreferredBounds();
            int width = prefferedBounds.width;
            int height = prefferedBounds.height;
            if (height > maxHeight) {
                maxHeight = height;
            }
            if (width > maxWidth) {
                maxWidth = width;
            }
        }

        //positions all Children in a gridlayou
        for (Widget child : children) {
            Rectangle prefferedBounds = child.getPreferredBounds();
            int x = prefferedBounds.x;
            int y = prefferedBounds.y;
            int width = prefferedBounds.width;
            int height = prefferedBounds.height;
            int lx = posX - x;
            int ly = posY - y;
            if (child.isVisible()) {
                child.resolveBounds(new Point(lx, ly), new Rectangle(x, y, width, height));
                posX += maxWidth;
            } else {
                child.resolveBounds(new Point(lx, ly), new Rectangle(x, y, 0, 0));
            }
            col++;
            if(col == columns){
                col = 0;
                posX = 0;
                posY +=maxHeight;
            }
        }
    }

    @Override
    public boolean requiresJustification(Widget widget) {
        return false;
    }

    @Override
    public void justify(Widget widget) {
    }
}
