package got.common.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.Entity;

public class GOTPacketHandler {
	public static final SimpleNetworkWrapper NETWORK_WRAPPER = NetworkRegistry.INSTANCE.newSimpleChannel("got_");

	private GOTPacketHandler() {
	}

	@SuppressWarnings({"UnusedAssignment", "ValueOfIncrementOrDecrementUsed"})
	public static void preInit() {
		int id = 0;
		NETWORK_WRAPPER.registerMessage(GOTPacketEnableReputationZones.Handler.class, GOTPacketEnableReputationZones.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketFellowshipInvitePlayer.Handler.class, GOTPacketFellowshipInvitePlayer.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketFellowshipAcceptInviteResult.Handler.class, GOTPacketFellowshipAcceptInviteResult.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketAchievement.Handler.class, GOTPacketAchievement.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketAchievementRemove.Handler.class, GOTPacketAchievementRemove.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketReputation.Handler.class, GOTPacketReputation.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketReputationBonus.Handler.class, GOTPacketReputationBonus.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketReputationSee.Handler.class, GOTPacketReputationSee.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketAnvilEngraveOwner.Handler.class, GOTPacketAnvilEngraveOwner.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketAnvilReforge.Handler.class, GOTPacketAnvilReforge.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketAnvilRename.Handler.class, GOTPacketAnvilRename.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketBannerData.Handler.class, GOTPacketBannerData.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketBannerRequestInvalidName.Handler.class, GOTPacketBannerRequestInvalidName.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketBannerValidate.Handler.class, GOTPacketBannerValidate.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketBiomeVariantsUnwatch.Handler.class, GOTPacketBiomeVariantsUnwatch.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketBiomeVariantsWatch.Handler.class, GOTPacketBiomeVariantsWatch.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketBrandingIron.Handler.class, GOTPacketBrandingIron.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketBrewingButton.Handler.class, GOTPacketBrewingButton.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketBrokenOath.Handler.class, GOTPacketBrokenOath.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketBuyUnit.Handler.class, GOTPacketBuyUnit.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketCWPProtectionMessage.Handler.class, GOTPacketCWPProtectionMessage.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketCWPSharedHide.Handler.class, GOTPacketCWPSharedHide.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketCWPSharedHideClient.Handler.class, GOTPacketCWPSharedHideClient.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketCWPSharedUnlockClient.Handler.class, GOTPacketCWPSharedUnlockClient.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketCancelItemHighlight.Handler.class, GOTPacketCancelItemHighlight.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketCape.Handler.class, GOTPacketCape.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketCargocart.Handler.class, GOTPacketCargocart.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketCargocartControl.Handler.class, GOTPacketCargocartControl.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketCargocartUpdate.Handler.class, GOTPacketCargocartUpdate.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketCheckMenuPrompt.Handler.class, GOTPacketCheckMenuPrompt.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketClientInfo.Handler.class, GOTPacketClientInfo.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketClientMQEvent.Handler.class, GOTPacketClientMQEvent.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketClientsideGUI.Handler.class, GOTPacketClientsideGUI.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketCoinExchange.Handler.class, GOTPacketCoinExchange.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketConquestGrid.Handler.class, GOTPacketConquestGrid.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketConquestGridRequest.Handler.class, GOTPacketConquestGridRequest.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketConquestNotification.Handler.class, GOTPacketConquestNotification.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketCreateCWP.Handler.class, GOTPacketCreateCWP.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketCreateCWPClient.Handler.class, GOTPacketCreateCWPClient.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketDate.Handler.class, GOTPacketDate.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketDeleteCWP.Handler.class, GOTPacketDeleteCWP.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketDeleteCWPClient.Handler.class, GOTPacketDeleteCWPClient.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketDeleteMiniquest.Handler.class, GOTPacketDeleteMiniquest.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketDragonControl.Handler.class, GOTPacketDragonControl.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketDragonFireballTimer.Handler.class, GOTPacketDragonFireballTimer.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketEditBanner.Handler.class, GOTPacketEditBanner.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketEditNPCRespawner.Handler.class, GOTPacketEditNPCRespawner.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketEditSign.Handler.class, GOTPacketEditSign.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketEntityUUID.Handler.class, GOTPacketEntityUUID.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketEnvironmentOverlay.Handler.class, GOTPacketEnvironmentOverlay.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketFTBounceClient.Handler.class, GOTPacketFTBounceClient.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketFTBounceServer.Handler.class, GOTPacketFTBounceServer.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketFTCooldown.Handler.class, GOTPacketFTCooldown.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketFTScreen.Handler.class, GOTPacketFTScreen.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketFTTimer.Handler.class, GOTPacketFTTimer.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketFactionData.Handler.class, GOTPacketFactionData.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketFactionRelations.Handler.class, GOTPacketFactionRelations.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketFamilyInfo.Handler.class, GOTPacketFamilyInfo.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketFastTravel.Handler.class, GOTPacketFastTravel.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketFellowship.Handler.class, GOTPacketFellowship.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketFellowshipCreate.Handler.class, GOTPacketFellowshipCreate.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketFellowshipDisband.Handler.class, GOTPacketFellowshipDisband.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketFellowshipDoPlayer.Handler.class, GOTPacketFellowshipDoPlayer.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketFellowshipLeave.Handler.class, GOTPacketFellowshipLeave.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketFellowshipNotification.Handler.class, GOTPacketFellowshipNotification.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketFellowshipPartialUpdate.AddMember.Handler.class, GOTPacketFellowshipPartialUpdate.AddMember.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketFellowshipPartialUpdate.ChangeIcon.Handler.class, GOTPacketFellowshipPartialUpdate.ChangeIcon.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketFellowshipPartialUpdate.RemoveAdmin.Handler.class, GOTPacketFellowshipPartialUpdate.RemoveAdmin.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketFellowshipPartialUpdate.RemoveMember.Handler.class, GOTPacketFellowshipPartialUpdate.RemoveMember.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketFellowshipPartialUpdate.Rename.Handler.class, GOTPacketFellowshipPartialUpdate.Rename.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketFellowshipPartialUpdate.SetAdmin.Handler.class, GOTPacketFellowshipPartialUpdate.SetAdmin.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketFellowshipPartialUpdate.SetOwner.Handler.class, GOTPacketFellowshipPartialUpdate.SetOwner.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketFellowshipPartialUpdate.ToggleHiredFriendlyFire.Handler.class, GOTPacketFellowshipPartialUpdate.ToggleHiredFriendlyFire.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketFellowshipPartialUpdate.TogglePvp.Handler.class, GOTPacketFellowshipPartialUpdate.TogglePvp.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketFellowshipPartialUpdate.ToggleShowMap.Handler.class, GOTPacketFellowshipPartialUpdate.ToggleShowMap.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketFellowshipPartialUpdate.UpdatePlayerTitle.Handler.class, GOTPacketFellowshipPartialUpdate.UpdatePlayerTitle.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketFellowshipRemove.Handler.class, GOTPacketFellowshipRemove.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketFellowshipRename.Handler.class, GOTPacketFellowshipRename.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketFellowshipRespondInvite.Handler.class, GOTPacketFellowshipRespondInvite.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketFellowshipSetIcon.Handler.class, GOTPacketFellowshipSetIcon.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketFellowshipToggle.Handler.class, GOTPacketFellowshipToggle.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketHiredGui.Handler.class, GOTPacketHiredGui.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketHiredInfo.Handler.class, GOTPacketHiredInfo.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketHiredUnitCommand.Handler.class, GOTPacketHiredUnitCommand.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketHiredUnitDismiss.Handler.class, GOTPacketHiredUnitDismiss.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketHiredUnitInteract.Handler.class, GOTPacketHiredUnitInteract.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketHornSelect.Handler.class, GOTPacketHornSelect.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketInvasionWatch.Handler.class, GOTPacketInvasionWatch.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketIsOpRequest.Handler.class, GOTPacketIsOpRequest.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketIsOpResponse.Handler.class, GOTPacketIsOpResponse.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketItemSquadron.Handler.class, GOTPacketItemSquadron.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketLocationFX.Handler.class, GOTPacketLocationFX.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketLogin.Handler.class, GOTPacketLogin.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketLoginPlayerData.Handler.class, GOTPacketLoginPlayerData.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketMapTp.Handler.class, GOTPacketMapTp.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketMenuPrompt.Handler.class, GOTPacketMenuPrompt.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketMercenaryInteract.Handler.class, GOTPacketMercenaryInteract.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketMessage.Handler.class, GOTPacketMessage.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketMiniquest.Handler.class, GOTPacketMiniquest.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketMiniquestOffer.Handler.class, GOTPacketMiniquestOffer.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketMiniquestOfferClose.Handler.class, GOTPacketMiniquestOfferClose.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketMiniquestRemove.Handler.class, GOTPacketMiniquestRemove.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketMiniquestTrack.Handler.class, GOTPacketMiniquestTrack.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketMiniquestTrackClient.Handler.class, GOTPacketMiniquestTrackClient.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketMoneyGet.Handler.class, GOTPacketMoneyGet.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketMoneyGive.Handler.class, GOTPacketMoneyGive.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketMountControl.Handler.class, GOTPacketMountControl.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketMountControlServerEnforce.Handler.class, GOTPacketMountControlServerEnforce.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketNPCCombatStance.Handler.class, GOTPacketNPCCombatStance.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketNPCFX.Handler.class, GOTPacketNPCFX.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketNPCIsEating.Handler.class, GOTPacketNPCIsEating.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketNPCIsOfferingQuest.Handler.class, GOTPacketNPCIsOfferingQuest.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketNPCRespawner.Handler.class, GOTPacketNPCRespawner.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketNPCSpeech.Handler.class, GOTPacketNPCSpeech.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketNPCSquadron.Handler.class, GOTPacketNPCSquadron.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketOpenSignEditor.Handler.class, GOTPacketOpenSignEditor.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketOptions.Handler.class, GOTPacketOptions.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketOath.Handler.class, GOTPacketOath.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketOathSet.Handler.class, GOTPacketOathSet.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketPortalPos.Handler.class, GOTPacketPortalPos.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketRenameCWP.Handler.class, GOTPacketRenameCWP.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketRenameCWPClient.Handler.class, GOTPacketRenameCWPClient.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketRenamePouch.Handler.class, GOTPacketRenamePouch.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketSelectCape.Handler.class, GOTPacketSelectCape.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketSelectShield.Handler.class, GOTPacketSelectShield.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketSelectTitle.Handler.class, GOTPacketSelectTitle.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketSell.Handler.class, GOTPacketSell.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketSetOption.Handler.class, GOTPacketSetOption.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketShareCWP.Handler.class, GOTPacketShareCWP.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketShareCWPClient.Handler.class, GOTPacketShareCWPClient.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketShield.Handler.class, GOTPacketShield.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketStopItemUse.Handler.class, GOTPacketStopItemUse.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketTitle.Handler.class, GOTPacketTitle.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketTraderInfo.Handler.class, GOTPacketTraderInfo.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketTraderInteract.Handler.class, GOTPacketTraderInteract.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketUnitTraderInteract.Handler.class, GOTPacketUnitTraderInteract.class, id++, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(GOTPacketUpdatePlayerLocations.Handler.class, GOTPacketUpdatePlayerLocations.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketUpdateViewingFaction.Handler.class, GOTPacketUpdateViewingFaction.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketWaypointRegion.Handler.class, GOTPacketWaypointRegion.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketWaypointUseCount.Handler.class, GOTPacketWaypointUseCount.class, id++, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(GOTPacketWeaponFX.Handler.class, GOTPacketWeaponFX.class, id++, Side.CLIENT);
	}

	public static NetworkRegistry.TargetPoint nearEntity(Entity entity, double range) {
		return new NetworkRegistry.TargetPoint(entity.dimension, entity.posX, entity.boundingBox.minY, entity.posZ, range);
	}
}