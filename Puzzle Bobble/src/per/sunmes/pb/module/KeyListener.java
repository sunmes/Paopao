package per.sunmes.pb.module;

import java.awt.event.KeyEvent;

public class KeyListener implements java.awt.event.KeyListener {
	private static final boolean keys[] = new boolean[200];

	private IKeyTyped iKey;

	public KeyListener(IKeyTyped iKey) {
		this.iKey = iKey;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		iKey.typed(e.getKeyCode());
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
		// System.out.println("Press key:" + e.getKeyChar() + " code:"
		// + e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	public boolean getKey(int keyCode) {
		return keys[keyCode];
	}
}
