package per.sunmes.pb.module;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import per.sunmes.pb.model.Bobble;
import per.sunmes.pb.model.IBobble;
import per.sunmes.pb.model.IKey;
import per.sunmes.pb.model.Scence;

public class GameManager implements IKeyTyped {
	private static GameManager instance = new GameManager();

	private List<Bobble> lockBobbles = new ArrayList<Bobble>();

	private GameRun gameRun;
	private boolean runing;

	private List<Color> colors = new ArrayList<Color>();

	public static final List<float[]> ballCenters;
	static {
		ballCenters = new ArrayList<float[]>();
		boolean y2;
		for (int y = 0; y < IBobble.yBallCount; y++) {
			y2 = y % 2 == 0;
			for (int x = 0; x < IBobble.xBallCount - (y2 ? 0 : 1); x++) {
				float[] coordinate = new float[] {
						IBobble.radius / 2 + x * IBobble.radius
								+ (y2 ? 0 : IBobble.radius / 2),
						IBobble.yRadius / 2 + y * IBobble.yRadius };
				ballCenters.add(coordinate);
			}
		}

		balls = new Bobble[ballCenters.size()];
	}

	private static final Bobble[] balls;

	public static void newGameStart() {
		instance._newGameStart();
	}

	public static void endGame() {
		instance._endGame();
	}

	private void _endGame() {
		if (gameRun != null && gameRun.isRuning()) {
			gameRun.setRuning(false);
			gameRun = null;
		}
		runing = false;
	}

	public static void addLockBobble(Bobble bobble) {
		instance._addLockBobble(bobble);
	}

	public static Bobble isLockedPosition(int x, int y) {
		return instance._isLockedPosition(x, y);
	}

	public static List<Bobble> getLockBobbles() {
		return instance.lockBobbles;
	}

	public static List<Color> getColors() {
		return instance.colors;
	}

	public Bobble _isLockedPosition(int x, int y) {
		int length;
		for (Bobble bobble : lockBobbles) {
			length = isHit((int) bobble.getX(), (int) bobble.getY(), x, y);
			if (length > 0) {
				return bobble;
			}
		}
		return null;
	}

	private int isHit(int x, int y, int x2, int y2) {
		return (IBobble.radius * IBobble.radius)
				- ((x - x2) * (x - x2) + (y - y2) * (y - y2));
	}

	private float distance(float x, float y, float x2, float y2) {
		return ((x - x2) * (x - x2) + (y - y2) * (y - y2));
	}

	/*
	 * private List<float[]> getPosition(float xo, float yo) {
	 * List<float[]> result = new ArrayList<float[]>();
	 * int y = getBoxYPosition(yo);
	 * int x = getBoxXPosition(xo);
	 * if (y % 2 == 0) {
	 * if (x == IBobble.xBallCount) {
	 * x--;
	 * }
	 * }
	 * 
	 * float[] fs = getPositionFormBall(x + x * y);
	 * if (fs != null) {
	 * result.add(fs);
	 * }
	 * fs = getPositionFormBall((x - 1) + (x - 1) * y);
	 * if (fs != null) {
	 * result.add(fs);
	 * }
	 * fs = getPositionFormBall((x - 1) + (x - 1) * (y - 1));
	 * if (fs != null) {
	 * result.add(fs);
	 * }
	 * fs = getPositionFormBall(x + x * (y - 1));
	 * if (fs != null) {
	 * result.add(fs);
	 * }
	 * fs = getPositionFormBall(x + 1 + (x + 1) * (y - 1));
	 * if (fs != null) {
	 * result.add(fs);
	 * }
	 * fs = getPositionFormBall(x + 1 + (x + 1) * (y + 1));
	 * if (fs != null) {
	 * result.add(fs);
	 * }
	 * fs = getPositionFormBall(x + x * (y + 1));
	 * if (fs != null) {
	 * result.add(fs);
	 * }
	 * 
	 * return result;
	 * }
	 * 
	 * private float[] getPositionFormBall(int p) {
	 * if (p >= 0 && p < ballCenters.size()) {
	 * return ballCenters.get(p);
	 * } else {
	 * return null;
	 * }
	 * }
	 */
	public void _addLockBobble(Bobble bobble) {
		lockBobbles.add(bobble);
		float xo = bobble.getX() + IBobble.radius / 2;
		float yo = bobble.getY() + IBobble.radius / 2;
		// List<float[]> positions = getPosition(xo, yo);
		float distance = 10000;
		int p = 0, i = 0;
		float cd;

		for (i = 0; i < ballCenters.size(); i++) {
			if (balls[i] != null) {
				continue;
			}
			float[] fs = ballCenters.get(i);
			cd = distance(xo, yo, fs[0], fs[1]);
			if (cd > 954) {
				continue;
			}
			// System.out.println("i[" + i + "]" + " cd: " + cd + " DI["
			// + distance + "]" + " p[" + p + "]");
			if (distance > Math.abs(cd)) {
				distance = Math.abs(cd);
				p = i;
			}
		}

		if (distance == 10000) {
			// gameOver;
			gameOver();
			return;
		}

		// System.out.println("distance:" + distance + "  P[" + p + "]");
		float[] coordinate = ballCenters.get(p);
		balls[p] = bobble;
		bobble.setPosition(p);
		bobble.setCoordinate(coordinate[0] - IBobble.radius / 2, coordinate[1]
				- IBobble.radius / 2);

		List<Bobble> sameColors = new ArrayList<Bobble>();
		getSameColor(sameColors, bobble);
		// System.out.println("SameColors:" + sameColors.size());

		if (sameColors.size() > 2) {
			for (Bobble ob : sameColors) {
				balls[ob.getPosition()] = null;
				lockBobbles.remove(ob);
				ob.disappear();
			}
		}

		if (lockBobbles.size() == 0) {
			gameWin();
			return;
		}
		//
		checkColor();
		gameRun.checkColor();

		if (lockBobbles.size() == 0) {
			gameWin();
		}
	}

	private void getSameColor(List<Bobble> result, Bobble b) {
		int p = b.getPosition();
		List<Bobble> bList = getAroundBobble(p);
		// System.out.println("p[" + p + "]  around[" + bList.size() + "]");
		for (Bobble bobble : bList) {
			if (bobble != null && bobble.getColor() == b.getColor()) {
				if (!result.contains(bobble)) {
					result.add(bobble);
					getSameColor(result, bobble);
				}
			}
		}
	}

	private void getLinkBobbles(List<Bobble> result, Bobble b) {
		int p = b.getPosition();
		List<Bobble> bList = getAroundBobble(p);
		for (Bobble bobble : bList) {
			if (bobble != null) {
				if (!result.contains(bobble)) {
					result.add(bobble);
					getLinkBobbles(result, bobble);
				}
			}
		}
	}

	private List<Bobble> getAroundBobble(int p) {
		int y = p / (IBobble.xBallCount * 2 - 1);
		y *= 2;
		int x = p % (IBobble.xBallCount * 2 - 1);
		if (x >= IBobble.xBallCount) {
			x -= IBobble.xBallCount;
			y++;
		}
		List<Bobble> result = new ArrayList<Bobble>();

		// System.out.println("p[" + p + "]" + "  xy[" + x + "-" + y + "]");

		if (y % 2 == 0) {

			if (x > 0) {
				Bobble lb = balls[p - 1];
				if (lb != null) {
					result.add(lb);
				}
				if (y > 0) {
					Bobble lub = balls[getPosition(x - 1, y - 1)];
					if (lub != null) {
						result.add(lub);
					}
				}
				if (y < IBobble.yBallCount - 1) {
					Bobble ldb = balls[getPosition(x - 1, y + 1)];
					if (ldb != null) {
						result.add(ldb);
					}
				}
			}

			if (x < IBobble.xBallCount - 1) {
				Bobble rb = balls[p + 1];
				if (rb != null) {
					result.add(rb);
				}
				if (y > 0) {
					Bobble ub = balls[getPosition(x, y - 1)];
					if (ub != null) {
						result.add(ub);
					}
				}
				if (y < IBobble.yBallCount - 1) {
					Bobble db = balls[getPosition(x, y + 1)];
					if (db != null) {
						result.add(db);
					}
				}
			}

		} else {

			if (x > 0) {
				Bobble lb = balls[p - 1];
				if (lb != null) {
					result.add(lb);
				}
			}
			if (y > 0) {
				Bobble ub = balls[getPosition(x, y - 1)];
				if (ub != null) {
					result.add(ub);
				}
			}
			if (y < IBobble.yBallCount - 1) {
				Bobble ldb = balls[getPosition(x, y + 1)];
				if (ldb != null) {
					result.add(ldb);
				}
			}

			if (x < IBobble.xBallCount - 2) {
				Bobble rb = balls[p + 1];
				if (rb != null) {
					result.add(rb);
				}
			}

			if (y > 0) {
				Bobble rub = balls[getPosition(x + 1, y - 1)];
				if (rub != null) {
					result.add(rub);
				}
			}
			if (y < IBobble.yBallCount - 1) {
				Bobble rdb = balls[getPosition(x + 1, y + 1)];
				if (rdb != null) {
					result.add(rdb);
				}
			}
		}

		return result;
	}

	public void gameOver() {
		lockBobbles.clear();
		GameSys.paintClear();
		GameSys.getPaint().addSpirit(Scence.GAME_OVER);
		_endGame();
	}

	public void gameWin() {
		lockBobbles.clear();
		GameSys.paintClear();
		GameSys.getPaint().addSpirit(Scence.GAME_WIN);
		_endGame();
	}

	public static int getBoxYPosition(float z) {
		float w = z / IBobble.yRadius;
		if (z % IBobble.yRadius > IBobble.yRadius / 2) {
			w++;
		}
		return (int) w;
	}

	public static int getBoxXPosition(float z) {
		float w = z / IBobble.radius;
		if (z % IBobble.radius > IBobble.radius / 2) {
			w++;
		}
		return (int) (w);
	}

	private void checkColor() {
		colors.clear();

		List<Bobble> linkBobbles = new ArrayList<Bobble>();
		for (int i = 0; i < IBobble.xBallCount; i++) {
			if (balls[i] != null) {
				getLinkBobbles(linkBobbles, balls[i]);
			}
		}

		Iterator<Bobble> iterator = lockBobbles.iterator();
		while (iterator.hasNext()) {
			Bobble b = iterator.next();
			if (!linkBobbles.contains(b)
					&& b.getPosition() >= IBobble.xBallCount) {
				b.DropDown();
				balls[b.getPosition()] = null;
				iterator.remove();
			}

			if (!colors.contains(b.getColor())) {
				colors.add(b.getColor());
			}
		}

	}

	private void _newGameStart() {
		for (int y = 0; y < 5; y++) {
			boolean sy = y % 2 == 0;
			for (int x = 0; x < IBobble.xBallCount - (sy ? 0 : 1); x++) {
				Bobble b = Bobble.valueOf(x * IBobble.radius
						+ (sy ? 0 : IBobble.radius / 2), y * IBobble.yRadius,
						GameRun.getRandomColor());
				GameSys.getPaint().addSpirit(b);
				lockBobbles.add(b);
				b.setPosition(getPosition(x, y));
				balls[getPosition(x, y)] = b;
			}
		}

		checkColor();

		gameRun = new GameRun();
		gameRun.start();
		runing = true;
	}

	private int getPosition(int x, int y) {
		int p = y * IBobble.xBallCount - y / 2;
		return p + x;
	}

	public static GameManager getInstance() {
		return instance;
	}

	@Override
	public void typed(int keyCode) {
		if (runing) {
			if (keyCode == IKey.shoot) {
				gameRun.shoot();
			}
		}
	}

}
