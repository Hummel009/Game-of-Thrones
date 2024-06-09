package got.common.entity.essos.gold;

import got.common.database.GOTItems;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityGoldenCompanySpearman extends GOTEntityGoldenCompanyWarrior {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityGoldenCompanySpearman(World world) {
		super(world);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.essosPike));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		return data;
	}
}