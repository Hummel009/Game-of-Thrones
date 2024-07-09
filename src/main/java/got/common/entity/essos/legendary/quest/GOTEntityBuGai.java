package got.common.entity.essos.legendary.quest;

import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.faction.GOTFaction;
import got.common.quest.GOTMiniQuestFactory;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityBuGai extends GOTEntityHumanBase {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityBuGai(World world) {
		super(world);
		setupLegendaryNPC(true);
		faction = GOTFaction.YI_TI;
		miniQuestFactory = GOTMiniQuestFactory.BUGAI;
		alignmentBonus = 500.0f;
		killAchievement = GOTAchievement.killBuGai;
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		dropItem(GOTItems.katana, 1);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.katana));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}