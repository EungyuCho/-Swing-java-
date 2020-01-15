package team_p1; // 바꿔야됨 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class loginformTemp extends JFrame implements ActionListener, MouseListener {
	private class addBgPanel extends JPanel {
		public void paintComponent(Graphics g) {
			Dimension panSize = getSize();
			ImageIcon img = callOption.changeSize("loginBg", 500, 500);
			g.drawImage(img.getImage(), 0, 0, panSize.width, panSize.height, null);
		}
	}
					private String _driver = "org.gjt.mm.mysql.Driver", 
					_url = "jdbc:mysql://localhost:3306/mydb?useUnicode=true&characterEncoding=EUC_KR",
					_user = "root",
		    		_password = "1234";

	    Connection con = null;
	    Statement stmt;
	private addBgPanel contentPane;
	private JTextField idField;
	private JPasswordField pw;
	private JLabel IblResult;
	private JButton idButton, pwdButton, btnNewButton, joinBtn, loginLogo, closeButton, catButton;
	private Map<String, String> map;
	private String userid, userpwd;
	
	FrameOption callOption = new FrameOption();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					loginformTemp frame = new loginformTemp();
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
	public loginformTemp() {
//			Class.forName(_driver);
//			con = DriverManager.getConnection(_url,_user,_password);
//			stmt = con.createStatement();
		System.out.println("연결성공");
		// 연결까지 함. 이제 DB에 있는 값과 동일하면 됨.
		///////////////////////////////////////////////////
		/*
		 * map= new HashMap<String,String>(); map.put("kim", "1234"); map.put("park",
		 * "1111"); map.put("hong", "2222");
		 */
		///////////////////////////////////////////////////

		Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
		setSize(480, 720);
		setLocationRelativeTo(null);
		setUndecorated(true);

		contentPane = new addBgPanel();
		contentPane.setBackground(null);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		int closeButtonSize = 40;
		closeButton = new JButton(callOption.changeIcon("closeUnfocus", closeButtonSize, closeButtonSize));
		callOption.buttonSet(closeButton);
		closeButton.setBounds(getWidth() - closeButtonSize, 0, closeButtonSize, closeButtonSize);
		closeButton.setRequestFocusEnabled(false);
		closeButton.setCursor(handCursor);

		catButton = new JButton(callOption.changeIcon("cat", 150, 300));
		callOption.buttonSet(catButton);
		callOption.buttonSet(catButton);
		catButton.setRequestFocusEnabled(false);
		catButton.setBounds(50, 440, 130, 260);

		loginLogo = new JButton(callOption.changeIcon("homeLogo", 300, 150));
		loginLogo.setBounds(85, 80, 300, 150);
		loginLogo.setRequestFocusEnabled(false);
		callOption.buttonSet(loginLogo);

		idButton = new JButton(callOption.changeIcon("idIcon", 36, 36));
		callOption.buttonSet(idButton);
		idButton.setBounds(50, 310, 36, 36);
		idButton.setRequestFocusEnabled(false);

		pwdButton = new JButton(callOption.changeIcon("lock", 45, 36));
		callOption.buttonSet(pwdButton);
		pwdButton.setBounds(43, 405, 45, 36);
		pwdButton.setRequestFocusEnabled(false);

		idField = new JTextField();
		int fieldWidth = 250;
		Border bor = BorderFactory.createLineBorder(new Color(155, 155, 155));
		idField.setBounds(getWidth() / 2 - fieldWidth / 2, getHeight() / 2 - 50, fieldWidth, 30);
		idField.setBorder(bor);
		idField.setCaretColor(Color.WHITE);
		idField.setBackground(new Color(130, 130, 130));
		idField.setFont(new Font("Serif", Font.PLAIN, 14));
		
		idField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				pw.requestFocus();
			}
		});
		idField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					pw.requestFocus();
				}
			}
		});
		add(catButton);
		add(closeButton);
		add(loginLogo);
		add(idButton);
		add(pwdButton);
		add(idField);

		pw = new JPasswordField();
		pw.setBorder(bor);
		pw.setCaretColor(Color.WHITE);
		pw.setBackground(new Color(130, 130, 130));
		pw.setBounds(getWidth() / 2 - fieldWidth / 2, getHeight() / 2 + 50, fieldWidth, 30);
		pw.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					

					loginCheck();
				}
			}
		});
		add(pw);
		
		btnNewButton = new JButton(callOption.changeIcon("login", 130, 70));
		callOption.buttonSet(btnNewButton);
		btnNewButton.setBounds(getWidth() - 300, getHeight() - 230, 130, 70);
		btnNewButton.setCursor(handCursor);

		joinBtn = new JButton(callOption.changeIcon("signUp", 130, 70));
		callOption.buttonSet(joinBtn);
		joinBtn.setCursor(handCursor);
		joinBtn.setBounds(getWidth() - 200, getHeight() - 230, 130, 70);

		joinBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SignForm frame = new SignForm();
				frame.setVisible(true);
				frame.requestFocus();
			}
		});
		closeButton.addActionListener(this);
		btnNewButton.addMouseListener(this);
		btnNewButton.addActionListener(this);

		contentPane.add(btnNewButton);
		contentPane.add(joinBtn);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				idField.requestFocus();
			}
		});
	}
	void loginCheck() {
		try {
			String typingID = idField.getText();
			String typingPWD = String.copyValueOf(pw.getPassword());
			
			Class.forName(_driver);
			con = DriverManager.getConnection(_url,_user,_password);
			stmt = con.createStatement();
			
			String sql = "select * from login01";
			ResultSet rs=stmt.executeQuery(sql); // 값 로드
			int accountCounter = 0;
			while(rs.next()){
				accountCounter++;
			}
			int counterCheck = 0;
			rs.beforeFirst();
			while(rs.next()) {
				counterCheck++;
				userid = rs.getString("userid");
				userpwd = rs.getString("userpwd");
				System.out.println(userid);
				System.out.println(userpwd);
				if (userid.equals(typingID) && userpwd.equals(typingPWD)) {
				// 로그인성공이벤트
				 new ButtonFrame(userid);
				 dispose();
				return;
				}
				if(accountCounter==counterCheck) {
					new dialogFrame(1);
					idField.setText("");
					pw.setText("");
					idField.requestFocus();
					
					break;
				}
			
//				new dialogFrame(1); // 아이디 비밀번호 확인해주세요
				
//    }
		}} catch (Exception e123) {
			e123.printStackTrace();
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();

		if (obj == closeButton) {
			System.exit(0);
		} else if (obj == btnNewButton) {
			loginCheck();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		Object obj = e.getSource();

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
