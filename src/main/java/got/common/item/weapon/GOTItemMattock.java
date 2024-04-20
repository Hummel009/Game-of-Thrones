package got.common.item.weapon;

import got.common.item.tool.GOTItemPickaxe;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.stream.Stream;

public class GOTItemMattock extends GOTItemPickaxe {
	private final float efficiencyOnProperMaterial;

	public GOTItemMattock(Item.ToolMaterial material) {
		super(material);
		efficiencyOnProperMaterial = material.getEfficiencyOnProperMaterial();
		setHarvestLevel("axe", material.getHarvestLevel());
	}

	@Override
	@SuppressWarnings("StreamToLoop")
	public float func_150893_a(ItemStack itemstack, Block block) {
		float f = super.func_150893_a(itemstack, block);
		if (f == 1.0f && Stream.of(Material.wood, Material.plants, Material.vine).anyMatch(material -> block.getMaterial() == material)) {
			return efficiencyOnProperMaterial;
		}
		return f;
	}
}