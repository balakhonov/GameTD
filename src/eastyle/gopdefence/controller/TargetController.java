package eastyle.gopdefence.controller;

import org.anddev.andengine.entity.IEntity;
import android.util.Log;
import eastyle.gopdefence.GameActivity;
import eastyle.gopdefence.logic.Target;
import eastyle.gopdefence.logic.Tower;
import eastyle.gopdefence.maps.FirstMap;
import eastyle.gopdefence.view.GameZone;
import eastyle.gopdefence.view.GoldView;

public class TargetController {
	private static int waveLevel = 0;
	private static Thread runTargertThread;
	public static int targetsCount;
	private static Object[] targetInfo;
	public static int difficult = 1;

	/**
	 * Send new targets wave
	 */
	public static void sendNewWave() {
		GoldView.calculateGold();
		GameActivity.globalScene.unregisterTouchArea(GameZone.gameMap);
		// get info about map
		targetInfo = FirstMap.getTestTargetProperties()[waveLevel];
		// Get count of targets
		targetsCount = (Integer) targetInfo[4];

		/* Register Targets in ArrayList */
		for (int count = 0; count < targetsCount; count++) {
			Target target = new Target(targetInfo);
			GameZone.addChild(target);
			GameZone.globalTargets.add(target);
			GameZone.globalScene.registerTouchArea(target);
		}
		GameActivity.globalScene.registerTouchArea(GameZone.gameMap);

		/* Send Targets */
		sendTargets();
	}

	/**
	 * 
	 */
	private static void sendTargets() {
		runTargertThread = new Thread(new Runnable() {
			public void run() {
				for (int i = 0; i < (Integer) targetInfo[4]; i++) {
					if (GameZone.isDestroy)
						break;
					GameZone.isPause();
					sendTarget(i);
				}
			}
		});
		runTargertThread.start();
	}

	/**
	 * @param targetsCount
	 */
	private static void sendTarget(int i) {
		try {
			GameZone.globalTargets.get(i).go();
			int sleepStep = 0;
			while (sleepStep < ((Integer) targetInfo[5])) {
				GameZone.isPause();
				sleepStep += 5 * GameZone.gameSpeed;
				Thread.sleep(5);
				// Log.w("sendTarget", sleepStep + "|"
				// + ((Integer) targetInfo[5] / GameZone.gameSpeed) + "  "
				// + 100 * sleepStep
				// / ((Integer) targetInfo[5] / GameZone.gameSpeed) + "%");
				// FIXME recount for stepsleep
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reset Wave Level when the map restarted
	 */
	public static void resetWaveLevel() {
		Log.i("resetWaveLevel", "resetWaveLevel()");
		waveLevel = 0;
	}

	/**
	 * 
	 */
	public static void checkTargets() {
		if (targetsCount == 0) {

//			try {
//				Thread.sleep(5000);
//			} catch (InterruptedException e1) {
//				e1.printStackTrace();
//			}

//			for (Tower tower : GameZone.globalTowers) {
//				while (tower.isTargetCaptured()) {
//					try {
//						Thread.sleep(100);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//			}

			GameZone.globalTargets.clear();
			for (Target target : GameZone.globalTargets) {
				GameZone.gameMap.detachChild(target);
			}
			GameZone.globalProjectile.clear();
			for (IEntity projectile : GameZone.globalProjectile) {
				GameZone.gameMap.detachChild(projectile);
			}
			waveLevel++;
			if (waveLevel < FirstMap.getTestTargetProperties().length) {
				sendNewWave();
			} else {
				waveLevel = 0;
				difficult++;
				sendNewWave();
				// TODO Show message "You win!";
				// ...
				// end
			}
		}
	}
}
