package got.common.entity.essos.pentos;

import got.common.database.GOTItems;
import got.common.entity.other.utils.GOTEntityUtils;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityPentosLevyman extends GOTEntityPentosMan {
	private static final ItemStack[] WEAPONS = {new ItemStack(GOTItems.essosSword), new ItemStack(GOTItems.essosDagger), new ItemStack(GOTItems.essosDaggerPoisoned), new ItemStack(GOTItems.essosHammer)};

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityPentosLevyman(World world) {
		super(world);
		addTargetTasks(true);
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);
		int i = rand.nextInt(WEAPONS.length);
		npcItemsInv.setMeleeWeapon(WEAPONS[i].copy());
		if (rand.nextInt(5) == 0) {
			npcItemsInv.setSpearBackup(npcItemsInv.getMeleeWeapon());
			npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.essosSpear));
		}
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		GOTEntityUtils.setupEssosLevymanArmor(this, rand);
		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}