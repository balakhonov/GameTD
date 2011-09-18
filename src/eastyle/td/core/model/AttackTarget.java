package eastyle.td.core.model;

import eastyle.td.core.controller.TowerController;
import eastyle.td.core.logic.Target;
import eastyle.td.core.logic.Tower;

public class AttackTarget {
	public AttackTarget() {

	}

	public static void attackTarget(final Target target, final Tower tower) {
		tower.setTargetCaptured(true);
		new Thread(new Runnable() {
			public void run() {
				try {
					target.setScale(0.7f);
					while (tower.isTargetCaptured()) {
						//Log.i("[Atack]", tower.isShooting() +" Tower " + tower.towerId + " atack " + target.id);
						tower.animateShot();
						target.setHeals(target.getHeals()
								- tower.getAttackDamage());
						target.viewHeals();

						if (target.getHeals() <= 0) {
							synchronized (target) {
								target.killTarget();
							}
							Thread.sleep(tower.getAttackDelay());
							break;
						}
						Thread.sleep(tower.getAttackDelay());
						if (TowerController.getDistance(tower, target) > tower.getAttackRange()) {
							break;
						}
					}
					target.setScale(1f);
					tower.setTargetCaptured(false);
					tower.activate();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).run();
	}
}
