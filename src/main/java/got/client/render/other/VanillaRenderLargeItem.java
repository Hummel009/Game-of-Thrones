package got.client.render.other;

import got.GOT;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class VanillaRenderLargeItem implements IItemRenderer {
	private static final ResourceLocation ENCHANTMENT_TEXTURE = new ResourceLocation("textures/misc/enchanted_item_glint.png");
	private static final Map<String, Float> SIZE_FOLDERS = new HashMap<>();

	static {
		SIZE_FOLDERS.put("vlarge-2x", 2.0f);
		SIZE_FOLDERS.put("vlarge-3x", 3.0f);
	}

	private final Item item;
	private final String folder;
	private final float scale;

	private VanillaRenderLargeItem(Item item, String folder, float scale) {
		this.item = item;
		this.folder = folder;
		this.scale = scale;
	}

	public static VanillaRenderLargeItem getRendererIfLarge(Item item) {
		for (Map.Entry<String, Float> folder : SIZE_FOLDERS.entrySet()) {
			String itemTexturePath = getItemTexturePath(item, folder.getKey());
			InputStream inputStream = null;
			try {
				inputStream = GOT.class.getResourceAsStream(itemTexturePath);
				if (inputStream != null) {
					return new VanillaRenderLargeItem(item, folder.getKey(), folder.getValue());
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (inputStream != null) {
						inputStream.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	private static ResourceLocation getItemResourceLocation(Item item, String folder) {
		return new ResourceLocation("got:textures/items/" + folder + '/' + getItemName(item) + ".png");
	}

	private static String getItemTexturePath(Item item, String folder) {
		return "/assets/got/textures/items/" + folder + '/' + getItemName(item) + ".png";
	}

	private static String getItemName(Item item) {
		return item.getUnlocalizedName().substring("item.".length());
	}

	@Override
	public void renderItem(ItemRenderType itemRenderType, ItemStack itemStack, Object... data) {
		GL11.glPushMatrix();

		TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
		ResourceLocation itemResourceLocation = getItemResourceLocation(item, folder);
		String itemTexturePath = getItemTexturePath(item, folder);
		textureManager.bindTexture(itemResourceLocation);

		GL11.glTranslatef(-(scale - 1.0f) / 2.0f, -(scale - 1.0f) / 2.0f, 0.0f);
		GL11.glScalef(scale, scale, 1.0f);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

		Tessellator tessellator = Tessellator.instance;
		int textureSize = itemTexturePath.contains("2x") ? 32 : 48;
		ItemRenderer.renderItemIn2D(tessellator, 1.0f, 0.0f, 0.0f, 1.0f, textureSize, textureSize, 0.0625f);

		if (itemStack.hasEffect(0)) {
			GL11.glDepthFunc(514);
			GL11.glDisable(2896);
			textureManager.bindTexture(ENCHANTMENT_TEXTURE);
			GL11.glEnable(3042);
			GL11.glBlendFunc(768, 1);
			float shade = 0.76f;
			GL11.glColor4f(0.5f * shade, 0.25f * shade, 0.8f * shade, 1.0f);
			GL11.glMatrixMode(5890);
			GL11.glPushMatrix();
			float scale = 0.125f;
			GL11.glScalef(scale, scale, scale);
			float randomShift = Minecraft.getSystemTime() % 3000L / 3000.0f * 8.0f;
			GL11.glTranslatef(randomShift, 0.0f, 0.0f);
			GL11.glRotatef(-50.0f, 0.0f, 0.0f, 1.0f);
			ItemRenderer.renderItemIn2D(tessellator, 0.0f, 0.0f, 1.0f, 1.0f, 256, 256, 0.0625f);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glScalef(scale, scale, scale);
			randomShift = Minecraft.getSystemTime() % 4873L / 4873.0f * 8.0f;
			GL11.glTranslatef(-randomShift, 0.0f, 0.0f);
			GL11.glRotatef(10.0f, 0.0f, 0.0f, 1.0f);
			ItemRenderer.renderItemIn2D(tessellator, 0.0f, 0.0f, 1.0f, 1.0f, 256, 256, 0.0625f);
			GL11.glPopMatrix();
			GL11.glMatrixMode(5888);
			GL11.glDisable(3042);
			GL11.glEnable(2896);
			GL11.glDepthFunc(515);
		}

		GL11.glPopMatrix();
	}

	@Override
	public boolean handleRenderType(ItemStack itemStack, ItemRenderType itemRenderType) {
		return itemRenderType == ItemRenderType.EQUIPPED || itemRenderType == ItemRenderType.EQUIPPED_FIRST_PERSON;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType itemRenderType, ItemStack itemStack, ItemRendererHelper itemRendererHelper) {
		return false;
	}
}