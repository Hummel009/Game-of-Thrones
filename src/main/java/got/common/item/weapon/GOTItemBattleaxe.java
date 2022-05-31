package got.common.item.weapon;

import got.common.database.GOTMaterial;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.*;
import net.minecraft.world.World;

public class GOTItemBattleaxe extends GOTItemSword {
	public float efficiencyOnProperMaterial;

	public GOTItemBattleaxe(GOTMaterial material) {
		this(material.toToolMaterial());
		gotMaterial = material;
	}

	public GOTItemBattleaxe(Item.ToolMaterial material) {
		super(material);
		efficiencyOnProperMaterial = material.getEfficiencyOnProperMaterial();
		setHarvestLevel("axe", material.getHarvestLevel());
		gotWeaponDamage += 2.0f;
	}

	@Override
	public float func_150893_a(ItemStack itemstack, Block block) {
		float f = super.func_150893_a(itemstack, block);
		if (f == 1.0f && block != null && (block.getMaterial() == Material.wood || block.getMaterial() == Material.plants || block.getMaterial() == Material.vine)) {
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
