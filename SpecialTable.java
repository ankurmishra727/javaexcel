import javax.swing.*;
import java.awt.*;
/**
 * <b>Programm:</b> JSheet - Spreadsheet<br>
 * <b>Copyright:</b> 2001 Andreas Gohr, Frank Schubert<br>
 * <b>License:</b> GPL2 or higher<br>
 * <b>Version:</b> 1.0<br>
 * <b>Date:</b> 22.06.2001<br>
 * <br>
 * <b>Info:</b> SpecialTable fasst ein JScrollpanel mit JTable usw. auf einem JPanel zusammen
 */
public class SpecialTable extends JPanel {

  private JTable _jt = null;
  private DatenModell _dm = null;
  private JScrollPane _jsp = null;
  
  /////////////////////////////////////////////////////////////////////////////  
  /**
   * Konstruktor mit Dimension
   */
  public SpecialTable(int row, int col) {
    _dm = new DatenModell(row,col);
    _jt = new JTable(_dm);
    initTable();
    _jsp = new JScrollPane(_jt);
    initScrollPane();
    this.setLayout(new GridLayout(1,1));
    this.add(_jsp);
  }

  /////////////////////////////////////////////////////////////////////////////  
  /**
   * Konstruktor mit Dateiname
   */
  public SpecialTable(String filename) throws Exception {
    _dm = new DatenModell(filename);
    _jt = new JTable(_dm);
    initTable();
    _jsp = new JScrollPane(_jt);
    initScrollPane();
    this.setLayout(new GridLayout(1,1));
    this.add(_jsp);
  }

  /////////////////////////////////////////////////////////////////////////////
  /**
   * Initialisiert die JScrollPane
   */
  public void initScrollPane() {
    _jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    _jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    _jsp.setRowHeaderView(new RowHeader(_jt));
  }

  /////////////////////////////////////////////////////////////////////////////
  /**
   * Initialisiert die Tabelle mit ein paar Standardwerten
   */
  public void initTable(){
    _jt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    _jt.setColumnSelectionAllowed(true);
    _jt.setRowSelectionAllowed(true);
    _jt.setDefaultRenderer(Object.class,new MyCellRenderer());
    //Headernamen aendern:
    for (int i=0; i< _jt.getColumnCount();i++) {
      _jt.getColumnModel().getColumn(i).setHeaderValue(Integer.toString(i+1));
    }
  }

  /////////////////////////////////////////////////////////////////////////////
  /**
   * Liefert das DatenModell zurueck
   */
  public DatenModell getModel() {
    return(_dm);
  }

  /////////////////////////////////////////////////////////////////////////////
  /**
   * Liefert die Tabelle zurueck
   */
  public JTable getTable() {
    return(_jt);
  }
}
