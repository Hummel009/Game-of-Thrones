package got.client.render.other;

import net.minecraft.util.IIcon;

class GOTExtraLargeIconToken {
	private String name;
	private IIcon icon;

	GOTExtraLargeIconToken(String s) {
		name = s;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public IIcon getIcon() {
		return icon;
	}

	public void setIcon(IIcon icon) {
		this.icon = icon;
	}
}