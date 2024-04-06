package got.client.render.npc;

import got.client.model.GOTModelHuman;
import got.client.render.other.GOTRandomSkins;
import got.client.render.other.GOTRenderBiped;
import got.common.entity.other.GOTEntityNPC;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class GOTRenderFactionNPCLayered extends GOTRenderBiped {
	private final String path;
	private final String outfit;
	private final ModelBiped model = new GOTModelHuman(0.6f, false);

	public GOTRenderFactionNPCLayered(String texture) {
		super(new GOTModelHuman(), 0.5f);
		path = texture;
		outfit = "outfit";
	}

	public GOTRenderFactionNPCLayered(String texture, String layer) {
		super(new GOTModelHuman(), 0.5f);
		path = texture;
		outfit = layer;
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		GOTEntityNPC mob = (GOTEntityNPC) entity;
		if (mob.familyInfo.isMale()) {
			if (mob.isChild()) {
				return GOTRandomSkins.loadSkinsList("got:textures/entity/" + path + "/malechild").getRandomSkin(mob);
			}
			return GOTRandomSkins.loadSkinsList("got:textures/entity/" + path + "/male").getRandomSkin(mob);
		}
		if (mob.isChild()) {
			return GOTRandomSkins.loadSkinsList("got:textures/entity/" + path + "/femalechild").getRandomSkin(mob);
		}
		return GOTRandomSkins.loadSkinsList("got:textures/entity/" + path + "/female").getRandomSkin(mob);
	}

	private ResourceLocation getSecondLayerTexture() {
		return new ResourceLocation("got:textures/entity/" + path + '/' + outfit + ".png");
	}

	@Override
	public int shouldRenderPass(EntityLiving entity, int pass, float f) {
		GOTEntityNPC legend = (GOTEntityNPC) entity;
		if (pass == 0 && legend.getEquipmentInSlot(4) == null || pass == 1 && legend.getEquipmentInSlot(3) == null) {
			setRenderPassModel(model);
			bindTexture(getSecondLayerTexture());
			return 1;
		}
		return super.shouldRenderPass(legend, pass, f);
	}
}