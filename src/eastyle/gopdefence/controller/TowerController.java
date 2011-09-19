package eastyle.gopdefence.controller;

import org.anddev.andengine.entity.sprite.Sprite;

import android.util.Log;
import eastyle.gopdefence.GameActivity;
import eastyle.gopdefence.logic.Target;
import eastyle.gopdefence.logic.Tower;
import eastyle.gopdefence.model.AttackTarget;
import eastyle.gopdefence.view.GameZone;
import eastyle.gopdefence.view.TowersPanelLayer;

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
		float distance;
		try {
			Thread.sleep(100);
			for (Target target : GameZone.globalTargets) {
				if (!target.isDestroied && target.isVisible()) {
					distance = getDistance(tower, target);
					if (distance < tower.getAttackRange()) {
						AttackTarget.attackTarget(target, tower);
						break;
					}
				}
			}
		} catch (Exception e) {
			Log.i("Error", "Error");
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
	public synchronized static float getDistance(final Sprite aTtower, final Sprite aTarget) {
		return (float) Math.sqrt(Math.pow(
				(aTtower.getX() - aTarget.getX()), 2)
				+ Math.pow((aTtower.getY() - aTarget.getY()), 2));
	}
	
	public static void buildTower(int action, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		switch (action) {
		case 1:
			TowersPanelLayer.is_towerBuildSelect = false;
			break;
		case 0:
			Log.i("Add tower on map", "Add tower on map");
			Tower towerMaket = new Tower(
					TowersPanelLayer.selectedTower);
			// if (GoldView.getGold() >= towerMaket.getTowerCoast())
			// {
			// GoldView.delGold(towerMaket.getTowerCoast());
			// //Sprite tower = towerMaket.getTowerSprite();
			float x = ((int) (pTouchAreaLocalX / GameZone.elementSize))
					* GameZone.elementSize;
			float y = ((int) (pTouchAreaLocalY / GameZone.elementSize))
					* GameZone.elementSize;
			towerMaket.setPosition(x, y);
			GameZone.gameMap.attachChild(towerMaket);
			GameZone.globalTowers.add(towerMaket);
			GameActivity.globalScene
					.unregisterTouchArea(GameZone.gameMap);
			GameActivity.globalScene
					.registerTouchArea(GameZone.gameMap);
			break;
		default:
			break;
		}
	}
}
