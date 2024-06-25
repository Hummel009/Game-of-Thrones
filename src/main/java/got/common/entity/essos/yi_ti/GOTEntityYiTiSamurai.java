package got.common.entity.essos.yi_ti;

import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.database.GOTShields;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityYiTiSamurai extends GOTEntityYiTiMan {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityYiTiSamurai(World world) {
		super(world);
		addTargetTasks(true);
		shield = GOTShields.YI_TI_SAMURAI;
		cape = GOTCapes.YI_TI_SAMURAI;
	}

	@Override
	public float getAlignmentBonus() {
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

		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.yiTiSword));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.yiTiBootsSamurai));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.yiTiLeggingsSamurai));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.yiTiChestplateSamurai));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.yiTiHelmetSamurai));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}