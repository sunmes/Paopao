package per.sunmes.pb.model;

import java.awt.Color;
import java.awt.Graphics;

import per.sunmes.pb.module.GameManager;

public class GuideLine implements Spirit {

	@Override
	public boolean isDisappear() {
		return false;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		for (int x = 0; x < IBobble.zoneWidth; x += IBobble.radius) {
			for (int y = 0; y < IBobble.zoneWidth; y += IBobble.yRadius) {
				if ((y / IBobble.yRadius) % 2 != 0) {
					if (x >= IBobble.zoneWidth - IBobble.radius) {
						continue;
					}
					g.drawOval(x + IBobble.radius / 2, y, IBobble.radius,
							IBobble.radius);
				} else {
					g.drawOval(x, y, IBobble.radius, IBobble.radius);
				}
			}
		}

		int i = 0;
		for (float[] f : GameManager.ballCenters) {
			g.drawString(String.valueOf(i++), (int) f[0] - 5, (int) f[1] + 5);
		}

	}
}
