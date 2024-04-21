package got.common.entity.other;

import got.common.database.GOTItems;
import got.common.database.GOTNames;
import got.common.item.other.GOTItemRobes;
import got.common.item.other.GOTItemTurban;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityDarkSkinThief extends GOTEntityLightSkinThief {
	private static final ItemStack[] WEAPONS = {new ItemStack(GOTItems.bronzeDagger), new ItemStack(GOTItems.ironDagger), new ItemStack(GOTItems.essosDagger), new ItemStack(GOTItems.essosDaggerPoisoned)};
	private static final int[] ROBE_COLORS = {3354412, 5984843, 5968655, 3619908, 9007463, 3228720};

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityDarkSkinThief(World world) {
		super(world);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);
		int i = rand.nextInt(WEAPONS.length);
		npcItemsInv.setMeleeWeapon(WEAPONS[i].copy());
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(4, null);
		if (rand.nextInt(4) == 0) {
			ItemStack turban = new ItemStack(GOTItems.robesHelmet);
			int robeColor = ROBE_COLORS[rand.nextInt(ROBE_COLORS.length)];
			GOTItemRobes.setRobesColor(turban, robeColor);
			if (rand.nextInt(3) == 0) {
				GOTItemTurban.setHasOrnament(turban, true);
			}
			setCurrentItemOrArmor(4, turban);
		}
		return entityData;
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