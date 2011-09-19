package eastyle.gopdefence.model.attack;

import org.anddev.andengine.entity.primitive.Line;
import eastyle.gopdefence.controller.TowerController;
import eastyle.gopdefence.logic.Target;
import eastyle.gopdefence.logic.Tower;
import eastyle.gopdefence.view.GameZone;

public class TeslaAttack {

	public TeslaAttack(final Tower tower, final Target target) {
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				final float towerHeight = tower.getHeight();
				final float towerWidth = tower.getWidth();
				Line teslaBeam = new Line(tower.getX() + towerWidth / 2, tower
						.getY() + towerHeight / 2, target.getX()
						+ target.getWidth()/ 2, target.getY() + target.getHeight()/ 2);
				teslaBeam.setColor(255, 255, 0);
				teslaBeam.setLineWidth(2);
				GameZone.gameMap.attachChild(teslaBeam);
				GameZone.globalProjectile.add(teslaBeam);
				while (true) {
					try {

						if (TowerController.getDistance(target, tower) > tower
								.getAttackRange() || target.isDestroied) {
							teslaBeam.setVisible(false);
							tower.setTargetCaptured(false);
							break;
						}
						teslaBeam.setPosition(tower.getX() + towerWidth / 2,
								tower.getY() + towerHeight / 2, target.getX()
										+ target.getWidth()/ 2, target.getY()
										+ target.getHeight()/2);

						target.setHeals(target.getHeals()
								- tower.getAttackDamage());
						target.viewHeals();

						if (target.getHeals() <= 0) {
							teslaBeam.setVisible(false);
							tower.setTargetCaptured(false);
							target.killTarget();
							break;
						}
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
