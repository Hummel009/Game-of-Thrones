package got.client.render.other;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraftforge.common.MinecraftForge;

public class GOTRenderCrosshair {
	public Minecraft mc;

	float zLevel = -90.0F;

	public GOTRenderCrosshair() {
		FMLCommonHandler.instance().bus().register(this);
		MinecraftForge.EVENT_BUS.register(this);
		mc = Minecraft.getMinecraft();
	}

	public void drawTexturedModalRect(int p_73729_1_, int p_73729_2_, int p_73729_3_, int p_73729_4_, int p_73729_5_, int p_73729_6_) {
		float f = 0.00390625F;
		float f1 = 0.00390625F;
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(p_73729_1_ + 0, p_73729_2_ + p_73729_6_, zLevel, (p_73729_3_ + 0) * f, (p_73729_4_ + p_73729_6_) * f1);
		tessellator.addVertexWithUV(p_73729_1_ + p_73729_5_, p_73729_2_ + p_73729_6_, zLevel, (p_73729_3_ + p_73729_5_) * f, (p_73729_4_ + p_73729_6_) * f1);
		tessellator.addVertexWithUV(p_73729_1_ + p_73729_5_, p_73729_2_ + 0, zLevel, (p_73729_3_ + p_73729_5_) * f, (p_73729_4_ + 0) * f1);
		tessellator.addVertexWithUV(p_73729_1_ + 0, p_73729_2_ + 0, zLevel, (p_73729_3_ + 0) * f, (p_73729_4_ + 0) * f1);
		tessellator.draw();
	}
}
