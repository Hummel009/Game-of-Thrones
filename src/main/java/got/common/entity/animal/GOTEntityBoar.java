package got.common.entity.animal;

import got.common.entity.ai.GOTEntityAIAttackOnCollide;
import got.common.world.biome.GOTBiome;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class GOTEntityBoar extends GOTEntityHorse {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityBoar(World world) {
		super(world);
		setSize(0.9f, 0.8f);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0);
	}

	@Override
	public double clampChildHealth(double health) {
		return MathHelper.clamp_double(health, 10.0, 30.0);
	}

	@Override
	public double clampChildSpeed(double speed) {
		return MathHelper.clamp_double(speed, 0.08, 0.35);
	}

	@Override
	public EntityAIBase createMountAttackAI() {
		return new GOTEntityAIAttackOnCollide(this, 1.2, true);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		int meat = rand.nextInt(3) + 1 + rand.nextInt(1 + i);
		for (int l = 0; l < meat; ++l) {
			if (isBurning()) {
				dropItem(Items.cooked_porkchop, 1);
				continue;
			}
			dropItem(Items.porkchop, 1);
		}
	}

	@Override
	public void func_145780_a(int i, int j, int k, Block block) {
		playSound("mob.pig.step", 0.15f, 1.0f);
	}

	@Override
	public String getAngrySoundName() {
		return "mob.pig.say";
	}

	@Override
	public String getDeathSound() {
		return "mob.pig.death";
	}

	@Override
	public int getHorseType() {
		return 0;
	}

	@Override
	public String getHurtSound() {
		return "mob.pig.say";
	}

	@Override
	public String getLivingSound() {
		return "mob.pig.say";
	}

	@Override
	public boolean isBreedingItem(ItemStack itemstack) {
		return itemstack != null && itemstack.getItem() == Items.carrot;
	}

	@Override
	public boolean isMountHostile() {
		return true;
	}

	@Override
	public void onGOTHorseSpawn() {
		double maxHealth = getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue();
		maxHealth = Math.min(maxHealth, 25.0);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(maxHealth);
		double speed = getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(speed);
	}
}