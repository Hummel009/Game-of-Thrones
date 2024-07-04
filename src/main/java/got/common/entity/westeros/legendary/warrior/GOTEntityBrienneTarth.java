package got.common.entity.westeros.legendary.warrior;

import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import got.common.database.GOTSpeech;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.faction.GOTFaction;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityBrienneTarth extends GOTEntityHumanBase {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityBrienneTarth(World world) {
		super(world);
		setupLegendaryNPC(true);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0);
	}

	@Override
	public float getAlignmentBonus() {
		return 200.0f;
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.STORMLANDS;
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killBrienneTarth;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityPlayer) {
		if (isFriendly(entityPlayer)) {
			return "legendary/brienne_friendly";
		}
		return GOTSpeech.HOSTILE;
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
		familyInfo.setMale(false);
	}
}