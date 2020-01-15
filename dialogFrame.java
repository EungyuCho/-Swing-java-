package team_p1;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class dialogFrame extends JFrame implements ActionListener, KeyListener {
	JPanel mainP;
	JButton mainPanel, okButton, ok2, idpwdCheckDialog, cancel, dialogBottom;
	FrameOption calloption;
	private boolean okButtonCustom =false;
	private int iSetting=0;		//다이얼로그 판단용(창 off)
	/*
	 * 모드 추가 방법 : Ctrl+F 로 모드추가부분 들어가서 마지막 모드에서 숫자 하나 늘려서 다이얼로그 바꿀걸로모드 추가시키면 됌 모드
	 * 업데이트시 주석 업데이트 할 것
	 * mode 1: 아이디 비밀번호가 잘못되었습니다.
	 * mode 2: 아이디가 중복되었습니다.
	 * mode 3: 아이디를 입력해주세요.
	 * mode 4: 사용 할 수 있는 아이디입니다.
	 * mode 5: 비밀번호가 일치하지 않습니다.
	 * mode 6: 회원가입이 완료되었습니다. 
	 * mode 7: 회원가입창을 닫으시겠습니까?
	 */
	public dialogFrame(int mode) {
		publicParts();
		okButton = new JButton(calloption.changeIcon("ok", 120, 60));
		okButton.setBounds(90, 110, 120, 60);
		okButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		calloption.buttonSet(okButton);
		
		okButton.addActionListener(this);
		// 모드추가부분
		if (mode == 1) {
			idpwdCheckDialog = new JButton(calloption.changeIcon("idpwdCheckDialog", 280, 45));
			idpwdCheckDialog.setBounds(13, 65, 280, 45);
			calloption.buttonSet(idpwdCheckDialog);

		} else if (mode == 2) {
			idpwdCheckDialog = new JButton(calloption.changeIcon("idno", 280, 45));
			idpwdCheckDialog.setBounds(13, 65, 280, 45);
			calloption.buttonSet(idpwdCheckDialog);
		} else if (mode == 3) {
			idpwdCheckDialog = new JButton(calloption.changeIcon("requestInputId", 280, 45));
			idpwdCheckDialog.setBounds(13, 65, 280, 45);
			calloption.buttonSet(idpwdCheckDialog);
		} else if (mode == 4) {
			idpwdCheckDialog = new JButton(calloption.changeIcon("idok", 280, 45));
			idpwdCheckDialog.setBounds(13, 65, 280, 45);
			calloption.buttonSet(idpwdCheckDialog);
		} else if (mode == 5) {
			idpwdCheckDialog = new JButton(calloption.changeIcon("pwdno", 280, 45));
			idpwdCheckDialog.setBounds(13, 65, 280, 45);
			calloption.buttonSet(idpwdCheckDialog);
		} else if (mode == 6) {
			idpwdCheckDialog = new JButton(calloption.changeIcon("signUpok", 280, 45));
			idpwdCheckDialog.setBounds(13, 65, 280, 45);
			calloption.buttonSet(idpwdCheckDialog);
		} else if (mode == 7) {
			idpwdCheckDialog = new JButton(calloption.changeIcon("closeDialog", 280, 45));
			idpwdCheckDialog.setBounds(13, 65, 280, 45);
			calloption.buttonSet(idpwdCheckDialog);

			okButtonCustom = true;
			
			cancel = new JButton(calloption.changeIcon("cancle", 120, 60));
			cancel.setBounds(180, 110, 120, 60);
			cancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
			cancel.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			calloption.buttonSet(cancel);
			
			ok2 = new JButton(calloption.changeIcon("ok", 120, 60));
			ok2.setBounds(90, 110, 120, 60);
			ok2.setCursor(new Cursor(Cursor.HAND_CURSOR));
			
			calloption.buttonSet(ok2);
			calloption.buttonSet(cancel);
			add(ok2);
			add(cancel);
			
		} else {
			this.dispose();
		}

		add(idpwdCheckDialog);
		if(!okButtonCustom)
			add(okButton);
		add(mainPanel);

		requestFocus();
		repaint();
	}

	void publicParts() {
		setSize(300, 200);
		setLocationRelativeTo(null);
		setUndecorated(true);
		setBackground(new Color(0, 0, 0, 1));
		setLayout(null);
		setVisible(true);
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					dispose();
			}
		});
		calloption = new FrameOption();

		mainP = new JPanel();
		mainP.setPreferredSize(new Dimension(300, 150));
		mainPanel = new JButton(calloption.changeIcon("dialogFrame", 300, 150));
		mainPanel.setBounds(0, 0, 300, 200);
		calloption.buttonSet(mainPanel);

	}
	private void iSet(int i) {
		iSetting = i;
	}
	public int getValue() {
		return iSetting;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj==okButton) {
			dispose();
			
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
