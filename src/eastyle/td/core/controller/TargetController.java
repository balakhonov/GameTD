package eastyle.td.core.controller;

import eastyle.gopdefence.GameActivity;
import eastyle.gopdefence.view.GameZone;
import eastyle.resourse.maps.FirstMap;
import eastyle.td.core.logic.Target;

public class TargetController {
	private static int waveLevel = 0;
	private static Object[] targetInfo;

	/**
	 * 
	 */
	public TargetController() {
		sendNewWave();
	}

	/**
	 * 
	 */
	private static void sendNewWave() {
		GameActivity.globalScene.unregisterTouchArea(GameZone.gameMap);

		/* Register Targets in ArrayList */
		for (int count = 0; count < 10; count++) {
			targetInfo = FirstMap.getTestTargetProperties()[waveLevel];
			Target _target = new Target(targetInfo);
			GameZone.addChild(_target.getSprite());
			GameZone.globalTargets.add(_target);
			GameZone.globalScene.registerTouchArea(_target.getSprite());
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
				sendTarget(GameZone.globalTargets.size());
			}
		}).start();
	}

	/**
	 * @param alltargetscount
	 */
	private static void sendTarget(int alltargetscount) {
		try {
			for (int i = 0; i < alltargetscount; i++) {
				if (alltargetscount != GameZone.globalTargets.size()) {
					synchronized (GameZone.globalTargets) {
						alltargetscount = GameZone.globalTargets.size();
					}
					continue;
				}
				GameZone.globalTargets.get(i).go();
				Thread.sleep(2000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 */
	public static void checkTargets() {
		if (GameZone.globalTargets.size() == 0) {
			waveLevel++;
			if (FirstMap.getTestTargetProperties().length > waveLevel) {
				sendNewWave();
			}
		}
	}
}
