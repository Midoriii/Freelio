package pv168.freelancer.ui.utils;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

/**
 *  This class allows you to move a Component by using a mouse. The Component
 *  moved can be a high level Window (ie. Window, Frame, Dialog) in which case
 *  the Window is moved within the desktop. Or the Component can belong to a
 *  Container in which case the Component is moved within the Container.
 *
 *  When moving a Window, the listener can be added to a child Component of
 *  the Window. In this case attempting to move the child will result in the
 *  Window moving. For example, you might create a custom "Title Bar" for an
 *  undecorated Window and moving of the Window is accomplished by moving the
 *  title bar only. Multiple components can be registered as "window movers".
 *
 *  Components can be registered when the class is created. Additional
 *  components can be added at any time using the registerComponent() method.
 *
 *  Courtesy of https://tips4java.wordpress.com/2009/06/14/moving-windows/
 *  The Author himself states that the code is free to use and modify, and no
 *  mention of the author is needed.
 *
 */
public class ComponentMover extends MouseAdapter
{
    private Insets dragInsets = new Insets(0, 0, 0, 0);
    private Dimension snapSize = new Dimension(1, 1);
    private Insets edgeInsets = new Insets(0, 0, 0, 0);
    private boolean changeCursor = true;
    private boolean autoLayout = false;

    private Class destinationClass;
    private Component destinationComponent;
    private Component destination;
    private Component source;

    private Point pressed;
    private Point location;

    private Cursor originalCursor;
    private boolean autoscrolls;
    private boolean potentialDrag;


    /**
     *  Constructor for moving individual components. The components must be
     *  regisetered using the registerComponent() method.
     */
    public ComponentMover()
    {
    }

    /**
     *  Set the drag insets. The insets specify an area where mouseDragged
     *  events should be ignored and therefore the component will not be moved.
     *  This will prevent these events from being confused with a
     *  MouseMotionListener that supports component resizing.
     *
     *  @param  dragInsets
     */
    public void setDragInsets(Insets dragInsets)
    {
        this.dragInsets = dragInsets;
    }

    /**
     *  Add the required listeners to the specified component
     *
     *  @param components  the component the listeners are added to
     */
    public void registerComponent(Component... components)
    {
        for (Component component : components)
            component.addMouseListener( this );
    }

    /**
     *	Get the snap size
     *
     *  @return the snap size
     */
    public Dimension getSnapSize()
    {
        return snapSize;
    }

    /**
     *  Setup the variables used to control the moving of the component:
     *
     *  source - the source component of the mouse event
     *  destination - the component that will ultimately be moved
     *  pressed - the Point where the mouse was pressed in the destination
     *      component coordinates.
     */
    @Override
    public void mousePressed(MouseEvent e)
    {
        source = e.getComponent();
        int width  = source.getSize().width  - dragInsets.left - dragInsets.right;
        int height = source.getSize().height - dragInsets.top - dragInsets.bottom;
        Rectangle r = new Rectangle(dragInsets.left, dragInsets.top, width, height);

        if (r.contains(e.getPoint()))
            setupForDragging(e);
    }

    private void setupForDragging(MouseEvent e)
    {
        source.addMouseMotionListener( this );
        potentialDrag = true;

        //  Determine the component that will ultimately be moved

        if (destinationComponent != null)
        {
            destination = destinationComponent;
        }
        else if (destinationClass == null)
        {
            destination = source;
        }
        else  //  forward events to destination component
        {
            destination = SwingUtilities.getAncestorOfClass(destinationClass, source);
        }

        pressed = e.getLocationOnScreen();
        location = destination.getLocation();

        if (changeCursor)
        {
            originalCursor = source.getCursor();
            source.setCursor( Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR) );
        }

        //  Making sure autoscrolls is false will allow for smoother dragging of
        //  individual components

        if (destination instanceof JComponent)
        {
            JComponent jc = (JComponent)destination;
            autoscrolls = jc.getAutoscrolls();
            jc.setAutoscrolls( false );
        }
    }

    /**
     *  Move the component to its new location. The dragged Point must be in
     *  the destination coordinates.
     */
    @Override
    public void mouseDragged(MouseEvent e)
    {
        Point dragged = e.getLocationOnScreen();
        int dragX = getDragDistance(dragged.x, pressed.x, snapSize.width);
        int dragY = getDragDistance(dragged.y, pressed.y, snapSize.height);

        int locationX = location.x + dragX;
        int locationY = location.y + dragY;

        //  Mouse dragged events are not generated for every pixel the mouse
        //  is moved. Adjust the location to make sure we are still on a
        //  snap value.

/*        while (locationX < edgeInsets.left)
            locationX += snapSize.width;

        while (locationY < edgeInsets.top)
            locationY += snapSize.height;

        Dimension d = getBoundingSize( destination );

        while (locationX + destination.getSize().width + edgeInsets.right > d.width)
            locationX -= snapSize.width;

        while (locationY + destination.getSize().height + edgeInsets.bottom > d.height)
            locationY -= snapSize.height;*/

        //  Adjustments are finished, move the component

        destination.setLocation(locationX, locationY);
    }

    /*
     *  Determine how far the mouse has moved from where dragging started
     *  (Assume drag direction is down and right for positive drag distance)
     */
    private int getDragDistance(int larger, int smaller, int snapSize)
    {
        int halfway = snapSize / 2;
        int drag = larger - smaller;
        drag += (drag < 0) ? -halfway : halfway;
        drag = (drag / snapSize) * snapSize;

        return drag;
    }

    /**
     *  Restore the original state of the Component
     */
    @Override
    public void mouseReleased(MouseEvent e)
    {
        if (!potentialDrag) return;

        source.removeMouseMotionListener( this );
        potentialDrag = false;

        if (changeCursor)
            source.setCursor( originalCursor );

        if (destination instanceof JComponent)
        {
            ((JComponent)destination).setAutoscrolls( autoscrolls );
        }

        //  Layout the components on the parent container

        if (autoLayout)
        {
            if (destination instanceof JComponent)
            {
                ((JComponent)destination).revalidate();
            }
            else
            {
                destination.validate();
            }
        }
    }
}