package team_p1;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class FrameOption {
	
	public FrameOption() {}
	
	public ImageIcon changeSize(String fileName, int width, int height) {
		URL imgURL = this.getClass().getResource("IUimage/" + fileName + ".JPG");
		Image img = new ImageIcon(imgURL).getImage();
		Image newImg = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(newImg);
	}
	
	public ImageIcon changeIcon(String fileName, int width, int height) {
		URL imgURL = this.getClass().getResource("IUimage/" + fileName + ".png");
		Image img = new ImageIcon(imgURL).getImage();
		Image newImg = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(newImg);
	}
	public void buttonSet(JButton button) {
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
	}	
	public void buttonChange(JButton btn, String fileName, int size) {
		btn.setIcon(changeIcon(fileName, size, size));
	}
	public void buttonChange(JButton btn, String fileName, int width, int height) {
		btn.setIcon(changeIcon(fileName, width, height));
	}
}
