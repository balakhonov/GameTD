package eastyle.gopdefence.model.attack;

import org.anddev.andengine.entity.sprite.Sprite;

import eastyle.gopdefence.GameActivity;
import eastyle.gopdefence.controller.TowerController;
import eastyle.gopdefence.logic.Target;
import eastyle.gopdefence.logic.Tower;
import eastyle.gopdefence.view.GameZone;

public class PointHomingAttack {

	public PointHomingAttack(final Tower tower, final Target target) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				Sprite projectile = new Sprite(tower.getX(), tower.getY(),
						GameActivity.mBlueTargetTextureRegion);
				projectile.setScale(0.4f);
				GameZone.gameMap.attachChild(projectile);
				GameZone.globalProjectile.add(projectile);

				float step = 8;
				while (true) {
					try {
						Thread.sleep(100);
						if (projectile.getX() > target.getX()) {
							projectile.setPosition(projectile.getX() - step,
									projectile.getY());
						}

						if (projectile.getY() > target.getY()) {
							projectile.setPosition(projectile.getX(),
									projectile.getY() - step);
						}

						if (projectile.getX() < target.getX()) {
							projectile.setPosition(projectile.getX() + step,
									projectile.getY());
						}

						if (projectile.getY() < target.getY()) {
							projectile.setPosition(projectile.getX(),
									projectile.getY() + step);
						}

						if (TowerController.getDistance(projectile, tower) > tower
								.getAttackRange() || target.isDestroied) {
							projectile.setVisible(false);
							tower.setTargetCaptured(false);
							break;
						}

						if (TowerController.getDistance(projectile, target) < 10) {
							projectile.setVisible(false);

							target.setHeals(target.getHeals()
									- tower.getAttackDamage());
							target.viewHeals();

							if (target.getHeals() <= 0) {
								target.killTarget();
								tower.setTargetCaptured(false);
							}
							break;
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}
