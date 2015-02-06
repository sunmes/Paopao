package per.sunmes.pb.module;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import per.sunmes.pb.model.Bobble;
import per.sunmes.pb.model.Bow;
import per.sunmes.pb.model.IBobble;
import per.sunmes.pb.model.IGame;
import per.sunmes.pb.model.IKey;

public class GameRun extends Thread {

	private boolean isRuning = true;

	private Bobble nextBobble;
	private Bobble currentBobble;

	private Bow bow = new Bow();
	private List<Bobble> flyingBobble = new ArrayList<Bobble>();

	private long inputTime;
	private long actionTime;

	@Override
	public void run() {
		GameSys.getPaint().addSpirit(bow);
		creatNextBobble();
		long now;
		while (isRuning) {
			now = System.currentTimeMillis();
			if (nextBobble == null) {
				creatNextBobble();
			}
			if (currentBobble == null) {
				currentBobble = nextBobble;
				currentBobble.setCoordinate(IBobble.zoneWidth / 2
						- IBobble.radius / 2, 435);
				creatNextBobble();
			}
			if (now - actionTime > IGame.delay) {
				synchronized (flyingBobble) {
					Iterator<Bobble> iterator = flyingBobble.iterator();
					while (iterator.hasNext()) {
						Bobble bobble = iterator.next();
						if (bobble.isFlying()) {
							bobble.fly();
						} else {
							iterator.remove();
							GameManager.addLockBobble(bobble);
						}
					}
				}
				actionTime = now;
			}

			if (now - inputTime > IKey.delay) {
				if (GameSys.getKey(IKey.left)) {
					bow.turnLeft();
				}
				if (GameSys.getKey(IKey.right)) {
					bow.turnRight();
				}
				inputTime = now;
			}
		}
	}

	public void checkColor() {
		if (!GameManager.getColors().contains(currentBobble.getColor())) {
			currentBobble.setColor(getRandomColorFromLeft());
		}
		if (!GameManager.getColors().contains(nextBobble.getColor())) {
			nextBobble.setColor(getRandomColorFromLeft());
		}
	}

	public void shoot() {
		// if (flyingBobble.isEmpty()) {
		if (currentBobble != null) {
			currentBobble.setOff(bow.getOffX(), 0 - bow.getOffY());
			currentBobble.setFlying(true);
			synchronized (flyingBobble) {
				flyingBobble.add(currentBobble);
			}
			currentBobble = null;
		}
		// }
	}

	private Color getRandomColorFromLeft() {
		return GameManager.getColors().get(
				(int) (Math.random() * GameManager.getColors().size()));
	}

	private void creatNextBobble() {
		nextBobble = Bobble.valueOf(250, 435, getRandomColorFromLeft());
		GameSys.getPaint().addSpirit(nextBobble);
	}

	public static Color getRandomColor() {
		int c = (int) (Math.random() * 6);
		switch (c) {
		case 0:
			return Color.red;
		case 1:
			return Color.green;
		case 2:
			return Color.orange;
		case 3:
			return Color.blue;
		case 4:
			return Color.cyan;
		case 5:
			return Color.pink;
		}
		return Color.pink;// something wrong..
	}

	public void setRuning(boolean isRuning) {
		this.isRuning = isRuning;
	}

	public boolean isRuning() {
		return isRuning;
	}
}
