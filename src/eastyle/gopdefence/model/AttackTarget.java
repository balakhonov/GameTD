package eastyle.gopdefence.model;

import eastyle.gopdefence.logic.Target;
import eastyle.gopdefence.logic.Tower;
import eastyle.gopdefence.model.attack.MassPointAttack;
import eastyle.gopdefence.model.attack.TeslaAttack;
import eastyle.gopdefence.model.attack.PointHomingAttack;

public class AttackTarget {
	public static void attackTarget(final Target target, final Tower tower) {
		tower.setTargetCaptured(true);
		try {
			target.setScale(0.7f);

			while (tower.isTargetCaptured()) {
				/* anomate shot */
				tower.setScale(1.4f);
				Thread.sleep(150);
				tower.setScale(1);

				switch (tower.getTowerAttackType()) {
				case 0:
					new PointHomingAttack(tower, target);
					break;
				case 1:
					new TeslaAttack(tower, target);
					break;
				case 2:
					new MassPointAttack(tower, target);
					break;
				default:
					break;
				}

				Thread.sleep(tower.getAttackDelay());
			}

			target.setScale(1f);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
