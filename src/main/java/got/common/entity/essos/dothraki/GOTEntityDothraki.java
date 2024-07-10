package got.common.entity.essos.dothraki;

import got.common.GOTConfig;
import got.common.database.GOTChestContents;
import got.common.database.GOTItems;
import got.common.database.GOTNames;
import got.common.database.GOTSpeech;
import got.common.entity.ai.GOTEntityAIDothrakiSkirmish;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.other.utils.GOTWeaponSetFactory;
import got.common.faction.GOTFaction;
import got.common.quest.GOTMiniQuestFactory;
import got.common.world.biome.GOTBiome;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.List;

public class GOTEntityDothraki extends GOTEntityHumanBase implements GOTBiome.ImmuneToHeat {
	private int skirmish;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityDothraki(World world) {
		super(world);
		int target = addTargetTasks(true);
		targetTasks.addTask(target + 1, new GOTEntityAIDothrakiSkirmish(this, true));
		spawnRidingHorse = rand.nextInt(10) == 0;
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.DOTHRAKI;
	}

	@Override
	public GOTMiniQuestFactory getMiniQuestFactory() {
		return GOTMiniQuestFactory.DOTHRAKI;
	}

	@Override
	public float getAlignmentBonus() {
		return 1.0f;
	}

	public boolean canDothrakiSkirmish() {
		return !questInfo.anyActiveQuestPlayers();
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		int bones = rand.nextInt(2) + rand.nextInt(i + 1);
		for (int l = 0; l < bones; ++l) {
			dropItem(Items.bone, 1);
		}
		if (rand.nextInt(6) == 0) {
			dropChestContents(GOTChestContents.DOTHRAKI, 1, 2 + i);
		}
	}

	public boolean isDothrakSkirmishing() {
		return skirmish > 0;
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (!worldObj.isRemote && isDothrakSkirmishing()) {
			if (!GOTConfig.enableDothrakiSkirmish) {
				skirmish = 0;
			} else if (!(getAttackTarget() instanceof GOTEntityDothraki)) {
				--skirmish;
			}
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		GOTWeaponSetFactory.setupPrimitiveIronWeaponSet(this, rand, true);

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.dothrakiBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.dothrakiLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.dothrakiChestplate));

		return entityData;
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		skirmish = nbt.getInteger("Skirmish");
	}

	public void setDothrakiSkirmishing() {
		int prevSkirmishTick = skirmish;
		skirmish = 160;
		if (!worldObj.isRemote && prevSkirmishTick == 0) {
			List<EntityPlayer> nearbyPlayers = worldObj.getEntitiesWithinAABB(EntityPlayer.class, boundingBox.expand(24.0, 24.0, 24.0));
			for (EntityPlayer nearbyPlayer : nearbyPlayers) {
				GOTSpeech.sendSpeech(nearbyPlayer, this, GOTSpeech.getRandomSpeechForPlayer(this, "special/gladiator", nearbyPlayer));
			}
		}
	}

	@Override
	public void setupNPCName() {
		familyInfo.setName(GOTNames.getDothrakiName(rand, familyInfo.isMale()));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("Skirmish", skirmish);
	}
}