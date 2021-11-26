package calendar;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class TableCellRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = -1L;
    private calendar tmp = null;
    private int nToday = 0; // 今日が何日か(ないなら０)

    public void setCalendar(calendar cal) {
	tmp = cal;
    }

    TableCellRenderer() {
	super();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
	    int row, int column) {
	super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

	/*
	 * どういう呼ばれ方をしてるのか確認するための出力文 目に見えるセルだけ毎度、描画しているらしい スクロールさせると、それがわかる
	 */
	// System.out.println("row:" + row + " /column:" + column + " /selected:" +
	// isSelected + " /focus:" + hasFocus
	// + " /value:" + value);

	// 選択されている行を赤色にする
	if (isSelected) {
//	    this.setBackground(Color.RED);
	} else if (column == 0 && value.equals("") == false) {
	    // 日曜日
	    this.setForeground(Color.BLACK);
	    this.setBackground(Color.RED);
	} else if (column == 6 && value.equals("") == false) {
	    // 土曜日
	    this.setForeground(Color.WHITE);
	    this.setBackground(Color.BLUE);
	} else if (value.equals("") == false && nToday == Integer.parseInt((String) value)) {
	    // 今日があるなら
	    this.setForeground(Color.BLACK);
	    this.setBackground(Color.ORANGE);
	} else {
	    this.setForeground(Color.BLACK);
	    this.setBackground(table.getBackground());
	}

	// フォーカスが当たっているセルを黄色にする
	if (hasFocus) {
	    this.setBackground(Color.yellow);
	    System.out.println("select" + "row:" + row + " /column:" + column);
	    tmp.setDay(row, column);
	}

	return this;
    }

    public void setToday(int n) {
	nToday = n;
    }

}
