package eastyle.gopdefence.view;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.entity.scene.menu.MenuScene;
import org.anddev.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.anddev.andengine.entity.scene.menu.item.IMenuItem;
import org.anddev.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import eastyle.gopdefence.GameActivity;
import eastyle.gopdefence.controller.TargetController;

import android.util.Log;

public class GameZoneMenu extends GameActivity implements
		IOnMenuItemClickListener {

	// ===========================================================
	// Constants
	// ===========================================================
	protected static final int MENU_RESET = 0;
	protected static final int MENU_QUIT = 1;

	// ===========================================================
	// Fields
	// ===========================================================
	public static MenuScene mMenuScene;


	protected static TextureRegion mMenuResetTextureRegion;
	protected TextureRegion mMenuQuitTextureRegion;

	private BitmapTextureAtlas mMenuTexture;

	public GameZoneMenu() {
		loadResources();
		createMenu();
	}

	public void loadResources() {
		Log.i("GameZoneMenu onLoadResources", "GameZoneMenu onLoadResources");

		mMenuTexture = new BitmapTextureAtlas(256, 128,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		Log.i("BitmapTexture", mMenuTexture + " | " + this);

		mMenuResetTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mMenuTexture, GameActivity.gameActivity, "menu_reset.png", 0,
						0);
		this.mMenuQuitTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mMenuTexture, GameActivity.gameActivity, "menu_quit.png", 0,
						50);
		engine.getTextureManager().loadTexture(mMenuTexture);
	}

	protected void createMenu() {
		 mMenuScene = new MenuScene(GameActivity.mCamera, this);
		
		 final SpriteMenuItem resetMenuItem = new SpriteMenuItem(MENU_RESET,
		 mMenuResetTextureRegion);
		 resetMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA,
		 GL10.GL_ONE_MINUS_SRC_ALPHA);
		 mMenuScene.addMenuItem(resetMenuItem);
		
		 final SpriteMenuItem quitMenuItem = new SpriteMenuItem(MENU_QUIT,
		 this.mMenuQuitTextureRegion);
		 quitMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA,
		 GL10.GL_ONE_MINUS_SRC_ALPHA);
		 mMenuScene.addMenuItem(quitMenuItem);
		
		 mMenuScene.buildAnimations();
		 mMenuScene.setBackgroundEnabled(false);
	}

	@Override
	public boolean onMenuItemClicked(final MenuScene pMenuScene,
			final IMenuItem pMenuItem, final float pMenuItemLocalX,
			final float pMenuItemLocalY) {
		Log.i("Click", "Click2");
		switch (pMenuItem.getID()) {
		case MENU_RESET:
			//GameActivity.restartMap();
			/* Restart the animation. */
			//GameActivity.globalScene.reset();

			/* Remove the menu and reset it. */
			GameActivity.globalScene.clearChildScene();
			mMenuScene.reset();
			GameZone.restartMap();
			return true;
		case MENU_QUIT:
			/* End Activity. */
			GameActivity.gameActivity.finish();
			return true;
		default:
			return false;
		}
	}
}
