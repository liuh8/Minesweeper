package game;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BombJMenuBar menuBar = new BombJMenuBar(this);

	private MineCountsJPanel minescountJPanel = new MineCountsJPanel(this);

	private BombJPanel bombJPanel = new BombJPanel(this);

//	private TimerListener timerListener = new TimerListener(this);
//
//	private Timer timer = new Timer(1000, timerListener);

	public MainFrame() {
		init();

//		this.setIconImage(StaticTool.imageIcon.getImage());
		this.setTitle("Minesweeper");
		this.setSize(new Dimension(220, 300));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		this.addWindowListener(new WindowAdapter() {
//			  public void windowClosing(WindowEvent e) {
//			    int confirmed = JOptionPane.showConfirmDialog(null, 
//			        "Exiting the program? Your game progress will not be saved", "Exit Game Message",
//			        JOptionPane.YES_NO_OPTION);
//
//			    if (confirmed == JOptionPane.YES_OPTION) {
//			    	dispose();
//			    } 
//			    if (confirmed == JOptionPane.NO_OPTION) {
//			    	System.out.println("cancel");
//			    	setVisible(false);
//				}
//			  }
//			});
		this.setVisible(true);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.pack();
		this.setVisible(true);

	}

	private void init() {
		this.setJMenuBar(menuBar);
		this.add(minescountJPanel, BorderLayout.NORTH);
		this.add(bombJPanel);

	}

	public void reStartGame() {

		this.remove(minescountJPanel);
		this.remove(bombJPanel);

		DataController.bombCount = DataController.allcount;
		DataController.timecount = 0;
		DataController.winCount = 0;
		DataController.blankCount = 0;
		DataController.isStart = false;

		minescountJPanel = new MineCountsJPanel(this);
		bombJPanel = new BombJPanel(this);
		this.add(minescountJPanel, BorderLayout.NORTH);
		this.add(bombJPanel);
		this.pack();
		this.validate();

//		getTimer().stop();

	}

	public MineCountsJPanel getMinesJPanel() {
		return minescountJPanel;
	}

	public BombJPanel getBombJPanel() {
		return bombJPanel;
	}
//
//	public Timer getTimer() {
//		return timer;
//	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		new MainFrame();

	}

}
