package me.a0g.hyk.commands;

import me.a0g.hyk.HypixelKentik;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;

import java.util.List;

public class Move extends CommandBase {

    private final HypixelKentik main = HypixelKentik.getInstance();
    public static int[] mainXY = {0, 0};
    public static int[] cakeXY = {0, 0};
    public static int[] armorXY = {0, 0};
    public static int[] commsXY = {0, 0};

    @Override
    public String getCommandName() {
        return "move";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "/move";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] args) throws CommandException {
        int arg1 = Integer.parseInt(args[1]);
        int arg2 = Integer.parseInt(args[2]);
        if(args[0].equalsIgnoreCase("main")){
            mainXY[0] = arg1;
            mainXY[1] = arg2;
            main.getHyConfig().setMainx( mainXY[0] );
            main.getHyConfig().setMainy( mainXY[1] );
            main.getHyConfig().markDirty();
            main.getHyConfig().writeData();
        }
        else if (args[0].equalsIgnoreCase("cake") ){
            cakeXY[0] = arg1;
            cakeXY[1] = arg2;
            main.getHyConfig().setMainx( cakeXY[0] );
            main.getHyConfig().setMainy( cakeXY[1] );
            main.getHyConfig().markDirty();
            main.getHyConfig().writeData();
        }
        else if (args[0].equalsIgnoreCase("armor") ){
            armorXY[0] = arg1;
            armorXY[1] = arg2;
            main.getHyConfig().setArmorx( armorXY[0] );
            main.getHyConfig().setArmory( armorXY[1] );
            main.getHyConfig().markDirty();
            main.getHyConfig().writeData();
        }
        else if (args[0].equalsIgnoreCase("comms") ){
            commsXY[0] = arg1;
            commsXY[1] = arg2;
            main.getHyConfig().setCommsx( commsXY[0] );
            main.getHyConfig().setCommsy( commsXY[1] );
            main.getHyConfig().markDirty();
            main.getHyConfig().writeData();
        }
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, "main","cake","armor","comms");
        }
        return null;
    }

}
