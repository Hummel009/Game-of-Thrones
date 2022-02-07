package got.common.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.*;

public class GOTItemPlantableBlock extends ItemBlock implements IPlantable {
	private IPlantable plantableBlock;

	private GOTItemPlantableBlock(Block block) {
		super(block);
		plantableBlock = (IPlantable) block;
	}

	@Override
	public Block getPlant(IBlockAccess world, int i, int j, int k) {
		return plantableBlock.getPlant(world, i, j, k);
	}

	@Override
	public int getPlantMetadata(IBlockAccess world, int i, int j, int k) {
		return plantableBlock.getPlantMetadata(world, i, j, k);
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, int i, int j, int k) {
		return plantableBlock.getPlantType(world, i, j, k);
	}
}
