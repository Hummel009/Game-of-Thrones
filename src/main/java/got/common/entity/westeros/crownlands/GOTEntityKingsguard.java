package got.common.entity.westeros.crownlands;

import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.entity.ai.GOTEntityAINearestAttackableTargetPatriot;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityKingsguard extends GOTEntityCrownlandsGuard {
	public GOTEntityKingsguard(World world) {
		super(world);
		npcCape = GOTCapes.ROYALGUARD;
		addTargetTasks(true, GOTEntityAINearestAttackableTargetPatriot.class);
	}

	@Override
	public float getAlignmentBonus() {
		return 3.0f;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		setCurrentItemOrArmor(0, new ItemStack(GOTItems.westerosSword));
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.westerosSword));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(GOTItems.kingsguardBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.kingsguardLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.kingsguardChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.kingsguardHelmet));
		return data;
	}
}
