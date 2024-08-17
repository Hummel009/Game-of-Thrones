package got.common.entity.essos.yi_ti;

import got.common.block.other.GOTBlockBomb;
import got.common.database.GOTBlocks;
import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.database.GOTShields;
import got.common.entity.ai.GOTEntityAIAttackOnCollide;
import got.common.entity.ai.GOTEntityAIBombardierAttack;
import got.common.entity.ai.GOTEntityAISmoke;
import got.common.entity.other.inanimate.GOTEntityBomb;
import got.common.entity.other.utils.GOTEntityUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityYiTiBombardier extends GOTEntityYiTiMan {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityYiTiBombardier(World world) {
		super(world);
		addTargetTasks(true);
		tasks.addTask(1, new EntityAIAvoidEntity(this, GOTEntityBomb.class, 12.0f, 1.5, 2.0));
		GOTEntityUtils.removeAITask(this, GOTEntityAISmoke.class);
	}

	@Override
	public GOTShields getShield() {
		return GOTShields.YI_TI_BOMBARDIER;
	}

	@Override
	public GOTCapes getCape() {
		return GOTCapes.YI_TI_BOMBARDIER;
	}

	@Override
	public float getAlignmentBonus() {
		return 3.0f;
	}

	@Override
	public EntityAIBase getAttackAI() {
		tasks.addTask(2, new GOTEntityAIBombardierAttack(this, 1.4));
		return new GOTEntityAIAttackOnCollide(this, 1.4, false);
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(17, (byte) -1);
	}

	@Override
	public ItemStack getHeldItemLeft() {
		if (npcItemsInv.getBomb() != null) {
			return npcItemsInv.getBomb();
		}
		return super.getHeldItemLeft();
	}

	@Override
	public void onAttackModeChange(AttackMode mode, boolean mounted) {
		if (npcItemsInv.getBomb() != null) {
			setCurrentItemOrArmor(0, npcItemsInv.getBombingItem());
		} else {
			super.onAttackModeChange(mode, mounted);
		}
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (worldObj.isRemote) {
			byte meta = dataWatcher.getWatchableObjectByte(17);
			if (meta == -1) {
				npcItemsInv.setBomb(null);
			} else {
				npcItemsInv.setBomb(new ItemStack(GOTBlocks.bomb, 1, meta));
			}
		} else {
			ItemStack bomb = npcItemsInv.getBomb();
			int meta = -1;
			if (bomb != null && Block.getBlockFromItem(bomb.getItem()) instanceof GOTBlockBomb) {
				meta = bomb.getItemDamage();
			}
			dataWatcher.updateObject(17, (byte) meta);
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setBombingItem(new ItemStack(GOTItems.fuse));
		npcItemsInv.setBomb(new ItemStack(GOTBlocks.bomb, 1, 1));
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.ironDagger));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.yiTiBombardierBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.yiTiBombardierLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.yiTiBombardierChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.yiTiBombardierHelmet));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}