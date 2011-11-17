package eastyle.gopdefence.model.attack;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.primitive.Line;

import android.util.Log;
import eastyle.gopdefence.controller.TowerController;
import eastyle.gopdefence.logic.Target;
import eastyle.gopdefence.logic.Tower;
import eastyle.gopdefence.view.GameZone;

public class TeslaAttack implements AttackTypeInterface {
	Line teslaBeam;

	float towerHeight;
	float towerWidth;
	private String towerId = "Lazer ";

	public TeslaAttack(Tower tower) {
		towerHeight = tower.getHeight();
		towerWidth = tower.getWidth();
		teslaBeam = new Line(0, 0, 0, 0);
		teslaBeam.setColor(255, 255, 0);
		teslaBeam.setLineWidth(2);
		GameZone.gameMap.attachChild(teslaBeam);
		GameZone.globalProjectile.add(teslaBeam);
		towerId += tower.towerId;
	}

	public void attackTarget(final Tower tower, final Target target) {
		tower.setTargetCaptured(true); // захватил цель
		try {
			target.setScale(0.7f); //

			while (tower.isTargetCaptured()) {
				if (GameZone.isDestroy) // if set destroy all objects
					break;
				GameZone.isPause();
				/* anomate shot */
				tower.animate(150 / GameZone.gameSpeed);
				// tower.setScale(1.4f);
				Thread.sleep(150 / GameZone.gameSpeed);
				// tower.setScale(1);
				attack(tower, target);
				tower.stopAnimation();
				// Thread.sleep(tower.getAttackDelay());
				// Sleep thread
				int sleepStep = 0;
				while (sleepStep < (tower.getAttackDelay())) {
					GameZone.isPause();
					sleepStep += 7 * GameZone.gameSpeed;
					Thread.sleep(7);
				}
			}

			target.setScale(1f);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void attack(final Tower tower, final Target target) {
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				teslaBeam.setVisible(true);
				final float towerHeight = tower.getHeight();
				final float towerWidth = tower.getWidth();

				while (true) {
					if (GameZone.isDestroy)
						break;
					try {

						if (getDistance(target, tower) > tower.getAttackRange()
								|| target.isDestroied) {
							teslaBeam.setVisible(false);
							tower.setTargetCaptured(false);
							break;
						}
						teslaBeam.setPosition(tower.getX() + towerWidth / 2,
								tower.getY() + towerHeight / 2, target.getX()
										+ target.getWidth() / 2, target.getY()
										+ target.getHeight() / 2);

						target.setHeals(target.getHeals()
								- tower.getAttackDamage());
						target.viewHeals();
						TowerController.shots++;
						//Log.w("Shot", "shot=" + TowerController.shots);
						Log.w("Shooting", "tower=" + towerId);
						if (target.getHeals() <= 0) {
							teslaBeam.setVisible(false);
							tower.setTargetCaptured(false);
							target.killTarget();
							break;
						}
						// Thread.sleep(100);
						int sleepStep = 0;
						while (sleepStep < (100)) {
							GameZone.isPause();
							sleepStep += 7 * GameZone.gameSpeed;
							Thread.sleep(7);

						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		},towerId);
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public float getDistance(final IEntity aTtower, final IEntity aTarget) {
		return (float) Math.sqrt(Math.pow((aTtower.getX() - aTarget.getX()), 2)
				+ Math.pow((aTtower.getY() - aTarget.getY()), 2));
	}
}
