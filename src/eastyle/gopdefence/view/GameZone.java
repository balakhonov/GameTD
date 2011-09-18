package eastyle.gopdefence.view;

import java.util.ArrayList;

import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import eastyle.gopdefence.GameActivity;
import eastyle.gopdefence.controller.TowerController;
import eastyle.gopdefence.logic.Target;
import eastyle.gopdefence.logic.Tower;

public class GameZone extends GameActivity {
	public static ArrayList<Target> globalTargets = new ArrayList<Target>();
	public static ArrayList<Tower> globalTowers = new ArrayList<Tower>();
	private BitmapTextureAtlas fMapTexture;
	private TextureRegion fMapTextureRegions;
	public static Sprite gameMap;
	public static float elementSize = 40;

	public GameZone() {
		//TODO Move to GameActivity loadRsorses method
		this.fMapTexture = new BitmapTextureAtlas(512, 512,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.fMapTextureRegions = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.fMapTexture, GameActivity.gameActivity,
						"map1.jpg", 0, 0);
		engine.getTextureManager().loadTexture(fMapTexture);
		//end
		
		gameMap = new Sprite(0, 0, fMapTextureRegions) {
			float oldTouchPosX = 0;
			float oldTouchPosY = 0;
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
					final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

				if (TowersPanelLayer.is_towerBuildSelect) {
					TowerController.buildTower(pSceneTouchEvent.getAction(),
							pTouchAreaLocalX, pTouchAreaLocalY);
				} else {
					switch (pSceneTouchEvent.getAction()) {
					case 2:
						if (Math.abs(oldTouchPosX - pSceneTouchEvent.getX()) > 22
								&& Math.abs(oldTouchPosY
										- pSceneTouchEvent.getY()) > 22) {
							oldTouchPosX = pSceneTouchEvent.getX();
							oldTouchPosY = pSceneTouchEvent.getY();
						} else {
							oldTouchPosX = this.getX()
									- (oldTouchPosX - pSceneTouchEvent.getX());
							oldTouchPosY = this.getY()
									- (oldTouchPosY - pSceneTouchEvent.getY());

							if (oldTouchPosX > -150
									&& (oldTouchPosX + this.getWidth() - 150) < CAMERA_WIDTH) {
								this.setPosition(oldTouchPosX, this.getY());
							}
							if (oldTouchPosY > -150
									&& (oldTouchPosY + this.getHeight() - 150) < CAMERA_HEIGHT) {
								this.setPosition(this.getX(), oldTouchPosY);
							}
							oldTouchPosX = pSceneTouchEvent.getX();
							oldTouchPosY = pSceneTouchEvent.getY();
						}
						break;

					default:
						break;
					}
				}
				return true;
			}
		};
		globalScene.attachChild(gameMap);
		globalScene.registerTouchArea(gameMap);
	}

	public void setBackground() {

	}
	
	public static void addChild(Sprite sprite) {
		gameMap.attachChild(sprite);
	}
}
