package got.common.entity.other;

import got.common.database.GOTRegistry;
import got.common.item.other.*;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityThiefEssos extends GOTEntityThief {
	public static ItemStack[] weapons = { new ItemStack(GOTRegistry.bronzeDagger), new ItemStack(GOTRegistry.ironDagger), new ItemStack(GOTRegistry.essosDagger), new ItemStack(GOTRegistry.essosDaggerPoisoned) };
	public static int[] robeColors = { 3354412, 5984843, 5968655, 3619908, 9007463, 3228720 };

	public GOTEntityThiefEssos(World world) {
		super(world);
		canBeMarried = false;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		int i = rand.nextInt(weapons.length);
		npcItemsInv.setMeleeWeapon(weapons[i].copy());
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
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
}
