package got.client.model;

import net.minecraft.client.model.ModelRenderer;

public class GOTModelRoyceHelmet extends GOTModelBiped {
	public ModelRenderer[] spikes = new ModelRenderer[8];

	public GOTModelRoyceHelmet() {
		this(0.0f);
	}

	public GOTModelRoyceHelmet(float f) {
		super(f);
		int i;
		bipedHead = new ModelRenderer(this, 0, 0);
		bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 12, 8, f);
		bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
		bipedHead.setTextureOffset(0, 20).addBox(-3.5f, -18.0f, -3.5f, 7, 10, 1, f);
		for (i = 0; i < spikes.length; ++i) {
			spikes[i] = new ModelRenderer(this, 16, 20);
			spikes[i].setRotationPoint(0.0f, 0.0f, 0.0f);
		}
		spikes[0].addBox(-1.0f, -5.5f, -10.0f, 1, 1, 4);
		spikes[0].rotateAngleX = (float) -0.3490658503988659;
		spikes[0].rotateAngleY = (float) 0.3490658503988659;
		spikes[1].addBox(0.0f, -5.5f, -10.0f, 1, 1, 4);
		spikes[1].rotateAngleX = (float) -0.3490658503988659;
		spikes[1].rotateAngleY = (float) -0.3490658503988659;
		spikes[2].addBox(6.0f, -5.5f, -1.0f, 4, 1, 1);
		spikes[2].rotateAngleZ = (float) -0.3490658503988659;
		spikes[2].rotateAngleY = (float) 0.3490658503988659;
		spikes[3].addBox(6.0f, -5.5f, 0.0f, 4, 1, 1);
		spikes[3].rotateAngleZ = (float) -0.3490658503988659;
		spikes[3].rotateAngleY = (float) -0.3490658503988659;
		spikes[4].addBox(0.0f, -5.5f, 6.0f, 1, 1, 4);
		spikes[4].rotateAngleX = (float) 0.3490658503988659;
		spikes[4].rotateAngleY = (float) 0.3490658503988659;
		spikes[5].addBox(-1.0f, -5.5f, 6.0f, 1, 1, 4);
		spikes[5].rotateAngleX = (float) 0.3490658503988659;
		spikes[5].rotateAngleY = (float) -0.3490658503988659;
		spikes[6].addBox(-10.0f, -5.5f, 0.0f, 4, 1, 1);
		spikes[6].rotateAngleZ = (float) 0.3490658503988659;
		spikes[6].rotateAngleY = (float) 0.3490658503988659;
		spikes[7].addBox(-10.0f, -5.5f, -1.0f, 4, 1, 1);
		spikes[7].rotateAngleZ = (float) 0.3490658503988659;
		spikes[7].rotateAngleY = (float) -0.3490658503988659;
		for (i = 0; i < spikes.length; ++i) {
			bipedHead.addChild(spikes[i]);
		}
		bipedBody.cubeList.clear();
		bipedRightArm.cubeList.clear();
		bipedLeftArm.cubeList.clear();
		bipedRightLeg.cubeList.clear();
		bipedLeftLeg.cubeList.clear();
	}
}
