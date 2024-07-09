package got.common.entity.westeros.legendary.trader;

import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import got.common.database.GOTTradeEntries;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.other.iface.GOTSmith;
import got.common.faction.GOTFaction;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityTobhoMott extends GOTEntityHumanBase implements GOTSmith {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityTobhoMott(World world) {
		super(world);
		setupLegendaryNPC(true);
		faction = GOTFaction.CROWNLANDS;
		alignmentBonus = 10.0f;
		killAchievement = GOTAchievement.killTobhoMott;
	}

	@Override
	public GOTTradeEntries getSellsPool() {
		return GOTTradeEntries.TOBHO_SELLS;
	}

	@Override
	public GOTTradeEntries getBuysPool() {
		return GOTTradeEntries.EMPTY_BUYS;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.blacksmithHammer));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}