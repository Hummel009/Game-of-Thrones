package got.common.entity.essos.ghiscar;

import got.common.database.GOTItems;
import got.common.entity.ai.GOTEntityAINearestAttackableTargetPatriot;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityGhiscarHarpy extends GOTEntityGhiscarLevyman {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityGhiscarHarpy(World world) {
		super(world);
		addTargetTasks(true, GOTEntityAINearestAttackableTargetPatriot.class);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData data1 = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.essosDaggerPoisoned));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.harpy));
		return data1;
	}
}