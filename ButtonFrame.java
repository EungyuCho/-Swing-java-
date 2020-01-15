package team_p1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;


public class ButtonFrame {
	private JLayeredPane layer;
	private JFrame frame;
	private JButton button_1, button_2, button_3, mainButton;
	private boolean mouseEnteredCheck;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private int screenHeight = (int)(screenSize.getHeight());
	private int screenWidth = (int)(screenSize.getWidth());
	private int pX,pY;
	private boolean mo_state = false;
	FrameOption callOption;
	private boolean state_1;
	private boolean openButton;
	private String userid = "";
	public ButtonFrame(String userid) {
		this.userid = userid;
		initialize();
		//System.out.println(screenSize.getHeight());
	}
	
	void initialize() {
		callOption = new FrameOption();
		frame = new JFrame();
		frame.setLayout(null);
		frame.setBounds(screenWidth-200, screenHeight-200, 200, 200);
		frame.setUndecorated(true);
		frame.setBackground(new Color(0, 0, 0, 1));//Åõ¸í
		frame.setVisible(true);
		frame.setAlwaysOnTop(true);
		frame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				if(openButton){
				System.out.println("³ª¿È");
				openButton=false;
				button_2.setVisible(false);
				button_3.setVisible(false);
				}
			}
		});
		
		button_1 = new JButton();
		button_1.setBounds(frame.getWidth()-28, 0, 28, 28);
		callOption.buttonChange(button_1, "closeUnfocus", 28);
		button_1.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				if (state_1 == false)
					callOption.buttonChange(button_1, "closeFocus", 28);
				else if (state_1 == true)
					callOption.buttonChange(button_1, "closeClick", 28);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				callOption.buttonChange(button_1, "closeFocus", 28);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				state_1 = true;
				callOption.buttonChange(button_1, "closeClick", 28);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				state_1 = false;
				callOption.buttonChange(button_1, "closeUnfocus", 28);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		
		
		});
		
		button_2 = new JButton();
		callOption.buttonChange(button_2, "memoRootIcon", 40);
		button_2.setBounds(frame.getWidth() - 90, frame.getHeight() - 145, 40, 40);
		button_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new memoMain(userid, 1);
			}
		});
		button_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				button_2.setVisible(true);
				button_3.setVisible(true);
				
			}
			@Override
			public void mouseExited(MouseEvent e) {
				button_2.setVisible(true);
				button_3.setVisible(true);

			}
		});
		button_3 = new JButton();
		callOption.buttonChange(button_3, "calRoot", 40);
		button_3.setBounds(frame.getWidth() - 150, frame.getHeight() - 80, 40, 40);
		button_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				button_2.setVisible(true);
				button_3.setVisible(true);
				
			}
			@Override
			public void mouseExited(MouseEvent e) {
				button_2.setVisible(true);
				button_3.setVisible(true);

			}
		});
		button_3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ScheduleMain(userid);
			}
		});
		button_2.setVisible(false);
		button_3.setVisible(false);
		
		callOption.buttonSet(button_2);
		callOption.buttonSet(button_3);
		
		
		mainButton = new JButton();
		callOption.buttonChange(mainButton, "mainRoot", 110);
		mainButton.setBounds(frame.getWidth() - 110, frame.getHeight() - 110, 110, 110);
		 mainButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
					if(!openButton) {
						openButton=true;
						System.out.println("µé¾î¿È");
						button_2.setVisible(true);
						button_3.setVisible(true);
						}
			}
		});
		callOption.buttonSet(mainButton);
		callOption.buttonSet(button_1);
		
		frame.add(mainButton);
		frame.add(button_1);
		frame.add(button_2);
		frame.add(button_3);
		
		frame.repaint();
		frame.validate();
	}
	

}
