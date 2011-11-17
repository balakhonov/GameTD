package eastyle.gopdefence.logic;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.input.touch.TouchEvent;
import android.util.Log;
import eastyle.gopdefence.GameActivity;
import eastyle.gopdefence.controller.TowerController;
import eastyle.gopdefence.maps.FirstMap;
import eastyle.gopdefence.model.attack.AttackTypeInterface;
import eastyle.gopdefence.model.attack.MassPointAttack;
import eastyle.gopdefence.model.attack.PointHomingAttack;
import eastyle.gopdefence.model.attack.TeslaAttack;
import eastyle.gopdefence.view.AttackRadius;
import eastyle.gopdefence.view.GameZone;

public class Tower extends AnimatedSprite implements Runnable {
	private boolean isTargetCaptured = false;
	private Thread thread;
	public int towerId = 0;
	/* properties */
	private int attackDelay;
	private float attackRange;
	private int attackDamage;
	private int towerCoast;
	private int towerAttackType;
	private AttackTypeInterface attackType;

	/**
	 * @param Id
	 *            Tower index from towers array in FirstMap
	 */

	public Tower(int Id) {
		super(0, 0, FirstMap.mapElementSize, FirstMap.mapElementSize,
				GameActivity.loadNewTowerTexture(Id));
		towerId = TowerController.towerId;
		TowerController.towerId++;
		/* Set Tower Propertyes */
		Object[] towerInfo = FirstMap.getTestTowerProperties()[Id];
		setAttackDamage((Integer) towerInfo[1]);
		setAttackRange((Float) towerInfo[2]);
		setAttackDelay((Integer) towerInfo[3]);
		setTowerCoast((Integer) towerInfo[4]);
		setTowerAttackType((Integer) towerInfo[5]);

		/* Set Tower Type */
		setTowerType(getTowerAttackType());

		GameActivity.globalScene.registerTouchArea(this);

		/* init main tower Thread */
		thread = new Thread(this);

		/* activate tower */
		activateTower();
	}

	private void setTowerType(int towerType) {
		switch (towerType) {
		case 0:
			attackType = new PointHomingAttack();
			break;
		case 1:
			attackType = new TeslaAttack(this);
			break;
		case 2:
			attackType = new MassPointAttack();
			break;
		default:
			break;
		}
	}

	public void activateTower() {
		thread.start();
	}

	public void showRange() {
		attachChild(new AttackRadius(getWidth(), getHeight(), attackRange)
				.getAttackRadiusObject());
		Log.i("showRange", "" + getChildCount());
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

	private void searchTarget() {
		float distance;
		try {
			for (Target target : GameZone.globalTargets) {
				//Log.w("Targets count", GameZone.globalTargets.size() + "");
				if (!target.isDestroied && target.isVisible()) {
					distance = getDistance((IEntity) this, target);
					if (distance < this.getAttackRange()) {
						attackType.attackTarget(this, target);
						break;
					}
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized static float getDistance(final IEntity aTtower,
			final IEntity aTarget) {
		return (float) Math.sqrt(Math.pow((aTtower.getX() - aTarget.getX()), 2)
				+ Math.pow((aTtower.getY() - aTarget.getY()), 2));
	}

	public void run() {
		try {
			while (!isTargetCaptured()) {
				if (!isTargetCaptured()) {
					// Log.i("[SEARCHING]", "Tower searching target");
					searchTarget();
				}
				/* перезарядка */
				int sleepStep = 0;
				while (sleepStep < (1000)) {
					GameZone.isPause();
					sleepStep += 7 * GameZone.gameSpeed;
					Thread.sleep(7);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		Log.i("onAreaTouched", "onAreaTouched");
		if (pSceneTouchEvent.isActionDown()) {
			showRange();
		}
		// TODO Show Tower info
		// ....
		// end
		return true;
	}

	public int getTowerAttackType() {
		return towerAttackType;
	}

	public void setTowerAttackType(int towerAttackType) {
		this.towerAttackType = towerAttackType;
	}
}
