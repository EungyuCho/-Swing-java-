package team_p1;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class ScheduleMain extends JFrame implements ActionListener{

	JButton schedule_button, table_button, add_button; 
	private String userid = "";
	private FrameOption callOption;
	public ScheduleMain(String userid) {
		this.userid = userid;
		callOption =new FrameOption();
	setSize(600,600);
	setTitle("스케줄 메인화면");
	setLocationRelativeTo(null);
	
//	schedule_button = new JButton("달력보기");
//	table_button = new JButton("일정목록");
//	add_button = new JButton("일정추가");
	
	schedule_button = new JButton(callOption.changeIcon("scheCal", 100, 100));
	table_button = new JButton(callOption.changeIcon("scheList", 100, 100));
	add_button = new JButton(callOption.changeIcon("schePlus", 100, 100));
	
	callOption.buttonSet(schedule_button);
	callOption.buttonSet(table_button);
	callOption.buttonSet(add_button);
	
	schedule_button.addActionListener(this);
	table_button.addActionListener(this);
	add_button.addActionListener(this);
	
	//schedule_button.setBounds(0,0,100,100);
	//table_button.setBounds(0,0,100,100);
	//add_button.setBounds(0,0,100,100);
	
	getContentPane().add(schedule_button, BorderLayout.WEST);
	getContentPane().add(table_button, BorderLayout.CENTER);
	getContentPane().add(add_button, BorderLayout.EAST);
	
	setVisible(true);
}



@Override
public void actionPerformed(ActionEvent e) {
	Object obj = e.getSource();
	System.out.println(userid);
	if(obj==schedule_button) {new CalendarFrame(userid); dispose();}
	else if (obj==table_button) {new scheduleAct(userid); dispose();}
	else if (obj==add_button) {new scheduleAdd(userid); dispose();}
}

}
