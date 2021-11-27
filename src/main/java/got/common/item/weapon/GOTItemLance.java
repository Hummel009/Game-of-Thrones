package got.common.item.weapon;

import java.util.UUID;

import got.common.database.GOTMaterial;
import got.common.item.GOTMaterialFinder;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.Item;

public class GOTItemLance extends GOTItemPolearmLong implements GOTMaterialFinder {
	public static UUID lanceSpeedBoost_id = UUID.fromString("4da96302-7457-42ed-9709-f1be0c465ec3");
	public static AttributeModifier lanceSpeedBoost = new AttributeModifier(lanceSpeedBoost_id, "Lance speed boost", -0.2, 2).setSaved(false);
	public GOTMaterial gotMaterial;

	public GOTItemLance(GOTMaterial material) {
		this(material.toToolMaterial());
		gotMaterial = material;
	}

	public GOTItemLance(Item.ToolMaterial material) {
		super(material);
	}

	@Override
	public GOTMaterial getMaterial() {
		return gotMaterial;
	}
}
