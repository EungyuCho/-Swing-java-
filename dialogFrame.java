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
	private int iSetting=0;		//���̾�α� �Ǵܿ�(â off)
	/*
	 * ��� �߰� ��� : Ctrl+F �� ����߰��κ� ���� ������ ��忡�� ���� �ϳ� �÷��� ���̾�α� �ٲܰɷθ�� �߰���Ű�� �� ���
	 * ������Ʈ�� �ּ� ������Ʈ �� ��
	 * mode 1: ���̵� ��й�ȣ�� �߸��Ǿ����ϴ�.
	 * mode 2: ���̵� �ߺ��Ǿ����ϴ�.
	 * mode 3: ���̵� �Է����ּ���.
	 * mode 4: ��� �� �� �ִ� ���̵��Դϴ�.
	 * mode 5: ��й�ȣ�� ��ġ���� �ʽ��ϴ�.
	 * mode 6: ȸ�������� �Ϸ�Ǿ����ϴ�. 
	 * mode 7: ȸ������â�� �����ðڽ��ϱ�?
	 */
	public dialogFrame(int mode) {
		publicParts();
		okButton = new JButton(calloption.changeIcon("ok", 120, 60));
		okButton.setBounds(90, 110, 120, 60);
		okButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		calloption.buttonSet(okButton);
		
		okButton.addActionListener(this);
		// ����߰��κ�
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
