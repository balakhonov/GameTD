package eastyle.td.core.controller;

import eastyle.gopdefence.GameActivity;
import eastyle.gopdefence.view.GameZone;
import eastyle.resourse.maps.FirstMap;
import eastyle.td.core.logic.Target;

public class TargetController {

	// private static GameZone gameZone;
	private static int waveLevel = 0;
	private static Object[] targetInfo;

	public TargetController() {
		// TargetController.gameZone = gameZone;
		sendNewWave();
	}

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

	private static void sendTargets() {
		new Thread(new Runnable() {
			public void run() {
				try {
					for (Target target : GameZone.globalTargets) {
						target.go();
						Thread.sleep(2000);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public static void checkTargets() {
		// if (gameZone.getGameTargets().size() == 0) {
		// waveLevel++;
		// if (FirstMap.getTestTargetProperties().length > waveLevel) {
		// sendNewWave();
		// }
		// }
	}
}
