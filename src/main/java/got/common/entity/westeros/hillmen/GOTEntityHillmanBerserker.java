package got.common.entity.westeros.hillmen;

import got.common.database.GOTItems;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityHillmanBerserker extends GOTEntityHillmanWarrior {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityHillmanBerserker(World world) {
		super(world);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setRangedWeapon(new ItemStack(GOTItems.ironBattleaxe));
		npcItemsInv.setIdleItem(npcItemsInv.getRangedWeapon());

		setCurrentItemOrArmor(4, null);

		return entityData;
	}
}