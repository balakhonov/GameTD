package eastyle.gopdefence.view;

import org.anddev.andengine.entity.scene.menu.MenuScene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;

import android.util.Log;

import eastyle.gopdefence.GameActivity;
import eastyle.resourse.maps.FirstMap;

public class TowersPanelLayer extends GameActivity {
	public static MenuScene mTowersMenuScene;
	public static boolean is_towerBuildSelect = false;
	public static int selectedTower;

	public TowersPanelLayer() {
//		MenuItem[] items = new MenuItem[FirstMap.getTestTowerProperties().length];
//		int posX = 35;
//		for (int index = 0; index < FirstMap.getTestTowerProperties().length; index++) {
//			Log.i("TOWER added", "id = " + index);
//			MenuItem item1 = new MenuItemExt(index, this).returnMenuItem();
//			item1.setPosition(SystemProperties.windowWidth - posX, 90);
//			posX += 35;
//			items[index] = item1;
//		}
//
//		Log.i("MenuItem", "len = " + items.length);
//		mTowersMenuScene = Menu.menu(items);
//		mTowersMenuScene.setPosition(0.0f, 0.0f);
		createMenu();
	}

	protected void createMenu() {
//		mTowersMenuScene = new MenuScene(GameActivity.mCamera, this);

//		SpriteMenuItem[] towerItems = new SpriteMenuItem[FirstMap
//				.getTestTowerProperties().length];

		for (int index = 0; index < FirstMap.getTestTowerProperties().length; index++) {
			Log.i("TOWER added", "id = " + index);
			final Sprite tower = new Sprite(200, 200, mFaceTextureRegion) {
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
						final float pTouchAreaLocalX,
						final float pTouchAreaLocalY) {
					if(!is_towerBuildSelect || selectedTower != ((Integer)this.getUserData())) {
						is_towerBuildSelect = true;
						selectedTower = ((Integer)this.getUserData());
						Log.i("Click", "Tower id="+ ((Integer)this.getUserData()));
					}
					return true;
				}
			};
			tower.setPosition(CAMERA_WIDTH - tower.getWidth()-5, index*tower.getHeight() + 10);
			tower.setUserData(new Integer(index));
			globalScene.attachChild(tower);
			globalScene.registerTouchArea(tower);
//			towerItems[index] = resetMenuItem;
			
//			Log.i("menu", "len = " + mTowersMenuScene.getChildCount());
		}
//
//		final SpriteMenuItem quitMenuItem = new SpriteMenuItem(1,
//				mRedTargetTextureRegion);
//		quitMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA,
//				GL10.GL_ONE_MINUS_SRC_ALPHA);
//		mTowersMenuScene.addMenuItem(quitMenuItem);

//		mTowersMenuScene.buildAnimations();
//		mTowersMenuScene.setPosition(0, 0);
//		mTowersMenuScene.setBackgroundEnabled(false);
//		globalScene.setChildScene(mTowersMenuScene);
	}
}
