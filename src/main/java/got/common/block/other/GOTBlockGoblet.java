package got.common.block.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.database.GOTBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;

public abstract class GOTBlockGoblet extends GOTBlockMug {
	protected GOTBlockGoblet() {
		super(2.5f, 9.0f);
		setStepSound(soundTypeMetal);
	}

	public static class Bronze extends GOTBlockGoblet {
		@SideOnly(Side.CLIENT)
		@Override
		public IIcon getIcon(int i, int j) {
			return GOTBlocks.blockMetal1.getIcon(i, 2);
		}
	}

	public static class Valyrian extends GOTBlockGoblet {
		@SideOnly(Side.CLIENT)
		@Override
		public IIcon getIcon(int i, int j) {
			return GOTBlocks.blockMetal1.getIcon(i, 4);
		}
	}

	public static class Copper extends GOTBlockGoblet {
		@SideOnly(Side.CLIENT)
		@Override
		public IIcon getIcon(int i, int j) {
			return GOTBlocks.blockMetal1.getIcon(i, 0);
		}
	}

	public static class Gold extends GOTBlockGoblet {
		@SideOnly(Side.CLIENT)
		@Override
		public IIcon getIcon(int i, int j) {
			return Blocks.gold_block.getIcon(i, 0);
		}
	}

	public static class Silver extends GOTBlockGoblet {
		@SideOnly(Side.CLIENT)
		@Override
		public IIcon getIcon(int i, int j) {
			return GOTBlocks.blockMetal1.getIcon(i, 3);
		}
	}

	public static class Wood extends GOTBlockGoblet {
		public Wood() {
			setStepSound(soundTypeWood);
		}

		@SideOnly(Side.CLIENT)
		@Override
		public IIcon getIcon(int i, int j) {
			return Blocks.planks.getIcon(i, 0);
		}
	}
}