package got.common;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;

public class GOTPotionChanges {
	private GOTPotionChanges() {
	}

	public static void preInit() {
		Potion.damageBoost.func_111184_a(SharedMonsterAttributes.attackDamage, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 2.0, 2);
		Potion.weakness.func_111184_a(SharedMonsterAttributes.attackDamage, "22653B89-116E-49DC-9B6B-9971489B5BE5", 2.0, 0);
	}
}