package got.common.entity.essos.mossovy;

import got.common.database.GOTChestContents;
import got.common.database.GOTFoods;
import got.common.database.GOTItems;
import got.common.database.GOTNames;
import got.common.entity.ai.*;
import got.common.entity.animal.GOTEntityHorse;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.iface.GOTNPCMount;
import got.common.faction.GOTFaction;
import got.common.quest.GOTMiniQuest;
import got.common.quest.GOTMiniQuestFactory;
import got.common.world.biome.GOTBiome;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityMossovyMan extends GOTEntityHumanBase implements GOTBiome.ImmuneToFrost {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityMossovyMan(World world) {
		super(world);
		setSize(0.6f, 1.8f);
		getNavigator().setAvoidsWater(true);
		getNavigator().setBreakDoors(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new GOTEntityAIHiredRemainStill(this));
		tasks.addTask(2, createMossovyAttackAI());
		tasks.addTask(3, new GOTEntityAIFollowHiringPlayer(this));
		tasks.addTask(4, new EntityAIOpenDoor(this, true));
		tasks.addTask(5, new EntityAIWander(this, 1.0));
		tasks.addTask(6, new GOTEntityAIEat(this, GOTFoods.WESTEROS, 8000));
		tasks.addTask(6, new GOTEntityAIDrink(this, GOTFoods.WESTEROS_DRINK, 8000));
		tasks.addTask(7, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0f, 0.02f));
		tasks.addTask(7, new EntityAIWatchClosest2(this, GOTEntityNPC.class, 5.0f, 0.02f));
		tasks.addTask(8, new EntityAIWatchClosest(this, EntityLiving.class, 8.0f, 0.02f));
		tasks.addTask(9, new EntityAILookIdle(this));
		addTargetTasks(true);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22);
	}

	@Override
	public GOTMiniQuest createMiniQuest() {
		return GOTMiniQuestFactory.MOSSOVY.createQuest(this);
	}

	protected EntityAIBase createMossovyAttackAI() {
		return new GOTEntityAIAttackOnCollide(this, 1.4, true);
	}

	@Override
	public GOTNPCMount createMountToRide() {
		GOTEntityHorse horse = (GOTEntityHorse) super.createMountToRide();
		horse.setMountArmor(new ItemStack(GOTItems.ironHorseArmor));
		return horse;
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		int bones = rand.nextInt(2) + rand.nextInt(i + 1);
		for (int l = 0; l < bones; ++l) {
			dropItem(Items.bone, 1);
		}
		dropMossovyItems(i);
	}

	private void dropMossovyItems(int i) {
		if (rand.nextInt(6) == 0) {
			dropChestContents(GOTChestContents.MOSSOVY, 1, 2 + i);
		}
	}

	@Override
	public float getAlignmentBonus() {
		return 1.0f;
	}

	@Override
	public GOTMiniQuestFactory getBountyHelpSpeechDir() {
		return GOTMiniQuestFactory.MOSSOVY;
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.MOSSOVY;
	}

	@Override
	public String getNPCName() {
		return familyInfo.getName();
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			return "standard/civilized/usual_friendly";
		}
		return "standard/civilized/usual_hostile";
	}

	@Override
	public void onAttackModeChange(AttackMode mode, boolean mounted) {
		if (mode == AttackMode.IDLE) {
			setCurrentItemOrArmor(0, npcItemsInv.getIdleItem());
		} else {
			setCurrentItemOrArmor(0, npcItemsInv.getMeleeWeapon());
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.mossovyDagger));
		npcItemsInv.setIdleItem(null);
		return entityData;
	}

	@Override
	public void setupNPCName() {
		familyInfo.setName(GOTNames.getMossovyName(rand, familyInfo.isMale()));
	}
}