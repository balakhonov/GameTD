package eastyle.gopdefence.view;

import org.anddev.andengine.entity.scene.menu.MenuScene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;

import android.util.Log;

import eastyle.gopdefence.GameActivity;
import eastyle.gopdefence.controller.TargetController;
import eastyle.gopdefence.maps.FirstMap;

public class TowersPanelLayer extends GameActivity {
	public static MenuScene mTowersMenuScene;
	public static boolean is_towerBuildSelect = false;
	public static int selectedTower;

	public TowersPanelLayer() {
		createMenu();
	}

	protected void createMenu() {
		for (int index = 0; index < FirstMap.getTestTowerProperties().length; index++) {
			Log.i("TOWER added", "id = " + index);
			final Sprite tower = new Sprite(200, 200, mFaceTextureRegion) {

				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
						final float pTouchAreaLocalX,
						final float pTouchAreaLocalY) {
					if (!is_towerBuildSelect
							|| selectedTower != ((Integer) this.getUserData())) {
						is_towerBuildSelect = true;
						selectedTower = ((Integer) this.getUserData());
						Log.i("Click",
								"Tower id=" + ((Integer) this.getUserData()));
					}
					return true;
				}
			};
			tower.setPosition(CAMERA_WIDTH - tower.getWidth() - 5, index
					* tower.getHeight() + 10);
			tower.setUserData(new Integer(index));
			globalScene.attachChild(tower);
			globalScene.registerTouchArea(tower);

			createPauseContinueButton();
			createSpeedButton();
			crateGoldLabel();
		}
	}

	private void crateGoldLabel() {
		globalScene.attachChild(new GoldView().getGoldLabel());
	}

	private Sprite createSpeedButton() {
		Sprite speedButton = new Sprite(200, 200, mFaceTextureRegion) {

			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
					final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()) {
					GameZone.nextSpeed();
				}
				return true;
			}
		};
		speedButton.setPosition(speedButton.getWidth() + 5, 10);
		globalScene.attachChild(speedButton);
		globalScene.registerTouchArea(speedButton);
		return speedButton;
	}

	private Sprite createPauseContinueButton() {
		Sprite pauseButton = new Sprite(200, 200, mFaceTextureRegion) {

			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
					final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()) {
					GameZone.isPause = !GameZone.isPause;
				}
				return true;
			}
		};
		pauseButton.setPosition(5, 10);
		globalScene.attachChild(pauseButton);
		globalScene.registerTouchArea(pauseButton);
		return pauseButton;
	}
}
