package eastyle.td.core.logic;

import org.anddev.andengine.entity.modifier.ColorModifier;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;

import android.util.Log;

import eastyle.gopdefence.GameActivity;
import eastyle.gopdefence.shapes.Ellipse;
import eastyle.gopdefence.view.GameZone;
import eastyle.resourse.maps.FirstMap;
import eastyle.td.core.controller.TowerController;

public class Tower implements Runnable {
	private Sprite tower;
	private boolean isTargetCaptured = false;
	private Thread thread;
	/* properties */
	private int attackDelay;
	private float attackRange;
	private int attackDamage;
	private int towerCoast;

	public Tower(int Id) {
		/* Set Tower Propertyes */
		Object[] towerInfo = FirstMap.getTestTowerProperties()[Id];
		
		setAttackDamage((Integer) towerInfo[1]);
		setAttackRange((Float) towerInfo[2]);
		setAttackDelay((Integer) towerInfo[3]);
		setTowerCoast((Integer) towerInfo[4]);
		tower = new Sprite(0, 0, GameActivity.mBlueTargetTextureRegion) {

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
		GameActivity.globalScene.registerTouchArea(tower);

		thread = new Thread(this);
		thread.start();
	}

	public void animateShot() {
		new Thread(new Runnable() {
			public void run() {
				try {
					tower.setScale(1.4f);
					Thread.sleep(150);
					tower.setScale(1.2f);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void run() {
		try {
			while (!isTargetCaptured()) {
				if (!isTargetCaptured()) {
					Log.i("[SEARCHING]", "Tower searching target");
					new TowerController().searchTarget(this);
				}
				Thread.sleep(200);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getAttackDamage() {
		// TODO Add to attack damege point bufs from attackDmgBuffList
		return attackDamage;
	}

	public void setAttackDamage(int attackDamage) {
		this.attackDamage = attackDamage;
	}

	public float getAttackRange() {
		// TODO Add to attack range point bufs from attackRangeBuffList
		return attackRange;
	}

	public void setAttackRange(float attackRange) {
		this.attackRange = attackRange;
	}

	public int getAttackDelay() {
		// TODO Add to attack delay point bufs from attackDelayBuffList
		return attackDelay;
	}

	public void setAttackDelay(int attackDelay) {
		this.attackDelay = attackDelay;
	}

	public boolean isTargetCaptured() {
		return isTargetCaptured;
	}

	public void setTargetCaptured(boolean isShooting) {
		this.isTargetCaptured = isShooting;
	}

	public int getTowerCoast() {
		return towerCoast;
	}

	public void setTowerCoast(int towerCoast) {
		this.towerCoast = towerCoast;
	}

	public Sprite getTowerSprite() {
		return tower;
	}

	public void activate() {
		thread.run();
	}
}
