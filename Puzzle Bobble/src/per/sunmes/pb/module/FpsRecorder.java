package per.sunmes.pb.module;

import javax.swing.JFrame;

public class FpsRecorder extends Thread {

	private int fps;
	private int count;

	private JFrame jf;
	private String title;

	public FpsRecorder(JFrame jf) {
		this.jf = jf;
		title = jf.getTitle();
		this.start();
	}

	@Override
	public void run() {
		while (true) {
			fps = count;
			count = 0;

			if (jf != null) {
				jf.setTitle(title + " -FPS[" + fps + "]");
			}
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void countIncrease() {
		++count;
	}
}
