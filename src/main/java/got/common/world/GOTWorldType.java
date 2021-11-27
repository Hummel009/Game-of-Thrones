package got.common.world;

import cpw.mods.fml.relauncher.*;
import net.minecraft.world.WorldType;

public class GOTWorldType extends WorldType {
	public GOTWorldType(String name) {
		super(name);
	}

	@Override
	@SideOnly(value = Side.CLIENT)
	public boolean showWorldInfoNotice() {
		return true;
	}
}