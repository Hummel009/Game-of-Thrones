package got.common.item.weapon;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.Item;

import java.util.UUID;

public class GOTItemLance extends GOTItemPolearmLong {
	public static final UUID LANCE_SPEED_BOOST_ID = UUID.fromString("4da96302-7457-42ed-9709-f1be0c465ec3");
	public static final AttributeModifier LANCE_SPEED_BOOST = new AttributeModifier(LANCE_SPEED_BOOST_ID, "Lance speed boost", -0.2, 2).setSaved(false);

	public GOTItemLance(Item.ToolMaterial material) {
		super(material);
	}
}