package got.common.entity.westeros.legendary.warrior;

import got.common.database.GOTAchievement;
import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.faction.GOTFaction;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityMerynTrant extends GOTEntityHumanBase {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityMerynTrant(World world) {
		super(world);
		addTargetTasks(true);
		setupLegendaryNPC(true);
		cape = GOTCapes.ROYALGUARD;
		faction = GOTFaction.CROWNLANDS;
		alignmentBonus = 100.0f;
		killAchievement = GOTAchievement.killMerynTrant;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.alloySteelSword));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.kingsguardBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.kingsguardLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.kingsguardChestplate));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}