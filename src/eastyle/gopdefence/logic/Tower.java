package eastyle.gopdefence.logic;

import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.util.Log;
import eastyle.gopdefence.GameActivity;
import eastyle.gopdefence.controller.TowerController;
import eastyle.gopdefence.maps.FirstMap;
import eastyle.gopdefence.view.AttackRadius;

public class Tower extends AnimatedSprite implements Runnable {
	private boolean isTargetCaptured = false;
	private Thread thread;
	/* properties */
	private int attackDelay;
	private float attackRange;
	private int attackDamage;
	private int towerCoast;
	private int towerAttackType;

	/**
	 * @param Id Tower index from towers array in FirstMap
	 */
	
	public Tower(int Id) {
		super(0, 0, 50, 50, GameActivity.loadNewTowerTexture(Id));
//		AnimatedSprite mAnimatedSprite = new AnimatedSprite(300, 100, (TiledTextureRegion) GameActivity.mPlantTextureRegion );
//		//Включаем простую последовательную анимацию с частотой 100 мсек.
//		mAnimatedSprite.animate(100);
		
		/* Set Tower Propertyes */
		Object[] towerInfo = FirstMap.getTestTowerProperties()[Id];
		setAttackDamage((Integer) towerInfo[1]);
		setAttackRange((Float) towerInfo[2]);
		setAttackDelay((Integer) towerInfo[3]);
		setTowerCoast((Integer) towerInfo[4]);
		setTowerAttackType((Integer) towerInfo[5]);
		
		/* init tower */
//		tower = new Sprite(0, 0, GameActivity.mBlueTargetTextureRegion) {
//			/* Click on tower */
//			@Override
//			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
//					final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
//				Log.i("onAreaTouched", "onAreaTouched");
//				showRange();
//				//TODO Show Tower info
//				//....
//				// end
//				return true;
//			}
//		};
		GameActivity.globalScene.registerTouchArea(this);

		/* init main tower Thread */
		thread = new Thread(this);
		thread.start();
	}

	public void showRange() {
		attachChild(new AttackRadius(getWidth(),getHeight(), attackRange).getAttackRadiusObject());
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

	public void run() {
		try {
			while (!isTargetCaptured()) {
				if (!isTargetCaptured()) {
					// Log.i("[SEARCHING]", "Tower searching target");
					new TowerController().searchTarget(this);
				}
				Thread.sleep(200);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		Log.i("onAreaTouched", "onAreaTouched");
		showRange();
		//TODO Show Tower info
		//....
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
