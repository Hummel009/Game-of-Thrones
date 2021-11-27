package got.common.fellowship;

import java.util.UUID;

public class GOTFellowshipInvite {
	public UUID fellowshipID;
	public UUID inviterID;

	public GOTFellowshipInvite(UUID fs, UUID inviter) {
		fellowshipID = fs;
		inviterID = inviter;
	}
}
