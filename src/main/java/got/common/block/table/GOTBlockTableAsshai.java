package got.common.block.table;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.GOT;
import got.common.database.GOTBlocks;
import got.common.database.GOTGuiId;
import got.common.faction.GOTFaction;
import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.Random;

public class GOTBlockTableAsshai extends GOTBlockCraftingTable {
	public GOTBlockTableAsshai() {
		super(Material.wood, GOTFaction.ASSHAI, GOTGuiId.TABLE_ASSHAI);
		setStepSound(soundTypeWood);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		if (i == 1) {
			return tableIcons[1];
		}
		if (i == 0) {
			return GOTBlocks.planks1.getIcon(0, 3);
		}
		return tableIcons[0];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		if (random.nextInt(20) == 0) {
			for (int l = 0; l < 16; l++) {
				double d = i + 0.25D + random.nextFloat() * 0.5F;
				double d1 = j + 1.0D;
				double d2 = k + 0.25D + random.nextFloat() * 0.5F;
				double d3 = -0.05D + random.nextFloat() * 0.1D;
				double d4 = 0.1D + random.nextFloat() * 0.1D;
				double d5 = -0.05D + random.nextFloat() * 0.1D;
				GOT.proxy.spawnParticle("asshaiTorch", d, d1, d2, d3, d4, d5);
			}
		}
	}
}