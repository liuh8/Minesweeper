package game;


import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

public class Listener implements MouseListener {
	MineLable[][] mineLable;
	MainFrame mainFrame;
	private boolean isDoublePress = false;
	
	public Listener(MineLable[][] mineLable, MainFrame mainFrame) {
		this.mineLable = mineLable;
		this.mainFrame = mainFrame;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		MineLable mineLable = (MineLable) e.getSource();

		int row = mineLable.getRowx();
		int col = mineLable.getColy();

		if (e.getModifiersEx() == InputEvent.BUTTON1_DOWN_MASK
				+ InputEvent.BUTTON3_DOWN_MASK) {
			isDoublePress = true;
			doublePress(row, col);

		} else if (e.getModifiers() == InputEvent.BUTTON1_MASK
				&& mineLable.isFlagTag() == false) {
			if (mineLable.isExpendTag() == false) {
				mineLable.setIcon(DataController.icon0);
				mineLable.setCleaned();
			}

		} else if (e.getModifiers() == InputEvent.BUTTON3_MASK
				&& mineLable.isExpendTag() == false) {
			if (mineLable.getRightClickCount() == 0) {
				mineLable.setIcon(DataController.flagIcon);
				mineLable.setRightClickCount(1);
				mineLable.setFlagTag(true);
				if(mineLable.isMineTag()) {
					DataController.winCount++;
				}
				DataController.bombCount--;
				mainFrame.getMinesJPanel().setNumber(DataController.bombCount);

			} else if (mineLable.getRightClickCount() == 1) {
				mineLable.setIcon(DataController.askIcon);
				mineLable.setRightClickCount(2);
				mineLable.setFlagTag(false);
				if(mineLable.isMineTag()) {
					DataController.winCount--;
				}
				DataController.bombCount++;
				mainFrame.getMinesJPanel().setNumber(DataController.bombCount);

			} else {
				mineLable.setIcon(DataController.iconBlank);
				mineLable.setRightClickCount(0);
			}

		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {

		MineLable mineLable = (MineLable) e.getSource();
		int row = mineLable.getRowx();
		int col = mineLable.getColy();
		if (isDoublePress) {
			isDoublePress = false;
			if (mineLable.isExpendTag() == false
					&& mineLable.isFlagTag() == false) {
				backIcon(row, col);
			} else {

				boolean isEquals = isEquals(row, col);
				if (isEquals) {
					doubleExpend(row, col);
				} else {
					backIcon(row, col);
				}
			}
		} else if (e.getModifiers() == InputEvent.BUTTON1_MASK
				&& mineLable.isFlagTag() == false) {
			if (DataController.isStart == false) {
				LayBomb.lay(this.mineLable, row, col);

				DataController.isStart = true;

			}
//			mainFrame.getTimer().start();

			if (mineLable.isMineTag() == true) {
				bombAction(row, col);
				mineLable.setIcon(DataController.bloodIcon);
				mainFrame.getMinesJPanel().setTest("You lost!");
				int input = JOptionPane.showOptionDialog(mainFrame, "You Lost!! Try again!", "Sorry!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

				if(input == JOptionPane.OK_OPTION)
				{
					mainFrame.reStartGame();
				}
			} else {
				expand(row, col);

			}

		}
//		degrade_mode();
		isWin();
	}

	private void bombAction(int row, int col) {

		for (int i = 0; i < mineLable.length; i++) {
			for (int j = 0; j < mineLable[i].length; j++) {
				if (mineLable[i][j].isMineTag()) {
					if (mineLable[i][j].isFlagTag() == false) {
						mineLable[i][j].setIcon(DataController.blackBombIcon);
					}
				} else {
					if (mineLable[i][j].isFlagTag()) {
						mineLable[i][j].setIcon(DataController.errorBombIcon);
					}
				}
			}

		}

//		mainFrame.getTimer().stop();

		for (int i = 0; i < mineLable.length; i++) {
			for (int j = 0; j < mineLable[i].length; j++) {
				mineLable[i][j].removeMouseListener(this);
			}
		}
	}

	private void expand(int x, int y) {

		int count = mineLable[x][y].getCounAround();

		if (mineLable[x][y].isExpendTag() == false
				&& mineLable[x][y].isFlagTag() == false) {

			if (count == 0) {
				mineLable[x][y].setIcon(DataController.num[count]);
				mineLable[x][y].setExpendTag(true);
				for (int i = Math.max(0, x - 1); i <= Math.min(mineLable.length - 1, x + 1); i++) {
					for (int j = Math.max(0, y - 1); j <= Math.min(mineLable[x].length - 1, y + 1); j++) {
						expand(i, j);
					}
				}

			} else {
				mineLable[x][y].setIcon(DataController.num[count]);
				mineLable[x][y].setExpendTag(true);
			}

		}

	}

	private void backIcon(int i, int j) {
		for (int x = Math.max(0, i - 1); x <= Math.min(DataController.allrow - 1,
				i + 1); x++) {
			for (int y = Math.max(0, j - 1); y <= Math.min(
					DataController.allcol - 1, j + 1); y++) {
				if (mineLable[x][y].isFlagTag() == false
						&& mineLable[x][y].isExpendTag() == false) {
					int rightClickCount = mineLable[x][y].getRightClickCount();
					if (rightClickCount == 2) {
						mineLable[x][y].setIcon(DataController.askIcon);
					} else {
						mineLable[x][y].setIcon(DataController.iconBlank);

					}
				}
			}
		}

	}

	private boolean isEquals(int i, int j) {
		int count = mineLable[i][j].getCounAround();
		int flagCount = 0;
		for (int x = Math.max(0, i - 1); x <= Math.min(DataController.allrow - 1,
				i + 1); x++) {
			for (int y = Math.max(0, j - 1); y <= Math.min(
					DataController.allcol - 1, j + 1); y++) {
				if (mineLable[x][y].isFlagTag()) {
					flagCount++;
				}
			}
		}
		if (count == flagCount) {
			return true;
		}
		return false;
	}

	private void doublePress(int i, int j) {
		for (int x = Math.max(0, i - 1); x <= Math.min(DataController.allrow - 1,
				i + 1); x++) {
			for (int y = Math.max(0, j - 1); y <= Math.min(
					DataController.allcol - 1, j + 1); y++) {
				if (mineLable[x][y].isExpendTag() == false
						&& mineLable[x][y].isFlagTag() == false) {
					int rightClickCount = mineLable[x][y].getRightClickCount();
					if (rightClickCount == 1) {
						mineLable[x][y].setIcon(DataController.askPressIcon);

					} else {
						mineLable[x][y].setIcon(DataController.icon0);
						mineLable[x][y].setCleaned();
					}
				}
			}
		}
	}

	private void doubleExpend(int i, int j) {
		for (int x = Math.max(0, i - 1); x <= Math.min(DataController.allrow - 1,
				i + 1); x++) {
			for (int y = Math.max(0, j - 1); y <= Math.min(
					DataController.allcol - 1, j + 1); y++) {
				if (mineLable[x][y].isMineTag()) {
					if (mineLable[x][y].isFlagTag() == false) {
						bombAction(x, y);

					}
				} else {

					if (mineLable[x][y].isFlagTag() == false) {
						expand(x, y);
					}

				}

			}
		}

	}

	private void isWin() {

		int needCount = DataController.allrow * DataController.allcol
				- DataController.allcount;
		int expendCount = 0;
		for (int i = 0; i < mineLable.length; i++) {
			for (int j = 0; j < mineLable[i].length; j++) {
				if (mineLable[i][j].isExpendTag()) {
					expendCount++;
				}

			}

		}
		if (DataController.winCount == DataController.allcount || needCount == expendCount) {
			mainFrame.getMinesJPanel().setNumber(0);
			for (int i = 0; i < mineLable.length; i++) {
				for (int j = 0; j < mineLable[i].length; j++) {
					mineLable[i][j].removeMouseListener(this);
				}
			}
			mainFrame.getMinesJPanel().setTest("You Win!");
			int input = JOptionPane.showOptionDialog(mainFrame, "You Win!!", "Congrats!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

			if(input == JOptionPane.OK_OPTION)
			{
				mainFrame.reStartGame();
			}
		}

	}
	
	private void degrade_mode() {
		int[][] board = to2dInt(this.mineLable);
		
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board.length; j++){
				builder.append(board[i][j]+"");
				if(j < board.length - 1) builder.append(",");
			}
			builder.append("\n");
		}
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter("./previous_step.txt"));
			writer.write(builder.toString());//save the string representation of the board
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private int[][] to2dInt(MineLable[][] m){
		int[][] toreturn = new int[m.length][m[0].length];
		for(int i=0; i<m.length; i++) {
			for (int j=0; j<m[i].length; j++) {
				if (m[i][j].iscleaned()) {
					toreturn[i][j] = 0; //cleaned
				} else if (m[i][j].isFlagTag()) {
					toreturn[i][j] = 1; //flagged
				} else if (m[i][j].isMineTag() && !m[i][j].isFlagTag()) {
					toreturn[i][j] = 2; //mine but not flagged
				} else {
					toreturn[i][j] = 3; //not mine not cleaned not flagged
				}
			}
		}
		return toreturn;
	}
	
	private int[][] readBack(){
		String savedGameFile = "./previous_step.txt";
		int[][] board = new int[9][9];
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(savedGameFile));
			String line = "";
			int row = 0;
			try {
				while((line = reader.readLine()) != null){
				   String[] cols = line.split(","); //note that if you have used space as separator you have to split on " "
				   int col = 0;
				   for(String  c : cols){
				      board[row][col] = Integer.parseInt(c);
				      col++;
				   }
				   row++;
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return board;
	}
}
