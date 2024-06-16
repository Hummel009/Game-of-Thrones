package got.common.entity.essos.yiti;

import got.common.database.GOTItems;
import got.common.item.other.GOTItemRobes;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityYiTiLevyman extends GOTEntityYiTiMan {
	private static final ItemStack[] LEVY_WEAPONS = {new ItemStack(GOTItems.yitiDagger), new ItemStack(GOTItems.yitiDaggerPoisoned), new ItemStack(GOTItems.ironDagger), new ItemStack(GOTItems.bronzeDagger), new ItemStack(GOTItems.yitiBattleaxe), new ItemStack(Items.iron_sword), new ItemStack(GOTItems.bronzeSword), new ItemStack(GOTItems.ironBattleaxe), new ItemStack(GOTItems.bronzeBattleaxe), new ItemStack(GOTItems.yitiSpear), new ItemStack(GOTItems.ironSpear), new ItemStack(GOTItems.bronzeSpear)};
	private static final ItemStack[] LEVY_SPEARS = {new ItemStack(GOTItems.yitiSpear), new ItemStack(GOTItems.ironSpear), new ItemStack(GOTItems.bronzeSpear)};
	private static final ItemStack[] LEVY_BODIES = {new ItemStack(Items.leather_chestplate), new ItemStack(GOTItems.bronzeChestplate)};
	private static final ItemStack[] LEVY_LEGS = {new ItemStack(Items.leather_leggings), new ItemStack(GOTItems.bronzeLeggings)};
	private static final ItemStack[] LEVY_BOOTS = {new ItemStack(Items.leather_boots), new ItemStack(GOTItems.bronzeBoots)};
	private static final int[] KAFTAN_COLORS = {14823729, 11862016, 5512477, 14196753, 11374145, 7366222};

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityYiTiLevyman(World world) {
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
		int i = rand.nextInt(LEVY_WEAPONS.length);
		npcItemsInv.setMeleeWeapon(LEVY_WEAPONS[i].copy());
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		if (rand.nextInt(5) == 0) {
			npcItemsInv.setSpearBackup(npcItemsInv.getMeleeWeapon());
			i = rand.nextInt(LEVY_SPEARS.length);
			npcItemsInv.setMeleeWeapon(LEVY_SPEARS[i].copy());
		}
		i = rand.nextInt(LEVY_BOOTS.length);
		setCurrentItemOrArmor(1, LEVY_BOOTS[i].copy());
		i = rand.nextInt(LEVY_LEGS.length);
		setCurrentItemOrArmor(2, LEVY_LEGS[i].copy());
		i = rand.nextInt(LEVY_BODIES.length);
		setCurrentItemOrArmor(3, LEVY_BODIES[i].copy());
		setCurrentItemOrArmor(4, null);
		if (rand.nextBoolean()) {
			ItemStack kaftan = new ItemStack(GOTItems.kaftanChestplate);
			int kaftanColor = KAFTAN_COLORS[rand.nextInt(KAFTAN_COLORS.length)];
			GOTItemRobes.setRobesColor(kaftan, kaftanColor);
			setCurrentItemOrArmor(3, kaftan);
			if (rand.nextBoolean()) {
				ItemStack kaftanLegs = new ItemStack(GOTItems.kaftanLeggings);
				GOTItemRobes.setRobesColor(kaftanLegs, kaftanColor);
				setCurrentItemOrArmor(2, kaftanLegs);
			}
		}
		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}