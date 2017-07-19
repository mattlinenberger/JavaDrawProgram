import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

 enum ShapeName
{LINE,POLYLINE};

class Canvas extends JPanel implements MouseListener, MouseMotionListener
{ 

   public Dimension getPreferredSize( )
   {
      return new Dimension( 500, 500 );
   } 
   //Internal data for colors
   private Color selectedBorderColor;
   
   public Color getSelectedBorderColor( )
   {
      return selectedBorderColor;
   }   
   
   public void setSelectedBorderColor( Color borderColor )
   {
      selectedBorderColor = borderColor;
   } 

   // internal data for shapes with get and set methods
   // currently selected shape
   private ShapeName selectedShape;
   
   public ShapeName getSelectedShape( )
   {
      return selectedShape;
   }   
   
   public void setSelectedShape( ShapeName shape )
   {
      selectedShape = shape;
   } 
   
   // internal data for shape drawing
   private boolean mouseDown;
   private int mouseIsAtX, mouseIsAtY;
   private boolean mouseClicked;
   private Shape newestShape;
   private ShapeStack shapeStack;
   
   
   public Canvas( )
   // Configure the panel.
   {
      selectedShape = ShapeName.LINE;
      selectedBorderColor = Color.BLACK;
      shapeStack = new ShapeStack();
      this.addMouseListener( this );
      this.addMouseMotionListener( this );
      this.setBackground( Color.WHITE );
   }    

   public void paint( Graphics g )
   {
      super.paint( g );
      shapeStack.drawAll(g);
      if(mouseDown || mouseClicked)
         newestShape.drawUnfinished(g,mouseIsAtX,mouseIsAtY);
         
   }
   
   //======== MouseListener event handlers ==========================

   public void mouseEntered( MouseEvent e )
   {
   
   }

   public void mouseExited( MouseEvent e )
   {
   
   }

   public void mousePressed( MouseEvent e )
   // Capture mouse down position and signal started.
   {
    if ( selectedShape == ShapeName.LINE )
      {
         mouseDown = true;
         newestShape= new Line(selectedBorderColor,e.getX(),e.getY());
         
      }
   
   }

   public void mouseReleased( MouseEvent e )
   // Capture mouse up position and signal done.
   {
        if ( selectedShape == ShapeName.LINE )
      {
         mouseDown = false;
         newestShape.lastPt(e.getX(),e.getY());
         shapeStack.add(newestShape);
         repaint( ); 
      }
   }

   public void mouseClicked( MouseEvent e )
   {
      if ( selectedShape == ShapeName.POLYLINE )
      {
         if ( e.getClickCount( ) == 1 && !mouseClicked ) // first click
         {
           
            mouseClicked = true;    // signal started
            newestShape = new Polyline(selectedBorderColor,e.getX(),e.getY());
         }
         else if ( e.getClickCount( ) == 1 && mouseClicked ) // subsequent click
         {
            newestShape.nextPt(e.getX(),e.getY());
            repaint( );
         }
         else if ( e.getClickCount( ) == 2 ) // last click
         {
            
            mouseClicked = false;   // signal done
            newestShape.lastPt(e.getX(),e.getY());
            shapeStack.add(newestShape);
            repaint( ); 
         }        
      }   
   }

   //======== MouseMotionListener event handlers =======================

   public void mouseMoved( MouseEvent e )
   {
      if ( selectedShape == ShapeName.POLYLINE && mouseClicked )
      {
         mouseIsAtX = e.getX( );
         mouseIsAtY = e.getY( );
         repaint( );
      }
   }

   public void mouseDragged( MouseEvent e )
   // Capture mouse position and draw to down point.
   {
        if ( selectedShape == ShapeName.LINE )
      {
         mouseIsAtX = e.getX( );
         mouseIsAtY = e.getY( );
         repaint( );
      }
   } 

}