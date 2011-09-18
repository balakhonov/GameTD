package eastyle.gopdefence.view;

import java.util.ArrayList;

import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import android.util.Log;
import eastyle.gopdefence.GameActivity;
import eastyle.td.core.logic.Target;
import eastyle.td.core.logic.Tower;

public class GameZone extends GameActivity {
	public static ArrayList<Target> globalTargets = new ArrayList<Target>();
	private BitmapTextureAtlas fMapTexture;
	private TextureRegion fMapTextureRegions;
	public static Sprite gameMap;
	public static float elementSize = 40;
	public GameZone() {

		this.fMapTexture = new BitmapTextureAtlas(512, 512,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.fMapTextureRegions = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.fMapTexture, GameActivity.gameActivity,
						"map1.jpg", 0, 0);
		engine.getTextureManager().loadTexture(fMapTexture);

		gameMap = new Sprite(0, 0, fMapTextureRegions) {
			float posX = 0;
			float posY = 0;

			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
					final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if (TowersPanelLayer.is_towerBuildSelect) {
					Log.i("Add tower on map", "Add tower on map");
					Tower towerMaket = new Tower(TowersPanelLayer.selectedTower);
					// if (GoldView.getGold() >= towerMaket.getTowerCoast()) {
					// GoldView.delGold(towerMaket.getTowerCoast());
					// //Sprite tower = towerMaket.getTowerSprite();
					 float x = ((int) (pTouchAreaLocalX / elementSize))
					 * elementSize;
					 float y = ((int) (pTouchAreaLocalY / elementSize))
					 * elementSize;
					 towerMaket.getTowerSprite().setPosition(x, y);
					gameMap.attachChild(towerMaket.getTowerSprite());
					TowersPanelLayer.is_towerBuildSelect = false;
					// }
				} else {
					if (Math.abs(posX - pSceneTouchEvent.getX()) > 6
							&& Math.abs(posY - pSceneTouchEvent.getY()) > 6) {
						posX = pSceneTouchEvent.getX();
						posY = pSceneTouchEvent.getY();
					} else {

						posX = this.getX() - (posX - pSceneTouchEvent.getX());
						posY = this.getY() - (posY - pSceneTouchEvent.getY());

						if (posX > -150
								&& (posX + this.getWidth() - 150) < CAMERA_WIDTH) {
							this.setPosition(posX, this.getY());
						}
						if (posY > -150
								&& (posY + this.getHeight() - 150) < CAMERA_HEIGHT) {
							this.setPosition(this.getX(), posY);
						}

						posX = pSceneTouchEvent.getX();
						posY = pSceneTouchEvent.getY();

					}
				}
				return true;
			}
		};
		globalScene.attachChild(gameMap);
		globalScene.registerTouchArea(gameMap);

	}

	public void setBackground() {
		// Sprite land = Sprite.sprite(map.getImageMap()); // background img
		// land.setAnchorPoint(0, 0);
		// // land.scale(map.getNewScaleXmapElementSize());
		// land.setPosition(0, 0);
		// addChild(land);
	}

	public static void addChild(Sprite sprite) {
		gameMap.attachChild(sprite);
	}
}
