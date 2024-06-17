package got.common.entity.westeros.hillmen;

import got.common.database.GOTItems;
import got.common.entity.other.iface.GOTBannerBearer;
import got.common.item.other.GOTItemBanner;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityHillmanBannerBearer extends GOTEntityHillmanWarrior implements GOTBannerBearer {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityHillmanBannerBearer(World world) {
		super(world);
	}

	@Override
	public GOTItemBanner.BannerType getBannerType() {
		return GOTItemBanner.BannerType.HILLMEN;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setRangedWeapon(new ItemStack(GOTItems.ironDagger));
		npcItemsInv.setIdleItem(npcItemsInv.getRangedWeapon());

		return entityData;
	}
}