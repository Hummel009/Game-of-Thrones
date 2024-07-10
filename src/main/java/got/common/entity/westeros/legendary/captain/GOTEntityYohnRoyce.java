package got.common.entity.westeros.legendary.captain;

import got.common.database.GOTAchievement;
import got.common.database.GOTInvasions;
import got.common.database.GOTItems;
import got.common.database.GOTUnitTradeEntries;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.other.iface.GOTUnitTradeable;
import got.common.faction.GOTFaction;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityYohnRoyce extends GOTEntityHumanBase implements GOTUnitTradeable {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityYohnRoyce(World world) {
		super(world);
		setupLegendaryNPC(true);
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.ARRYN;
	}

	@Override
	public float getAlignmentBonus() {
		return 100.0f;
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killYohnRoyce;
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		dropItem(GOTItems.lamentation, 1);
	}

	@Override
	public GOTUnitTradeEntries getUnits() {
		return GOTUnitTradeEntries.ARRYN;
	}

	@Override
	public GOTInvasions getWarhorn() {
		return GOTInvasions.ARRYN;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.lamentation));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.bronzeChainmailBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.bronzeLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.bronzeChainmailChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.bronzeHelmet));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}