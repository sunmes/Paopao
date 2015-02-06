package per.sunmes.pb.module;

import java.awt.Toolkit;

import javax.swing.JFrame;

import per.sunmes.pb.model.IBobble;

public class GameWindow {
	private static JFrame window = null;

	public static JFrame creatWindow() {
		if (window == null) {
			window = new JFrame(IBobble.gameName);
			int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
			int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
			window.setBounds((screenWidth - IBobble.windowWidth) / 2,
					(screenHeight - IBobble.windowHeight) / 2,
					IBobble.windowWidth, IBobble.windowHeight);
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		return window;
	}

}
