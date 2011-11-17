package eastyle.gopdefence.model.attack;

import java.util.ArrayList;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.sprite.Sprite;
import eastyle.gopdefence.GameActivity;
import eastyle.gopdefence.logic.Target;
import eastyle.gopdefence.logic.Tower;
import eastyle.gopdefence.view.GameZone;

public class MassPointAttack implements AttackTypeInterface {
	public MassPointAttack() {

	}

	public void attack(final Tower tower, final Target target) {
		final float endX = target.getX();
		final float endY = target.getY();
		new Thread(new Runnable() {
			Sprite projectile = new Sprite(tower.getX(), tower.getY(),
					GameActivity.mBlueTargetTextureRegion);

			@Override
			public void run() {
				float step = 4;
				float boomRange = 200;

				projectile.setScale(0.4f);
				GameZone.gameMap.attachChild(projectile);
				GameZone.globalProjectile.add(projectile);
				while (true) {
					if (GameZone.isDestroy)
						break;
					try {
						// Thread.sleep(100);
						int sleepStep = 0;
						while (sleepStep < (100 / GameZone.gameSpeed)) {
							sleepStep += 5;
							Thread.sleep(5);
							// FIXME recount for stepsleep
						}
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

						if (Math.abs(projectile.getY() - endY) <= step
								&& Math.abs(projectile.getX() - endX) <= step) {
							projectile.setAlpha(20);
							projectile.setScale(2);

							ArrayList<Target> targets = GameZone.globalTargets;
							// GameZone.globalTargets;
							for (Target _target : targets) {
								if (getDistance(projectile, _target) < boomRange) {
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
							// Thread.sleep(200);
							sleepStep = 0;
							while (sleepStep < (200 / GameZone.gameSpeed)) {
								sleepStep += 5;
								GameZone.isPause();
								Thread.sleep(5);
							}
							projectile.setVisible(false);
							break;
						}

						if (getDistance(target, tower) > tower.getAttackRange()
								|| target.isDestroied) {
							tower.setTargetCaptured(false);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				//projectile.setVisible(false);
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
