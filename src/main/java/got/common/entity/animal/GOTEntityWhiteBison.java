package got.common.entity.animal;

import got.common.database.GOTRegistry;
import got.common.entity.ai.GOTEntityAIAttackOnCollide;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.world.World;

public class GOTEntityWhiteBison extends GOTEntityBison {
	public GOTEntityWhiteBison(World world) {
		super(world);
		setSize(aurochsWidth * 1.15f, aurochsHeight * 1.15f);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0);
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5.0);
	}

	@Override
	public EntityAIBase createAurochsAttackAI() {
		return new GOTEntityAIAttackOnCollide(this, 1.9, true);
	}

	@Override
	public EntityCow createChild(EntityAgeable entity) {
		return new GOTEntityWhiteBison(worldObj);
	}

	@Override
	public void dropHornItem(boolean flag, int i) {
		dropItem(GOTRegistry.kineArawHorn, 1);
	}
}
