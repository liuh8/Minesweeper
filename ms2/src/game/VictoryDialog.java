package game;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class VictoryDialog extends JDialog {
	private JPanel panel = null;
	JTextArea textArea = new JTextArea();
	private int level = 0;

	public VictoryDialog(int level, MainFrame mainFrame) {
		super(mainFrame);
		this.level = level;
		this.setTitle("ɨ��Ӣ�۰�");
		this.add(getPanel());
		this.setSize(new Dimension(220, 150));
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setModal(true);
		this.setVisible(true);
	}

	public JPanel getPanel() {
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.append("Congrats! You Win!!\n");
		JScrollPane scrollPane = new JScrollPane(textArea);
		panel = new JPanel(new BorderLayout());
		panel.add(scrollPane);
		return panel;
	}

}
