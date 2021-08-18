package me.a0g.hyk.commands;

import me.a0g.hyk.HypixelKentik;
import me.a0g.hyk.utils.ItemUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.FMLLog;

import java.util.List;

public class SlotNbt extends CommandBase {

    private final HypixelKentik main = HypixelKentik.getInstance();

    @Override
    public String getCommandName() {
        return "slotnbt";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/slotnbt";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender)
    {
        return true;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {

        if(args.length == 0){
            final EntityPlayer pl = Minecraft.getMinecraft().thePlayer;

            if(pl.getHeldItem() != null) {
                ItemStack stack = pl.getHeldItem();

                String message = stack.getTagCompound() + "";


                main.getUtils().sendMessage(message);
            }
        }
        else if(args.length == 1 && args[0].equalsIgnoreCase("test")){
            final EntityPlayer pl = Minecraft.getMinecraft().thePlayer;

            if(pl.getHeldItem() != null) {
                ItemStack stack = pl.getHeldItem();

                //item lore
                String tocompact = pl.getHeldItem().getTagCompound().getCompoundTag("display").getTag("Lore").toString();

                /*NBTTagCompound compound = pl.getHeldItem().getTagCompound().getCompoundTag("display");
                NBTTagList nbtTagList = compound.getTagList("Lore", Constants.NBT.TAG_COMPOUND);
                for(int i = 0;i<nbtTagList.tagCount();i++){
                 //   NBTTagCompound tag = nbtTagList.getCompoundTagAt(i);
                   // tag.
                    String tagString = nbtTagList.getStringTagAt(i);
                    FMLLog.info(tagString);
                }*/
                List<String> lores = ItemUtils.getItemLore(stack);
                for(String lore : lores){
                    FMLLog.info(lore);
                }
                FMLLog.info(lores.toString());


                main.getUtils().sendMessage(tocompact);

            }
        }
        else if(args.length == 1 && args[0].equalsIgnoreCase("name")){
            final EntityPlayer pl = Minecraft.getMinecraft().thePlayer;

            if(pl.getHeldItem() != null) {
                ItemStack stack = pl.getHeldItem();

                //item lore
                String tocompact = stack.getDisplayName();

                main.getUtils().sendMessage(tocompact);

            }
        }
        else if(args.length == 1 && args[0].equalsIgnoreCase("extra")) {
            final EntityPlayer pl = Minecraft.getMinecraft().thePlayer;

            if(pl.getHeldItem() != null) {
                ItemStack stack = pl.getHeldItem();

                //item lore
                String tocompact = stack.getTagCompound().getCompoundTag("ExtraAttributes").toString();

                main.getUtils().sendMessage(tocompact);

            }
        }
        else {
            main.getUtils().sendMessage(getCommandUsage(sender), false);
        }
    }
}
