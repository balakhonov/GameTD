package eastyle.gopdefence;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import eastyle.gopdefence.controller.TargetController;
import eastyle.gopdefence.view.GameZone;
import eastyle.gopdefence.view.GameZoneMenu;
import eastyle.gopdefence.view.TowersPanelLayer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;

public class GameActivity extends BaseGameActivity {
	// ===========================================================
	// Constants
	// ===========================================================

	public static final int CAMERA_WIDTH = 720;
	public static final int CAMERA_HEIGHT = 480;

	protected static final int MENU_RESET = 0;
	protected static final int MENU_QUIT = MENU_RESET + 1;

	// ===========================================================
	// Fields
	// ===========================================================

	/* Global */
	public static Camera mCamera;
	public static Scene globalScene;
	protected static Engine engine;
	protected static GameActivity gameActivity;

	/* texture for test object */
	public static TextureRegion mFaceTextureRegion;
	/* texture for red tower/target */
	public static TextureRegion mRedTargetTextureRegion;
	/* texture for blue tower/target */
	public static TextureRegion mBlueTargetTextureRegion;

	@Override
	public Engine onLoadEngine() {
		Log.i("onLoadEngine", "onLoadEngine");

		/* GET SCREEN SIZE */
		/*
		 * WindowManager w = getWindowManager(); Display d =
		 * w.getDefaultDisplay(); DisplayMetrics metrics = new DisplayMetrics();
		 * d.getMetrics(metrics); CAMERA_WIDTH = d.getWidth(); CAMERA_HEIGHT =
		 * d.getHeight(); Log.i("DisplayMetrics", CAMERA_WIDTH + " | " +
		 * CAMERA_HEIGHT);
		 */

		gameActivity = this;

		mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		final EngineOptions options = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(
						getScreenResolutionRatio()), mCamera);
		return new Engine(options);
	}

	@Override
	public void onLoadResources() {
		Log.i("onLoadResources", "onLoadResources");
		engine = this.mEngine;
		BitmapTextureAtlas mBitmapTextureAtlas;

		/* texture for test object */
		mBitmapTextureAtlas = new BitmapTextureAtlas(64, 64,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mBitmapTextureAtlas, this,
						"face_box_menu.png", 0, 0);
		engine.getTextureManager().loadTexture(mBitmapTextureAtlas);

		/* texture for red tower/target */
		mBitmapTextureAtlas = new BitmapTextureAtlas(256, 128,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mRedTargetTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mBitmapTextureAtlas, this, "red_tower.png", 0,
						0);
		engine.getTextureManager().loadTexture(mBitmapTextureAtlas);

		/* texture for red tower/target */
		mBitmapTextureAtlas = new BitmapTextureAtlas(256, 128,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mBlueTargetTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mBitmapTextureAtlas, this, "blue_tower.png", 0,
						0);
		engine.getTextureManager().loadTexture(mBitmapTextureAtlas);
	}

	@SuppressWarnings("deprecation")
	@Override
	public Scene onLoadScene() {
		/* FPSLogger in console */
		this.mEngine.registerUpdateHandler(new FPSLogger());

		/* Create Global Scene */
		globalScene = new Scene(3);
		globalScene.setBackground(new ColorBackground(0.09804f, 0.6274f,
				0.8784f));

		/* Attach gameZone menu */
		new GameZoneMenu();

		/* Add game map */
		new GameZone();

		/* Add towers menu */
		new TowersPanelLayer();

		return globalScene;
	}

	@Override
	public void onLoadComplete() {
		Log.i("onLoadComplete", "onLoadComplete");
		initGameZone();
	}

	// ===========================================================
	// Methods
	// ===========================================================

	private void initGameZone() {
		globalScene.setTouchAreaBindingEnabled(true);

		/* add targets */
		TargetController.sendNewWave();

		/* test object */
		// final Sprite face = new Sprite(0, 0, this.mFaceTextureRegion);
		// face.registerEntityModifier(new MoveModifier(5, 0, CAMERA_WIDTH
		// - face.getWidth(), 0, CAMERA_HEIGHT - face.getHeight()));
		// globalScene.attachChild(face);

		/* test object */
		// final Sprite face1 = new Sprite(50, 0, this.mFaceTextureRegion);
		// face1.registerEntityModifier(new MoveModifier(30, 100, CAMERA_WIDTH
		// - face1.getWidth(), 0, CAMERA_HEIGHT - face1.getHeight()));
		// GameZone.addChild(face1);

		/* test object */
		// new Thread(new Runnable() {
		//
		// @Override
		// public void run() {
		// final Sprite face2 = new Sprite(0, 0, mFaceTextureRegion);
		// face2.registerEntityModifier(new MoveModifier(30, 0,
		// CAMERA_WIDTH - face2.getWidth(), 0, CAMERA_HEIGHT
		// - face2.getHeight()));
		// GameZone.addChild(face2);
		// }
		// }).start();

		// /* test object */
		// final Sprite face2 = new Sprite(200, 200, mFaceTextureRegion) {
		// @Override
		// public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
		// final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		// this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2,
		// pSceneTouchEvent.getY() - this.getHeight() / 2);
		// Log.i("onAreaTouched", "onAreaTouched");
		// return true;
		// }
		// };
		// face2.setScale(4);
		// globalScene.attachChild(face2);
		// globalScene.registerTouchArea(face2);

		/* test line */
		// final Line line = new Line(0f, 0f, 500f, 500f);
		// line.setColor(255f, 200f, 0, 50f);
		// line.setLineWidth(50f);
		// line.registerEntityModifier(new MoveByModifier(20, 500, 500));
		// globalScene.attachChild(line);

		/* test Rectangle */
		// Rectangle baseRectangle = new Rectangle(0, 0, 100, 100);
		// baseRectangle.setColor(0, 200, 255);
		// globalScene.attachChild(baseRectangle);

		/* Ellipse test */
		// Ellipse ellipse = new Ellipse(0, 0, 200);
		// ellipse.setColor(0, 255, 0);
		// ellipse.registerEntityModifier(new ColorModifier(30, 0, 255, 100,
		// 200,
		// 255, 100));
		// ellipse.setLineWidth(2f);
		// globalScene.attachChild(ellipse);
	}
	
	@Override
	public boolean onKeyDown(final int pKeyCode, final KeyEvent pEvent) {
		Log.i("onKeyDown", "onKeyDown");
		if (pKeyCode == KeyEvent.KEYCODE_MENU
				&& pEvent.getAction() == KeyEvent.ACTION_DOWN) {
			Log.i("onKeyDown", "onKeyDown");
			if (globalScene.hasChildScene()) {
				/* Remove the menu and reset it. */
				GameZoneMenu.mMenuScene.back();
			} else {
				/* Attach the menu. */
				globalScene.setChildScene(GameZoneMenu.mMenuScene, true, true,
						true);
			}
			return true;
		} else {
			return super.onKeyDown(pKeyCode, pEvent);
		}
	}

	private float getScreenResolutionRatio() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		return ((float) dm.widthPixels) / ((float) dm.heightPixels);
	}
}