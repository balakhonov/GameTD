package eastyle.gopdefence.model.attack;

import org.anddev.andengine.entity.sprite.Sprite;

import eastyle.gopdefence.GameActivity;
import eastyle.gopdefence.controller.TowerController;
import eastyle.gopdefence.logic.Target;
import eastyle.gopdefence.logic.Tower;
import eastyle.gopdefence.view.GameZone;

public class MassPointAttack {
	public MassPointAttack(final Tower tower, final Target target) {
		final float endX = target.getX();
		final float endY = target.getY();
		new Thread(new Runnable() {

			@Override
			public void run() {
				float step = 4;
				float boomRange = 200;
				
				Sprite projectile = new Sprite(tower.getX(), tower.getY(),
						GameActivity.mBlueTargetTextureRegion);
				projectile.setScale(0.4f);
				GameZone.gameMap.attachChild(projectile);
				GameZone.globalProjectile.add(projectile);
				while (true) {
					try {
						Thread.sleep(100);
						if (projectile.getX() > endX) {
							projectile.setPosition(projectile.getX() - step,
									projectile.getY());
						}

						if (projectile.getY() > endY) {
							projectile.setPosition(projectile.getX(),
									projectile.getY() - step);
						}

						if (projectile.getX() < endX) {
							projectile.setPosition(projectile.getX() + step,
									projectile.getY());
						}

						if (projectile.getY() < endY) {
							projectile.setPosition(projectile.getX(),
									projectile.getY() + step);
						}

						if (Math.abs(projectile.getY() - endY) <= step && Math.abs(projectile.getX() - endX) <= step) {
							projectile.setAlpha(20);
							projectile.setScale(2);
							
							for (Target _target : GameZone.globalTargets) {
								if (TowerController.getDistance(projectile,
										_target) < boomRange) {
									_target.setHeals(_target.getHeals()
											- tower.getAttackDamage());
									_target.viewHeals();
									if (_target.getHeals() <= 0) {
										_target.killTarget();
										if (target.isDestroied) {
											tower.setTargetCaptured(false);
										}
									}
								}
							}
							Thread.sleep(200);
							
							projectile.setVisible(false);
							break;
						}

						if (TowerController.getDistance(target, tower) > tower
								.getAttackRange() || target.isDestroied) {
							tower.setTargetCaptured(false);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}
