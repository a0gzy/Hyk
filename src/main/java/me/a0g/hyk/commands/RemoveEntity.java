package me.a0g.hyk.commands;

import me.a0g.hyk.HypixelKentik;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

public class RemoveEntity extends CommandBase {

    private final HypixelKentik main = HypixelKentik.getInstance();

    @Override
    public String getCommandName() {
        return "removeentity";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return EnumChatFormatting.AQUA  + "removeentity <nick>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {

        if(args.length == 1) {
            final String username = args[0];

            for(Object e: Minecraft.getMinecraft().theWorld.loadedEntityList){
                if(e instanceof EntityPlayer && !(e instanceof EntityPlayerSP)) {

                    if(((EntityPlayer) e).getName().equalsIgnoreCase(username)) {
                            Minecraft.getMinecraft().theWorld.removeEntity((EntityPlayer) e);
                            main.getUtils().sendMessage( ((EntityPlayer) e).getDisplayName().getFormattedText() + EnumChatFormatting.GOLD + " is removed");
                    }
                }
            }
        }
        else{
            main.getUtils().sendMessage(getCommandUsage(sender), false);
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender)
    {
        return true;
    }

    @Override
    public List<String> addTabCompletionOptions(final ICommandSender sender, final String[] args, BlockPos pos) {
        // TODO Auto-generated method stub
        return (args.length == 1) ? main.getTabutil().getListOfStringsMatchingLastWord(args, main.getTabutil().getTabUsernames()) : null;
    }
}
