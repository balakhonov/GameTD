package eastyle.gopdefence.model.attack;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.sprite.Sprite;
import eastyle.gopdefence.GameActivity;
import eastyle.gopdefence.logic.Target;
import eastyle.gopdefence.logic.Tower;
import eastyle.gopdefence.view.GameZone;

public class PointHomingAttack implements AttackTypeInterface {
	public PointHomingAttack() {

	}

	public void attack(final Tower tower, final Target target) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				Sprite projectile = new Sprite(tower.getX(), tower.getY(),
						GameActivity.mBlueTargetTextureRegion);
				projectile.setScale(0.4f);
				GameZone.gameMap.attachChild(projectile);
				GameZone.globalProjectile.add(projectile);

				float step = 1f;
				while (true) {
					if (GameZone.isDestroy)
						break;
					try {
						int sleepStep = 0;
						while (sleepStep < (10 / GameZone.gameSpeed)) {
							sleepStep += 5;
							GameZone.isPause();
							Thread.sleep(5);
							// FIXME recount for stepsleep
						}
						// Thread.sleep(100);
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

						if (getDistance(projectile, tower) > tower
								.getAttackRange() || target.isDestroied) {
							projectile.setVisible(false);
							tower.setTargetCaptured(false);
							break;
						}

						if (getDistance(projectile, target) < 10) {
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

	public float getDistance(final IEntity aTtower, final IEntity aTarget) {
		return (float) Math.sqrt(Math.pow((aTtower.getX() - aTarget.getX()), 2)
				+ Math.pow((aTtower.getY() - aTarget.getY()), 2));
	}

	@Override
	public void attackTarget(Tower tower, Target target) {
		attack(tower, target);
	}
}
