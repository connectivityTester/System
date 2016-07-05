package gui;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class ColumnRender extends DefaultTableCellRenderer implements TableCellRenderer 
{
  private Font font;
  private Color color;
  private int alignment;

  public ColumnRender (Font font, Color color, int alignment) 
  {
    super();
    this.font = font;
    this.color = color;
    this.alignment = alignment;
  }
  
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
  {
    final Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    comp.setFont(font);
    setHorizontalAlignment(alignment);
    comp.setForeground(color);
    return comp;
  }
}