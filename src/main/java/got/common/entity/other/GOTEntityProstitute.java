package got.common.entity.other;

import got.common.database.GOTAchievement;
import got.common.database.GOTFoods;
import got.common.database.GOTNames;
import got.common.entity.ai.GOTEntityAIDrink;
import got.common.entity.ai.GOTEntityAIEat;
import got.common.entity.ai.GOTEntityAIFollowHiringPlayer;
import got.common.entity.ai.GOTEntityAIHiredRemainStill;
import got.common.faction.GOTFaction;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class GOTEntityProstitute extends GOTEntityHumanBase {
	public ProstituteType prostituteType;

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
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(18, (byte) 0);
		setProstituteType(prostituteType);
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.UNALIGNED;
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killProstitute;
	}

	@Override
	public String getNPCName() {
		return familyInfo.getName();
	}

	public ProstituteType getProstituteType() {
		byte i = dataWatcher.getWatchableObjectByte(18);
		return ProstituteType.forID(i);
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			return "standart/special/prostitute_friendly";
		}
		return "standart/special/prostitute_hostile";
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

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(false);
	}

	@Override
	public void setupNPCName() {
		String name = null;
		switch (rand.nextInt(10)) {
			case 0:
				name = GOTNames.getWildName(rand, familyInfo.isMale());
				prostituteType = ProstituteType.WILD;
				break;
			case 1:
				name = GOTNames.getGhiscarName(rand, familyInfo.isMale());
				prostituteType = ProstituteType.DARK;
				break;
			case 2:
				name = GOTNames.getSothoryosName(rand, familyInfo.isMale());
				prostituteType = ProstituteType.BLACK;
				break;
			case 3:
				switch (rand.nextInt(2)) {
					case 0:
						name = GOTNames.getDothrakiName(rand, familyInfo.isMale());
						break;
					case 1:
						name = GOTNames.getLhazarName(rand, familyInfo.isMale());
						break;
				}
				prostituteType = ProstituteType.NOMAD;
				break;
			case 4:
				name = GOTNames.getYiTiName(rand, familyInfo.isMale());
				prostituteType = ProstituteType.YITI;
				break;
			case 5:
				name = GOTNames.getJogosName(rand, familyInfo.isMale());
				prostituteType = ProstituteType.JOGOS;
				break;
			default:
				switch (rand.nextInt(3)) {
					case 0:
						name = GOTNames.getWesterosName(rand, familyInfo.isMale());
						break;
					case 1:
						name = GOTNames.getEssosName(rand, familyInfo.isMale());
						break;
					case 2:
						name = GOTNames.getQarthName(rand, familyInfo.isMale());
						break;
				}
				switch (rand.nextInt(5)) {
					case 0:
						prostituteType = ProstituteType.LIGHT_1;
						break;
					case 1:
						prostituteType = ProstituteType.LIGHT_2;
						break;
					case 2:
						prostituteType = ProstituteType.LIGHT_3;
						break;
					case 3:
						prostituteType = ProstituteType.LIGHT_4;
						break;
					case 4:
						prostituteType = ProstituteType.LIGHT_5;
						break;
				}
				break;
		}
		familyInfo.setName(name);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setByte("ProstituteType", (byte) getProstituteType().prostituteID);
	}

	public enum ProstituteType {
		LIGHT_1(0), LIGHT_2(1), LIGHT_3(2), LIGHT_4(3), LIGHT_5(4), DARK(5), BLACK(6), NOMAD(7), YITI(8), JOGOS(9), WILD(10);

		public int prostituteID;

		ProstituteType(int i) {
			prostituteID = i;
		}

		public String textureName() {
			return name().toLowerCase();
		}

		public static ProstituteType forID(int ID) {
			for (ProstituteType t : ProstituteType.values()) {
				if (t.prostituteID == ID) {
					return t;
				}
			}
			return LIGHT_1;
		}

		public static String[] typeNames() {
			String[] names = new String[ProstituteType.values().length];
			for (int i = 0; i < names.length; ++i) {
				names[i] = ProstituteType.values()[i].textureName();
			}
			return names;
		}
	}
}
