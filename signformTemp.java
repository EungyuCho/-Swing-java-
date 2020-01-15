package team_p1; // �ٲ�ߵ� @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class signformTemp extends JFrame {


					private String _driver = "org.gjt.mm.mysql.Driver", 
					_url = "jdbc:mysql://localhost:3306/mydb?useUnicode=true&characterEncoding=EUC_KR",
					_user = "root",
					_password = "1234";
	
					Connection con = null;
					Statement stmt;
					
	private JTextField idField;
	private JPasswordField pw;
	private FrameOption callOption;
	private JButton mainPanel, closeButton, btnNewButton, idLabel, pwdLabel, pwdLabel2;
	private dialogFrame dia; 
	private String userid, userpwd;
	private JPasswordField pwchk;
	private boolean IDswitch, PWDswitch, dialogCall, enterCheck, dialogFive;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					signformTemp frame = new signformTemp();
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
	public signformTemp() {
		try {
			Class.forName(_driver);
			con = DriverManager.getConnection(_url,_user,_password);
			stmt = con.createStatement();
			System.out.println("���Ἲ��");
			callOption = new FrameOption();
			
			Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
			// ������� ��. ���� DB�� �ִ� ���� �����ϸ� ��.
			
			setUndecorated(true);
			setTitle("ȸ������");	//380 300
			setBounds(850, 400, 400, 600);	
			setLocationRelativeTo(null);
	        setBackground(new Color(0, 0, 0, 1));
	        addFocusListener(new FocusAdapter() {
	        	@Override
	        	public void focusGained(FocusEvent e) {
	        		idField.requestFocus();
	        	}
			});
	        
	        
	        mainPanel = new JButton(callOption.changeIcon("dialogFrame", 400, 600));
	        mainPanel.setLayout(null);
	        mainPanel.setBounds(0, 0, 400, 600);
	        callOption.buttonSet(mainPanel);
	        add(mainPanel);
	        
	        idLabel = new JButton(callOption.changeIcon("signupId", 45, 45));
	        idLabel.setBounds(54, 120, 45, 45);
	        callOption.buttonSet(idLabel);
	        mainPanel.add(idLabel);
	        
			idField = new JTextField();
			idField.setBounds(100, 120, 170, 45);
			idField.setBorder(BorderFactory.createEmptyBorder());
			idField.setFont(new Font("serif", Font.PLAIN, 18));
			mainPanel.add(idField);
			
			idField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						if(!enterCheck) {
							idCheck();
							enterCheck=true;
						}
					}
				}
				@Override
				public void keyReleased(KeyEvent e) {
					if(e.getKeyCode() == KeyEvent.VK_ENTER) {
						if(enterCheck)
							enterCheck=false;
					}
				}
			});
			
			pw = new JPasswordField();
			pw.setBounds(100, 200, 170, 45);
			pw.setBorder(BorderFactory.createEmptyBorder());
			pw.setFont(new Font("serif", Font.PLAIN, 18));
			pw.setEditable(false);
			pw.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					pwchk.requestFocus();
				}
			});
			mainPanel.add(pw);

	        pwdLabel = new JButton(callOption.changeIcon("signupPwd", 45, 45));
	        pwdLabel.setBounds(54, 200, 45, 45);
	        callOption.buttonSet(pwdLabel);
	
			pwchk = new JPasswordField();
			pwchk.setBounds(100, 280, 170, 43);
			pwchk.setBorder(BorderFactory.createEmptyBorder());
			pwchk.setFont(new Font("serif", Font.PLAIN, 18));
			pwchk.setEditable(false);
		
	        pwdLabel2 = new JButton(callOption.changeIcon("signupPwd", 45, 45));
	        pwdLabel2.setBounds(54, 280, 45, 45);
	        callOption.buttonSet(pwdLabel2);
	        
			mainPanel.add(pwchk);

			JButton idCheck = new JButton(callOption.changeIcon("checkIdOverlap", 130, 80));
			idCheck.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					idCheck();
				}
			});
			idCheck.setBounds(270, 100, 130, 80); // �ߺ�üũ ��ư
			callOption.buttonSet(idCheck);
			mainPanel.add(idCheck);

			JLabel pwdCheck = new JLabel();

			pwdCheck.setBounds(100, 325, 200, 20); // �ߺ�üũ ��ư
			mainPanel.add(pwdCheck);

			pwchk.addKeyListener(new KeyAdapter() { // ��й�ȣ �ڵ�üũ ��� (209�ٱ���)
				@Override
				public void keyPressed(KeyEvent e) {

					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						signUp();
					} else {
						if(pw.getText().length()>0 && pwchk.getText().length()>0)
						{
							new Thread() {
								public void run() {
									try {
										Thread.sleep(20);
										boolean tempCheck = pwdCheckResult();
										if (tempCheck == true) {
											pwdCheck.setForeground(Color.GREEN);
											pwdCheck.setText("��й�ȣ�� �����ϴ�");
										} else if (tempCheck == false) {
											pwdCheck.setForeground(Color.RED);
											pwdCheck.setText("��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
										}
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
								}
							}.start();
							
						}
					}
				}
				
			});
			
			pw.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {

					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						pwchk.requestFocus();
					} 
				}
				
			});

			btnNewButton = new JButton(callOption.changeIcon("signUpEnable", 130, 80)); // �Է��Ѵ�� DB�� �־�ߵ�
			callOption.buttonSet(btnNewButton);
			btnNewButton.setBounds(100, 350, 130, 80);
			// �ߺ�üũ ���ϰ�, ��й�ȣ Ȯ�� �� �� ���ϸ� ��ư ������ �۵� �ȵǰ� �ؾߵ�.

			mainPanel.add(btnNewButton);
			/*
			 * IblResult = new JLabel("New label"); IblResult.setBounds(12, 176, 199, 92);
			 * contentPane.add(IblResult);
			 */

			int closeButtonSize = 40;
			closeButton = new JButton(callOption.changeIcon("closeUnfocus", closeButtonSize, closeButtonSize));
			closeButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					if(dialogCall==false) {
					dia = new dialogFrame(7);
					dia.ok2.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							dia.dispose();
							dispose();
							dialogCall=false;
						}
					});
					dia.repaint();
					dialogCall = true;
					}else {
						dia.requestFocus();
					}
					dia.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosed(WindowEvent e) {
							dialogCall = false;
						}
					});
				}				
			});

			callOption.buttonSet(closeButton);
			closeButton.setCursor(handCursor);
			closeButton.setBounds(350, 3, closeButtonSize, closeButtonSize);
			mainPanel.add(closeButton);
			repaint();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void idCheck() {
		try {
		String load = "select * from login01";
			ResultSet rs=stmt.executeQuery(load); // �� �ε�
			String typingID = idField.getText();
			boolean idOverlapCheck = false;
			int counter = 0;
			if(typingID.equals("")||typingID.equals(null)){
				dia = new dialogFrame(3);
				dia.repaint();
				dia.addWindowListener(new WindowAdapter() {
					public void windowClosed(java.awt.event.WindowEvent e) {
						idField.requestFocus();
					};
				});
				IDswitch = false;
			}
			else {
			while(rs.next()) {
				counter++;
			}
			String stringArray[] = new String [counter];
			int m=0;
			rs.beforeFirst();
			while(rs.next()) {
				stringArray[m] = rs.getString("userid");
					m++;
					System.out.println(rs.getString("userid"));
			}
			
			for(int i=0; i<counter; i++) {
				if(typingID.equals(stringArray[i])) {
					dia = new dialogFrame(2);
					dia.repaint();
					dia.addWindowListener(new WindowAdapter() {
						public void windowClosed(java.awt.event.WindowEvent e) {
							idField.setText("");
							idField.requestFocus();
						};
					});
					IDswitch = false;
					break;
				}
				if(i==counter-1) {
					dia = new dialogFrame(4);
					dia.repaint();
					//�ߺ�üũ ����
					dia.addWindowListener(new WindowAdapter() {
						public void windowClosed(java.awt.event.WindowEvent e) {
							idField.setEditable(false);
							idField.setBackground(new Color(200,200,200));
							pw.setEditable(true);
							pwchk.setEditable(true);
							
							btnNewButton.setIcon(callOption.changeIcon("signUpAble", 130, 80));
							btnNewButton.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									signUp();
								}
							});
							
					        mainPanel.add(pwdLabel);
					        mainPanel.add(pwdLabel2);
					        
							repaint();
							new Thread() {
								public void run() {
									try {
										Thread.sleep(20);
										pw.requestFocus();
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								
							}.start();
							
						};
					});			 
					IDswitch = true; // ����ġ ����.
				}
			}
			}
	
		} catch (Exception e1231) {
			e1231.printStackTrace();
		}
	}

	private boolean pwdCheckResult() {
		String typingPWD = pw.getText();
		String typingPWDCHK = pwchk.getText();
		if (!typingPWD.equals(typingPWDCHK)) {
			PWDswitch = false;
		} else {
			PWDswitch = true;
		}
		return PWDswitch;
	}

	private void signUp() {
		try {
			String load = "select * from login01";
			ResultSet rs=stmt.executeQuery(load); // �� �ε�
			String typingID = idField.getText();
			String typingPWD = String.copyValueOf(pw.getPassword());
			String typingPassCheck = String.copyValueOf(pwchk.getPassword());
			
			while(rs.next()) {
				userid = rs.getString("userid");
				userpwd = rs.getString("userpwd");
			
			// ���̵� �ߺ�üũ ������ ���� 
			if (userid.equals(typingID) && typingPassCheck.equals(typingPWD) && IDswitch) {
				break;
			}
			//��й�ȣ �� ��ġ
			else if (!PWDswitch) {
				if(!dialogFive) {
				System.out.println("���̾�α� ȣ�� ����");
				dia = new dialogFrame(5);
				dia.repaint();
				}
				dia.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						pw.setText("");
						pwchk.setText("");
						pw.requestFocus();
						dialogFive=true;
					}
				});
				
				break;
				//ȸ������
			} else if (IDswitch && PWDswitch) { // ���⼭ DB�� �־���
				// values���� ������ ���� �ƴ϶� �Է¹������ (typingId, typingPWD) �־�ߵ�.
				try {
					PreparedStatement ps=con.prepareStatement("insert into login01(userid,userpwd) values (?,?)");

					ps.setString(1, typingID);
					ps.setString(2, typingPWD);
	                ps.executeUpdate();
	                
				} catch (Exception e) {
					System.out.println("insert conect error");
				e.printStackTrace();
				}
				

				dia = new dialogFrame(6);
				dia.repaint();
				dispose();

                break;
				//db.setId(idField.getText()); //�Էµ� ���̵� ������ dto�� ����
			//	db.setPassword(String.copyValueOf(pw.getPassword()));  //�Էµ� ��й�ȣ�� ������ dto�� ����
					// ���̰͵� �Ȱ��� �ߴµ� ������ ���ɴϴ�.
				//System.out.println(String.copyValueOf(pw.getPassword()));
				//try {
				//	 insertDB.create(db);  //dto�� DAO�� �Ѱ��ش�.
				//   } catch (Exception e1) {
				 //   e1.printStackTrace();
				//   }
			} 
		}
	}catch (Exception e123) {
			e123.printStackTrace();
		}
	}
}
