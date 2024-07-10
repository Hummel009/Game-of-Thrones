package got.common.entity.westeros.legendary.captain;

import got.common.database.*;
import got.common.entity.ai.GOTEntityAINearestAttackableTargetBasic;
import got.common.entity.essos.ghiscar.GOTEntityGhiscarHarpy;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.other.iface.GOTUnitTradeable;
import got.common.faction.GOTFaction;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityBarristanSelmy extends GOTEntityHumanBase implements GOTUnitTradeable {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityBarristanSelmy(World world) {
		super(world);
		addTargetTasks();
		setupLegendaryNPC(true);
	}

	@Override
	public GOTCapes getCape() {
		return GOTCapes.ROYALGUARD;
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.CROWNLANDS;
	}

	@Override
	public float getAlignmentBonus() {
		return 200.0f;
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killBarristanSelmy;
	}

	private void addTargetTasks() {
		int target = addTargetTasks(true);
		targetTasks.addTask(target + 1, new GOTEntityAINearestAttackableTargetBasic(this, GOTEntityGhiscarHarpy.class, 0, true));
	}

	@Override
	public GOTUnitTradeEntries getUnits() {
		return GOTUnitTradeEntries.KINGSGUARD;
	}

	@Override
	public GOTInvasions getWarhorn() {
		return null;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.alloySteelSword));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.kingsguardBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.kingsguardLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.kingsguardChestplate));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}