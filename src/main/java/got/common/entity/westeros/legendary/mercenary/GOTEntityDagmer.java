package got.common.entity.westeros.legendary.mercenary;

import got.common.database.GOTItems;
import got.common.database.GOTSpeech;
import got.common.database.GOTUnitTradeEntries;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.other.iface.GOTMercenary;
import got.common.faction.GOTFaction;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityDagmer extends GOTEntityHumanBase implements GOTMercenary {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityDagmer(World world) {
		super(world);
		addTargetTasks(true);
		setupLegendaryNPC(true);
	}

	@Override
	public boolean canTradeWith(EntityPlayer entityPlayer) {
		return isFriendly(entityPlayer);
	}

	@Override
	public float getAlignmentBonus() {
		return 10.0f;
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.IRONBORN;
	}

	@Override
	public float getMercAlignmentRequired() {
		return GOTUnitTradeEntries.SOLDIER_F;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityPlayer) {
		return GOTSpeech.getCaptainSpeech(this, entityPlayer);
	}

	@Override
	public int getMercBaseCost() {
		return GOTUnitTradeEntries.SOLDIER * 5;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.alloySteelSword));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}