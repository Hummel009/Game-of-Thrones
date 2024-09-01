package got.common.brotherhood;

import java.util.UUID;

public class GOTBrotherhoodInvite {
	private final UUID inviterID;

	private final UUID brotherhoodID;

	public GOTBrotherhoodInvite(UUID fs, UUID inviter) {
		brotherhoodID = fs;
		inviterID = inviter;
	}

	public UUID getBrotherhoodID() {
		return brotherhoodID;
	}

	public UUID getInviterID() {
		return inviterID;
	}
}