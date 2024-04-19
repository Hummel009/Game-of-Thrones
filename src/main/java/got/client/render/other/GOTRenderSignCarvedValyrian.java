package got.client.render.other;

import got.client.GOTClientProxy;
import got.common.tileentity.GOTTileEntitySignCarved;
import got.common.tileentity.GOTTileEntitySignCarvedValyrian;
import net.minecraft.tileentity.TileEntity;

public class GOTRenderSignCarvedValyrian extends GOTRenderSignCarved {
	@Override
	public int getTextColor(GOTTileEntitySignCarved sign, float f) {
		float glow = ((GOTTileEntitySignCarvedValyrian) sign).getGlowBrightness(f);
		int alpha = GOTClientProxy.getAlphaInt(glow);
		return 0xFFFFFF | alpha << 24;
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
		GOTTileEntitySignCarvedValyrian sign = (GOTTileEntitySignCarvedValyrian) tileentity;
		float alphaFunc = GOTRenderSilverGlow.setupGlow(sign.getGlowBrightness(f));
		super.renderTileEntityAt(tileentity, d, d1, d2, f);
		GOTRenderSilverGlow.endGlow(alphaFunc);
	}
}