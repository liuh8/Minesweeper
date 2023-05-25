package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class BombJMenuBar extends JMenuBar {
	/**
	 * 
	 */
	JMenu menuGame = new JMenu("Game Settings");

	JMenu menuHelp = new JMenu("Hint");

	JMenuItem menuItemStart = new JMenuItem("Start");

	JMenuItem menuItemC = new JMenuItem("Easy");
	JMenuItem menuItemZ = new JMenuItem("Intermediate");
	JMenuItem menuItemG = new JMenuItem("Expert");

	JMenuItem menuItemCustom = new JMenuItem("Custome Game");

	JMenuItem menuItemHole = new JMenuItem("HELP!");

	MainFrame mainFrame;

	public BombJMenuBar(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		init();
	}

	private void init() {
		menuGame.add(menuItemStart);
		menuItemStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.reStartGame();
			}
		});

		menuGame.addSeparator();

		menuGame.add(menuItemC);
		menuItemC.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DataController.allrow = 9;
				DataController.allcol = 9;
				DataController.allcount = 10;
				mainFrame.reStartGame();
			}
		});

		menuGame.add(menuItemZ);
		menuItemZ.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DataController.allrow = 16;
				DataController.allcol = 16;
				DataController.allcount = 40;
				mainFrame.reStartGame();
			}
		});

		menuGame.add(menuItemG);
		menuItemG.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DataController.allrow = 16;
				DataController.allcol = 30;
				DataController.allcount = 99;
				mainFrame.reStartGame();
			}
		});
		menuGame.addSeparator();
		menuGame.add(menuItemCustom);
		menuItemCustom.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new UserDefinedJDialog(mainFrame);

			}
		});

		menuHelp.add(menuItemHole);
		menuItemHole.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MineLable[][] mineLable = mainFrame.getBombJPanel().labels;
				int cols[] = new int[DataController.allcount-DataController.winCount];
				int rows[] = new int[DataController.allcount-DataController.winCount];
//				System.out.println("length="+cols.length);
				for (int i = 0; i < cols.length; i++) {
		            cols[i]=0;
		        }
				for (int i = 0; i < rows.length; i++) {
					rows[i]=0;
		        }
//				System.out.println(DataController.allcount);
				int c = 0;
				for (int i = 0; i < mineLable.length; i++) {
					for (int j = 0; j < mineLable[i].length; j++) {
						if (mineLable[i][j].isMineTag() && mineLable[i][j].isFlagTag() == false) {
//							mineLable[i][j].setIcon(DataController.holeIcon);
							cols[c] = i;
							rows[c] = j;
//							System.out.println("i" + i);
//							System.out.println("j" + j);
							c++;
						}
					}
				}
//				System.out.println(cols.toString());
//				System.out.println(rows.toString());
				int rnd = new Random().nextInt(cols.length);
				mineLable[cols[rnd]][rows[rnd]].setIcon(DataController.holeIcon);
//				if(DataController.isHole == false) {
//					DataController.isHole = true;
//				} else {
//					DataController.isHole = false;
//				}

			}
		});
		
		this.add(menuGame);
		this.add(menuHelp);

	}

}
