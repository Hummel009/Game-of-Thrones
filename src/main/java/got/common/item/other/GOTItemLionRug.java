package got.common.item.other;

import got.common.entity.animal.GOTEntityLionRug;
import got.common.entity.other.GOTEntityRugBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

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

		public int lionID;

		LionRugType(int i) {
			lionID = i;
		}

		public String textureName() {
			return name().toLowerCase();
		}

		public static LionRugType forID(int ID) {
			for (LionRugType t : LionRugType.values()) {
				if (t.lionID != ID) {
					continue;
				}
				return t;
			}
			return LION;
		}

		public static String[] lionRugNames() {
			String[] names = new String[LionRugType.values().length];
			for (int i = 0; i < names.length; ++i) {
				names[i] = LionRugType.values()[i].textureName();
			}
			return names;
		}
	}

}
