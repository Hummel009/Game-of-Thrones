package got.common.item.weapon;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.stream.Stream;

public class GOTItemBattleaxe extends GOTItemSword {
	private final float efficiencyOnProperMaterial;

	public GOTItemBattleaxe(ToolMaterial material) {
		super(material);
		efficiencyOnProperMaterial = material.getEfficiencyOnProperMaterial();
		setHarvestLevel("axe", material.getHarvestLevel());
		gotWeaponDamage += 2.0f;
	}

	@Override
	@SuppressWarnings("StreamToLoop")
	public float func_150893_a(ItemStack itemstack, Block block) {
		float f = super.func_150893_a(itemstack, block);
		if (f == 1.0f && block != null && Stream.of(Material.wood, Material.plants, Material.vine).anyMatch(material -> block.getMaterial() == material)) {
			return efficiencyOnProperMaterial;
		}
		return f;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return EnumAction.none;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack itemstack, World world, Block block, int i, int j, int k, EntityLivingBase entity) {
		if (block.getBlockHardness(world, i, j, k) != 0.0) {
			itemstack.damageItem(1, entity);
		}
		return true;
	}
}