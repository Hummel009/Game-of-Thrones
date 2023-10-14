package got.common.item.weapon;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.Item;

import java.util.UUID;

public class GOTItemLance extends GOTItemPolearmLong {
	public static UUID lanceSpeedBoost_id = UUID.fromString("4da96302-7457-42ed-9709-f1be0c465ec3");
	public static AttributeModifier lanceSpeedBoost = new AttributeModifier(lanceSpeedBoost_id, "Lance speed boost", -0.2, 2).setSaved(false);

	public GOTItemLance(Item.ToolMaterial material) {
		super(material);
	}
}
