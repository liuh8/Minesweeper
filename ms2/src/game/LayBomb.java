package game;


import java.util.Random;


public class LayBomb {
	public static void lay(MineLable[][] lable, int row, int col) {

		int count = 0;
		Random random = new Random();
		while (count < DataController.allcount) {
			int x = random.nextInt(DataController.allrow);
			int y = random.nextInt(DataController.allcol);
			if (lable[x][y].isMineTag() == false && !(x == row && y == col)) {
				lable[x][y].setMineTag(true);
				lable[x][y].setCounAround(9);
				if (DataController.isHole == true) {
					lable[x][y].setIcon(DataController.holeIcon);
				}
				count++;
			}
		}
		computeBomb(lable);
	}

	public static void computeBomb(MineLable lable[][]) {

		for (int i = 0; i < lable.length; i++) {
			for (int j = 0; j < lable[i].length; j++) {
				if (lable[i][j].isMineTag() == false) {
					int count = 0;
					for (int x = Math.max(0, i - 1); x <= Math.min(
							DataController.allrow - 1, i + 1); x++) {
						for (int y = Math.max(0, j - 1); y <= Math.min(
								DataController.allcol - 1, j + 1); y++) {
							if (lable[x][y].isMineTag() == true) {
								count++;
							}
						}
					}
					lable[i][j].setCounAround(count);
				}
			}
		}
	}
}
