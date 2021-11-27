package got.common.block.other;

import cpw.mods.fml.relauncher.*;
import got.common.database.GOTRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;

public class GOTBlockGoblet extends GOTBlockMug {
	public GOTBlockGoblet() {
		super(2.5f, 9.0f);
		setStepSound(Block.soundTypeMetal);
	}

	public static class Copper extends GOTBlockGoblet {
		@SideOnly(value = Side.CLIENT)
		@Override
		public IIcon getIcon(int i, int j) {
			return GOTRegistry.blockMetal1.getIcon(i, 0);
		}
	}

	public static class Gold extends GOTBlockGoblet {
		@SideOnly(value = Side.CLIENT)
		@Override
		public IIcon getIcon(int i, int j) {
			return Blocks.gold_block.getIcon(i, 0);
		}
	}

	public static class Silver extends GOTBlockGoblet {
		@SideOnly(value = Side.CLIENT)
		@Override
		public IIcon getIcon(int i, int j) {
			return GOTRegistry.blockMetal1.getIcon(i, 3);
		}
	}

	public static class Wood extends GOTBlockGoblet {
		public Wood() {
			setStepSound(Block.soundTypeWood);
		}

		@SideOnly(value = Side.CLIENT)
		@Override
		public IIcon getIcon(int i, int j) {
			return Blocks.planks.getIcon(i, 0);
		}
	}

}
