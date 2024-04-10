package got.common.item.other;

import got.common.item.AnvilNameColorProvider;
import net.minecraft.util.EnumChatFormatting;

public class GOTItemGemWithAnvilNameColor extends GOTItemGem implements AnvilNameColorProvider {
	private final EnumChatFormatting anvilNameColor;

	public GOTItemGemWithAnvilNameColor(EnumChatFormatting color) {
		anvilNameColor = color;
	}

	@Override
	public EnumChatFormatting getAnvilNameColor() {
		return anvilNameColor;
	}
}