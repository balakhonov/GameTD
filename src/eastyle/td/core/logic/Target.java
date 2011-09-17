package eastyle.td.core.logic;

import org.anddev.andengine.entity.modifier.ColorModifier;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;

import android.util.Log;

import eastyle.gopdefence.GameActivity;
import eastyle.gopdefence.shapes.Ellipse;

public class Target implements Runnable {
	public int id;
	public final Sprite sp;
	// private Label labelHeals;
	private float startX;
	private float startY;
	private float step = 3.0f;
	// private GameZone gameZone;
	private Thread thread;
	private boolean threadStopFlag = false;
	public boolean isDestroied = false;
	/* properties */
	private int speed;
	private int heals;
	private int coast;

	public Target(Object[] targetInfo) {
		// this.gameZone = map;
		// sp = Sprite.sprite((String) targetInfo[0]);
		sp = new Sprite(0, 0, GameActivity.mRedTargetTextureRegion) {

			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
					final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				Log.i("onAreaTouched", "onAreaTouched");
				/* Ellipse test */
				Ellipse ellipse = new Ellipse(0, 0, 100);
				ellipse.setColor(0, 255, 0);
				ellipse.registerEntityModifier(new ColorModifier(30, 0, 255, 100, 200,
						255, 100));
				ellipse.setLineWidth(1f);
				this.attachChild(ellipse);
				return true;
			}
		};		
		speed = (Integer) targetInfo[1];
		heals = (Integer) targetInfo[2];
		coast = (Integer) targetInfo[3];
		GameActivity.globalScene.registerTouchArea(sp);
		
		// addHealsLabel();
	}

	public final Sprite getSprite() {
		return sp;
	}

	/**
	 * Called wich target killed.
	 */
	public synchronized void killTarget() {
		if (!isDestroied) {
			deleteTarget();
			// GoldView.addGold(coast);
		}
	}

	/**
	 * Delete target from the game zone
	 */
	private void deleteTarget() {
		isDestroied = true;
		threadStopFlag = true;
		// gameZone.getGameTargets().remove(this);
		// gameZone.removeChild(sp, true);
		// TargetController.checkTargets();
	}

	/**
	 * Called wich target not killed.
	 */
	private void targetWent() {
		// TODO subtract points from user
		deleteTarget();
	}

	public float getPositionX() {
		return startX;
	}

	public float getPositionY() {
		return startY;
	}

	public void go() {

		thread = new Thread(this);
		thread.start();
	}

	public int getHeals() {
		return heals;
	}

	public void setHeals(int heals) {
		this.heals = heals;
	}

	// private void addHealsLabel() {
	// labelHeals = Label.label("Console", "DroidSans", 14.0f);
	// labelHeals.setColor(new CCColor3B(0, 0, 250));
	// labelHeals.setString(getHeals() + "");
	// labelHeals.setAnchorPoint(-0.3f, -0.3f);
	// sp.addChild(labelHeals);
	// }

	public void viewHeals() {
		// labelHeals.setString(getHeals() + "");
	}

	public int getCoast() {
		return coast;
	}

	public void setCoast(int coast) {
		this.coast = coast;
	}

	public void setScale(float scale) {
		sp.setScale(scale);
	}

	public void run() {
		final float[][] marshrut = { { 400.0f, 400.0f }, { 100.0f, 400.0f },
				{ 50.0f, 200.0f }, { 50.0f, 200.0f }, { 50.0f, 100.0f },
				{ 200.0f, 100.0f }, { 0, 0 } };

		float endX = marshrut[1][0];
		float endY = marshrut[1][1];
		startX = marshrut[0][0];
		startY = marshrut[0][1];
		int pointIndex = 1;
		int points = marshrut.length;
		float exitEndYH = endY + step;
		float exitEndYL = endY - step;
		float exitEndXH = endX + step;
		float exitEndXL = endX - step;
		while (true) {
			try {
				if (threadStopFlag)
					break;
				if (startX > endX) {
					startX = startX - step;
					sp.setPosition(startX, startY);
				}
				if (startY > endY) {
					startY = startY - step;
					sp.setPosition(startX, startY);
				}

				if (startX < endX) {
					startX = startX + step;
					sp.setPosition(startX, startY);
				}

				if (startY < endY) {
					startY = startY + step;
					sp.setPosition(startX, startY);
				}

				Thread.sleep(speed);
				if (threadStopFlag)
					break;

				// Log.i("[POINTS]", "points=" + points + "pointIndex="
				// + pointIndex);
				if (startY < exitEndYH && startY > exitEndYL
						&& startX < exitEndXH && startX > exitEndXL) {
					points--;

					// Log.i("[COO]", "startX=" + startX + "startY="
					// + startY + "endX=" + endX + "endY=" + endY);
					// Log.i("[POINTS]", "points=" + points
					// + "pointIndex=" + pointIndex);

					if (points == 1) {
						targetWent();
						break;
					}
					pointIndex++;
					startX = marshrut[pointIndex - 1][0];
					startY = marshrut[pointIndex - 1][1];

					endX = marshrut[pointIndex][0];
					endY = marshrut[pointIndex][1];
					exitEndYH = endY + step;
					exitEndYL = endY - step;
					exitEndXH = endX + step;
					exitEndXL = endX - step;
				}
			} catch (InterruptedException e) {
			}
		}

	}
}
