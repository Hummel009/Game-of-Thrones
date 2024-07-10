package got.common.entity.westeros.legendary.captain;

import got.common.database.*;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.other.iface.GOTUnitTradeable;
import got.common.faction.GOTFaction;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityJanosSlynt extends GOTEntityHumanBase implements GOTUnitTradeable {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityJanosSlynt(World world) {
		super(world);
		setupLegendaryNPC(true);
	}

	@Override
	public GOTCapes getCape() {
		return GOTCapes.CROWNLANDS;
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.CROWNLANDS;
	}

	@Override
	public float getAlignmentBonus() {
		return 200.0f;
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killJanosSlynt;
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		entityDropItem(new ItemStack(GOTItems.coin, 2, 2), 0.0f);
	}

	@Override
	public GOTUnitTradeEntries getUnits() {
		return GOTUnitTradeEntries.KINGSGUARD;
	}

	@Override
	public GOTInvasions getWarhorn() {
		return null;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.alloySteelSword));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.crownlandsBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.crownlandsLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.crownlandsChestplate));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}