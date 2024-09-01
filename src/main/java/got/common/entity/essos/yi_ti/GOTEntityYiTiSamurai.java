package got.common.entity.essos.yi_ti;

import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.database.GOTShields;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityYiTiSamurai extends GOTEntityYiTiMan {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityYiTiSamurai(World world) {
		super(world);
		addTargetTasks(true);
	}

	@Override
	public GOTShields getShield() {
		return GOTShields.YI_TI_SAMURAI;
	}

	@Override
	public GOTCapes getCape() {
		return GOTCapes.YI_TI_SAMURAI;
	}

	@Override
	public float getReputationBonus() {
		return 3.0f;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(Items.iron_sword));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.yiTiSamuraiBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.yiTiSamuraiLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.yiTiSamuraiChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.yiTiSamuraiHelmet));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}