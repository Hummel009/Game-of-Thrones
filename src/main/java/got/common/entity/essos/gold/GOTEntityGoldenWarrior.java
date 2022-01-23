package got.common.entity.essos.gold;

import got.common.database.GOTRegistry;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityGoldenWarrior extends GOTEntityGoldenMan {
	public GOTEntityGoldenWarrior(World world) {
		super(world);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.westerosSword));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(GOTRegistry.goldBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTRegistry.goldLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTRegistry.goldChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTRegistry.goldHelmet));
		return data;
	}
}
