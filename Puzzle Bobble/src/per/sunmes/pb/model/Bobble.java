package per.sunmes.pb.model;

import java.awt.Color;
import java.awt.Graphics;

import per.sunmes.pb.module.GameManager;

public class Bobble implements Spirit {

	private int position;
	private float x;
	private float y;
	private Color color;

	private float offX;
	private float offY;

	private boolean flying;

	private boolean dropDown;
	private boolean _disappear;

	private boolean disappear;

	private int endAnimate = 1;
	private float v = 0.001f;

	@Override
	public String toString() {
		return "{Bobble [ p(" + position + ") ]}";
	}

	public static Bobble valueOf(float x, float y, Color color) {
		Bobble b = new Bobble();
		b.setColor(color);
		b.setCoordinate(x, y);
		return b;
	}

	public void disappear() {
		this._disappear = true;
	}

	public void DropDown() {
		this.dropDown = true;
	}

	public void setOff(float offX, float offY) {
		this.offX = offX;
		this.offY = offY;
	}

	public void fly() {
		int count = IBobble.speed;
		while (count-- > 0) {
			if (isFlying()) {
				if (this.x == (IBobble.zoneWidth - IBobble.radius)) {
					offX = 0 - offX;
				}
				if (this.x == 0) {
					offX = 0 - offX;
				}

				this.x += offX;
				this.y += offY;

				// this.pY = GameManager.getBoxYPosition(this.y);
				// this.pX = GameManager.getBoxXPosition(this.x);
				Bobble c = GameManager.isLockedPosition((int) x, (int) y);
				if (c != null) {
					flying = false;
				}

				if (this.x > (IBobble.zoneWidth - IBobble.radius)) {
					this.x = IBobble.zoneWidth - IBobble.radius;
				}
				if (this.x < 0) {
					this.x = 0;
				}

				if (this.y < 0) {
					this.y = 0;
					flying = false;
				}
			}
		}
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public void setCoordinate(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public void draw(Graphics g) {

		g.setColor(color);
		g.fillOval((int) x, (int) y, IBobble.radius, IBobble.radius);
		g.setColor(Color.darkGray);
		g.fillOval((int) x + 5, (int) y + 5, IBobble.radius - 5,
				IBobble.radius - 5);
		g.setColor(color);
		g.fillOval((int) x + 3, (int) y + 3, IBobble.radius - 5,
				IBobble.radius - 5);
		g.setColor(Color.white);
		g.fillOval((int) x + 3, (int) y + 3, IBobble.radius / 5,
				IBobble.radius / 5);

		if (_disappear) {
			g.setColor(Color.black);
			g.fillOval((int) x + IBobble.radius - endAnimate, (int) y
					+ IBobble.radius - endAnimate, endAnimate, endAnimate);

			endAnimate += v;
			v *= 1.05;
			if (endAnimate >= IBobble.radius) {
				endAnimate = -1;
			}
		} else if (dropDown) {
			y += v;
			v *= 1.02;
			if (y > IBobble.zoneHeight) {
				endAnimate = -1;
			}
		}

		if (endAnimate < 0) {
			this.disappear = true;
		}
	}

	@Override
	public boolean isDisappear() {
		return disappear;
	}

	public boolean isFlying() {
		return flying;
	}

	public void setFlying(boolean flying) {
		this.flying = flying;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

}
