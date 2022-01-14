package got.common.entity.westeros.crownlands;

import got.common.database.*;
import got.common.entity.ai.*;
import got.common.entity.other.GOTEntityNPC;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityKingsguard extends GOTEntityCrownlandsLevyman {
	public GOTEntityKingsguard(World world) {
		super(world);
		spawnRidingHorse = false;
		npcCape = GOTCapes.ROYALGUARD;
		this.addTargetTasks(true, GOTEntityAINearestAttackableTargetPatriot.class);
	}

	@Override
	public float getAlignmentBonus() {
		return 10.0f;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		setCurrentItemOrArmor(0, new ItemStack(GOTRegistry.westerosSword));
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.westerosSword));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(GOTRegistry.kingsguardBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTRegistry.kingsguardLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTRegistry.kingsguardChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTRegistry.kingsguardHelmet));
		return data;
	}
}
