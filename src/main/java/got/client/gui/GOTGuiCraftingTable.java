package got.client.gui;

import org.lwjgl.opengl.GL11;

import got.common.*;
import got.common.inventory.GOTContainerCraftingTable;
import got.common.network.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.*;
import net.minecraft.world.World;

public abstract class GOTGuiCraftingTable extends GuiContainer {
	public static ResourceLocation craftingTexture = new ResourceLocation("textures/gui/container/crafting_table.png");
	public String unlocalizedName;
	public GOTContainerCraftingTable container;
	public GuiButton tableSwitcher;

	public GOTGuiCraftingTable(GOTContainerCraftingTable container, String s) {
		super(container);
		this.container = container;
		unlocalizedName = s;
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled) {
			if (button instanceof GOTGuiButtonTableSwitcher) {
				GOTPacketSetOption packet = new GOTPacketSetOption(button.id);
				GOTPacketHandler.networkWrapper.sendToServer(packet);
			} else {
				super.actionPerformed(button);
			}
		}
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		Minecraft mc = Minecraft.getMinecraft();
		tableSwitcher.drawButton(mc, i, j);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(craftingTexture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}

	@Override
	public void drawGuiContainerForegroundLayer(int i, int j) {
		GOTPlayerData pd = GOTLevelData.getData(mc.thePlayer);
		boolean tableSwitched = pd.getTableSwitched();
		if (tableSwitched) {
			String title = StatCollector.translateToLocal("container.crafting");
			fontRendererObj.drawString(title, 28, 6, 4210752);
		} else {
			String title = StatCollector.translateToLocal("got.container.crafting." + unlocalizedName);
			fontRendererObj.drawString(title, 28, 6, 4210752);
		}
		fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
	}

	@Override
	public void initGui() {
		super.initGui();
		tableSwitcher = new GOTGuiButtonTableSwitcher(9, guiLeft + 9, guiTop + 35, StatCollector.translateToLocal("got.gui.tableSwitcher"), container.tableBlock);
		buttonList.add(tableSwitcher);
	}

	public static class Arryn extends GOTGuiCraftingTable {
		public Arryn(InventoryPlayer inv, World world, int i, int j, int k) {
			super(new GOTContainerCraftingTable.Arryn(inv, world, i, j, k), "arryn");
		}
	}

	public static class Asshai extends GOTGuiCraftingTable {
		public Asshai(InventoryPlayer inv, World world, int i, int j, int k) {
			super(new GOTContainerCraftingTable.Asshai(inv, world, i, j, k), "asshai");
		}
	}

	public static class Braavos extends GOTGuiCraftingTable {
		public Braavos(InventoryPlayer inv, World world, int i, int j, int k) {
			super(new GOTContainerCraftingTable.Braavos(inv, world, i, j, k), "braavos");
		}
	}

	public static class Crownlands extends GOTGuiCraftingTable {
		public Crownlands(InventoryPlayer inv, World world, int i, int j, int k) {
			super(new GOTContainerCraftingTable.Crownlands(inv, world, i, j, k), "crownlands");
		}
	}

	public static class Dorne extends GOTGuiCraftingTable {
		public Dorne(InventoryPlayer inv, World world, int i, int j, int k) {
			super(new GOTContainerCraftingTable.Dorne(inv, world, i, j, k), "dorne");
		}
	}

	public static class Dothraki extends GOTGuiCraftingTable {
		public Dothraki(InventoryPlayer inv, World world, int i, int j, int k) {
			super(new GOTContainerCraftingTable.Dothraki(inv, world, i, j, k), "dothraki");
		}
	}

	public static class Dragonstone extends GOTGuiCraftingTable {
		public Dragonstone(InventoryPlayer inv, World world, int i, int j, int k) {
			super(new GOTContainerCraftingTable.Dragonstone(inv, world, i, j, k), "dragonstone");
		}
	}

	public static class Ghiscar extends GOTGuiCraftingTable {
		public Ghiscar(InventoryPlayer inv, World world, int i, int j, int k) {
			super(new GOTContainerCraftingTable.Ghiscar(inv, world, i, j, k), "ghiscar");
		}
	}

	public static class Gift extends GOTGuiCraftingTable {
		public Gift(InventoryPlayer inv, World world, int i, int j, int k) {
			super(new GOTContainerCraftingTable.Gift(inv, world, i, j, k), "gift");
		}
	}

	public static class Hillmen extends GOTGuiCraftingTable {
		public Hillmen(InventoryPlayer inv, World world, int i, int j, int k) {
			super(new GOTContainerCraftingTable.Hillmen(inv, world, i, j, k), "hillmen");
		}
	}

	public static class Ibben extends GOTGuiCraftingTable {
		public Ibben(InventoryPlayer inv, World world, int i, int j, int k) {
			super(new GOTContainerCraftingTable.Ibben(inv, world, i, j, k), "ibben");
		}
	}

	public static class Ironborn extends GOTGuiCraftingTable {
		public Ironborn(InventoryPlayer inv, World world, int i, int j, int k) {
			super(new GOTContainerCraftingTable.Ironborn(inv, world, i, j, k), "ironborn");
		}
	}

	public static class Jogos extends GOTGuiCraftingTable {
		public Jogos(InventoryPlayer inv, World world, int i, int j, int k) {
			super(new GOTContainerCraftingTable.Jogos(inv, world, i, j, k), "jogos");
		}
	}

	public static class Lhazar extends GOTGuiCraftingTable {
		public Lhazar(InventoryPlayer inv, World world, int i, int j, int k) {
			super(new GOTContainerCraftingTable.Lhazar(inv, world, i, j, k), "lhazar");
		}
	}

	public static class Lorath extends GOTGuiCraftingTable {
		public Lorath(InventoryPlayer inv, World world, int i, int j, int k) {
			super(new GOTContainerCraftingTable.Lorath(inv, world, i, j, k), "lorath");
		}
	}

	public static class Lys extends GOTGuiCraftingTable {
		public Lys(InventoryPlayer inv, World world, int i, int j, int k) {
			super(new GOTContainerCraftingTable.Lys(inv, world, i, j, k), "lys");
		}
	}

	public static class Mossovy extends GOTGuiCraftingTable {
		public Mossovy(InventoryPlayer inv, World world, int i, int j, int k) {
			super(new GOTContainerCraftingTable.Mossovy(inv, world, i, j, k), "mossovy");
		}
	}

	public static class Myr extends GOTGuiCraftingTable {
		public Myr(InventoryPlayer inv, World world, int i, int j, int k) {
			super(new GOTContainerCraftingTable.Myr(inv, world, i, j, k), "myr");
		}
	}

	public static class North extends GOTGuiCraftingTable {
		public North(InventoryPlayer inv, World world, int i, int j, int k) {
			super(new GOTContainerCraftingTable.North(inv, world, i, j, k), "north");
		}
	}

	public static class Norvos extends GOTGuiCraftingTable {
		public Norvos(InventoryPlayer inv, World world, int i, int j, int k) {
			super(new GOTContainerCraftingTable.Norvos(inv, world, i, j, k), "norvos");
		}
	}

	public static class Pentos extends GOTGuiCraftingTable {
		public Pentos(InventoryPlayer inv, World world, int i, int j, int k) {
			super(new GOTContainerCraftingTable.Pentos(inv, world, i, j, k), "pentos");
		}
	}

	public static class Qarth extends GOTGuiCraftingTable {
		public Qarth(InventoryPlayer inv, World world, int i, int j, int k) {
			super(new GOTContainerCraftingTable.Qarth(inv, world, i, j, k), "qarth");
		}
	}

	public static class Qohor extends GOTGuiCraftingTable {
		public Qohor(InventoryPlayer inv, World world, int i, int j, int k) {
			super(new GOTContainerCraftingTable.Qohor(inv, world, i, j, k), "qohor");
		}
	}

	public static class Reach extends GOTGuiCraftingTable {
		public Reach(InventoryPlayer inv, World world, int i, int j, int k) {
			super(new GOTContainerCraftingTable.Reach(inv, world, i, j, k), "reach");
		}
	}

	public static class Riverlands extends GOTGuiCraftingTable {
		public Riverlands(InventoryPlayer inv, World world, int i, int j, int k) {
			super(new GOTContainerCraftingTable.Riverlands(inv, world, i, j, k), "riverlands");
		}
	}

	public static class Sothoryos extends GOTGuiCraftingTable {
		public Sothoryos(InventoryPlayer inv, World world, int i, int j, int k) {
			super(new GOTContainerCraftingTable.Sothoryos(inv, world, i, j, k), "sothoryos");
		}
	}

	public static class Stormlands extends GOTGuiCraftingTable {
		public Stormlands(InventoryPlayer inv, World world, int i, int j, int k) {
			super(new GOTContainerCraftingTable.Stormlands(inv, world, i, j, k), "stormlands");
		}
	}

	public static class Summer extends GOTGuiCraftingTable {
		public Summer(InventoryPlayer inv, World world, int i, int j, int k) {
			super(new GOTContainerCraftingTable.Summer(inv, world, i, j, k), "summer");
		}
	}

	public static class Tyrosh extends GOTGuiCraftingTable {
		public Tyrosh(InventoryPlayer inv, World world, int i, int j, int k) {
			super(new GOTContainerCraftingTable.Tyrosh(inv, world, i, j, k), "tyrosh");
		}
	}

	public static class Volantis extends GOTGuiCraftingTable {
		public Volantis(InventoryPlayer inv, World world, int i, int j, int k) {
			super(new GOTContainerCraftingTable.Volantis(inv, world, i, j, k), "volantis");
		}
	}

	public static class Westerlands extends GOTGuiCraftingTable {
		public Westerlands(InventoryPlayer inv, World world, int i, int j, int k) {
			super(new GOTContainerCraftingTable.Westerlands(inv, world, i, j, k), "westerlands");
		}
	}

	public static class Wildling extends GOTGuiCraftingTable {
		public Wildling(InventoryPlayer inv, World world, int i, int j, int k) {
			super(new GOTContainerCraftingTable.Wildling(inv, world, i, j, k), "wildling");
		}
	}

	public static class YiTi extends GOTGuiCraftingTable {
		public YiTi(InventoryPlayer inv, World world, int i, int j, int k) {
			super(new GOTContainerCraftingTable.YiTi(inv, world, i, j, k), "yiti");
		}
	}
}