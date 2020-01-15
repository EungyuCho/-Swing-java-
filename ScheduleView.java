package team_p1;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class ScheduleView extends JFrame {
	
	JLabel label;
	Font titleFont;
	public ScheduleView() {
		setSize(350,200);
		setTitle("달력화면");
		
		label = new JLabel("작업중....");
		titleFont = new Font("바탕", Font.BOLD, 50);
		label.setFont(titleFont);
		//label.setBounds(50, 30, 0, 0);
		
		add(label);
		setVisible(true);
		}	
	public static void main(String[] args) {
		new ScheduleView();
	}
}
