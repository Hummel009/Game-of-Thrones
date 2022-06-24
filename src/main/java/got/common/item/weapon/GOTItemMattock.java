package got.common.item.weapon;

import got.common.item.tool.GOTItemPickaxe;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.*;

public class GOTItemMattock extends GOTItemPickaxe {
	public float efficiencyOnProperMaterial;

	public GOTItemMattock(Item.ToolMaterial material) {
		super(material);
		efficiencyOnProperMaterial = material.getEfficiencyOnProperMaterial();
		setHarvestLevel("axe", material.getHarvestLevel());
	}

	@Override
	public float func_150893_a(ItemStack itemstack, Block block) {
		float f = super.func_150893_a(itemstack, block);
		if (f == 1.0f && block != null && (block.getMaterial() == Material.wood || block.getMaterial() == Material.plants || block.getMaterial() == Material.vine)) {
			return efficiencyOnProperMaterial;
		}
		return f;
	}
}
