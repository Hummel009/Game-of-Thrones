package got.common.entity.westeros.legendary.trader;

import got.common.database.GOTTradeEntries;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.other.iface.GOTTradeable;
import got.common.faction.GOTFaction;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.world.World;

public class GOTEntityHotPie extends GOTEntityHumanBase implements GOTTradeable {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityHotPie(World world) {
		super(world);
		setupLegendaryNPC(true);
		setSize(0.6f * 0.9f, 1.8f * 0.9f);
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.RIVERLANDS;
	}

	@Override
	public float getAlignmentBonus() {
		return 1.0f;
	}

	@Override
	public EntityAIBase getAttackAI() {
		return new EntityAIPanic(this, 1.4);
	}

	@Override
	public GOTTradeEntries getSellsPool() {
		return GOTTradeEntries.BAKER_SELLS;
	}

	@Override
	public GOTTradeEntries getBuysPool() {
		return GOTTradeEntries.BAKER_BUYS;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}