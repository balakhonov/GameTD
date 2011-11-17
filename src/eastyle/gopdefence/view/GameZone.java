package eastyle.gopdefence.view;

import java.util.ArrayList;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import eastyle.gopdefence.GameActivity;
import eastyle.gopdefence.controller.TargetController;
import eastyle.gopdefence.controller.TowerController;
import eastyle.gopdefence.controller.WaveController;
import eastyle.gopdefence.logic.Target;
import eastyle.gopdefence.logic.Tower;
import eastyle.gopdefence.maps.FirstMap;

public class GameZone extends GameActivity {
	public static ArrayList<Target> globalTargets = new ArrayList<Target>();
	public static ArrayList<Tower> globalTowers = new ArrayList<Tower>();
	public static ArrayList<IEntity> globalProjectile = new ArrayList<IEntity>();
	private BitmapTextureAtlas fMapTexture;
	private TextureRegion fMapTextureRegions;
	public static volatile Sprite gameMap;
	public static float elementSize = 40;
	// Using for destroy all threads
	public static boolean isDestroy = false;
	// Using for pause all threads
	public static boolean isPause = true;
	// speed flag 1x 2x 3x 4x 8x
	public static int gameSpeed = 1;
	// Change when speed changing
	public static boolean speedShange = true;

	public static void nextSpeed() {
		try {
			isPause = true;
			Thread.sleep(200);
			speedShange = !speedShange;
			gameSpeed = (gameSpeed == 8) ? 1 : gameSpeed * 2;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		isPause = false;
	}

	public static void isPause() {
		while (isPause) {
			try {
				Thread.sleep(7);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public GameZone() {
		// TODO Move to GameActivity loadRsorses method
		this.fMapTexture = new BitmapTextureAtlas(1024, 1024,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.fMapTextureRegions = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.fMapTexture, GameActivity.gameActivity,
						FirstMap.imageMap, 0, 0); // 20x12
		engine.getTextureManager().loadTexture(fMapTexture);
		// end

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
					case 1:
						AttackRadius.hideRadius();
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

	public static float getMarshrutPointCoordinate(int x, int y) {
		return FirstMap.marshrut[x][y] * FirstMap.mapElementSize;
	}

	public static void addChild(Sprite sprite) {
		gameMap.attachChild(sprite);
	}

	public static void restartMap() {
		// destroy all objects
		for (Target target : GameZone.globalTargets) {
			target.destroyTarget();
		}
		//
		// GameZone.globalTargets.clear();
		// GameZone.globalTowers.clear();
		gameSpeed = 1;
		isPause = true;
		GoldView.resetGold();
		TargetController.resetWaveLevel();
		TargetController.sendNewWave();
	}
}
