package game;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserDefinedListener implements ActionListener {
	UserDefinedJDialog userDefinedJDialog;

	MainFrame mainFrame;

	public UserDefinedListener(UserDefinedJDialog userDefinedJDialog,
			MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		this.userDefinedJDialog = userDefinedJDialog;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == userDefinedJDialog.getButtonCancer()) {
			userDefinedJDialog.dispose();
			mainFrame.reStartGame();
		} else if (e.getSource() == userDefinedJDialog.getButtonSure()) {
			String highT = userDefinedJDialog.getjTextFieldHigh().getText();
			Pattern pattern = Pattern.compile("^[0-9]{1,3}$");
			Matcher matcher = pattern.matcher(highT);
			int row = 0;
			if (!matcher.matches()) {
				userDefinedJDialog.getjLabelMessage()
						.setText("");
				return;
			} else {
				row = Integer.parseInt(highT);
				if (row < 9 || row > 30) {
					userDefinedJDialog.getjLabelMessage().setText(
							"should be in range of 9-30");
					return;
				}

			}
			String colT = userDefinedJDialog.getjTextFieldWide().getText();
			int col = 0;
			try {
				col = Integer.parseInt(colT);
				if (col < 9 || col > 30) {
					userDefinedJDialog.getjLabelMessage().setText(
							"should be in range of 9-30");
					return;
				}
			} catch (Exception e2) {
				userDefinedJDialog.getjLabelMessage().setText(
						"should be in range of 9-30");
				return;
			}

			String mineT = userDefinedJDialog.getjTextFieldBomb().getText();
			int mine = 0;
			try {
				mine = Integer.parseInt(mineT);
				if (mine < 10) {
					mine = 10;
				} else {
					mine = Math.min(mine, DataController.allrow * DataController.allcol * 4 / 5);
				}
			} catch (Exception e3) {
				userDefinedJDialog.getjLabelMessage().setText("Number of mines problem");
				return;
			}
			userDefinedJDialog.dispose();
			DataController.allrow = row;
			DataController.allcol = col;
			DataController.allcount = mine;
			System.out.println(mine);

			mainFrame.reStartGame();
		}

	}

}
