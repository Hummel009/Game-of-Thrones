package got.common.entity.animal;

import got.common.item.other.GOTItemLionRug;
import net.minecraft.world.World;

public class GOTEntityLion extends GOTEntityLionBase {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityLion(World world) {
		super(world);
	}

	@Override
	public GOTItemLionRug.LionRugType getLionRugType() {
		return GOTItemLionRug.LionRugType.LION;
	}

	@Override
	public boolean isMale() {
		return true;
	}
}