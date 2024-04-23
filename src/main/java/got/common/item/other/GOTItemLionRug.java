package got.common.item.other;

import got.common.entity.other.inanimate.GOTEntityLionRug;
import got.common.entity.other.inanimate.GOTEntityRugBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Locale;

public class GOTItemLionRug extends GOTItemRugBase {
	public GOTItemLionRug() {
		super(LionRugType.lionRugNames());
	}

	@Override
	public GOTEntityRugBase createRug(World world, ItemStack itemstack) {
		GOTEntityLionRug rug = new GOTEntityLionRug(world);
		rug.setRugType(LionRugType.forID(itemstack.getItemDamage()));
		return rug;
	}

	public enum LionRugType {
		LION(0), LIONESS(1);

		private final int lionID;

		LionRugType(int i) {
			lionID = i;
		}

		public static LionRugType forID(int ID) {
			for (LionRugType t : values()) {
				if (t.lionID != ID) {
					continue;
				}
				return t;
			}
			return LION;
		}

		private static String[] lionRugNames() {
			String[] names = new String[values().length];
			for (int i = 0; i < names.length; ++i) {
				names[i] = values()[i].textureName();
			}
			return names;
		}

		private String textureName() {
			return name().toLowerCase(Locale.ROOT);
		}

		public int getLionID() {
			return lionID;
		}
	}
}