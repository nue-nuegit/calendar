package calendar;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class calendar extends JFrame {

    private JPanel contentPane;
    private String[][] tabledata = { { "", "", "", "", "", "", "" }, { "", "", "", "", "", "", "" },
	    { "", "", "", "", "", "", "" }, { "", "", "", "", "", "", "" }, { "", "", "", "", "", "", "" },
	    { "", "", "", "", "", "", "" } };

    private String[] columnNames = { "日", "月", "火", "水", "木", "金", "土" };
    private int thismonth = 11; // 今年
    private int thisyear = 2021;// 今月
    private int month = 11;
    private int year = 2021;
    private int date = 1;
    private JTable table;
    private JTextArea textArea;
    private HashMap<String, String> map = new HashMap<String, String>();
    private JLabel ImageLabel;
    private ImageIcon Img = new ImageIcon("C:\\java\\kadai\\GUI\\2021個人製作\\animal_hitsuji_sleep.png");
    private JPanel panel_5;
    private JPanel panel_4;
    private JLabel DayLabel;

    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    calendar frame = new calendar();
		    frame.setVisible(true);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	});
    }

    /**
     * Create the frame.
     */
    public calendar() {
	setTitle("カレンダー");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 585, 522);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);
	contentPane.setLayout(null);

	JPanel panel = new JPanel();
	panel.setBounds(5, 5, 559, 222);
	contentPane.add(panel);
	panel.setLayout(null);

	JPanel panel_2 = new JPanel();
	panel_2.setBounds(67, 32, 95, 64);
	panel.add(panel_2);
	panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));

	panel_4 = new JPanel();
	panel_2.add(panel_4);

	JLabel YearLabel = new JLabel();
	YearLabel.setText("2021年");
	panel_4.add(YearLabel);

	JLabel MonthLabel = new JLabel();
	MonthLabel.setText("11月");
	panel_4.add(MonthLabel);

	panel_5 = new JPanel();
	panel_2.add(panel_5);

	DayLabel = new JLabel("11月26日");
	panel_5.add(DayLabel);
	table = new JTable(tabledata, columnNames);
	// DefaultTableCellRendererを継承した独自のレンダラをセット
	TableCellRenderer tmp = new TableCellRenderer();
	tmp.setCalendar(this);
	table.setDefaultRenderer(Object.class, tmp);
	JScrollPane sp = new JScrollPane(table);
	sp.setBounds(194, 5, 365, 122);
	sp.setPreferredSize(new Dimension(250, 100));
	panel.add(sp);

	Calendar cal = Calendar.getInstance();
	LocalDateTime nowDateTime = LocalDateTime.now();
	thisyear = year = nowDateTime.getYear(); // 今年
	thismonth = month = nowDateTime.getMonthValue(); // 今月
	date = nowDateTime.getDayOfMonth();
	tmp.setToday(date); // 今日を通知する

	JPanel panel_6 = new JPanel();
	panel_6.setBounds(58, 128, 104, 84);
	panel.add(panel_6);
	panel_6.setLayout(new BoxLayout(panel_6, BoxLayout.X_AXIS));

	ImageLabel = new JLabel(Img);
	panel_6.add(ImageLabel);
	cal.set(year, month - 1, 1);
	int youbi = cal.get(Calendar.DAY_OF_WEEK);// 今月1日は何曜日か
	int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH); // その月は何日まであるか
	setCalendar(youbi, days);

	JPanel panel_3 = new JPanel();
	panel_3.setBounds(5, 222, 559, 234);
	contentPane.add(panel_3);
	panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));

	textArea = new JTextArea();
	panel_3.add(textArea);

	JPanel panel_1 = new JPanel();
	panel_1.setBounds(119, 456, 378, 21);
	contentPane.add(panel_1);

	JButton PrevMonthButton = new JButton("PrevMonth");
	PrevMonthButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		month -= 1;
		if (month < 1) {
		    month = 12;
		    year -= 1;
		    YearLabel.setText(String.valueOf(year) + "年");
		}
		if (thisyear == year && thismonth == month)
		    tmp.setToday(date); // 今日の月なら通知する
		else
		    tmp.setToday(0); // 今日の月じゃないなら通知しない
		cal.set(year, month - 1, 1);// その月1日は何曜日か
		int youbi = cal.get(Calendar.DAY_OF_WEEK);
		int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);// その月は何日まであるか
		setCalendar(youbi, days);
		MonthLabel.setText(String.valueOf(month) + "月");
		textArea.setText("");// テキストエリアをいったん空白にする
	    }
	});
	panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));

	JButton btnNewButton = new JButton("読み込み");
	btnNewButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		scheduleRead();
	    }
	});
	panel_1.add(btnNewButton);
	panel_1.add(PrevMonthButton);

	JButton NextMonthButton = new JButton("NextMonth");
	NextMonthButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		month += 1;
		if (month > 12) {
		    month = 1;
		    year += 1;
		    YearLabel.setText(String.valueOf(year) + "年");
		}
		if (thisyear == year && thismonth == month)
		    tmp.setToday(date); // 今日の月なら通知する
		else
		    tmp.setToday(0); // 今日の月じゃないなら通知しない
		cal.set(year, month - 1, 1);
		int youbi = cal.get(Calendar.DAY_OF_WEEK);// その月1日は何曜日か
		int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);// その月は何日まであるか
		setCalendar(youbi, days);
		MonthLabel.setText(String.valueOf(month) + "月");
		textArea.setText("");// テキストエリアをいったん空白にする
	    }
	});
	panel_1.add(NextMonthButton);

	JButton btnNewButton_1 = new JButton("書き込み");
	btnNewButton_1.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		if (DayLabel.getText().equals("") == false && textArea.getText().equals("") == false)
		    map.put(String.valueOf(year) + "年" + DayLabel.getText(), textArea.getText()); // mapに格納する
		try {
		    FileWriter fw = new FileWriter("test.txt");
		    for (String key : map.keySet()) {
			fw.write(key + ":" + map.get(key) + "\n");
		    }
		    fw.close();
		} catch (IOException ex) {
		    ex.printStackTrace();
		}
	    }
	});
	panel_1.add(btnNewButton_1);

	scheduleRead(); // まずスケジュールを読み込む

	// 今日にスケジュールが入っていたら表示する
	if (map.get(String.valueOf(year) + "年" + DayLabel.getText()) != null) {
	    textArea.setText(map.get(String.valueOf(year) + "年" + DayLabel.getText()));
	}
    }

    private void setCalendar(int youbi, int days) {
	int j = 0;
	for (int i = 0; i < youbi - 1; i++, j++) {
	    // 一段目の１日目までに空白を入れる
	    table.setValueAt("", 0, i);
	}
	for (int i = 0; i < days; i++, j++) {
	    // 日にちを入れていく
	    table.setValueAt(String.valueOf(i + 1), j / 7, j % 7);
	}
	for (int i = days; i < 35; i++, j++) {
	    // 残り枠に空白を入れる
	    table.setValueAt("", j / 7, j % 7);
	}
    }

    public void setDay(int row, int column) {
	if (DayLabel.getText().equals("") == false && textArea.getText().equals("") == false) {
	    map.put(String.valueOf(year) + "年" + DayLabel.getText(), textArea.getText()); // mapに格納する
	    System.out.println(String.valueOf(year) + "年" + DayLabel.getText() + ":" + textArea.getText());
	}
	textArea.setText("");// テキストエリアをいったん空白にする
	// 日にちラベルに選択した日付を記入する
	DayLabel.setText(String.valueOf(month) + "月" + table.getValueAt(row, column) + "日");
	if (map.get(String.valueOf(year) + "年" + DayLabel.getText()) != null) {
	    textArea.setText(map.get(String.valueOf(year) + "年" + DayLabel.getText()));
	}
    }

    public void scheduleRead() {
	textArea.setText(""); // 表示されているテキストエリアの内容を全て消す
	BufferedReader br;
	try {
	    br = new BufferedReader(new InputStreamReader(new FileInputStream("test.txt"), "UTF-8"));
	    String line = null;
	    while ((line = br.readLine()) != null) {
		String moji[] = line.split(":");
//		textArea.append(line);
//		textArea.append("\n");
		map.put(moji[0], moji[1]); // mapに格納する
		System.out.println(moji[0] + ":" + moji[1]);
	    }
	    br.close();
	} catch (UnsupportedEncodingException | FileNotFoundException e1) {
	    // TODO 自動生成された catch ブロック
	    e1.printStackTrace();
	} catch (IOException e1) {
	    // TODO 自動生成された catch ブロック
	    e1.printStackTrace();
	}
    }
}
