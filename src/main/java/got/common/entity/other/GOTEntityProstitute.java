package got.common.entity.other;

import got.common.database.GOTAchievement;
import got.common.database.GOTNames;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.Locale;

public class GOTEntityProstitute extends GOTEntityHumanBase {
	private ProstituteType prostituteType;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityProstitute(World world) {
		super(world);
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killProstitute;
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(18, (byte) 0);
		setProstituteType(prostituteType);
	}

	public ProstituteType getProstituteType() {
		byte i = dataWatcher.getWatchableObjectByte(18);
		return ProstituteType.forID(i);
	}

	private void setProstituteType(ProstituteType t) {
		dataWatcher.updateObject(18, (byte) t.getProstituteID());
	}

	@Override
	public String getSpeechBank(GOTEntityNPC npc, EntityPlayer entityPlayer) {
		if (isFriendly(entityPlayer)) {
			return "special/prostitute_friendly";
		}
		return "special/prostitute_hostile";
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		if (nbt.hasKey("ProstituteType")) {
			setProstituteType(ProstituteType.forID(nbt.getByte("ProstituteType")));
		}
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
				prostituteType = ProstituteType.YI_TI;
				break;
			case 5:
				name = GOTNames.getJogosNhaiName(rand, familyInfo.isMale());
				prostituteType = ProstituteType.JOGOS_NHAI;
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
		nbt.setByte("ProstituteType", (byte) getProstituteType().getProstituteID());
	}

	public enum ProstituteType {
		LIGHT_1(0), LIGHT_2(1), LIGHT_3(2), LIGHT_4(3), LIGHT_5(4), DARK(5), BLACK(6), NOMAD(7), YI_TI(8), JOGOS_NHAI(9), WILD(10);

		private final int prostituteID;

		ProstituteType(int i) {
			prostituteID = i;
		}

		private static ProstituteType forID(int ID) {
			for (ProstituteType t : values()) {
				if (t.prostituteID == ID) {
					return t;
				}
			}
			return LIGHT_1;
		}

		public String textureName() {
			return name().toLowerCase(Locale.ROOT);
		}

		private int getProstituteID() {
			return prostituteID;
		}
	}
}