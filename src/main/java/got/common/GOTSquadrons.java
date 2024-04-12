package got.common;

import got.common.entity.other.GOTEntityNPC;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StringUtils;

public class GOTSquadrons {
	public static final int SQUADRON_LENGTH_MAX = 200;

	private GOTSquadrons() {
	}

	public static boolean areSquadronsCompatible(GOTEntityNPC npc, ItemStack itemstack) {
		String npcSquadron = npc.hiredNPCInfo.getSquadron();
		String itemSquadron = getSquadron(itemstack);
		if (StringUtils.isNullOrEmpty(npcSquadron)) {
			return StringUtils.isNullOrEmpty(itemSquadron);
		}
		return npcSquadron.equalsIgnoreCase(itemSquadron);
	}

	public static String checkAcceptableLength(String squadron) {
		if (squadron != null && squadron.length() > SQUADRON_LENGTH_MAX) {
			return squadron.substring(0, SQUADRON_LENGTH_MAX);
		}
		return squadron;
	}

	public static String getSquadron(ItemStack itemstack) {
		if (itemstack.getItem() instanceof SquadronItem && itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("GOTSquadron")) {
			return itemstack.getTagCompound().getString("GOTSquadron");
		}
		return null;
	}

	public static void setSquadron(ItemStack itemstack, String squadron) {
		if (itemstack.getItem() instanceof SquadronItem) {
			if (itemstack.getTagCompound() == null) {
				itemstack.setTagCompound(new NBTTagCompound());
			}
			itemstack.getTagCompound().setString("GOTSquadron", squadron);
		}
	}

	public interface SquadronItem {
	}
}