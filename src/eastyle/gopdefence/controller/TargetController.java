package eastyle.gopdefence.controller;

import eastyle.gopdefence.GameActivity;
import eastyle.gopdefence.logic.Target;
import eastyle.gopdefence.maps.FirstMap;
import eastyle.gopdefence.view.GameZone;

public class TargetController {
	private static int waveLevel = 0;
	public static int targetsCount;
	private static Object[] targetInfo;

	/**
	 * 
	 */
	public static void sendNewWave() {
		GameActivity.globalScene.unregisterTouchArea(GameZone.gameMap);
		targetInfo = FirstMap.getTestTargetProperties()[waveLevel];
		targetsCount = (Integer) targetInfo[4];

		/* Register Targets in ArrayList */
		for (int count = 0; count < (Integer) targetInfo[4]; count++) {
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
		new Thread(new Runnable() {
			public void run() {
				for (int i = 0; i < (Integer) targetInfo[4]; i++) {
					sendTarget(i);
				}
			}
		}).start();
	}

	/**
	 * @param targetsCount
	 */
	private static void sendTarget(int i) {
		try {
			GameZone.globalTargets.get(i).go();
			Thread.sleep((Integer) targetInfo[5]);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	public static void checkTargets() {
		if (targetsCount == 0) {
			GameZone.globalTargets.clear();
			for (Target target : GameZone.globalTargets) {
				GameZone.gameMap.detachChild(target);
			}
			waveLevel++;
			if (waveLevel < FirstMap.getTestTargetProperties().length) {
				sendNewWave();
			} else {
				// TODO Show message "You win!";
				//...
				//end
			}
		}
	}
}
