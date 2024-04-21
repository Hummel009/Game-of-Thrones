package got.common.item.other;

import net.minecraft.util.EnumChatFormatting;

public class GOTItemGemWithAnvilNameColor extends GOTItemGem {
	private final EnumChatFormatting anvilNameColor;

	public GOTItemGemWithAnvilNameColor(EnumChatFormatting color) {
		anvilNameColor = color;
	}

	public EnumChatFormatting getAnvilNameColor() {
		return anvilNameColor;
	}
}