package per.sunmes.pb.module;

import javax.swing.JFrame;

public class GameSys {
	private static GameSys instance = new GameSys();

	private Paint paint;
	private KeyListener keyl = new KeyListener(GameManager.getInstance());

	private GameSys() {
	}

	public static void startGame() {
		instance.initialize();
	}

	private void initialize() {
		JFrame jf = GameWindow.creatWindow();
		jf.setVisible(true);
		jf.addKeyListener(keyl);

		paint = new Paint(jf, new FpsRecorder(jf));
		paint.start();

		GameManager.newGameStart();

//		paint.addSpirit(new GuideLine());
	}

	public static void paintClear() {
		instance.paint.clear();
	}

	public static Paint getPaint() {
		return instance.paint;
	}

	public static boolean getKey(int keyCode) {
		return instance.keyl.getKey(keyCode);
	}
}
