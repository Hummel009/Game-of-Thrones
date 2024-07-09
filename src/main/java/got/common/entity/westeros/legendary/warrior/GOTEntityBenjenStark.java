package got.common.entity.westeros.legendary.warrior;

import got.common.database.GOTAchievement;
import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.database.GOTSpeech;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.utils.IceUtils;
import got.common.faction.GOTFaction;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class GOTEntityBenjenStark extends GOTEntityHumanBase {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityBenjenStark(World world) {
		super(world);
		addTargetTasks(true);
		setupLegendaryNPC(true);
		cape = GOTCapes.NIGHT;
		faction = GOTFaction.NIGHT_WATCH;
		killAchievement = GOTAchievement.killBenjenStark;
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		boolean causeDamage = IceUtils.calculateDamage(this, damagesource, true);
		return super.attackEntityFrom(damagesource, causeDamage ? f : 0.0f);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		dropItem(GOTItems.valyrianSword, 1);
	}

	@Override
	public String getSpeechBank(GOTEntityNPC npc, EntityPlayer entityPlayer) {
		if (npc.isFriendly(entityPlayer)) {
			return "legendary/benjen_friendly";
		}
		return GOTSpeech.HOSTILE;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.valyrianSword));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());

		npcItemsInv.setMeleeWeaponMounted(new ItemStack(GOTItems.valyrianSword));
		npcItemsInv.setIdleItemMounted(npcItemsInv.getMeleeWeapon());

		return entityData;
	}

	public GOTEntityNPC setIsRider(boolean is) {
		spawnRidingHorse = is;
		return this;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}