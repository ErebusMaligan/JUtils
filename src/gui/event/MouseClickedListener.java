package gui.event;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Aug 3, 2015, 1:14:36 AM 
 */
public interface MouseClickedListener extends MouseListener {
	@Override
    public default void mouseEntered(MouseEvent e) {}

    @Override
    public default void mouseExited(MouseEvent e) {}

    @Override
    public default void mousePressed(MouseEvent e) {}

    @Override
    public default void mouseReleased(MouseEvent e) {}
}