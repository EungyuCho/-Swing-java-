package team_p1;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.net.URL;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

import team_p1.memoMain.MyScrollbarUI;

public class memolist extends JFrame implements ActionListener, MouseListener{
	private final static int TOPICON = 28;
	private final static int PADDING = 1;
	private JFrame frame1;
	private JPanel mainP, topP, memoListMainPanel;
	private JTextField tf_1;
	private JButton searchIcon, plusIcon, closeIcon;
	private FrameOption callOption;
	private boolean state_1, state_2;
	private JScrollPane scroll;
	private GridBagLayout gridBag;
	private  GridBagLayout gridControl;
	private int pX, pY;
	private int count=0, countY = 0;
	private boolean searchModeBoolean;
	private int panelInt=0;
	private LinkedList <String> memoContentString; 
	private Color whiteBase = new Color(255, 255, 255);
	private Color yellowColor = new Color(255, 242, 171); 
	private Color greenColor = new Color(203, 241, 196);
	private Color pinkColor = new Color(255, 204, 229);
	private Color blueColor = new Color(205, 233, 255);
	private Color grayColor = new Color(249, 249, 249);
	private Color yellowColorSeleted = new Color(255, 235, 129);
	private Color greenColorSeleted = new Color(175, 236, 164);
	private Color pinkColorSeleted = new Color(255, 187, 221);
	private Color blueColorSeleted = new Color(183, 223, 255);
	private Color grayColorSeleted = new Color(229, 229, 229);
	private int screenX, screenY;
	private String userid = "";
	public memolist(String userid) {//생성자
		this.userid = userid;
		
		screenX = (int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth();  
		screenY =(int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		Border blackline = BorderFactory.createLineBorder(Color.black); 
		 gridBag = new GridBagLayout();
		 gridControl = new GridBagLayout();
		callOption = new FrameOption();
		mainP = new JPanel();
		topP = new JPanel();
		tf_1 = new JTextField();
		memoListMainPanel = new JPanel();
		
		frame1 = new JFrame();//프레임 껍데기 만들기
		frame1.setSize(250, 500);
		frame1.setLocationRelativeTo(null);
//		setLayout(new BorderLayout());
		frame1.setLayout(null);
		frame1.getContentPane().setLayout(null);
		frame1.setResizable(true);
		frame1.setUndecorated(true);

		URL imgURL = this.getClass().getResource("IUimage/memoIcon.png"); // 아이콘 이미지 세팅
		frame1.setIconImage(new ImageIcon(imgURL).getImage());
		int tf_1size = 200;
		
		mainP.setLayout(null);
		mainP.setBounds(0, 0, frame1.getWidth(), frame1.getHeight());
		mainP.setBorder(blackline);
		mainP.setBackground(new Color(255, 255, 255));
		frame1.add(mainP);

		
		topP.setLayout(null);
		topP.setBackground(whiteBase);
		topP.setBounds(1, 1, frame1.getWidth() - 2, TOPICON);
		topP.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				pX = e.getX();
				pY = e.getY();
			}
		});
		topP.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				frame1.setLocation(x - pX, y - pY);
			}
		});
		
		plusIcon = new JButton(callOption.changeIcon("plusUnfocus", TOPICON, TOPICON));
		plusIcon.setBounds(PADDING, PADDING, TOPICON, TOPICON);
		plusIcon.addActionListener(this);
		plusIcon.addMouseListener(this);
		callOption.buttonSet(plusIcon);
		
		
		closeIcon = new JButton(callOption.changeIcon("closeUnfocus", TOPICON, TOPICON));
		closeIcon.setBounds(topP.getWidth()-TOPICON, PADDING, TOPICON, TOPICON);
		closeIcon.addActionListener(this);
		closeIcon.addMouseListener(this);
		callOption.buttonSet(closeIcon);
		
		
		topP.add(plusIcon);
		topP.add(closeIcon);
		//여기까지 Top Panel
		
		tf_1.setFont(new Font("Vandana", Font.PLAIN, 15));
		tf_1.setHorizontalAlignment(JTextField.CENTER);
		tf_1.setBounds(frame1.getWidth()/2-100, 50, tf_1size, 30);
		tf_1.setBackground(new Color(239,239,239));
		tf_1.setBorder(BorderFactory.createEmptyBorder());
		tf_1.setLayout(null);
		//텍스트박스 검색기능
		tf_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==10) {
				 mainP.remove(scroll);
				memoListMainPanel.removeAll();
				memoListMainPanel.updateUI();
				searchModeBoolean = true;
				repaint();
				update(tf_1.getText());
				repaint();
				}
			}
		});
		
		searchIcon = new JButton(callOption.changeIcon("searchIcon", 25, 25));
		callOption.buttonSet(searchIcon);
		searchIcon.setBounds(tf_1.getWidth()-30,0,30,30);

		tf_1.add(searchIcon);
		//텍스트필드 넣고
		//여기부터 memoListMainPanel #메인판넬
		 
		mainP.add(topP);
		mainP.add(tf_1);
		 
		initialize();
	}
	public void initialize() {//기본값, 초기화, 셋팅
		
		 scroll = new JScrollPane(memoListMainPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,	JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 
		 JScrollBar sb = scroll.getVerticalScrollBar();
		MyScrollbarUI setUiScroll = new MyScrollbarUI(whiteBase);
		sb.setUI(setUiScroll);
		scroll.setBorder(BorderFactory.createLineBorder(whiteBase));
		 
		 scroll.setBounds(1, 100, frame1.getWidth()-2, frame1.getHeight()-100); 
		 scroll.setBackground(new Color(255, 242, 171));
		 mainP.add(scroll);
		 
		 memoListMainPanel.setBackground(whiteBase);
		 memoListMainPanel.setLayout(gridBag);
		 
		Vector<memolistBean> memoBean  = new Vector<memolistBean>();
		memoMgr mgr = new memoMgr();
		// 여기서 유저 아이디 입력하였음 #유저아이디 # 유저 ID
		memoBean = mgr.loadMemolist(userid);
		
		//반복 시작
		for(int i=0; i<memoBean.size();i++) {
			memolistBean bean = memoBean.get(i);
			Vector <Object> tt = new Vector<Object>();
			tt.add(bean.getMemoidx());
			tt.add(bean.getContents());
			tt.add(bean.getColor());
			tt.add(bean.getUserid());
			
			JPanel t = new JPanel();
			t.setPreferredSize(new Dimension(250, 80));
			t.setLayout(null);
			//컬러 판단부
			JButton tPanel = new JButton();
			int memoPanWidth = 240;
			int memoPanHeight = 80;
			tPanel.setLayout(null);
			tPanel.setName(Integer.toString(bean.getMemoidx()));
			switch(bean.getColor()) {
			case "yellow":
				tPanel.setIcon(callOption.changeIcon("yellowMemoPanel", memoPanWidth, memoPanHeight));
				break;
			case "green":
				tPanel.setIcon(callOption.changeIcon("greenMemoPanel", memoPanWidth, memoPanHeight));
				break;
			case "pink":
				tPanel.setIcon(callOption.changeIcon("pinkMemoPanel", memoPanWidth, memoPanHeight));
				break;
			case "blue":
				tPanel.setIcon(callOption.changeIcon("blueMemoPanel", memoPanWidth, memoPanHeight));
				break;
			case "gray":
				tPanel.setIcon(callOption.changeIcon("grayMemoPanel", memoPanWidth, memoPanHeight));
				break;
			}
			
			//판단용 텍스트박스
			JTextField judgeField = new JTextField();
			judgeField.setText("0");

//			panelInt++;
//			judgeField.setName(Integer.toString(panelInt));
//			System.out.println(judgeField.getName());
			
			tPanel.setBounds(0, 10, memoPanWidth, memoPanHeight);
			callOption.buttonSet(tPanel);
			tPanel.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int tempColorJudge = colorJudgeSetting(bean.getColor());
					memoMain memo3 = null;
					if(judgeField.getText().equals("0")) {
						memo3 = new memoMain(userid, tempColorJudge, bean.getContents(), screenX-screenX/4-countY*300, count*200, true, bean.getMemoidx()); // 나중에 추가해야함(유저아이디)
				
				
					judgeField.setText("1");
					
					count++;
					if((1+count)*200>screenY) {
						count = 0;
						countY++;
					}
					
					//중복 생성 방지 구문
					memo3.button_3.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							new Thread() {
								public void run() {
					                    try {
					                        Thread.sleep(40);
					                       System.out.println("종료합니다");
					                      judgeField.setText("0");
					   					 mainP.remove(scroll);
					   					memoListMainPanel.removeAll();
					   					memoListMainPanel.updateUI();
					   					repaint();
					   					System.out.println(tf_1.getText());
				                        if(tf_1.getText().equals("") || tf_1.getText().equals(null))
					   					initialize();
				                        else
				                        update(tf_1.getText());
					   					repaint();
					                    } catch (Exception e) {
					                    }
								}
							}.start();
					
						}
					});
					}
				}
					
						
				}
			);
			// 시간 확인
			
			// 텍스트 필드 부분
			JTextField memoPanField = new JTextField();
			memoPanField.setBounds(15, 20, memoPanWidth-20, 40);
			memoPanField.setFont(new Font("serif",Font.PLAIN, 13));
			String contentResize= "";
			int substringSize = 15;
			if(bean.getContents().length()<substringSize)
				contentResize = bean.getContents();
			else
				contentResize = bean.getContents().substring(0, substringSize) +"...";

			
			memoPanField.setOpaque(false);
			memoPanField.setEditable(false);
			memoPanField.setFocusable(false);
			memoPanField.setBorder(BorderFactory.createEmptyBorder());
			memoPanField.setText(contentResize);
			memoPanField.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {		
					int tempColorJudge = colorJudgeSetting(bean.getColor());
					memoMain memo3 = null;
					if(judgeField.getText().equals("0")) {
						memo3 = new memoMain(userid, tempColorJudge, bean.getContents(), screenX-screenX/4-countY*300, count*200, true, bean.getMemoidx()); // 나중에 추가해야함(유저아이디)
				
					judgeField.setText("1");
					count++;
					if((count+1)*200>screenY) {
						count = 0;
						countY++;
					}
					
					//중복 생성 방지 구문
					memo3.button_3.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							new Thread() {
								public void run() {
					                    try {
					                        Thread.sleep(40);
					                       System.out.println("종료합니다");
					                      judgeField.setText("0");
					   					 mainP.remove(scroll);
					   					memoListMainPanel.removeAll();
					   					memoListMainPanel.updateUI();
					   					repaint();
					   					System.out.println(tf_1.getText());
				                        if(tf_1.getText().equals("") || tf_1.getText().equals(null))
					   					initialize();
				                        else
				                        update(tf_1.getText());
					   					repaint();
					                    } catch (Exception e) {
					                    }
								}
							}.start();
					
						}
					});
					
					}
				}
			});
			//제거용 버튼 뒤에업데이트에도 추가해야댐
			JButton removeButton = new JButton(callOption.changeIcon("memoRemoveIcon", 20, 20));
			removeButton.setBounds(205, 15, 20, 20);
			callOption.buttonSet(removeButton);
			removeButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//이벤트추가해야대여	
					Vector<memolistBean> memoBean  = new Vector<memolistBean>();
					memoMgr mgr = new memoMgr();
					// 여기서 유저 아이디 입력하였음 #유저아이디 # 유저 ID
					mgr.deleteMemo(Integer.parseInt(tPanel.getName()));
					mgr.idxProcesure();
					new Thread() {
  						public void run() {
  							try {
  			  					 mainP.remove(scroll);
  			  					memoListMainPanel.removeAll();
  			  					memoListMainPanel.updateUI();
  								Thread.sleep(30);
	  		  					repaint();
	  							initialize();
	  							repaint();
  							}
  							catch (InterruptedException e) {
								e.printStackTrace();
							}
  						}
  					}.start();
  					
				}
			});
			
			tPanel.add(removeButton);
			tPanel.add(memoPanField);
			
			//버튼 추가부
			t.add(tPanel);
			t.setBackground(null);

			
			create_form(t, 0, i*30, 30,10);
			
		}
		

		frame1.setVisible(true);
	}
	public void update(String cont) {//기본값, 초기화, 셋팅
		
		 scroll = new JScrollPane(memoListMainPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,	JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 
		 JScrollBar sb = scroll.getVerticalScrollBar();
		MyScrollbarUI setUiScroll = new MyScrollbarUI(whiteBase);
		sb.setUI(setUiScroll);
		scroll.setBorder(BorderFactory.createLineBorder(whiteBase));
		 
		 scroll.setBounds(1, 100, frame1.getWidth()-2, frame1.getHeight()-100); 
		 scroll.setBackground(new Color(255, 242, 171));
		 mainP.add(scroll);
		 
		 memoListMainPanel.setBackground(whiteBase);
		 memoListMainPanel.setLayout(gridBag);
		 
		Vector<memolistBean> memoBean  = new Vector<memolistBean>();
		memoMgr mgr = new memoMgr();
		// 여기서 유저 아이디 입력하였음 #유저아이디 # 유저 ID
		memoBean = mgr.loadSearch(cont);
		
		
		
		//반복 시작
		for(int i=0; i<memoBean.size();i++) {
			memolistBean bean = memoBean.get(i);
			Vector <Object> tt = new Vector<Object>();
			tt.add(bean.getMemoidx());
			tt.add(bean.getContents());
			tt.add(bean.getColor());
			tt.add(bean.getUserid());
			
			JPanel t = new JPanel();
			t.setPreferredSize(new Dimension(250, 80));
			t.setLayout(null);
			//컬러 판단부
			JButton tPanel = new JButton();
			int memoPanWidth = 240;
			int memoPanHeight = 80;
			tPanel.setLayout(null);
			System.out.println(bean.getColor());
			switch(bean.getColor()) {
			case "yellow":
				tPanel.setIcon(callOption.changeIcon("yellowMemoPanel", memoPanWidth, memoPanHeight));
				break;
			case "green":
				tPanel.setIcon(callOption.changeIcon("greenMemoPanel", memoPanWidth, memoPanHeight));
				break;
			case "pink":
				tPanel.setIcon(callOption.changeIcon("pinkMemoPanel", memoPanWidth, memoPanHeight));
				break;
			case "blue":
				tPanel.setIcon(callOption.changeIcon("blueMemoPanel", memoPanWidth, memoPanHeight));
				break;
			case "gray":
				tPanel.setIcon(callOption.changeIcon("grayMemoPanel", memoPanWidth, memoPanHeight));
				break;
			}
			
			//판단용 텍스트박스
			JTextField judgeField = new JTextField();
			judgeField.setText("0");
			
			
			tPanel.setBounds(0, 10, memoPanWidth, memoPanHeight);
			callOption.buttonSet(tPanel);
			tPanel.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int tempColorJudge = colorJudgeSetting(bean.getColor());
					memoMain memo3 = null;
					if(judgeField.getText().equals("0")) {
						memo3 = new memoMain(userid, tempColorJudge, bean.getContents(), screenX-screenX/4-countY*300, count*200, true, bean.getMemoidx()); // 나중에 추가해야함(유저아이디)
				
				
					judgeField.setText("1");

					count++;
					if((1+count)*200>screenY) {
						count = 0;
						countY++;
					}
					
					//중복 생성 방지 구문
					memo3.button_3.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							judgeField.setText("0");
							 mainP.remove(scroll);
							memoListMainPanel.removeAll();
							memoListMainPanel.updateUI();
							System.out.println("버튼 3이 눌렸네요");
							repaint();
							initialize();
							repaint();
						}
					});
					}
				}
					
						
				}
			);
			// 시간 확인
			
			// 텍스트 필드 부분
			JTextField memoPanField = new JTextField();
			memoPanField.setBounds(15, 20, memoPanWidth-20, 40);
			memoPanField.setFont(new Font("serif",Font.PLAIN, 13));
			String contentResize= "";
			int substringSize = 15;


			if(bean.getContents().length()<substringSize)
				contentResize = bean.getContents();
			else
				contentResize = bean.getContents().substring(0, substringSize) +"...";
			
			memoPanField.setOpaque(false);
			memoPanField.setEditable(false);
			memoPanField.setFocusable(false);
			memoPanField.setBorder(BorderFactory.createEmptyBorder());
			memoPanField.setText(contentResize);
			memoPanField.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {		
					int tempColorJudge = colorJudgeSetting(bean.getColor());
					memoMain memo3 = null;
					if(judgeField.getText().equals("0")) {
						memo3 = new memoMain(userid, tempColorJudge, bean.getContents(), screenX-screenX/4-countY*300, count*200, true, bean.getMemoidx()); // 나중에 추가해야함(유저아이디)
						
				
					judgeField.setText("1");

					count++;
					if((count+1)*200>screenY) {
						count = 0;
						countY++;
					}
					
					//중복 생성 방지 구문
					memo3.button_3.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							new Thread() {
								public void run() {
					                    try {
					                        Thread.sleep(40);
					                      judgeField.setText("0");
					   					 mainP.remove(scroll);
					   					memoListMainPanel.removeAll();
					   					memoListMainPanel.updateUI();
					   					repaint();
					   					initialize();
					   					repaint();
					                    } catch (Exception e) {
					                    }
								}
							}.start();
					
						}
					});
					
					}
				}
			});
			
			
			tPanel.add(memoPanField);
			
			//버튼 추가부
			t.add(tPanel);
			t.setBackground(null);
			
			create_form(t, 0, i*30, 30,10);
			
		}
		

		frame1.setVisible(true);
	}

	private int colorJudgeSetting(String colorData) {

		 int tempColorJudge = 1;
		switch(colorData) {
		case "yellow":
			tempColorJudge = 1;
			break;
		case "green":
			tempColorJudge = 2;
			break;
		case "pink":
			tempColorJudge = 3;
			break;
		case "blue":
			tempColorJudge = 4;
			break;
		case "gray":
			tempColorJudge = 5;
			break;
		}
		return tempColorJudge;
	}
	public void create_form(Component cmpt, int x, int y, int w, int h){

		  GridBagConstraints gbc = new GridBagConstraints();
			//여기는 임시 테스트 공간입니다
		  	gbc.anchor = GridBagConstraints.NORTH;
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = x;
			gbc.gridy = y;
			gbc.gridwidth = w;
			gbc.gridheight = h;
			gridBag.setConstraints(cmpt, gbc);
			memoListMainPanel.add(cmpt);
			memoListMainPanel.updateUI();
		}

	
//	
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					memolist window = new memolist(userid);
//					window.frame1.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj==plusIcon) {
			memoMain memoPlus = new memoMain(userid, 1);		//디폴트 노란 메모장 임시 취소		       

			memoPlus.button_3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new Thread() {
						public void run() {
							try {
		                        Thread.sleep(50);
		                        mainP.remove(scroll);
								memoListMainPanel.removeAll();
								memoListMainPanel.updateUI();
								System.out.println("동작");
								repaint();
								initialize();
								repaint();
		                    } catch (Exception e) {}
					}
				}.start();
				}
			});
		}
		else if(obj==closeIcon) {
			frame1.dispose();
		}
			
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		Object obj = e.getSource();
		
		if(obj==plusIcon) {
			state_1 = true;
			callOption.buttonChange(plusIcon, "plusClick", TOPICON);
		}
		else if(obj==closeIcon) {
			state_2 = true;
			callOption.buttonChange(closeIcon, "closeClick", TOPICON);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Object obj = e.getSource();
		if(obj==plusIcon) {
			state_1 = false;
			callOption.buttonChange(plusIcon, "plusUnfocus", TOPICON);
		}	
		else if(obj==closeIcon) {
			state_2 = false;
			callOption.buttonChange(closeIcon, "closeUnfocus", TOPICON);
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		Object obj = e.getSource();
		if(obj==plusIcon) {
		if (state_1 == false)
			callOption.buttonChange(plusIcon, "plusFocus", TOPICON);
		else if (state_1 == true)
			callOption.buttonChange(plusIcon, "plusClick", TOPICON);
		}
		else if(obj==closeIcon) {
			if (state_2 == false)
				callOption.buttonChange(closeIcon, "closeFocus", TOPICON);
			else if (state_2 == true)
				callOption.buttonChange(closeIcon, "closeClick", TOPICON);
		}
	}
	

	@Override
	public void mouseExited(MouseEvent e) {

		Object obj = e.getSource();

		if(obj==plusIcon) {
			callOption.buttonChange(plusIcon, "plusUnfocus", TOPICON);
		}
		if(obj==closeIcon) {
			callOption.buttonChange(closeIcon, "closeUnfocus", TOPICON);
		}
	}
}
