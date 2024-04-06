package got.client.render.animal;

import got.common.entity.animal.GOTEntityHorse;
import got.common.entity.other.GOTNPCMount;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelHorse;
import net.minecraft.client.renderer.entity.RenderHorse;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.LayeredTexture;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GOTRenderHorse extends RenderHorse {
	private static final Map<String, ResourceLocation> LAYERED_MOUNT_TEXTURES = new HashMap<>();

	public GOTRenderHorse() {
		super(new ModelHorse(), 0.75f);
	}

	public static ResourceLocation getLayeredMountTexture(GOTNPCMount mount, ResourceLocation mountSkin) {
		String skinPath = mountSkin.toString();
		String armorPath = mount.getMountArmorTexture();
		if (armorPath == null || StringUtils.isBlank(armorPath)) {
			return mountSkin;
		}
		Minecraft mc = Minecraft.getMinecraft();
		String path = "got_" + skinPath + '_' + armorPath;
		ResourceLocation texture = LAYERED_MOUNT_TEXTURES.get(path);
		if (texture == null) {
			texture = new ResourceLocation(path);
			ArrayList<String> layers = new ArrayList<>();
			ITextureObject skinTexture = mc.getTextureManager().getTexture(mountSkin);
			if (skinTexture instanceof LayeredTexture) {
				LayeredTexture skinTextureLayered = (LayeredTexture) skinTexture;
				layers.addAll(skinTextureLayered.layeredTextureNames);
			} else {
				layers.add(skinPath);
			}
			layers.add(armorPath);
			mc.getTextureManager().loadTexture(texture, new LayeredTexture(layers.toArray(new String[0])));
			LAYERED_MOUNT_TEXTURES.put(path, texture);
		}
		return texture;
	}

	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		GOTEntityHorse horse = (GOTEntityHorse) entity;
		horse.getHorseType();
		super.doRender(entity, d, d1, d2, f, f1);
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		GOTNPCMount horse = (GOTNPCMount) entity;
		ResourceLocation horseSkin = super.getEntityTexture(entity);
		return getLayeredMountTexture(horse, horseSkin);
	}
}
