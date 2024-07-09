package got.common.entity.westeros.legendary.warrior;

import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.faction.GOTFaction;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityLorasTyrell extends GOTEntityHumanBase {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityLorasTyrell(World world) {
		super(world);
		setupLegendaryNPC(true);
		faction = GOTFaction.STORMLANDS;
		alignmentBonus = 200.0f;
		killAchievement = GOTAchievement.killLorasTyrell;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.alloySteelSword));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.reachguardBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.reachguardLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.reachguardChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.reachguardHelmet));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}