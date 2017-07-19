import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class MyMenu extends JMenu
{
   protected Canvas canvas;  // application's content pane
   
   public MyMenu( JComponent contentPane, String caption, char hotKey )
   {
      // call JMenu constructor to build menu with given caption
      super( caption );
      // copy application's content pane to instance variable
      this.canvas = (Canvas)contentPane;
      // set menu ALT-key and font
      this.setMnemonic( hotKey );
      this.setFont( new Font( "Dialog", Font.PLAIN, 22 ) );
   }
}   
      
class ShapeMenu extends MyMenu implements ActionListener
{
   // contentPane is inherited from MyMenu
   // declare shape commands
   private JCheckBoxMenuItem cmdLine, cmdPolyline;
   
   public ShapeMenu( JComponent contentPane )
   {
      // call MyMenu constructor to initialize object
      super( contentPane, "Shape", 'S' );
      // build the commands
      cmdLine = new MyCheckBoxMenuItem( "Line", 'L' );
      cmdPolyline = new MyCheckBoxMenuItem( "Polyline", 'P' );
      // add them to the menu
      this.add( cmdLine );
      this.add( cmdPolyline );
      // register this menu object to listen to the commands
      cmdLine.addActionListener( this );
      cmdPolyline.addActionListener( this );
      // turn on the line command
      cmdLine.setState( true );
   }
        
   public void actionPerformed( ActionEvent e )
   {
      if ( e.getSource( ) == cmdLine )
      {
         clearShapeMenuItems( );
         cmdLine.setState( true );
         canvas.setSelectedShape( ShapeName.LINE );
      }
      if ( e.getSource( ) == cmdPolyline )
      {
         clearShapeMenuItems( );
         cmdPolyline.setState( true );
         canvas.setSelectedShape( ShapeName.POLYLINE );
      }
      else
         /* unrecoverable non-user error */ ;
   }
   
   private void clearShapeMenuItems( )
   {
      cmdLine.setState( false );
      cmdPolyline.setState( false );
   }
}

class MyCheckBoxMenuItem extends JCheckBoxMenuItem
{
   public MyCheckBoxMenuItem( String caption, char hotKey )
   {
      // call JCheckBoxMenuItem constructor to set command caption and state.
      super( caption, false );
      this.setMnemonic( hotKey );
      this.setFont( new Font( "Dialog", Font.PLAIN, 18 ) );
   }
}

     
class ColorMenu extends MyMenu implements ActionListener
{
   // contentPane is inherited from MyMenu
   // declare color commands
   private JMenuItem cmdBorderColor, cmdFillColor;
   
   public ColorMenu( JComponent contentPane )
   {
      // call MyMenu constructor to initialize object
      super( contentPane, "Color", 'C' );
      // build the commands
      cmdBorderColor = new MyMenuItem( "Border Color...", 'B' );
      // add them to the menu
      this.add( cmdBorderColor );
      // register this menu object to listen to the commands
      cmdBorderColor.addActionListener( this );
   }
        
   public void actionPerformed( ActionEvent e )
   {
      if ( e.getSource( ) == cmdBorderColor )
      {
         // get canvas's current border color
         Color current = canvas.getSelectedBorderColor( );
         // get new color from user using current as chooser's default
         Color temp = JColorChooser.showDialog( this, "Choose a color", current );
         // if null do nothing, if not null change canvas's color
         if ( temp != null )
            canvas.setSelectedBorderColor( temp );
      }
      else
         /* unrecoverable non-user error */ ;
   }
}

class MyMenuItem extends JMenuItem
{
   public MyMenuItem( String caption, char hotKey )
   {
      // call JMenuItem constructor to set command caption, ALT-key and font.
      super( caption, hotKey );
      this.setFont( new Font( "Dialog", Font.PLAIN, 18 ) );
   }
}