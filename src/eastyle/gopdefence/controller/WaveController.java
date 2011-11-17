package eastyle.gopdefence.controller;

public class WaveController {
	public WaveController() {
		Thread tt = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					TargetController.checkTargets();
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		tt.start();
	}
}
