package team_p1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.metal.MetalScrollBarUI;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/* Made by Gyu
 * Memo Ver1.2
 * Last Update (2019.10.18)
 * No commercial useProhibit 
 * */

public class memoMain extends JFrame implements ActionListener, ComponentListener, MouseListener {
	// 옵션(Don't touch)
	private final int TOPICONSIZE = 28;
	private final int BOTTOMICONSIZE = 22;
	private JFrame frame1;
	private JPanel mainP, memoTopP, memoBottomP, lineP;
	boolean yellow_state = true, green_state = false, pink_state = false, blue_state = false, gray_state = false; // 색상
	private SimpleAttributeSet fontBold, fontUnbold, fontItalic, fontUnitalic, fontStrike, fontUnstrike, fontUnderline,
			fontUnunderline;
	private StyledDocument doc;
	private JScrollPane scroll;
	private CLIOutput pane;
	private JButton button_1, button_2, button_4, button_5, button_6, button_7, bottomLine;
	public JButton button_3;
	private boolean status_1, status_2, status_3, status_4; // 1, 2, 3, 4 는 폰트 옵션 in, out 이벤트 제어
	private boolean clickState_1, clickState_2, clickState_3, clickState_4;
	private boolean state_1, state_2, state_3;
	private boolean listCall, updateMode, normalMode;					//updatemode = 업데이트모드로 메모 오픈
	private int memoIdx;
	private String listString;
	private int pX, pY;
	private int cursurPoint;
	private int colorModeJudge;
	private String colorModeString="yellow";
	static Document doc2;
	private int screenX, screenY;
	private String userid = "";
	// 클래스 따오는 부분
	SimpleAttributeSet now;
	FrameOption callOption;
	memoDialog mo;
	// mo 판단
	private boolean mo_state = false;

	Color yellowColor1 = new Color(255, 242, 171); // 노랑메모장 예시
	Color yellowColor2 = new Color(255, 235, 129);
	Color greenColor1 = new Color(203, 241, 196);
	Color greenColor2 = new Color(175, 236, 164);
	Color pinkColor1 = new Color(255, 204, 229);
	Color pinkColor2 = new Color(255, 187, 221);
	Color blueColor1 = new Color(205, 233, 255);
	Color blueColor2 = new Color(183, 223, 255);
	Color grayColor1 = new Color(249, 249, 249);
	Color grayColor2 = new Color(229, 229, 229);

	private Color selectedColor1;
	private Color selectedColor2;

	public memoMain(String userid, int colorMode) {// 생성자 (컬러모드 1: yellow, 2: green , 3: pink, 4:blue, 5: gray
		this.userid = userid;
		colorset(colorMode);
		initialize();
		normalMode = true;
	}

	public memoMain(String userid, int colorMode, int screenX, int screenY) {// 생성자 (컬러모드 1: yellow, 2: green , 3: pink, 4:blue, 5:gray
		this.userid = userid;
		colorStringSwitch(colorMode);														// gray
		colorset(colorMode);
		this.screenX = screenX;
		this.screenY = screenY;
		normalMode = true;
		initialize();
	}
	public memoMain(String userid, int colorMode, String listText, int screenX, int screenY, boolean updateMode, int memoIdx) {// 업데이트모드로 텍스트를 염
		// gray
		this.userid = userid;
		colorStringSwitch(colorMode);		
		colorset(colorMode);
		this.memoIdx = memoIdx;
		this.updateMode = true;
		this.listString = listText;
		this.listCall = true;
		this.screenX = screenX;
		this.screenY = screenY;
		
		initialize();
	}

	public void initialize() {// 기본값, 초기화, 셋팅

		URL imgURL = this.getClass().getResource("IUimage/memoIcon.png"); // 아이콘 이미지 세팅
		frame1 = new JFrame();
		callOption = new FrameOption();
		ComponentResizer cr = new ComponentResizer();
		cr.registerComponent(frame1);
		cr.setSnapSize(new Dimension(10, 10));
		frame1.setSize(310, 240);
		frame1.setUndecorated(true);
		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		if (screenX == 0 && screenY == 0) {
			frame1.setLocation((int) (screenSize.getWidth() - 310), 0);
		} else {
			frame1.setLocation(screenX, screenY);
		}
		frame1.addComponentListener(this);
		frame1.setIconImage(new ImageIcon(imgURL).getImage());
		frame1.addWindowFocusListener(new WindowFocusListener() {
			@Override
			public void windowLostFocus(WindowEvent e) {
				if (mo_state != true) {
					focusoutEvent();
					callOption.buttonChange(button_1, "plusUnfocus", TOPICONSIZE);
				}
			}

			@Override
			public void windowGainedFocus(WindowEvent e) {
				if (mo_state != true) {
					memoTopP.add(button_1);
					memoTopP.add(button_2);
					memoTopP.add(button_3);
					memoBottomP.add(button_4);
					memoBottomP.add(button_5);
					memoBottomP.add(button_6);
					memoBottomP.add(button_7);
					mainP.add(memoBottomP);
					mainP.add(lineP);
					mainP.add(memoTopP);
					frame1.repaint();
				}
			}
		});
		frame1.setVisible(true);
		pX = frame1.getX();
		pY = frame1.getY();

		Border blackline = BorderFactory.createLineBorder(Color.black); // 메모장 바깥 라인 보더 지정

		attributeSet();

		mainP = new JPanel();
		mainP.setBackground(selectedColor1);
		mainP.setBorder(blackline);
		mainP.setLayout(null);

		memoTopP = new JPanel();
		memoTopP.setLayout(null);
		memoTopP.setBackground(selectedColor2);
		memoTopP.setBounds(1, 1, frame1.getWidth() - 2, TOPICONSIZE);
		memoTopP.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				pX = e.getX();
				pY = e.getY();
				if (mo_state == true) {
					mo.dispose();
					mo_state = false;
				}
			}

		});

		memoTopP.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				frame1.setLocation(x - pX, y - pY);
			}

		});
		button_1 = new JButton();
		callOption.buttonChange(button_1, "plusUnfocus", TOPICONSIZE);
		button_1.setBounds(0, 0, TOPICONSIZE, TOPICONSIZE);
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (state_1 == false)
					callOption.buttonChange(button_1, "plusFocus", TOPICONSIZE);
				else if (state_1 == true)
					callOption.buttonChange(button_1, "plusClick", TOPICONSIZE);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				callOption.buttonChange(button_1, "plusUnfocus", TOPICONSIZE);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				int newFrameCount = 0;
				int nextWidth = frame1.getLocation().x;
				int nextHeight = frame1.getLocation().y + frame1.getHeight();
				if (nextHeight + frame1.getHeight() > screenSize.getHeight()) {
					nextHeight = 0;
					Random screenRandom = new Random();
					int tempRandom;
					while (true) {
						tempRandom = screenRandom.nextInt((int) screenSize.getWidth());
						if (tempRandom < 310 || tempRandom > screenSize.getWidth() - 310) // 스크린 위치가 310보다 적거나 스크린 맥스에서
																							// -310보다 크면 다시돌림
							continue;
						break;
					}
					nextWidth = tempRandom;
				}
				// 새 프레임 호출부
				if (yellow_state == true) {
					colorModeJudge = 1;
				} else if (green_state == true) {
					colorModeJudge = 2;
				} else if (pink_state == true) {
					colorModeJudge = 3;
				} else if (blue_state == true) {
					colorModeJudge = 4;
				} else if (gray_state == true) {
					colorModeJudge = 5;
				}

				new memoMain(userid, colorModeJudge, nextWidth, nextHeight);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				state_1 = true;
				callOption.buttonChange(button_1, "plusClick", TOPICONSIZE);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				state_1 = false;
				callOption.buttonChange(button_1, "plusUnfocus", TOPICONSIZE);
			}
		});

		button_2 = new JButton();
		callOption.buttonChange(button_2, "viewColorUnfocus", TOPICONSIZE);
		button_2.setBounds(memoTopP.getWidth() - (TOPICONSIZE * 2), 0, TOPICONSIZE, TOPICONSIZE);
		button_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (state_2 == false)
					callOption.buttonChange(button_2, "viewColorFocus", TOPICONSIZE);
				else if (state_2 == true)
					callOption.buttonChange(button_2, "viewColorClick", TOPICONSIZE);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				callOption.buttonChange(button_2, "viewColorUnfocus", TOPICONSIZE);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
//				frame1.getWidth() - 2;

				// 다이얼로그 생성
				if (!mo_state) {
					int dialogX = frame1.getLocationOnScreen().x + frame1.getWidth() - 202;
					int dialogY = frame1.getLocationOnScreen().y + TOPICONSIZE;
					mo = new memoDialog(true, false, false, false, false, dialogX, dialogY);
					mo_state = true;
					mo.addWindowFocusListener(new WindowFocusListener() {
						@Override
						public void windowLostFocus(WindowEvent e) {
							focusoutEvent();
							mo_state = false;
							mo.dispose();

						}

						@Override
						public void windowGainedFocus(WindowEvent e) {
						}
					});
				} else
					mo.requestFocus();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				state_2 = true;
				callOption.buttonChange(button_2, "viewColorClick", TOPICONSIZE);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				state_2 = false;
				callOption.buttonChange(button_2, "viewColorUnfocus", TOPICONSIZE);

			}
		});

		button_3 = new JButton();
		callOption.buttonChange(button_3, "closeUnfocus", TOPICONSIZE);
		button_3.setBounds(memoTopP.getWidth() - TOPICONSIZE, 0, TOPICONSIZE, TOPICONSIZE);
		button_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (state_3 == false)
					callOption.buttonChange(button_3, "closeUnfocus", TOPICONSIZE);
				else if (state_3 == true)
					callOption.buttonChange(button_3, "closeClick", TOPICONSIZE);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				callOption.buttonChange(button_3, "closeUnfocus", TOPICONSIZE);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				//메모 종료 이벤트 추가부 #종료
				if(updateMode) {
					new Thread() {
						public void run() {
							frame1.dispose();
						}
					}.start();
					memolistBean bean = new memolistBean();
					bean.setMemoidx(memoIdx);
					bean.setColor(colorModeString);
					bean.setContents( pane.getText());
					memoMgr mgr = new memoMgr();
					// 여기서 유저 아이디 입력하였음 #유저아이디 # 유저 ID
					mgr.updateMemo(bean);
					}
				//노멀모드
				else if(normalMode==true) {
					memolistBean bean = new memolistBean();
					bean.setContents( pane.getText());
					bean.setColor(colorModeString);
					//유저아이디 임시임력
					bean.setUserid(userid);
					memoMgr mgr = new memoMgr();
					// 여기서 유저 아이디 입력하였음 #유저아이디 # 유저 ID
					if(pane.getText().length()!=0)
						mgr.insertMemo(bean);
					frame1.dispose();
				}
				if (mo_state == true) {
					mo.dispose();
					mo_state = false;
				}
				//디스포즈
			}

			@Override
			public void mousePressed(MouseEvent e) {
				state_3 = true;
				callOption.buttonChange(button_3, "closeClick", TOPICONSIZE);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				state_3 = false;
				callOption.buttonChange(button_3, "closeUnfocus", TOPICONSIZE);
			}
		});

		callOption.buttonSet(button_1);
		callOption.buttonSet(button_2);
		callOption.buttonSet(button_3);

		memoTopP.add(button_1);
		memoTopP.add(button_2);
		memoTopP.add(button_3);

		mainP.add(memoTopP);
		pane = new CLIOutput();
		doc2 = pane.getDocument();
		pane.requestFocus();
		pane.setEditable(true);
		pane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				doc = (StyledDocument) pane.getDocument();
				if (mo_state == true) {
					mo.dispose();
					mo_state = false;
				}
				cursurPoint = pane.getCaretPosition() - 1;
				cursorPointAttributeCheck();
			}

		});
		
		// 키 이벤트
		pane.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				cursurPoint = pane.getCaretPosition() - 1;

				doc = (StyledDocument) pane.getDocument();

				cursorPointAttributeCheck();

			}

		});

		// Jtextpane 선언 및 정의

		pane.setBackground(selectedColor1);
		pane.setSize(frame1.getWidth() - 80, frame1.getHeight() - TOPICONSIZE * 2 - 15);
		JPanel textPanel = new JPanel(new BorderLayout());
		textPanel.setLocation(0, 0);
		pane.setText(listString);
		textPanel.add(pane);

		scroll = new JScrollPane(pane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		scroll.setBounds(8, 36, frame1.getWidth() - 20, frame1.getHeight() - TOPICONSIZE * 2 - 15);

		scroll.setBackground(selectedColor1);

		JScrollBar sb = scroll.getVerticalScrollBar();
		MyScrollbarUI setUiScroll = new MyScrollbarUI(selectedColor1);
		sb.setUI(setUiScroll);

		Border yellowLine = BorderFactory.createLineBorder(selectedColor1);
		scroll.setBorder(yellowLine);
		// bottom위에있는 반투명 panel
		lineP = new JPanel();

		lineP.setBackground(selectedColor1);
		lineP.setLayout(null);
		lineP.setBounds(1, frame1.getHeight() - 34, frame1.getWidth() - 2, 5);
		// 반투명 panel에 넣어주는 이미지
		bottomLine = new JButton(callOption.changeIcon("memoBottomLine", frame1.getWidth() - 1, 1));
		bottomLine.setBounds(1, 0, lineP.getWidth() - 2, lineP.getHeight());
		callOption.buttonSet(bottomLine);

		lineP.add(bottomLine);
		mainP.add(lineP);
		mainP.add(scroll);

		memoBottomP = new JPanel();
		memoBottomP.setBounds(1, frame1.getHeight() - 29, frame1.getWidth() - 2, 29);
		MatteBorder matBor = new MatteBorder(0, 0, 1, 0, Color.BLACK);
		memoBottomP.setBorder(matBor);
		memoBottomP.setBackground(selectedColor1);
		memoBottomP.setLayout(null);
		memoBottomP.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (mo_state == true) {
					mo.dispose();
					mo_state = false;
				}
			}
		});
		button_4 = new JButton();
		callOption.buttonChange(button_4, "boldUnfocus", BOTTOMICONSIZE);
		button_4.setBounds(4, 4, BOTTOMICONSIZE, BOTTOMICONSIZE);
		button_4.addMouseListener(this);

		button_5 = new JButton();
		callOption.buttonChange(button_5, "tiltingUnfocus", BOTTOMICONSIZE);
		button_5.setBounds(8 + BOTTOMICONSIZE, 4, BOTTOMICONSIZE, BOTTOMICONSIZE);
		button_5.addMouseListener(this);

		button_6 = new JButton();
		callOption.buttonChange(button_6, "strikethroughUnfocus", BOTTOMICONSIZE);
		button_6.setBounds(12 + BOTTOMICONSIZE * 2, 4, BOTTOMICONSIZE, BOTTOMICONSIZE);
		button_6.addMouseListener(this);

		button_7 = new JButton();
		callOption.buttonChange(button_7, "underlineUnfocus", BOTTOMICONSIZE);
		button_7.setBounds(16 + BOTTOMICONSIZE * 3, 4, BOTTOMICONSIZE, BOTTOMICONSIZE);
		button_7.addMouseListener(this);

		callOption.buttonSet(button_4);
		callOption.buttonSet(button_5);
		callOption.buttonSet(button_6);
		callOption.buttonSet(button_7);

		button_4.addActionListener(this);
		button_5.addActionListener(this);
		button_6.addActionListener(this);
		button_7.addActionListener(this);

		memoBottomP.add(button_4);
		memoBottomP.add(button_5);
		memoBottomP.add(button_6);
		memoBottomP.add(button_7);

		mainP.add(memoBottomP);

		frame1.add(mainP);

		now = new SimpleAttributeSet();

		pane.requestFocus();
	}

	private void focusoutEvent() {
		memoTopP.remove(button_1);
		memoTopP.remove(button_2);
		memoTopP.remove(button_3);
		memoBottomP.remove(button_4);
		memoBottomP.remove(button_5);
		memoBottomP.remove(button_6);
		memoBottomP.remove(button_7);
		mainP.remove(memoBottomP);
		mainP.remove(lineP);
		mainP.remove(memoTopP);
		frame1.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		cursurPoint = pane.getCaretPosition();
		if (e.getSource() == button_4) {
			int seletedIdxStart = pane.getSelectionStart();
			int seletedIdxEnd = pane.getSelectionEnd();
			int len = seletedIdxEnd - seletedIdxStart;
			boolean allBold = false;

			StyledDocument doc = (StyledDocument) pane.getDocument();

			// Select 안한 상태로 클릭 시

			if (len == 0) {
				if (!pane.getInputAttributes().toString().contains("bold=true")) {
					pane.setCharacterAttributes(fontBold, false);
					System.out.println("false에요! bold로 바꿀게요");
				} else {
					pane.setCharacterAttributes(fontUnbold, false);
					System.out.println("True에요! Unbold로 바꿀게요");
				}
			} else {
				// Select 부분에 bold여부 확인
				for (int i = 0; i < len; i++) {
					if (!doc.getCharacterElement(seletedIdxStart + i).getAttributes().containsAttributes(fontBold)) {
						allBold = false;
						break;
					}
					allBold = true;
				}
				// Seleted를 확인해서 현재 전부 bold 상태면 unBold 해주고 unBold면 select 부분 전부 Bold
				if (allBold == false) {
					pane.getStyledDocument().setCharacterAttributes(seletedIdxStart, len, fontBold, false);
				} else {
					pane.getStyledDocument().setCharacterAttributes(seletedIdxStart, len, fontUnbold, false);
				}
			}
		}
		if (e.getSource() == button_5) {

			int seletedIdxStart = pane.getSelectionStart();
			int seletedIdxEnd = pane.getSelectionEnd();
			int len = seletedIdxEnd - seletedIdxStart;
			boolean allItalic = false;

			StyledDocument doc = (StyledDocument) pane.getDocument();

			// Select 안한 상태로 클릭 시

			if (len == 0) {
				if (!pane.getInputAttributes().toString().contains("italic=true")) {
					pane.setCharacterAttributes(fontItalic, false);
					System.out.println("false에요! Italic으로 바꿀게요");
				} else {
					pane.setCharacterAttributes(fontUnitalic, false);
					System.out.println("True에요! Unitalic으로 바꿀게요");
				}
			} else {
				// Select 부분에 bold여부 확인(전부 bold 타입이면 allBold true 리턴 else false)
				for (int i = 0; i < len; i++) {
					if (!doc.getCharacterElement(seletedIdxStart + i).getAttributes().containsAttributes(fontItalic)) {
						allItalic = false;
						break;
					}
					allItalic = true;
				}
				// Seleted를 확인해서 현재 전부 bold 상태면 unBold 해주고 unBold면 select 부분 전부 Bold
				if (allItalic == false) {
					pane.getStyledDocument().setCharacterAttributes(seletedIdxStart, len, fontItalic, false);
				} else {
					pane.getStyledDocument().setCharacterAttributes(seletedIdxStart, len, fontUnitalic, false);
				}
			}
		}
		if (e.getSource() == button_6) {
			int seletedIdxStart = pane.getSelectionStart();
			int seletedIdxEnd = pane.getSelectionEnd();
			int len = seletedIdxEnd - seletedIdxStart;
			boolean allItalic = false;

			StyledDocument doc = (StyledDocument) pane.getDocument();

			// Select 안한 상태로 클릭 시
			if (len == 0) {
				if (!pane.getInputAttributes().toString().contains("strikethrough=true")) {
					pane.setCharacterAttributes(fontStrike, false);
					System.out.println("false에요! Strike으로 바꿀게요");
				} else {
					pane.setCharacterAttributes(fontUnstrike, false);
					System.out.println("True에요! Unstrike으로 바꿀게요");
				}
			} else {
				// Select 부분에 bold여부 확인(전부 bold 타입이면 allBold true 리턴 else false)
				for (int i = 0; i < len; i++) {
					if (!doc.getCharacterElement(seletedIdxStart + i).getAttributes().containsAttributes(fontStrike)) {
						allItalic = false;
						break;
					}
					allItalic = true;
				}
				// Seleted를 확인해서 현재 전부 bold 상태면 unBold 해주고 unBold면 select 부분 전부 Bold
				if (allItalic == false) {
					pane.getStyledDocument().setCharacterAttributes(seletedIdxStart, len, fontStrike, false);
				} else {
					pane.getStyledDocument().setCharacterAttributes(seletedIdxStart, len, fontUnstrike, false);
				}
			}
		}
		if (e.getSource() == button_7) {
			int seletedIdxStart = pane.getSelectionStart();
			int seletedIdxEnd = pane.getSelectionEnd();
			int len = seletedIdxEnd - seletedIdxStart;
			boolean allItalic = false;

			StyledDocument doc = (StyledDocument) pane.getDocument();

			if (len == 0) {
				if (!pane.getInputAttributes().toString().contains("underline=true")) {
					pane.setCharacterAttributes(fontUnderline, false);
					System.out.println("false에요! Underline으로 바꿀게요");
				} else {
					pane.setCharacterAttributes(fontUnunderline, false);
					System.out.println("True에요! Ununderline으로 바꿀게요");
				}
			} else {
				// Select 부분에 bold여부 확인(전부 bold 타입이면 allBold true 리턴 else false)
				for (int i = 0; i < len; i++) {
					if (!doc.getCharacterElement(seletedIdxStart + i).getAttributes()
							.containsAttributes(fontUnderline)) {
						allItalic = false;
						break;
					}
					allItalic = true;
				}
				// Seleted를 확인해서 현재 전부 bold 상태면 unBold 해주고 unBold면 select 부분 전부 Bold
				if (allItalic == false) {
					pane.getStyledDocument().setCharacterAttributes(seletedIdxStart, len, fontUnderline, false);
				} else {
					pane.getStyledDocument().setCharacterAttributes(seletedIdxStart, len, fontUnunderline, false);
				}
			}
		}
		pane.setCharacterAttributes(now, false);
		pane.requestFocus();
		pane.setCaretPosition(cursurPoint);

	}

	
	private void colorset(int colorMode) {
		if (colorMode == 1) {
			selectedColor1 = yellowColor1;
			selectedColor2 = yellowColor2;
			getColorStateFromDialog(true, false, false, false, false);
		} else if (colorMode == 2) {
			selectedColor1 = greenColor1;
			selectedColor2 = greenColor2;
			getColorStateFromDialog(false, true, false, false, false);
		} else if (colorMode == 3) {
			selectedColor1 = pinkColor1;
			selectedColor2 = pinkColor2;
			getColorStateFromDialog(false, false, true, false, false);
		} else if (colorMode == 4) {
			selectedColor1 = blueColor1;
			selectedColor2 = blueColor2;
			getColorStateFromDialog(false, false, false, true, false);
		} else if (colorMode == 5) {
			selectedColor1 = grayColor1;
			selectedColor2 = grayColor2;
			getColorStateFromDialog(false, false, false, false, true);
		}
	}

	public void getColorStateFromDialog(boolean yellowStat, boolean greenStat, boolean pinkStat, boolean blueStat,
			boolean grayStat) {
		this.yellow_state = yellowStat;
		this.green_state = greenStat;
		this.pink_state = pinkStat;
		this.blue_state = blueStat;
		this.gray_state = grayStat;
	}

	@Override
	public void componentResized(ComponentEvent e) {
		memoTopP.setBounds(1, 1, frame1.getWidth() - 2, TOPICONSIZE);
		button_2.setLocation(memoTopP.getWidth() - (TOPICONSIZE * 2), 0);
		button_3.setLocation(memoTopP.getWidth() - TOPICONSIZE, 0);
		lineP.setBounds(1, frame1.getHeight() - 34, frame1.getWidth() - 2, 5);
		bottomLine.setIcon(callOption.changeIcon("memoBottomLine", frame1.getWidth() - 1, 1));
		bottomLine.setBounds(1, 0, lineP.getWidth() - 2, lineP.getHeight());
		memoBottomP.setBounds(1, frame1.getHeight() - 29, frame1.getWidth() - 2, 29);
		scroll.setBounds(8, 36, frame1.getWidth() - 20, frame1.getHeight() - TOPICONSIZE * 2 - 15);
	}

	@Override
	public void componentMoved(ComponentEvent e) {
	}

	@Override
	public void componentShown(ComponentEvent e) {
	}

	@Override
	public void componentHidden(ComponentEvent e) {
	}

	// 마우스 이벤트
	@Override
	public void mouseEntered(MouseEvent e) {
		JButton obj = (JButton) e.getSource();
		String typeCheck = "";
		if (obj == button_4)
			typeCheck = "bold";
		else if (obj == button_5)
			typeCheck = "tilting";
		else if (obj == button_6)
			typeCheck = "strikethrough";
		else if (obj == button_7)
			typeCheck = "underline";
		// 버튼 인 이벤트
		switch (typeCheck) {
		case "bold":
			status_1 = true;
			if (clickState_1 == false)
				callOption.buttonChange(obj, typeCheck + "Focus", BOTTOMICONSIZE);
			else if (clickState_1 == true)
				callOption.buttonChange(obj, typeCheck + "Click", BOTTOMICONSIZE);
			break;

		case "tilting":
			status_2 = true;
			if (clickState_2 == false)
				callOption.buttonChange(obj, typeCheck + "Focus", BOTTOMICONSIZE);
			else if (clickState_2 == true)
				callOption.buttonChange(obj, typeCheck + "Click", BOTTOMICONSIZE);
			break;

		case "strikethrough":
			status_3 = true;
			if (clickState_3 == false)
				callOption.buttonChange(obj, typeCheck + "Focus", BOTTOMICONSIZE);
			else if (clickState_3 == true)
				callOption.buttonChange(obj, typeCheck + "Click", BOTTOMICONSIZE);
			break;

		case "underline":
			status_4 = true;
			if (clickState_4 == false)
				callOption.buttonChange(obj, typeCheck + "Focus", BOTTOMICONSIZE);
			else if (clickState_4 == true)
				callOption.buttonChange(obj, typeCheck + "Click", BOTTOMICONSIZE);
			break;
		}

	}

	@Override
	public void mouseExited(MouseEvent e) {
		JButton obj = (JButton) e.getSource();
		String typeCheck = "";
		if (obj == button_4)
			typeCheck = "bold";
		else if (obj == button_5)
			typeCheck = "tilting";
		else if (obj == button_6)
			typeCheck = "strikethrough";
		else if (obj == button_7)
			typeCheck = "underline";
		switch (typeCheck) {
		case "bold":
			status_1 = false;
			if (clickState_1 == false) {
				callOption.buttonChange(obj, typeCheck + "Unfocus", BOTTOMICONSIZE);
			}
			break;
		case "tilting":
			status_2 = false;
			if (clickState_2 == false)
				callOption.buttonChange(obj, typeCheck + "Unfocus", BOTTOMICONSIZE);
			break;
		case "strikethrough":
			status_3 = true;
			if (clickState_3 == false)
				callOption.buttonChange(obj, typeCheck + "Unfocus", BOTTOMICONSIZE);
			break;
		case "underline":
			status_4 = true;
			if (clickState_4 == false)
				callOption.buttonChange(obj, typeCheck + "Unfocus", BOTTOMICONSIZE);
			break;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JButton obj = (JButton) e.getSource();
		String typeCheck = "";

		if (obj == button_4)
			typeCheck = "bold";
		else if (obj == button_5)
			typeCheck = "tilting";
		else if (obj == button_6)
			typeCheck = "strikethrough";
		else if (obj == button_7)
			typeCheck = "underline";
		switch (typeCheck) {
		case "bold":
			if (clickState_1 == false) {
				callOption.buttonChange(obj, typeCheck + "Click", BOTTOMICONSIZE);
				clickState_1 = true;
			} else if (clickState_1 == true) {
				callOption.buttonChange(obj, typeCheck + "Focus", BOTTOMICONSIZE);
				clickState_1 = false;
			}
			break;
		case "tilting":
			if (clickState_2 == false) {
				callOption.buttonChange(obj, typeCheck + "Click", BOTTOMICONSIZE);
				clickState_2 = true;
			} else if (clickState_2 == true) {
				callOption.buttonChange(obj, typeCheck + "Focus", BOTTOMICONSIZE);
				clickState_2 = false;
			}
			break;
		case "strikethrough":
			if (clickState_3 == false) {
				callOption.buttonChange(obj, typeCheck + "Click", BOTTOMICONSIZE);
				clickState_3 = true;
			} else if (clickState_3 == true) {
				callOption.buttonChange(obj, typeCheck + "Focus", BOTTOMICONSIZE);
				clickState_3 = false;
			}
			break;
		case "underline":
			if (clickState_4 == false) {
				callOption.buttonChange(obj, typeCheck + "Click", BOTTOMICONSIZE);
				clickState_4 = true;
			} else if (clickState_4 == true) {
				callOption.buttonChange(obj, typeCheck + "Focus", BOTTOMICONSIZE);
				clickState_4 = false;
			}
			break;
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		JButton obj = (JButton) e.getSource();
		String typeCheck = "";

		if (obj == button_4)
			typeCheck = "bold";
		else if (obj == button_5)
			typeCheck = "tilting";
		else if (obj == button_6)
			typeCheck = "strikethrough";
		else if (obj == button_7)
			typeCheck = "underline";

		switch (typeCheck) {
		case "bold":
			callOption.buttonChange(obj, typeCheck + "Click", BOTTOMICONSIZE);
			break;
		case "tilting":
			callOption.buttonChange(obj, typeCheck + "Click", BOTTOMICONSIZE);
			break;
		case "strikethrough":
			callOption.buttonChange(obj, typeCheck + "Click", BOTTOMICONSIZE);
			break;
		case "underline":
			callOption.buttonChange(obj, typeCheck + "Click", BOTTOMICONSIZE);
			break;
		}
	}

// 릴리즈 이벤트
	@Override
	public void mouseReleased(MouseEvent e) {
//		JButton obj = (JButton) e.getSource();
//		String typeCheck = "";
//		if (obj == button_4)
//			typeCheck = "bold";
//		else if (obj == button_5)
//			typeCheck = "tilting";
//		else if (obj == button_6)
//			typeCheck = "strikethrough";
//		else if (obj == button_7)
//			typeCheck = "underline";
//		switch (typeCheck) {
//		case "bold":
//			if(status_1==false)
//			status_4 = false;
//			break;
//		case "tilting":
//			status_5 = false;
//			break;
//		case "strikethrough":
//			status_6 = false;
//			break;
//		case "underline":
//			status_7 = false;
//			break;
//		}
	}
	private void  colorStringSwitch(int colorJudgeNum) {//colorStringSwitch(컬러모드 1: yellow, 2: green , 3: pink, 4:blue, 5:gray
		switch(colorJudgeNum) {
		case 1:
			colorModeString = "yellow";
			break;
		case 2:
			colorModeString = "green";
			break;
		case 3:
			colorModeString = "pink";
			break;
		case 4:
			colorModeString = "blue";
			break;
		case 5:
			colorModeString = "gray";
			break;
		}
	}
	static class MyScrollbarUI extends MetalScrollBarUI {

		private Image imageThumb, imageTrack;
		private JButton b = new JButton() {

			@Override
			public Dimension getPreferredSize() {
				return new Dimension(0, 0);
			}

		};

		MyScrollbarUI(Color color) {
			imageThumb = FauxImage.create(16, 16, new Color(122, 122, 122));
			imageTrack = FauxImage.create(16, 16, color);
		}

		@Override
		protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
			g.setColor(Color.blue);
			((Graphics2D) g).drawImage(imageThumb, r.x + 14, r.y, r.width - 9, r.height, null);
		}

		@Override
		protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
			((Graphics2D) g).drawImage(imageTrack, r.x, r.y, r.width, r.height, null);
		}

		@Override
		protected JButton createDecreaseButton(int orientation) {
			return b;
		}

		@Override
		protected JButton createIncreaseButton(int orientation) {
			return b;
		}
	}

	private void cursorPointAttributeCheck() {
		int size = BOTTOMICONSIZE;
		// 인일떄 true 아웃일떄 false
		if (status_1 == false) {
			if (doc.getCharacterElement(cursurPoint).getAttributes().containsAttributes(fontBold))
				callOption.buttonChange(button_4, "boldClick", size);
			else if (!doc.getCharacterElement(cursurPoint).getAttributes().containsAttributes(fontBold))
				callOption.buttonChange(button_4, "boldUnfocus", size);
		}

		if (status_2 == false) {
			if (doc.getCharacterElement(cursurPoint).getAttributes().containsAttributes(fontItalic))
				callOption.buttonChange(button_5, "tiltingClick", size);
			else if (!doc.getCharacterElement(cursurPoint).getAttributes().containsAttributes(fontItalic))
				callOption.buttonChange(button_5, "tiltingUnfocus", size);
		}
		if (status_3 == false) {
			if (doc.getCharacterElement(cursurPoint).getAttributes().containsAttributes(fontStrike))
				callOption.buttonChange(button_6, "strikethroughClick", size);
			else if (!doc.getCharacterElement(cursurPoint).getAttributes().containsAttributes(fontStrike))
				callOption.buttonChange(button_6, "strikethroughUnfocus", size);
		}
		if (status_4 == false) {
			if (doc.getCharacterElement(cursurPoint).getAttributes().containsAttributes(fontUnderline))
				callOption.buttonChange(button_7, "underlineClick", size);
			else if (!doc.getCharacterElement(cursurPoint).getAttributes().containsAttributes(fontUnderline))
				callOption.buttonChange(button_7, "underlineUnfocus", size);
		}
	}

	private void attributeSet() {
		fontBold = new SimpleAttributeSet();
		fontUnbold = new SimpleAttributeSet();
		fontItalic = new SimpleAttributeSet();
		fontUnitalic = new SimpleAttributeSet();
		fontStrike = new SimpleAttributeSet();
		fontUnstrike = new SimpleAttributeSet();
		fontUnderline = new SimpleAttributeSet();
		fontUnunderline = new SimpleAttributeSet();

		StyleConstants.setBold(fontBold, true);
		StyleConstants.setBold(fontUnbold, false);
		StyleConstants.setItalic(fontItalic, true);
		StyleConstants.setItalic(fontUnitalic, false);
		StyleConstants.setStrikeThrough(fontStrike, true);
		StyleConstants.setStrikeThrough(fontUnstrike, false);
		StyleConstants.setUnderline(fontUnderline, true);
		StyleConstants.setUnderline(fontUnunderline, false);
	}

	private static class FauxImage {

		static public Image create(int w, int h, Color c) {
			BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = bi.createGraphics();
			g2d.setPaint(c);
			g2d.fillRect(0, 0, w, h);
			g2d.dispose();
			return bi;
		}
	}

	// 다이얼로그

	public class memoDialog extends JFrame implements ActionListener, MouseListener {
		JPanel dialogPanel;
		JButton yellow, green, pink, blue, gray, list;
		FrameOption callOption;
		int widthSize, heightSize;
		boolean yellow_state = true, green_state = false, pink_state = false, blue_state = false, gray_state = false; // 색상
																														// 판단

		public memoDialog(boolean yellow_stat, boolean green_stat, boolean pink_stat, boolean blue_stat,
				boolean gray_stat, int x, int y) {
			setSize(200, 100);
			setLocation(x, y + 1);
			setUndecorated(true);
			setLayout(null);
			super.setType(javax.swing.JFrame.Type.UTILITY);

			Border blackline = BorderFactory.createLineBorder(Color.DARK_GRAY); // 다이얼로그 바깥 라인 보더 지정
//    		setType(javax.swing.JFrame.Type.UTILITY);

			widthSize = getWidth() / 5;
			heightSize = (getHeight() / 3) * 2 - (getHeight() / 13);

			dialogPanel = new JPanel();
			callOption = new FrameOption();

			colorSet(yellow_stat, green_stat, pink_stat, blue_stat, gray_stat); // 컬러 세팅하고 인트형으로 남은 height사이즈 리턴받음

			dialogPanel.setBounds(0, 0, 400, 100);
			dialogPanel.setBackground(Color.gray); // 컬러 지정
			dialogPanel.setLayout(null);
			dialogPanel.setBorder(blackline);

			add(dialogPanel);
			setVisible(true);
			requestFocus();

		}

		private void colorSet(boolean y, boolean gr, boolean p, boolean b, boolean g) {

			yellow = new JButton();
			green = new JButton();
			pink = new JButton();
			blue = new JButton();
			gray = new JButton();
			list = new JButton();

			callOption.buttonSet(yellow);
			callOption.buttonSet(green);
			callOption.buttonSet(pink);
			callOption.buttonSet(blue);
			callOption.buttonSet(gray);
			callOption.buttonSet(list);

			yellow.setBounds(1, 1, widthSize, heightSize);
			green.setBounds(widthSize, 1, widthSize, heightSize);
			pink.setBounds(2 * widthSize, 1, widthSize, heightSize);
			blue.setBounds(3 * widthSize, 1, widthSize, heightSize);
			gray.setBounds(4 * widthSize - 1, 1, widthSize, heightSize);
			list.setBounds(1, heightSize, getWidth() - 2, getHeight() - heightSize - 1);
			callOption.buttonChange(list, "notelist", getWidth() - 2, getHeight() - heightSize);
			replaceColor(y, gr, p, b, g);

			list.addMouseListener(this);
			dialogPanel.add(yellow);
			dialogPanel.add(green);
			dialogPanel.add(pink);
			dialogPanel.add(blue);
			dialogPanel.add(gray);
			dialogPanel.add(list);

			yellow.addActionListener(this);
			green.addActionListener(this);
			pink.addActionListener(this);
			blue.addActionListener(this);
			gray.addActionListener(this);

		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();

			if (obj == yellow) {
				if (!yellow_state)
					replaceColor(true, false, false, false, false);
				selectedColor1 = yellowColor1;
				selectedColor2 = yellowColor2;
				repaintBackground();
				repaint();
			}

			else if (obj == green) {
				if (!green_state)
					replaceColor(false, true, false, false, false);
				selectedColor1 = greenColor1;
				selectedColor2 = greenColor2;
				repaintBackground();
				repaint();
			}

			else if (obj == pink) {
				if (!pink_state)
					replaceColor(false, false, true, false, false);
				selectedColor1 = pinkColor1;
				selectedColor2 = pinkColor2;
				repaintBackground();
				repaint();
			} else if (obj == blue) {
				if (!blue_state)
					replaceColor(false, false, false, true, false);
				selectedColor1 = blueColor1;
				selectedColor2 = blueColor2;
				repaintBackground();
				repaint();
			} else if (obj == gray) {
				if (!gray_state)
					replaceColor(false, false, false, false, true);
				selectedColor1 = grayColor1;
				selectedColor2 = grayColor2;
				repaintBackground();
				repaint();
			}
			getColorStateFromDialog(yellow_state, green_state, pink_state, blue_state, gray_state);

		}

		private void replaceColor(boolean yellowB, boolean greenB, boolean pinkB, boolean blueB, boolean grayB) {

			if (yellowB)
				callOption.buttonChange(yellow, "yellowSeleted", widthSize, heightSize);
			else
				callOption.buttonChange(yellow, "yellowUnseleted", widthSize, heightSize);
			if (greenB)
				callOption.buttonChange(green, "greenSeleted", widthSize, heightSize);
			else
				callOption.buttonChange(green, "greenUnseleted", widthSize, heightSize);
			if (pinkB)
				callOption.buttonChange(pink, "pinkSeleted", widthSize, heightSize);
			else
				callOption.buttonChange(pink, "pinkUnseleted", widthSize, heightSize);
			if (blueB)
				callOption.buttonChange(blue, "blueSeleted", widthSize, heightSize);
			else
				callOption.buttonChange(blue, "blueUnseleted", widthSize, heightSize);
			if (grayB)
				callOption.buttonChange(gray, "graySeleted", widthSize, heightSize);
			else
				callOption.buttonChange(gray, "grayUnseleted", widthSize, heightSize);

			if (yellowB)
				replaceColorState(true, false, false, false, false);
			if (greenB)
				replaceColorState(false, true, false, false, false);
			if (pinkB)
				replaceColorState(false, false, true, false, false);
			if (blueB)
				replaceColorState(false, false, false, true, false);
			if (grayB)
				replaceColorState(false, false, false, false, true);

		}
		private void replaceColorState(boolean yellowB, boolean greenB, boolean pinkB, boolean blueB, boolean grayB) {
			if (yellowB) {
				yellow_state = true;
				green_state = false;
				pink_state = false;
				blue_state = false;
				gray_state = false;
				colorModeString = "yellow";
			}
			if (greenB) {
				yellow_state = false;
				green_state = true;
				pink_state = false;
				blue_state = false;
				gray_state = false;
				colorModeString = "green";
			}
			if (pinkB) {
				yellow_state = false;
				green_state = false;
				pink_state = true;
				blue_state = false;
				gray_state = false;
				colorModeString = "pink";
			}
			if (blueB) {
				yellow_state = false;
				green_state = false;
				pink_state = false;
				blue_state = true;
				gray_state = false;
				colorModeString = "blue";
			}
			if (grayB) {
				yellow_state = false;
				green_state = false;
				pink_state = false;
				blue_state = false;
				gray_state = true;
				colorModeString = "gray";
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getSource()==list)
			new memolist(userid);
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		private void repaintBackground() {
			memoTopP.setBackground(selectedColor2);
			mainP.setBackground(selectedColor1);
			pane.setBackground(selectedColor1);
			scroll.setBackground(selectedColor1);
			lineP.setBackground(selectedColor1);
			memoBottomP.setBackground(selectedColor1);
			Border seletedBorder = BorderFactory.createLineBorder(selectedColor1); // 암옴김
			scroll.setBorder(seletedBorder);
			JScrollBar sb = scroll.getVerticalScrollBar();
			MyScrollbarUI setUiScroll = new MyScrollbarUI(selectedColor1);
			sb.setUI(setUiScroll);
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			Object obj = e.getSource();
			if (obj == list) {
				callOption.buttonChange(list, "noteliston", getWidth(), getHeight() - heightSize);
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			Object obj = e.getSource();
			if (obj == list) {
				callOption.buttonChange(list, "notelist", getWidth(), getHeight() - heightSize);
			}
		}
	}
}
