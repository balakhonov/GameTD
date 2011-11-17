package eastyle.gopdefence.controller;

import android.util.Log;
import eastyle.gopdefence.GameActivity;
import eastyle.gopdefence.logic.Tower;
import eastyle.gopdefence.maps.FirstMap;
import eastyle.gopdefence.view.GameZone;
import eastyle.gopdefence.view.GoldView;
import eastyle.gopdefence.view.TowersPanelLayer;

public class TowerController {
	public static int towerId = 0;
	public static int shots = 0;

	/**
	 * Search for target in tower attack radius
	 * 
	 * @param towerHashCod
	 * @param gameZone
	 * @param tower
	 */
	// public void searchTarget(Tower tower) {
	// float distance;
	// try {
	// //Thread.sleep(10);
	// for (Target target : GameZone.globalTargets) {
	// if (!target.isDestroied && target.isVisible()) {
	// distance = getDistance((IEntity) tower, target);
	// if (distance < tower.getAttackRange()) {
	// AttackTarget.attackTarget(target, tower);
	// break;
	// }
	// }
	// }
	// } catch (Exception e) {
	// Log.i("Error", "Error");
	// e.printStackTrace();
	// }
	// }

	/**
	 * Return distance between tower and target
	 * 
	 * @param aTtower
	 *            Link to the tower
	 * @param aTarget
	 *            Link to the target
	 * @return distance
	 */
	// public synchronized static float getDistance(final IEntity aTtower,
	// final IEntity aTarget) {
	// return (float) Math.sqrt(Math.pow((aTtower.getX() - aTarget.getX()), 2)
	// + Math.pow((aTtower.getY() - aTarget.getY()), 2));
	// }

	public static void buildTower(int action, float pTouchAreaLocalX,
			float pTouchAreaLocalY) {
		switch (action) {
		case 1:
			TowersPanelLayer.is_towerBuildSelect = false;
			break;
		case 0:
			Log.i("Add tower on map", "Add tower on map");
			if (GoldView.getGold() >= (Integer)FirstMap.getTestTowerProperties()[TowersPanelLayer.selectedTower][4]) {
				GoldView.delGold((Integer)FirstMap.getTestTowerProperties()[TowersPanelLayer.selectedTower][4]);
				Tower towerMaket = new Tower(TowersPanelLayer.selectedTower);			
				
				// //Sprite tower = towerMaket.getTowerSprite();
				float x = ((int) (pTouchAreaLocalX / FirstMap.mapElementSize))
						* FirstMap.mapElementSize;
				float y = ((int) (pTouchAreaLocalY / FirstMap.mapElementSize))
						* FirstMap.mapElementSize;
				towerMaket.setPosition(x, y);
				GameZone.gameMap.attachChild(towerMaket);
				GameZone.globalTowers.add(towerMaket);
				GameActivity.globalScene.unregisterTouchArea(GameZone.gameMap);
				GameActivity.globalScene.registerTouchArea(GameZone.gameMap);
			}
			break;
		default:
			break;
		}
	}
}
