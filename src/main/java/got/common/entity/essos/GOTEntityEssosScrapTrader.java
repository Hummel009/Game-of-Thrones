package got.common.entity.essos;

import got.common.database.GOTNames;
import got.common.database.GOTRegistry;
import got.common.entity.westeros.GOTEntityWesterosScrapTrader;
import got.common.item.other.GOTItemRobes;
import got.common.item.other.GOTItemTurban;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityEssosScrapTrader extends GOTEntityWesterosScrapTrader {
	public static ItemStack[] weapons = {new ItemStack(GOTRegistry.bronzeDagger), new ItemStack(GOTRegistry.ironDagger), new ItemStack(GOTRegistry.essosDagger), new ItemStack(GOTRegistry.essosDaggerPoisoned)};
	public static int[] robeColors = {3354412, 5984843, 5968655, 3619908, 9007463, 3228720};

	public GOTEntityEssosScrapTrader(World world) {
		super(world);
		canBeMarried = false;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		int i = rand.nextInt(weapons.length);
		npcItemsInv.setMeleeWeapon(weapons[i].copy());
		npcItemsInv.setIdleItem(null);
		setCurrentItemOrArmor(4, null);
		if (rand.nextInt(4) == 0) {
			ItemStack turban = new ItemStack(GOTRegistry.robesHelmet);
			int robeColor = robeColors[rand.nextInt(robeColors.length)];
			GOTItemRobes.setRobesColor(turban, robeColor);
			if (rand.nextInt(3) == 0) {
				GOTItemTurban.setHasOrnament(turban, true);
			}
			setCurrentItemOrArmor(4, turban);
		}
		return data;
	}

	@Override
	public void setupNPCName() {
		int i = rand.nextInt(6);
		switch (i) {
			case 0:
				familyInfo.setName(GOTNames.getGhiscarName(rand, true));
				break;
			case 2:
				familyInfo.setName(GOTNames.getLhazarName(rand, true));
				break;
			case 3:
				familyInfo.setName(GOTNames.getJogosName(rand, true));
				break;
			case 4:
				familyInfo.setName(GOTNames.getDothrakiName(rand, true));
				break;
			case 5:
				familyInfo.setName(GOTNames.getSothoryosName(rand, true));
				break;
			default:
				familyInfo.setName(GOTNames.getWildName(rand, true));
		}
	}
}
