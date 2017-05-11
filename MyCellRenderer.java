import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
/**
 * <b>Programm:</b> JSheet - Spreadsheet<br>
 * <b>Copyright:</b> 2001 Andreas Gohr, Frank Schubert<br>
 * <b>License:</b> GPL2 or higher<br>
 * <b>Version:</b> 1.0<br>
 * <b>Date:</b> 22.06.2001<br>
 * <br>
 * <b>Info:</b> MyCellRenderer liest den Formelwert aus dem Datenmodell
 */
public class MyCellRenderer extends DefaultTableCellRenderer{
  /**
   * Überschriebene Methode - Liefert die Komponente zurück die zur Darstellung
   * der Zelle verwendet wird (JLable aus DefaultTableCellRenderer)
   * Als anzuzeigender Wert wird dabei der berechnete Wert verwendet
   */
  public Component getTableCellRendererComponent (JTable table,
  						  Object value,
						  boolean isSelected,
						  boolean hasFocus,
						  int row,
						  int col){
  setFont(new Font(null,Font.PLAIN,12));

  if (isSelected) {
    setBackground(table.getSelectionBackground());
  }
  else{
    setBackground(table.getBackground());
  }
  setText((String)((DatenModell)table.getModel()).getCalcValueAt(row,col));
  setToolTipText((String)((DatenModell)table.getModel()).getValueAt(row,col));
  return this;
  }

}
