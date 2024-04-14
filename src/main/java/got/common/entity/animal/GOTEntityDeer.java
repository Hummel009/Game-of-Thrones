package got.common.entity.animal;

import got.common.database.GOTItems;
import got.common.entity.ai.GOTEntityAIAttackOnCollide;
import got.common.entity.other.GOTRandomSkinEntity;
import got.common.world.biome.GOTBiome;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.UUID;

public class GOTEntityDeer extends GOTEntityHorse implements GOTRandomSkinEntity, GOTBiome.ImmuneToFrost {
	@SuppressWarnings("WeakerAccess")
	public GOTEntityDeer(World world) {
		super(world);
		setSize(1.6f, 1.8f);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0);
	}

	@Override
	public double clampChildHealth(double health) {
		return MathHelper.clamp_double(health, 16.0, 50.0);
	}

	@Override
	public double clampChildSpeed(double speed) {
		return MathHelper.clamp_double(speed, 0.08, 0.34);
	}

	@Override
	public EntityAIBase createMountAttackAI() {
		return new GOTEntityAIAttackOnCollide(this, 1.25, true);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		int hide = rand.nextInt(3) + rand.nextInt(1 + i);
		for (int l = 0; l < hide; ++l) {
			dropItem(Items.leather, 1);
		}
		int meat = rand.nextInt(3) + rand.nextInt(1 + i);
		for (int l = 0; l < meat; ++l) {
			if (isBurning()) {
				dropItem(GOTItems.deerCooked, 1);
				continue;
			}
			dropItem(GOTItems.deerRaw, 1);
		}
	}

	@Override
	public String getDeathSound() {
		return "got:elk.death";
	}

	@Override
	public int getHorseType() {
		return 0;
	}

	@Override
	public String getHurtSound() {
		return "got:elk.hurt";
	}

	@Override
	public String getLivingSound() {
		return "got:elk.say";
	}

	@Override
	public boolean isBreedingItem(ItemStack itemstack) {
		return itemstack != null && itemstack.getItem() == Items.wheat;
	}

	@Override
	public boolean isMountHostile() {
		return true;
	}

	@Override
	public void onGOTHorseSpawn() {
		double maxHealth = getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(maxHealth * (1.0f + rand.nextFloat() * 0.5f));
	}

	@Override
	public void setUniqueID(UUID uuid) {
		entityUniqueID = uuid;
	}
}