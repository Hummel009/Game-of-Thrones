package got.common.command;

import java.util.List;

import got.common.GOTLevelData;
import net.minecraft.command.*;

public class GOTCommandEnableAlignmentZones
extends CommandBase {
    public String getCommandName() {
        return "alignmentZones";
    }

    public int getRequiredPermissionLevel() {
        return 2;
    }

    public String getCommandUsage(ICommandSender sender) {
        return "got.command.alignmentZones.usage";
    }

    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length >= 1) {
            String flag = args[0];
            if (flag.equals("enable")) {
                GOTLevelData.setEnableAlignmentZones(true);
                GOTCommandEnableAlignmentZones.func_152373_a((ICommandSender)sender, (ICommand)this, (String)"got.command.alignmentZones.enable", (Object[])new Object[0]);
                return;
            }
            if (flag.equals("disable")) {
            	GOTLevelData.setEnableAlignmentZones(false);
                GOTCommandEnableAlignmentZones.func_152373_a((ICommandSender)sender, (ICommand)this, (String)"got.command.alignmentZones.disable", (Object[])new Object[0]);
                return;
            }
        }
        throw new WrongUsageException(this.getCommandUsage(sender), new Object[0]);
    }

    public List addTabCompletionOptions(ICommandSender sender, String[] args) {
        if (args.length == 1) {
            return GOTCommandEnableAlignmentZones.getListOfStringsMatchingLastWord((String[])args, (String[])new String[]{"enable", "disable"});
        }
        return null;
    }

    public boolean isUsernameIndex(String[] args, int i) {
        return false;
    }
}

