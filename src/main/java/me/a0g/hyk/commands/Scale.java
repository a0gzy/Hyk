package me.a0g.hyk.commands;

import me.a0g.hyk.HypixelKentik;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;

import java.util.List;

public class Scale extends CommandBase {

    private final HypixelKentik main = HypixelKentik.getInstance();

    public static double mainScale;
    public static double sizerScale;
//    public static double cakeScale;
//    public static double armorScale;
//    public static double commsScale;

    @Override
    public String getCommandName() {
        return "scale";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "/scale";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] args) throws CommandException {
        if(args[0].equalsIgnoreCase("main")){
            double scaleAmount = Math.floor(Double.parseDouble(args[1]) * 100.0) / 100.0;
            mainScale = scaleAmount;
            main.getHyConfig().setScale( mainScale + "" );
            main.getHyConfig().markDirty();
            main.getHyConfig().writeData();
        }
        if(args.length == 2 && args[0].equalsIgnoreCase("sizer")){
            double scaleAmount = Math.floor(Double.parseDouble(args[1]) * 100.0) / 100.0;
            sizerScale = scaleAmount;
            main.getHyConfig().setSizescale( sizerScale + "" );
            main.getHyConfig().markDirty();
            main.getHyConfig().writeData();
        }
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, "main");
        }
        return null;
    }
}
