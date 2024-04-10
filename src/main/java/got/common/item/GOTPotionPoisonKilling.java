package got.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.GOT;
import got.common.GOTDamage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class GOTPotionPoisonKilling extends Potion {
	public GOTPotionPoisonKilling() {
		super(30, true, poison.getLiquidColor());
		setPotionName("got.potion.drinkPoison");
		setEffectiveness(poison.getEffectiveness());
		setIconIndex(0, 0);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean hasStatusIcon() {
		return false;
	}

	@Override
	public boolean isReady(int tick, int level) {
		int freq = 5 >> level;
		return freq == 0 || tick % freq == 0;
	}

	@Override
	public void performEffect(EntityLivingBase entity, int level) {
		entity.attackEntityFrom(GOTDamage.poisonDrink, 1.0f);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
		GOT.proxy.renderCustomPotionEffect(x, y, effect, mc);
	}
}
