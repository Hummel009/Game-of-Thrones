package got.common.entity.westeros;

import got.common.database.*;
import got.common.entity.ai.*;
import got.common.entity.other.*;
import got.common.faction.GOTFaction;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class GOTEntityProstitute extends GOTEntityHumanBase {
	public GOTEntityProstitute(World world) {
		super(world);
		canBeMarried = true;
		setSize(0.6f, 1.8f);
		getNavigator().setAvoidsWater(true);
		getNavigator().setBreakDoors(true);
		addTargetTasks(false);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new GOTEntityAIHiredRemainStill(this));
		tasks.addTask(2, new EntityAIPanic(this, 1.4));
		tasks.addTask(3, new GOTEntityAIFollowHiringPlayer(this));
		tasks.addTask(4, new EntityAIOpenDoor(this, true));
		tasks.addTask(5, new EntityAIWander(this, 1.0));
		tasks.addTask(4, new GOTEntityAIEat(this, GOTFoods.WESTEROS, 8000));
		tasks.addTask(5, new GOTEntityAIDrink(this, GOTFoods.WESTEROS_DRINK, 8000));
		tasks.addTask(7, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0f, 0.02f));
		tasks.addTask(7, new EntityAIWatchClosest2(this, GOTEntityNPC.class, 5.0f, 0.02f));
		tasks.addTask(8, new EntityAIWatchClosest(this, EntityLiving.class, 8.0f, 0.02f));
		tasks.addTask(9, new EntityAILookIdle(this));
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(18, (byte) 0);
		setProstituteType(ProstituteType.forID(rand.nextInt(ProstituteType.values().length)));
	}

	public ProstituteType getProstituteType() {
		byte i = dataWatcher.getWatchableObjectByte(18);
		return ProstituteType.forID(i);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		if (nbt.hasKey("ProstituteType")) {
			setProstituteType(ProstituteType.forID(nbt.getByte("ProstituteType")));
		}
	}

	public void setProstituteType(ProstituteType t) {
		dataWatcher.updateObject(18, (byte) t.prostituteID);
	}

	public enum ProstituteType {
		LIGHT(0), DARK(1), BLACK(2), NOMAD(4), YI_TI(5), JOGOS(6), WILD(6);

		public int prostituteID;

		ProstituteType(int i) {
			prostituteID = i;
		}

		public String textureName() {
			return name().toLowerCase();
		}

		public static String[] bearTypeNames() {
			String[] names = new String[ProstituteType.values().length];
			for (int i = 0; i < names.length; ++i) {
				names[i] = ProstituteType.values()[i].textureName();
			}
			return names;
		}

		public static ProstituteType forID(int ID) {
			for (ProstituteType t : ProstituteType.values()) {
				if (t.prostituteID == ID) {
					return t;
				}
			}
			return LIGHT;
		}
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22);
	}

	@Override
	public boolean canRenameNPC() {
		return true;
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.UNALIGNED;
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.KILL_PROSTITUTE;
	}

	@Override
	public String getNPCName() {
		return familyInfo.getName();
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			return "standart/special/prostitute_friendly";
		}
		return "standart/special/prostitute_hostile";
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(false);
	}

	@Override
	public void setupNPCName() {
		familyInfo.setName(GOTNames.getWesterosName(rand, familyInfo.isMale()));
	}
}
