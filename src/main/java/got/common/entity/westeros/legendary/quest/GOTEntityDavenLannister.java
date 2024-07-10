package got.common.entity.westeros.legendary.quest;

import got.common.database.GOTItems;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.faction.GOTFaction;
import got.common.quest.GOTMiniQuestFactory;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityDavenLannister extends GOTEntityHumanBase {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityDavenLannister(World world) {
		super(world);
		setupLegendaryNPC(true);
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.WESTERLANDS;
	}

	@Override
	public GOTMiniQuestFactory getMiniQuestFactory() {
		return GOTMiniQuestFactory.DAVEN;
	}

	@Override
	public float getAlignmentBonus() {
		return 100.0f;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.alloySteelSword));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.westerlandsBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.westerlandsLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.westerlandsChestplate));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}