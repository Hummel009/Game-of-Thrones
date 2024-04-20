package got.common.block.other;

import got.common.world.biome.essos.GOTBiomeShadowLand;
import net.minecraft.world.World;

public class GOTBlockAsshaiPlant extends GOTBlockFlower {
	@Override
	public boolean canBlockStay(World world, int i, int j, int k) {
		return GOTBiomeShadowLand.isBlackSurface(world, i, j - 1, k);
	}
}