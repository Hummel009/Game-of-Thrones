package got.common.fellowship;

import java.util.UUID;

public class GOTFellowshipInvite {
	private UUID fellowshipID;
	private UUID inviterID;

	public GOTFellowshipInvite(UUID fs, UUID inviter) {
		setFellowshipID(fs);
		setInviterID(inviter);
	}

	public UUID getFellowshipID() {
		return fellowshipID;
	}

	public UUID getInviterID() {
		return inviterID;
	}

	public void setFellowshipID(UUID fellowshipID) {
		this.fellowshipID = fellowshipID;
	}

	public void setInviterID(UUID inviterID) {
		this.inviterID = inviterID;
	}
}
