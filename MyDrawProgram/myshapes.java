import java.awt.*;
import java.util.*;

// Abstract class Shape models a shape that is drawn onto the canvas.
// Shape is 'abstract' since it has abstract methods,
// i.e. methods that haven't been implemented.
// It cannot be instantiated; it can only be used as a
// superclass.

abstract class Shape
{
   // state variables defining the shape
   protected Color borderColor;
   
   public Shape( Color color )
   // Define the border color for the Shape.
   {
      this.borderColor = color;
   }

   public abstract void nextPt( int x, int y );
   // Continue shape at mouse position (x, y).

   public abstract void lastPt( int x, int y );
   // End shape at mouse position (x, y).
     
   public abstract void drawFinished( Graphics g );
   // Draw finished shape onto the given Graphics object.
   
   public abstract void drawUnfinished( Graphics g, int x, int y );
   // Draw unfinished shape onto the given Graphics object.
   // (x, y) gives the mouse's current position. 
}

class Line extends Shape
{
   protected int mouseDownX, mouseDownY; // position of line start
   protected int mouseUpX, mouseUpY;     // position of line ending
      
   public Line( Color color, int x, int y )
   // Start a new Line object whose mouse down position is (x, y).
   {
      super( color );
      mouseDownX = x;
      mouseDownY = y;
   }

   public void nextPt( int x, int y )
   {
   }
      
   public void lastPt( int x, int y )
   // End line at mouse position (x, y).
   {
      mouseUpX = x;
      mouseUpY = y;
   }
       
   public void drawFinished( Graphics g )
   // Implement Shape's abstract method to draw a line.
   {
      Color hold = g.getColor( ); // hold g's current color
      g.setColor( borderColor );  // switch g's color to that of the shape
      g.drawLine( mouseDownX, mouseDownY, mouseUpX, mouseUpY );
      g.setColor( hold );         // restore g's color
   }
   
   public void drawUnfinished( Graphics g, int mouseIsAtX, int mouseIsAtY )
   // Implement Shape's abstract method to draw an unfinished line.
   {
      Color hold = g.getColor( );
      g.setColor( borderColor );
      g.drawLine( mouseDownX, mouseDownY, mouseIsAtX, mouseIsAtY );
      g.setColor( hold );
   }
}

class Polyline extends Line
{
   protected ArrayList<Integer> x = new ArrayList<Integer>(5);
   protected ArrayList<Integer> y = new ArrayList<Integer>(5); 
      
   public Polyline( Color color, int x, int y )
   // Start a new Polyline object whose first point is (x, y).
   {
      super( color, x, y );
      this.x.add( x );
      this.y.add( y );
   }

   public void nextPt( int x, int y )
   // Save current point and save new point.
   {
      this.x.add( mouseDownX = x );
      this.y.add( mouseDownY = y );
   }
   
   public void lastPt( int x, int y )
   // End polyline at mouse position (x, y).
   {
      this.x.add( x );
      this.y.add( y );
   }
      
   public void drawFinished( Graphics g )
   // Implement Shape's abstract method to draw a polyline.
   {
      Color hold = g.getColor( );
      g.setColor( borderColor );
      for (int k = 1; k < x.size( ); k++)
         g.drawLine( x.get(k-1), y.get(k-1), x.get(k), y.get(k) );
      g.setColor( hold );
   }
   
   public void drawUnfinished( Graphics g, int mouseIsAtX, int mouseIsAtY )
   // Implement Shape's abstract method to draw an unfinished polyline.
   {
      Color hold = g.getColor( );
      g.setColor( borderColor );
      for (int k = 1; k < x.size( ); k++)
         g.drawLine( x.get(k-1), y.get(k-1), x.get(k), y.get(k) );
      g.drawLine( mouseDownX, mouseDownY, mouseIsAtX, mouseIsAtY );
      g.setColor( hold );
   }
}

class ShapeStack
{
   // store to hold shapes
   private ArrayList<Shape> stack = new ArrayList<Shape>( );
   
   public void drawAll( Graphics g )
   // Draw every shape in stack onto the given graphics context.
   {
      for ( int k = 0; k < stack.size( ); k++ )
         stack.get( k ).drawFinished( g );
   }

   public void add( Shape item )
   // Add the item to the main stack.
   {
      stack.add( item );
   }      
}