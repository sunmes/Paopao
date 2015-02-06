package per.sunmes.pb.module;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.VolatileImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;

import per.sunmes.pb.model.IBobble;
import per.sunmes.pb.model.Spirit;

public class Paint extends Thread {

	private Graphics g;
	private VolatileImage vImg;

	private FpsRecorder fps;

	private static final Color backgroundColor = new Color(23, 36, 56);

	private List<Spirit> spiritList = new ArrayList<Spirit>();

	public void clear() {
		synchronized (spiritList) {
			spiritList.clear();
		}
	}

	public Paint(JFrame jf, FpsRecorder fps) {
		this.g = jf.getGraphics();
		this.vImg = jf.createVolatileImage(IBobble.zoneWidth,
				IBobble.zoneHeight);
		this.fps = fps;
	}

	public void addSpirit(Spirit spirit) {
		synchronized (spiritList) {
			spiritList.add(spirit);
		}
	}

	@Override
	public void run() {
		Graphics vg = vImg.getGraphics();
		vg.setColor(Color.black);
		while (g != null) {
			// vg.clearRect(0, 0, vImg.getWidth(), vImg.getHeight());
			vg.setColor(backgroundColor);
			vg.fillRect(0, 0, vImg.getWidth(), vImg.getHeight());

			if (fps != null) {
				fps.countIncrease();
				// vg.drawString("FPS:" + fps.getFps(), 210, 10);
			}
			// paint to vImg
			synchronized (spiritList) {
				Iterator<Spirit> iterator = spiritList.iterator();
				while (iterator.hasNext()) {
					Spirit spirit = iterator.next();
					if (spirit.isDisappear()) {
						iterator.remove();
					} else {
						spirit.draw(vg);
					}
				}
			}
			//
			//
			g.drawImage(vImg, IBobble.windowOffx, IBobble.windowOffy, null);
		}
	}

}
