package got.common.block.other;

import got.GOT;
import got.common.GOTDamage;
import got.common.faction.GOTFaction;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

import java.util.ArrayList;
import java.util.Random;

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
			entity.attackEntityFrom(GOTDamage.PLANT_HURT, 2.0f);
		}
	}

	@Override
	public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world, int i, int j, int k, int fortune) {
		ArrayList<ItemStack> drops = new ArrayList<>();
		drops.add(new ItemStack(this));
		return drops;
	}
}