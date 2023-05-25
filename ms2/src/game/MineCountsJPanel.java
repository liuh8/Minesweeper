package game;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class MineCountsJPanel extends JPanel {

	/**
	 * 
	 */
	private JLabel labelCountG = new JLabel();
	MainFrame mainFrame;

	public MineCountsJPanel(MainFrame frame) {
		this.mainFrame = frame;
		this.setLayout(new BorderLayout());

		init();

	}

	private void init() {
		JPanel panel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(panel, BoxLayout.LINE_AXIS);
		panel.setLayout(boxLayout);

		FaceLableListener faceLableListener;
		faceLableListener = new FaceLableListener();
		panel.addMouseListener(faceLableListener);

		labelCountG.setText("Mines Left: " + DataController.allcount + "");

		panel.add(labelCountG);

		Border borderLow = BorderFactory.createLoweredBevelBorder();
		Border borderEmpty = BorderFactory.createEmptyBorder(2, 2, 2, 2);
		Border borderCom1 = BorderFactory.createCompoundBorder(borderLow,
				borderEmpty);

		panel.setBorder(borderCom1);
		panel.setBackground(Color.LIGHT_GRAY);

		this.add(panel);
		Border borderEmpty2 = BorderFactory.createEmptyBorder(5, 5, 5, 5);

		this.setBorder(borderEmpty2);
		this.setBackground(Color.LIGHT_GRAY);

	}

	public void setNumber(int count) {
		labelCountG.setText("Mines Left: " + count+"");
	}

	public void setTest(String text) {
		labelCountG.setText(text);
	}
	
	public class FaceLableListener extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent e) {
			if (e.getModifiers() == InputEvent.BUTTON1_MASK) {
//				mainFrame.getTimer().stop();
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
//			if (e.getModifiers() == InputEvent.BUTTON1_MASK) {
//				mainFrame.reStartGame();
//			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (e.getModifiers() == InputEvent.BUTTON1_MASK) {
//				mainFrame.getTimer().start();
			}
		}

	}

}
