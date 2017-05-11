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
 * <b>Info:</b> DimDialog stellt einen Dialog zur Größeneingabe zur Verfuegung
 *              <P>Bestaetigt der User den den Dialog mit "Okay" liefert
 *                 <I>wasOkay()</I> <i>true</i> zurück.</P>
 */
public class DimDialog extends JDialog
		       implements ActionListener{
  private int x=0;
  private int y=0;
  private boolean clicked = false;
  private JTextField dimx;
  private JTextField dimy;

  /////////////////////////////////////////////////////////////////////////////
  /**
   * Konstruktor. Erzeugt einen modalen Dialog mit dem Besitzer owner.
   */  
  public DimDialog(Frame owner){
    super(owner,true);
    setTitle("Dimension");
    setSize(300, 110);
    getContentPane().setLayout(new BorderLayout());

    JPanel top = new JPanel();
    JPanel middle = new JPanel(new GridLayout(2,2));
    JPanel bottom = new JPanel(new GridLayout(1,2));

    //Top
    top.add(new JLabel("Size of the new table?"));
    
    //Middle
    dimx = new JTextField("15",20);
    dimy = new JTextField("15",20);
    middle.add(new JLabel("Rows:"));
    middle.add(dimx);
    middle.add(new JLabel("Columns:"));
    middle.add(dimy);

    //Bottom
    JButton cancel = new JButton("Cancel");
    cancel.setActionCommand("cancel");
    cancel.addActionListener(this);
    JButton okay = new JButton("Okay");
    okay.setActionCommand("okay");
    okay.addActionListener(this);
    bottom.add(cancel,BorderLayout.SOUTH);
    bottom.add(okay,BorderLayout.SOUTH);

    //All
    getContentPane().add(top,BorderLayout.NORTH);
    getContentPane().add(middle,BorderLayout.CENTER);
    getContentPane().add(bottom,BorderLayout.SOUTH);

    setVisible(true);
  }

  /////////////////////////////////////////////////////////////////////////////
  /**
   * Eventhandler zur Button-Auswertung
   */  
  public void actionPerformed( ActionEvent e ){
    String cmd = e.getActionCommand();
    if(cmd=="okay") {
      try {
        x=(Integer.valueOf(dimx.getText())).intValue();
        y=(Integer.valueOf(dimy.getText())).intValue();
      }
      catch (Exception ex){
        new Fehler("No valid dimension!");
        return;
      }
      clicked=true;
    }
    setVisible(false);
  }
  
  /////////////////////////////////////////////////////////////////////////////
  /**
   * Liefert die eingegebenen Zeilen zurueck
   */ 
  public int row(){
    return x;
  }
  
  /////////////////////////////////////////////////////////////////////////////
  /**
   * Liefert die eingegebenen Spalten zurueck
   */ 
  public int col(){
    return y;
  }
  
  /////////////////////////////////////////////////////////////////////////////
  /**
   * Liefert die eingegebenen Reihen zurueck
   */ 
  public boolean wasOkay(){
    return clicked;
  }
}
