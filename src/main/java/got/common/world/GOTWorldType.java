package got.common.world;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.WorldType;

public class GOTWorldType extends WorldType {
	public GOTWorldType(String name) {
		super(name);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean showWorldInfoNotice() {
		return true;
	}
}