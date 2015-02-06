package per.sunmes.pb.model;

import java.awt.Color;
import java.awt.Graphics;

public class Bow implements Spirit {

	private float angle = 90;
	private static final int length = 50;

	private int[] xsl = new int[] { IBow.ox - 10, IBow.ox, 0, IBow.ox - 10 };
	private int[] xsr = new int[] { IBow.ox, IBow.ox + 10, 0, IBow.ox };
	private int[] ys = new int[] { IBow.oy, IBow.oy, 0, IBow.oy };

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.lightGray);
		int x2 = IBow.ox + (int) (length * getOffX());
		int y2 = IBow.oy - (int) (length * getOffY());

		g.drawLine(IBow.ox, IBow.oy, x2, y2);
		g.drawLine(IBow.ox - 10, IBow.oy, x2, y2);
		g.drawLine(IBow.ox + 10, IBow.oy, x2, y2);

		xsl[2] = x2;
		xsr[2] = x2;
		ys[2] = y2;

		if (angle <= 90) {
			g.fillPolygon(xsr, ys, xsr.length);
			g.setColor(Color.white);
			g.fillPolygon(xsl, ys, xsl.length);
		} else {
			g.fillPolygon(xsl, ys, xsl.length);
			g.setColor(Color.white);
			g.fillPolygon(xsr, ys, xsr.length);

		}

		// System.out.println(x2 + "--" + y2 + " / " + angle);
	}

	@Override
	public boolean isDisappear() {
		return false;
	}

	public void turnLeft() {
		if (angle < 179) {
			this.angle += 1;
		}
	}

	public void turnRight() {
		if (angle > 1) {
			this.angle -= 1;
		}
	}

	public float getOffX() {
		return (float) Math.cos(angle * (Math.PI / 180));
	}

	public float getOffY() {
		return (float) Math.sin(angle * (Math.PI / 180));
	}
}
