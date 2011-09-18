package eastyle.gopdefence.model;

import eastyle.gopdefence.controller.TowerController;
import eastyle.gopdefence.logic.Target;
import eastyle.gopdefence.logic.Tower;

public class AttackTarget {
	public static void attackTarget(final Target target, final Tower tower) {
		tower.setTargetCaptured(true);
		try {
			target.setScale(0.7f);
			while (tower.isTargetCaptured()) {
				tower.setScale(1.4f);
				Thread.sleep(150);
				tower.setScale(1);
				target.setHeals(target.getHeals() - tower.getAttackDamage());
				target.viewHeals();

				if (target.getHeals() <= 0) {
					synchronized (target) {
						target.killTarget();
					}
					Thread.sleep(tower.getAttackDelay());
					break;
				}
				Thread.sleep(tower.getAttackDelay());
				if (TowerController.getDistance(tower, target) > tower
						.getAttackRange()) {
					break;
				}
			}
			target.setScale(1f);
			tower.setTargetCaptured(false);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
