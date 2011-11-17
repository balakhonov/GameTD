package eastyle.gopdefence.view;

import org.anddev.andengine.entity.modifier.ColorModifier;

import eastyle.gopdefence.shapes.Ellipse;

public class AttackRadius {
	private static Ellipse ellipse;

	public AttackRadius(float w, float h, float attackRange) {
		if (ellipse != null) {
			detachSelf();
		}
		ellipse = new Ellipse(w / 2, h / 2, attackRange);
		ellipse.setColor(0, 255, 0);
		ellipse.registerEntityModifier(new ColorModifier(30, 0, 255, 100, 200,
				255, 100));
		ellipse.setLineWidth(1f);
		ellipse.setVisible(true);
	}

	public Ellipse getAttackRadiusObject() {
		return ellipse;
	}

	private void detachSelf() {
		ellipse.detachSelf();
	}

	public static void hideRadius() {
		if (ellipse == null)
			return;
		ellipse.setVisible(false);
	}
}
