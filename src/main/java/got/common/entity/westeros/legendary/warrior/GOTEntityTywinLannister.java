package got.common.entity.westeros.legendary.warrior;

import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import got.common.database.GOTSpeech;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.faction.GOTFaction;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityTywinLannister extends GOTEntityHumanBase {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityTywinLannister(World world) {
		super(world);
		addTargetTasks(true);
		setupLegendaryNPC(true);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		dropItem(GOTItems.lannisterBrooch, 1);
		dropItem(GOTItems.widowWail, 1);
		dropItem(GOTItems.oathkeeper, 1);
		dropItem(GOTItems.westkingBoots, 1);
		dropItem(GOTItems.westkingLeggings, 1);
		dropItem(GOTItems.westkingChestplate, 1);
		dropItem(GOTItems.westkingHelmet, 1);
	}

	@Override
	public float getAlignmentBonus() {
		return 500.0f;
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.WESTERLANDS;
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killTywinLannister;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityPlayer) {
		if (isFriendly(entityPlayer)) {
			return "legendary/tywin_friendly";
		}
		return GOTSpeech.HOSTILE;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.westerosSword));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.westkingBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.westkingLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.westkingChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.westkingHelmet));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}