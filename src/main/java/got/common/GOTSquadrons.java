package got.common;

import got.common.entity.other.GOTEntityNPC;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StringUtils;

public class GOTSquadrons {
	private static int SQUADRON_LENGTH_MAX = 200;

	public static boolean areSquadronsCompatible(GOTEntityNPC npc, ItemStack itemstack) {
		String npcSquadron = npc.hiredNPCInfo.getSquadron();
		String itemSquadron = GOTSquadrons.getSquadron(itemstack);
		if (StringUtils.isNullOrEmpty(npcSquadron)) {
			return StringUtils.isNullOrEmpty(itemSquadron);
		}
		return npcSquadron.equalsIgnoreCase(itemSquadron);
	}

	public static String checkAcceptableLength(String squadron) {
		if (squadron != null && squadron.length() > getSquadronMaxLength()) {
			squadron = squadron.substring(0, getSquadronMaxLength());
		}
		return squadron;
	}

	public static String getSquadron(ItemStack itemstack) {
		if (itemstack.getItem() instanceof SquadronItem && itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("GOTSquadron")) {
			return itemstack.getTagCompound().getString("GOTSquadron");
		}
		return null;
	}

	public static int getSquadronMaxLength() {
		return SQUADRON_LENGTH_MAX;
	}

	public static void setSquadron(ItemStack itemstack, String squadron) {
		if (itemstack.getItem() instanceof SquadronItem) {
			if (itemstack.getTagCompound() == null) {
				itemstack.setTagCompound(new NBTTagCompound());
			}
			itemstack.getTagCompound().setString("GOTSquadron", squadron);
		}
	}

	public static void setSquadronMaxLength(int sQUADRON_LENGTH_MAX) {
		SQUADRON_LENGTH_MAX = sQUADRON_LENGTH_MAX;
	}

	public interface SquadronItem {
	}

}
