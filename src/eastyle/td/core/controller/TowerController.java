package eastyle.td.core.controller;

import org.anddev.andengine.entity.sprite.Sprite;

import android.util.Log;

import eastyle.gopdefence.view.GameZone;
import eastyle.td.core.logic.Target;
import eastyle.td.core.logic.Tower;
import eastyle.td.core.model.AttackTarget;

public class TowerController {
	public static int towerId = 0;

	/**
	 * Search for target in tower attack radius
	 * 
	 * @param towerHashCod
	 * @param gameZone
	 * @param tower
	 */
	public void searchTarget(Tower tower) {
		// Log.i("[SEARCHING]", "Tower " + towerHashCod + " searching target");
		float distance;
		try {
			Thread.sleep(100);
			for (Target target : GameZone.globalTargets) {
				if (!target.isDestroied) {
					distance = getDistance(tower, target);
					Log.i("getDistance",
							distance + " < " + tower.getAttackRange());
					if (distance < tower.getAttackRange()) {
						AttackTarget.attackTarget(target, tower);
						break;
					}
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Return distance between tower and target
	 * 
	 * @param aTtower
	 *            Link to the tower
	 * @param aTarget
	 *            Link to the target
	 * @return distance
	 */
	public static float getDistance(final Tower aTtower, final Target aTarget) {
		Sprite tower = aTtower.getTowerSprite();
		return (float) Math.sqrt(Math.pow(
				(tower.getX() - aTarget.getPositionX()), 2)
				+ Math.pow((tower.getY() - aTarget.getPositionY()), 2));
	}
}
