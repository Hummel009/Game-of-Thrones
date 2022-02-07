package got.common.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.Entity;

public class GOTPacketHandler {
	private static SimpleNetworkWrapper networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel("got_");
	private static int id = 0;

	public GOTPacketHandler() {
		getNetworkWrapper().registerMessage(GOTPacketAchievement.Handler.class, GOTPacketAchievement.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketAchievementRemove.Handler.class, GOTPacketAchievementRemove.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketAlignment.Handler.class, GOTPacketAlignment.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketAlignmentBonus.Handler.class, GOTPacketAlignmentBonus.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketAlignmentSee.Handler.class, GOTPacketAlignmentSee.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketAnvilEngraveOwner.Handler.class, GOTPacketAnvilEngraveOwner.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketAnvilReforge.Handler.class, GOTPacketAnvilReforge.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketAnvilRename.Handler.class, GOTPacketAnvilRename.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketBannerData.Handler.class, GOTPacketBannerData.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketBannerRequestInvalidName.Handler.class, GOTPacketBannerRequestInvalidName.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketBannerValidate.Handler.class, GOTPacketBannerValidate.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketBiomeVariantsUnwatch.Handler.class, GOTPacketBiomeVariantsUnwatch.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketBiomeVariantsWatch.Handler.class, GOTPacketBiomeVariantsWatch.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketBlockFX.Handler.class, GOTPacketBlockFX.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketBrandingIron.Handler.class, GOTPacketBrandingIron.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketBrewingButton.Handler.class, GOTPacketBrewingButton.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketBrokenPledge.Handler.class, GOTPacketBrokenPledge.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketBuyUnit.Handler.class, GOTPacketBuyUnit.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketCWPProtectionMessage.Handler.class, GOTPacketCWPProtectionMessage.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketCWPSharedHide.Handler.class, GOTPacketCWPSharedHide.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketCWPSharedHideClient.Handler.class, GOTPacketCWPSharedHideClient.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketCWPSharedUnlockClient.Handler.class, GOTPacketCWPSharedUnlockClient.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketCancelItemHighlight.Handler.class, GOTPacketCancelItemHighlight.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketCape.Handler.class, GOTPacketCape.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketCargocart.Handler.class, GOTPacketCargocart.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketCargocartControl.Handler.class, GOTPacketCargocartControl.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketCargocartUpdate.Handler.class, GOTPacketCargocartUpdate.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketCheckMenuPrompt.Handler.class, GOTPacketCheckMenuPrompt.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketClientInfo.Handler.class, GOTPacketClientInfo.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketClientMQEvent.Handler.class, GOTPacketClientMQEvent.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketClientsideGUI.Handler.class, GOTPacketClientsideGUI.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketCoinExchange.Handler.class, GOTPacketCoinExchange.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketConquestGrid.Handler.class, GOTPacketConquestGrid.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketConquestGridRequest.Handler.class, GOTPacketConquestGridRequest.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketConquestNotification.Handler.class, GOTPacketConquestNotification.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketCreateCWP.Handler.class, GOTPacketCreateCWP.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketCreateCWPClient.Handler.class, GOTPacketCreateCWPClient.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketDate.Handler.class, GOTPacketDate.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketDeleteCWP.Handler.class, GOTPacketDeleteCWP.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketDeleteCWPClient.Handler.class, GOTPacketDeleteCWPClient.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketDeleteMiniquest.Handler.class, GOTPacketDeleteMiniquest.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketDragonControl.Handler.class, GOTPacketDragonControl.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketEditBanner.Handler.class, GOTPacketEditBanner.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketEditNPCRespawner.Handler.class, GOTPacketEditNPCRespawner.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketEditSign.Handler.class, GOTPacketEditSign.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketEnableAlignmentZones.Handler.class, GOTPacketEnableAlignmentZones.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketEntityUUID.Handler.class, GOTPacketEntityUUID.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketEnvironmentOverlay.Handler.class, GOTPacketEnvironmentOverlay.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketFTBounceClient.Handler.class, GOTPacketFTBounceClient.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketFTBounceServer.Handler.class, GOTPacketFTBounceServer.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketFTCooldown.Handler.class, GOTPacketFTCooldown.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketFTScreen.Handler.class, GOTPacketFTScreen.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketFTTimer.Handler.class, GOTPacketFTTimer.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketFactionData.Handler.class, GOTPacketFactionData.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketFactionRelations.Handler.class, GOTPacketFactionRelations.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketFamilyInfo.Handler.class, GOTPacketFamilyInfo.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketFastTravel.Handler.class, GOTPacketFastTravel.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketFellowship.Handler.class, GOTPacketFellowship.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketFellowshipCreate.Handler.class, GOTPacketFellowshipCreate.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketFellowshipDisband.Handler.class, GOTPacketFellowshipDisband.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketFellowshipDoPlayer.Handler.class, GOTPacketFellowshipDoPlayer.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketFellowshipLeave.Handler.class, GOTPacketFellowshipLeave.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketFellowshipNotification.Handler.class, GOTPacketFellowshipNotification.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketFellowshipPartialUpdate.AddMember.Handler.class, GOTPacketFellowshipPartialUpdate.AddMember.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketFellowshipPartialUpdate.ChangeIcon.Handler.class, GOTPacketFellowshipPartialUpdate.ChangeIcon.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketFellowshipPartialUpdate.RemoveAdmin.Handler.class, GOTPacketFellowshipPartialUpdate.RemoveAdmin.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketFellowshipPartialUpdate.RemoveMember.Handler.class, GOTPacketFellowshipPartialUpdate.RemoveMember.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketFellowshipPartialUpdate.Rename.Handler.class, GOTPacketFellowshipPartialUpdate.Rename.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketFellowshipPartialUpdate.SetAdmin.Handler.class, GOTPacketFellowshipPartialUpdate.SetAdmin.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketFellowshipPartialUpdate.SetOwner.Handler.class, GOTPacketFellowshipPartialUpdate.SetOwner.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketFellowshipPartialUpdate.ToggleHiredFriendlyFire.Handler.class, GOTPacketFellowshipPartialUpdate.ToggleHiredFriendlyFire.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketFellowshipPartialUpdate.TogglePvp.Handler.class, GOTPacketFellowshipPartialUpdate.TogglePvp.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketFellowshipPartialUpdate.ToggleShowMap.Handler.class, GOTPacketFellowshipPartialUpdate.ToggleShowMap.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketFellowshipPartialUpdate.UpdatePlayerTitle.Handler.class, GOTPacketFellowshipPartialUpdate.UpdatePlayerTitle.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketFellowshipRemove.Handler.class, GOTPacketFellowshipRemove.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketFellowshipRename.Handler.class, GOTPacketFellowshipRename.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketFellowshipRespondInvite.Handler.class, GOTPacketFellowshipRespondInvite.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketFellowshipSetIcon.Handler.class, GOTPacketFellowshipSetIcon.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketFellowshipToggle.Handler.class, GOTPacketFellowshipToggle.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketHiredGui.Handler.class, GOTPacketHiredGui.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketHiredInfo.Handler.class, GOTPacketHiredInfo.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketHiredUnitCommand.Handler.class, GOTPacketHiredUnitCommand.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketHiredUnitDismiss.Handler.class, GOTPacketHiredUnitDismiss.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketHiredUnitInteract.Handler.class, GOTPacketHiredUnitInteract.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketHornSelect.Handler.class, GOTPacketHornSelect.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketInvasionWatch.Handler.class, GOTPacketInvasionWatch.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketIsOpRequest.Handler.class, GOTPacketIsOpRequest.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketIsOpResponse.Handler.class, GOTPacketIsOpResponse.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketItemSquadron.Handler.class, GOTPacketItemSquadron.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketLocationFX.Handler.class, GOTPacketLocationFX.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketLogin.Handler.class, GOTPacketLogin.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketLoginPlayerData.Handler.class, GOTPacketLoginPlayerData.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketMapTp.Handler.class, GOTPacketMapTp.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketMenuPrompt.Handler.class, GOTPacketMenuPrompt.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketMercenaryInteract.Handler.class, GOTPacketMercenaryInteract.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketMessage.Handler.class, GOTPacketMessage.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketMiniquest.Handler.class, GOTPacketMiniquest.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketMiniquestOffer.Handler.class, GOTPacketMiniquestOffer.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketMiniquestOfferClose.Handler.class, GOTPacketMiniquestOfferClose.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketMiniquestRemove.Handler.class, GOTPacketMiniquestRemove.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketMiniquestTrack.Handler.class, GOTPacketMiniquestTrack.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketMiniquestTrackClient.Handler.class, GOTPacketMiniquestTrackClient.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketMoneyGet.Handler.class, GOTPacketMoneyGet.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketMoneyGive.Handler.class, GOTPacketMoneyGive.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketMountControl.Handler.class, GOTPacketMountControl.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketMountControlServerEnforce.Handler.class, GOTPacketMountControlServerEnforce.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketMountOpenInv.Handler.class, GOTPacketMountOpenInv.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketNPCCombatStance.Handler.class, GOTPacketNPCCombatStance.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketNPCFX.Handler.class, GOTPacketNPCFX.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketNPCIsEating.Handler.class, GOTPacketNPCIsEating.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketNPCIsOfferingQuest.Handler.class, GOTPacketNPCIsOfferingQuest.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketNPCRespawner.Handler.class, GOTPacketNPCRespawner.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketNPCSpeech.Handler.class, GOTPacketNPCSpeech.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketNPCSquadron.Handler.class, GOTPacketNPCSquadron.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketOpenSignEditor.Handler.class, GOTPacketOpenSignEditor.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketOptions.Handler.class, GOTPacketOptions.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketPlayerMovement.Handler.class, GOTPacketPlayerMovement.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketPledge.Handler.class, GOTPacketPledge.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketPledgeSet.Handler.class, GOTPacketPledgeSet.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketPortalPos.Handler.class, GOTPacketPortalPos.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketRenameCWP.Handler.class, GOTPacketRenameCWP.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketRenameCWPClient.Handler.class, GOTPacketRenameCWPClient.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketRenamePouch.Handler.class, GOTPacketRenamePouch.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketSealCracker.Handler.class, GOTPacketSealCracker.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketSelectCape.Handler.class, GOTPacketSelectCape.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketSelectShield.Handler.class, GOTPacketSelectShield.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketSelectTitle.Handler.class, GOTPacketSelectTitle.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketSell.Handler.class, GOTPacketSell.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketSetOption.Handler.class, GOTPacketSetOption.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketSetPlayerRotation.Handler.class, GOTPacketSetPlayerRotation.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketShareCWP.Handler.class, GOTPacketShareCWP.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketShareCWPClient.Handler.class, GOTPacketShareCWPClient.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketShield.Handler.class, GOTPacketShield.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketStopItemUse.Handler.class, GOTPacketStopItemUse.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketTitle.Handler.class, GOTPacketTitle.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketTraderInfo.Handler.class, GOTPacketTraderInfo.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketTraderInteract.Handler.class, GOTPacketTraderInteract.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketUnitTraderInteract.Handler.class, GOTPacketUnitTraderInteract.class, id++, Side.SERVER);
		getNetworkWrapper().registerMessage(GOTPacketUpdatePlayerLocations.Handler.class, GOTPacketUpdatePlayerLocations.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketUpdateViewingFaction.Handler.class, GOTPacketUpdateViewingFaction.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketWaypointRegion.Handler.class, GOTPacketWaypointRegion.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketWaypointUseCount.Handler.class, GOTPacketWaypointUseCount.class, id++, Side.CLIENT);
		getNetworkWrapper().registerMessage(GOTPacketWeaponFX.Handler.class, GOTPacketWeaponFX.class, id++, Side.CLIENT);
	}

	public static int getId() {
		return id;
	}

	public static SimpleNetworkWrapper getNetworkWrapper() {
		return networkWrapper;
	}

	public static NetworkRegistry.TargetPoint nearEntity(Entity entity, double range) {
		return new NetworkRegistry.TargetPoint(entity.dimension, entity.posX, entity.boundingBox.minY, entity.posZ, range);
	}

	public static void setId(int id) {
		GOTPacketHandler.id = id;
	}

	public static void setNetworkWrapper(SimpleNetworkWrapper networkWrapper) {
		GOTPacketHandler.networkWrapper = networkWrapper;
	}
}