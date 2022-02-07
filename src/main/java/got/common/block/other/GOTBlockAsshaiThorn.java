package got.common.block.other;

import java.util.*;

import got.GOT;
import got.common.GOTDamage;
import got.common.faction.GOTFaction;
import net.minecraft.entity.Entity;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraftforge.common.IShearable;

public class GOTBlockAsshaiThorn extends GOTBlockAsshaiPlant implements IShearable {
	public GOTBlockAsshaiThorn() {
		setBlockBounds(0.1f, 0.0f, 0.1f, 0.9f, 0.8f, 0.9f);
	}

	@Override
	public Item getItemDropped(int i, Random random, int j) {
		return null;
	}

	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, int i, int j, int k) {
		return true;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity) {
		if (GOT.getNPCFaction(entity) != GOTFaction.ASSHAI) {
			entity.attackEntityFrom(GOTDamage.getPlantHurt(), 2.0f);
		}
	}

	@Override
	public ArrayList onSheared(ItemStack item, IBlockAccess world, int i, int j, int k, int fortune) {
		ArrayList<ItemStack> drops = new ArrayList<>();
		drops.add(new ItemStack(this));
		return drops;
	}
}
