import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
/**
 * <b>Programm:</b> JSheet - Spreadsheet<br>
 * <b>Copyright:</b> 2001 Andreas Gohr, Frank Schubert<br>
 * <b>License:</b> GPL2 or higher<br>
 * <b>Version:</b> 1.0<br>
 * <b>Date:</b> 22.06.2001<br>
 * <br>
 * <b>Info:</b> Diese Klasse stellt die Header für die Rows her
 */
class RowHeader extends JList{
  /**
   * Konstruktor Erzeugt den Row Header für die Tabelle
   */
  public RowHeader(JTable table){
    super();
    
    String[] liste = new String[table.getRowCount()];
    for (int i=0; i<table.getRowCount(); i++){
      liste[i] = Integer.toString(i+1);
    }
    
    this.setListData(liste); 
    this.setFixedCellWidth(25);
    this.setFixedCellHeight(table.getRowHeight());
    this.setCellRenderer(new RowHeaderRenderer(table));
  }
}

///////////////////////////////////////////////////////////////////////////////
/**
 * Stellt einen speziellen CellRenderer zur Verfügung der in RowHeader zur
 * Zellendarstellung benutzt wird
 */
class RowHeaderRenderer extends JLabel implements ListCellRenderer {

  /////////////////////////////////////////////////////////////////////////////
  /**
   * Konstruktor - Setzt die Eigenschaften des Headers auf die der
   * Tabelle
   */
  public RowHeaderRenderer(JTable table) {
    JTableHeader header = table.getTableHeader();
    setOpaque(true);
    setBorder(UIManager.getBorder("TableHeader.cellBorder"));
    setHorizontalAlignment(CENTER);
    setForeground(header.getForeground());
    setBackground(header.getBackground());
    setFont(header.getFont());
  }

  /////////////////////////////////////////////////////////////////////////////
  /**
   * Liefert die Komponente zurück die für das Zeichnen einer Header
   * Zelle verantwortlich ist
   */
  public Component getListCellRendererComponent( JList list, 
                                                 Object value, 
                                                 int index, 
                                                 boolean isSelected, 
                                                 boolean cellHasFocus) {
    if(value == null) setText("");
    else setText(value.toString());
    return this;
  }
}
