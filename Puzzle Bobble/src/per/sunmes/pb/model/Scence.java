package per.sunmes.pb.model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public enum Scence implements Spirit {
	GAME_OVER {
		float i = 2;

		@Override
		public void draw(Graphics g) {
			g.setColor(Color.black);
			g.fillRect(0, 0, IBobble.zoneWidth, IBobble.zoneHeight);
			g.setColor(Color.white);
			Font f = g.getFont().deriveFont(Font.BOLD, i);
			g.setFont(f);
			g.drawString("GameOver", 52, 250);
			if (i < 40) {
				i += 0.1;
			}
		}

		@Override
		public boolean isDisappear() {
			// TODO 自动生成的方法存根
			return false;
		}
	},
	GAME_WIN {

		@Override
		public void draw(Graphics g) {
			g.setColor(Color.black);
			g.fillRect(0, 0, IBobble.zoneWidth, IBobble.zoneHeight);
			g.setColor(Color.white);
			Font f = g.getFont().deriveFont(Font.BOLD, 40);
			g.setFont(f);
			g.drawString("GameWin", 60, 250);
		}

		@Override
		public boolean isDisappear() {
			// TODO 自动生成的方法存根
			return false;
		}
	},
	;

	@Override
	public abstract void draw(Graphics g);

	@Override
	public abstract boolean isDisappear();
}
