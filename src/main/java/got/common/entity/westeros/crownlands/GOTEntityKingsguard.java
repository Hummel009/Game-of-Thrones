package got.common.entity.westeros.crownlands;

import got.common.database.GOTAchievement;
import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.database.GOTShields;
import got.common.entity.ai.GOTEntityAINearestAttackableTargetPatriot;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityKingsguard extends GOTEntityCrownlandsMan {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityKingsguard(World world) {
		super(world);
		addTargetTasks(true, GOTEntityAINearestAttackableTargetPatriot.class);
		shield = GOTShields.CROWNLANDS;
		cape = GOTCapes.ROYALGUARD;
		alignmentBonus = 3.0f;
		killAchievement = GOTAchievement.killKingsguard;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.alloySteelSword));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.kingsguardBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.kingsguardLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.kingsguardChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.kingsguardHelmet));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}