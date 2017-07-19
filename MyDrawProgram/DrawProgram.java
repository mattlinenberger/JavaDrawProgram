import javax.swing.*;

public class DrawProgram
{ 
   public static void createAndShowGUI( )
   {
      // build the JFrame that is the application's GUI window
      JFrame win = new JFrame( "Matt's Draw Program" );
      win.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
      // build a JPanel to use as the window's content pane
      JComponent winContentPane = new Canvas();
      win.setContentPane( winContentPane );
      // build and configure the window's menu bar
      JMenuBar winMenuBar = new JMenuBar( );
      winMenuBar.add( new ShapeMenu(winContentPane));
      winMenuBar.add( new ColorMenu(winContentPane));
      win.setJMenuBar( winMenuBar );
      // size and display the window
      win.pack( );
      win.setVisible( true );
   }
   
   public static void main ( String [] args )
   {
      // schedule the frame creation on the event dispatch thread
      SwingUtilities.invokeLater(
         new Runnable( ) 
         {
            public void run( ) 
            {
               createAndShowGUI( );
            }
         }
      );
      // at this point in the code two threads, the main thread
      // and the event dispatch thread, are running. all GUI
      // interaction takes place on the event dispatch thread
   }
}