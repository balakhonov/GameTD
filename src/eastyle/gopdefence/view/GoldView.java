package eastyle.gopdefence.view;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.util.HorizontalAlign;

import eastyle.gopdefence.GameActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;



public class GoldView {
	private static int gold = 400;
	private static int oldGold = 400;
	
	//private Font mFont = new Font(GameActivity.mFontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 30, true, Color.BLACK);
	//private Text goldText = new Text(300, 5, mFont , "Gold: " + gold);
	//final ChangeableText fpsText = new ChangeableText(250, 240, this.mFont, "FPS:", "FPS: XXXXX".length());

    static ChangeableText textRight;
	public GoldView() {
		textRight = new ChangeableText(200, 5, GameActivity.mFont, "Gold:","Gold: XXXXXX".length());
		//textRight.setText("Gold: "+ gold);
	}
	public IEntity getGoldLabel() {
		return textRight;
	}
	public static void setGold(int gold) {
		GoldView.gold = gold;
	}

	public static void addGold(int aGold) {
		setGold(getGold() + aGold);
		//label.setString("Gold: " + gold);
		textRight.setText("Gold: "+ gold);
	}

	public static int getGold() {
		return gold;
	}

	public static void calculateGold() {
		GoldView.oldGold = gold;
		textRight.setText("Gold: "+ gold);
	}
	
	public static void resetGold() {
		gold = oldGold;
		textRight.setText("Gold: "+ gold);
		
	}
	public static void delGold(int aGold) {
		setGold(getGold() - aGold);
		textRight.setText("Gold: "+ gold);
	}
	

}
