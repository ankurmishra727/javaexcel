import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 * <b>Programm:</b> JSheet - Spreadsheet<br>
 * <b>Copyright:</b> 2001 Andreas Gohr, Frank Schubert<br>
 * <b>License:</b> GPL2 or higher<br>
 * <b>Version:</b> 1.0<br>
 * <b>Date:</b> 22.06.2001<br>
 * <br>
 * <b>Info:</b> MainMenu stellt das Hauptmenu des Programms zur Verfügung
 */
public class MainMenu extends JMenuBar {

  /////////////////////////////////////////////////////////////////////////////
  /**
   * Konstruktor - Erzeugt das Menu und bindet es an den ActionListener
   */
  public MainMenu( ActionListener l ) {
    JMenu menu = null;
    menu = new JMenu("File");
    // Syntax MakeMI:
    /* makeMI( String label, String name, 
               ActionListener listener, char Shortcut ) */
    menu.add( makeMI("New File ...", "new", l, 'n') );
    menu.add( makeMI("Open File ...", "open", l, 'o') );
    menu.add( makeMI("Save File ...", "save", l, 's') );
    menu.add( makeMI("Exit Program", "exit", l, 'x') );
    
    add(menu);

    menu = new JMenu("Table");
    menu.add( makeMI("Insert Column", "insert_column", l, 'i') );
    menu.add( makeMI("Insert Row", "insert_row", l, 'j') );
    
    menu.add( makeMI("Delete Column", "delete_column", l, 'd') );
    menu.add( makeMI("Delete Row", "delete_row", l, 'r') );
    
    add(menu);
    
    menu = new JMenu("?");
    menu.add( makeMI("Make coffee", "coffee", l, 'c') );
    menu.add( makeMI("About", "about", l, 'a') );

    add(menu);
  }
  
  /////////////////////////////////////////////////////////////////////////////
  /**
   * Methode um einen Menueeintrag hinzufuegen:
   */
  private JMenuItem makeMI (String Label, String name,
                            ActionListener listener, char SC) {
    JMenuItem mi;
    mi = new JMenuItem(  Label,SC);
    mi.setActionCommand( name);
    mi.addActionListener(listener);
    return mi;
  }
}