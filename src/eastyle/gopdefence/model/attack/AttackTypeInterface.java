package eastyle.gopdefence.model.attack;

import eastyle.gopdefence.logic.Target;
import eastyle.gopdefence.logic.Tower;

public interface AttackTypeInterface {
	public void attackTarget(final Tower tower, final Target target);
}
