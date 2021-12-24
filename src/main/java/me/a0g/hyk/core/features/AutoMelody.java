package me.a0g.hyk.core.features;

import java.util.ArrayList;

import me.a0g.hyk.Hyk;
import me.a0g.hyk.events.TickEndEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoMelody {

    private final Hyk main = Hyk.getInstance();

    private boolean inHarp = false;

    private ArrayList<Item> lastInventory = new ArrayList<>();

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        if (event.gui instanceof GuiChest && main.getUtils().checkForSkyblock() && main.getHyConfig().isAutoMelody() &&
               main.getUtils().getGuiName(event.gui).startsWith("Harp -")) {
            this.lastInventory.clear();
            this.inHarp = true;
        }
    }

    @SubscribeEvent
    public void onTick(TickEndEvent event) {
        if (!this.inHarp || !main.getHyConfig().isAutoMelody() || Minecraft.getMinecraft().thePlayer == null)
            return;
        String inventoryName = main.getUtils().getInventoryName();
        if (inventoryName == null || !inventoryName.startsWith("Harp -"))
            this.inHarp = false;
        ArrayList<Item> thisInventory = new ArrayList<>();
        for (Slot slot : Minecraft.getMinecraft().thePlayer.openContainer.inventorySlots) {
            if (slot.getStack() != null)
                thisInventory.add(slot.getStack().getItem());
        }
        if (!this.lastInventory.toString().equals(thisInventory.toString()))
            for (Slot slot : Minecraft.getMinecraft().thePlayer.openContainer.inventorySlots) {
                if (slot.getStack() != null && slot.getStack().getItem() instanceof ItemBlock && ((ItemBlock)slot.getStack().getItem()).getBlock() == Blocks.quartz_block) {
                    Minecraft.getMinecraft().playerController.windowClick(Minecraft.getMinecraft().thePlayer.openContainer.windowId, slot.slotNumber, 2, 0, (EntityPlayer)Minecraft.getMinecraft().thePlayer);
                    break;
                }
            }
        this.lastInventory.clear();
        this.lastInventory.addAll(thisInventory);
    }

}
