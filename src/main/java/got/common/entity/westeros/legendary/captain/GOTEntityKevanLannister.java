package got.common.entity.westeros.legendary.captain;

import got.common.database.*;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.iface.GOTUnitTradeable;
import got.common.faction.GOTFaction;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityKevanLannister extends GOTEntityHumanBase implements GOTUnitTradeable {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityKevanLannister(World world) {
		super(world);
		setupLegendaryNPC(true);
		faction = GOTFaction.WESTERLANDS;
		alignmentBonus = 300.0f;
		killAchievement = GOTAchievement.killKevanLannister;
	}

	@Override
	public String getSpeechBank(GOTEntityNPC npc, EntityPlayer entityPlayer) {
		if (npc.isFriendly(entityPlayer)) {
			return "legendary/kevan_friendly";
		}
		return GOTSpeech.HOSTILE;
	}

	@Override
	public GOTUnitTradeEntries getUnits() {
		return GOTUnitTradeEntries.WESTERLANDS;
	}

	@Override
	public GOTInvasions getWarhorn() {
		return GOTInvasions.WESTERLANDS;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.alloySteelDagger));
		npcItemsInv.setIdleItem(null);

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.westerlandsBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.westerlandsLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.westerlandsChestplate));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}