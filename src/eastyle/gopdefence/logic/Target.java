package eastyle.gopdefence.logic;

import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;
import android.util.Log;
import eastyle.gopdefence.GameActivity;
import eastyle.gopdefence.controller.TargetController;

public class Target extends Sprite implements Runnable {
	public int id;
	// public final Sprite targetSprite;
	// private Label labelHeals;
	private float startX;
	private float startY;
	private float step = 3.0f;
	private Thread thread;
	private boolean threadStopFlag = false;
	public boolean isDestroied = false;
	/* properties */
	private int speed;
	private int heals;
	private int coast;

	public Target(Object[] targetInfo) {
		super(0, 0, GameActivity.mRedTargetTextureRegion);
		hideTarget();
		speed = (Integer) targetInfo[1];
		heals = (Integer) targetInfo[2];
		coast = (Integer) targetInfo[3];
		GameActivity.globalScene.registerTouchArea(this);

		// TODO realise healse view element
		addHealsLabel();
		// end
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
		TargetController.targetsCount--;
		hideTarget();
		TargetController.checkTargets();
	}

	/**
	 * Called wich target not killed.
	 */
	private void targetWent() {
		// TODO subtract points from user
		// ....
		// end
		deleteTarget();
	}
//
//	public float getPositionX() {
//		return startX;
//	}
//
//	public float getPositionY() {
//		return startY;
//	}

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

	private void addHealsLabel() {
		// labelHeals = Label.label("Console", "DroidSans", 14.0f);
		// labelHeals.setColor(new CCColor3B(0, 0, 250));
		// labelHeals.setString(getHeals() + "");
		// labelHeals.setAnchorPoint(-0.3f, -0.3f);
		// sp.addChild(labelHeals);
	}

	public void viewHeals() {
		// labelHeals.setString(getHeals() + "");
	}

	public int getCoast() {
		return coast;
	}

	public void setCoast(int coast) {
		this.coast = coast;
	}

	public void hideTarget() {
		setVisible(false);
	}

	public void showTarget() {
		setVisible(true);
	}

	public void run() {
		showTarget();
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
					setPosition(startX, startY);
				}
				if (startY > endY) {
					startY = startY - step;
					setPosition(startX, startY);
				}

				if (startX < endX) {
					startX = startX + step;
					setPosition(startX, startY);
				}

				if (startY < endY) {
					startY = startY + step;
					setPosition(startX, startY);
				}

				Thread.sleep(speed);
				if (threadStopFlag)
					break;

				if (startY < exitEndYH && startY > exitEndYL
						&& startX < exitEndXH && startX > exitEndXL) {
					points--;

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

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		Log.i("Target ", "touch target");
		// TODO Show info about tower or update tools
		// ....
		// end
		return true;
	}
}
