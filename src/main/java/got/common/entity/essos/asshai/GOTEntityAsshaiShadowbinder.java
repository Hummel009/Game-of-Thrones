package got.common.entity.essos.asshai;

import got.common.database.GOTRegistry;
import got.common.entity.ai.GOTEntityAIAsshaiShadowbinderUseStaff;
import net.minecraft.entity.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityAsshaiShadowbinder extends GOTEntityAsshaiWarrior {
	public GOTEntityAsshaiShadowbinder(World world) {
		super(world);
		tasks.addTask(1, new GOTEntityAIAsshaiShadowbinderUseStaff(this));
		isImmuneToFire = true;
		npcShield = null;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(25.0);
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(17, Byte.valueOf((byte) 0));
	}

	@Override
	public float getAlignmentBonus() {
		return 10.0f;
	}

	public boolean getIsUsingStaff() {
		return dataWatcher.getWatchableObjectByte(17) == 1;
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (getIsUsingStaff() && worldObj.isRemote) {
			for (int i = 0; i < 6; ++i) {
				double d = posX - 2.0 + rand.nextFloat() * 4.0f;
				double d1 = posY + rand.nextFloat() * 3.0f;
				double d2 = posZ - 2.0 + rand.nextFloat() * 4.0f;
				double d3 = (posX - d) / 8.0;
				double d4 = (posY + 0.5 - d1) / 8.0;
				double d5 = (posZ - d2) / 8.0;
				double d6 = Math.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
				double d7 = 1.0 - d6;
				double d8 = 0.0;
				double d9 = 0.0;
				double d10 = 0.0;
				if (d7 > 0.0) {
					d7 *= d7;
					d8 += d3 / d6 * d7 * 0.2;
					d9 += d4 / d6 * d7 * 0.2;
					d10 += d5 / d6 * d7 * 0.2;
				}
				worldObj.spawnParticle("smoke", d, d1, d2, d8, d9, d10);
			}
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		setCurrentItemOrArmor(0, new ItemStack(GOTRegistry.asshaiStaff));
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.asshaiStaff));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(GOTRegistry.asshaiBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTRegistry.asshaiLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTRegistry.asshaiChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTRegistry.asshaiMask));
		return data;
	}

	public void setIsUsingStaff(boolean flag) {
		dataWatcher.updateObject(17, flag ? (byte) 1 : 0);
	}
}