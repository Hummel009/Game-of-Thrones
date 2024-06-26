package got.common.entity.westeros.legendary.reborn;

import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import got.common.database.GOTSpeech;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.faction.GOTFaction;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class GOTEntityLancelLannister extends GOTEntityHumanBase {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityLancelLannister(World world) {
		super(world);
		setupLegendaryNPC(true);
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.CROWNLANDS;
	}

	@Override
	public float getAlignmentBonus() {
		return 100.0f;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.westerosDagger));
		npcItemsInv.setIdleItem(null);

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}

	public static class LancelLannisterNormal extends GOTEntityLancelLannister {
		@SuppressWarnings({"WeakerAccess", "unused"})
		public LancelLannisterNormal(World world) {
			super(world);
		}

		@Override
		public void onDeath(DamageSource damagesource) {
			super.onDeath(damagesource);
			if (!worldObj.isRemote) {
				LancelLannisterReligious religious = new LancelLannisterReligious(worldObj);
				religious.copyLocationAndAnglesFrom(this);
				religious.onSpawnWithEgg(null);
				worldObj.spawnEntityInWorld(religious);
				setDead();
			}
		}
	}

	public static class LancelLannisterReligious extends GOTEntityLancelLannister {
		@SuppressWarnings({"WeakerAccess", "unused"})
		public LancelLannisterReligious(World world) {
			super(world);
		}

		@Override
		public GOTAchievement getKillAchievement() {
			return GOTAchievement.killLancelLannister;
		}

		@Override
		public String getSpeechBank(EntityPlayer entityPlayer) {
			return GOTSpeech.getFatherGrigoriSpeech(this, entityPlayer);
		}
	}
}