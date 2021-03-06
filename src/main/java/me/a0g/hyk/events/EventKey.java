package me.a0g.hyk.events;

import gg.essential.api.utils.GuiUtil;
import me.a0g.hyk.Hyk;
import me.a0g.hyk.commands.HyK;
import me.a0g.hyk.core.Feature;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSkull;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

import java.util.Objects;

public class EventKey {

    private final Hyk main = Hyk.getInstance();
    private final Minecraft mc = Minecraft.getMinecraft();

    public static boolean shifting = false;

    @SubscribeEvent
    public void onEvent(InputEvent.KeyInputEvent event) {



        KeyBinding[] keyBindings = main.keyBindings;

        // P
        if (keyBindings[0].isPressed()) {

            if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
                //HypixelKentik.guiToOpen = "edit2";
                //GuiUtil.open(Objects.requireNonNull(new EditLocationsGui()));
                GuiUtil.open(Objects.requireNonNull(HyK.privateGui));
                Feature.REACHRANGE.floatValue = main.getHyConfig().getRechr();
                Feature.REACH.isOn = main.getHyConfig().isRech();
                Feature.NAMETAG.isOn = main.getHyConfig().isNamet();

                return;
            }

          //  Minecraft.getMinecraft().displayGuiScreen(main.getHyConfig().gui());
            try {
                GuiUtil.open(Objects.requireNonNull(main.getHyConfig().gui()));
            }catch (Exception e){
                e.printStackTrace();
                main.getHyConfig().preload();
            }

           // GuiUtil.open(main.getHyConfig().gui());
           // ModCore.getInstance().getGuiHandler().open(main.getHyConfig().gui());

        }

        //render info  J
        if(keyBindings[2].isPressed()){

            if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                shifting = !shifting;
              //  mc.thePlayer.setSneaking(shifting);
              //  mc.thePlayer.movementInput.sneak = true;
               // FMLLog.info(  mc.thePlayer.movementInput.sneak + "");
              //  KeyBinding.setKeyBindState( Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode() , shifting );
                return;
            }


            if(main.getUtils().checkForIsland() && Render.farm){
                Render.farmclicksrender = ! Render.farmclicksrender;
            }

            if(Render.todesignp != null && Render.todesignbw != null && Render.todesignsw != null && Render.todesignsb != null) {
                Render.todesignrender = !Render.todesignrender;
            }

        }



        //x
        if(keyBindings[4].isPressed()){
            if(main.getUtils().checkForSkyblock()) {
                if (main.getHyConfig().isXr() || main.hray) {

                    main.hray = !main.hray;
                    Minecraft.getMinecraft().renderGlobal.loadRenderers();
                    return;
                }
            }
        }

        //farm  H
        if(keyBindings[3].isPressed()){

            if(Render.farm){

                KeyBinding.setKeyBindState(32,false);
                KeyBinding.setKeyBindState(30,false);
              //  KeyBinding.setKeyBindState(-100,false);
            }
            else {
                Render.farmstart = true;
            }
            Render.farm = !Render.farm;


        }


        if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
            if(Render.todesignpage > 1 && Render.todesignrender)
            Render.todesignpage = Render.todesignpage - 1;

            if(Render.farm)
                Render.farmLegit = !Render.farmLegit;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
            if(Render.todesignpage < 4 && Render.todesignrender)
                Render.todesignpage = Render.todesignpage + 1;

            if(Render.farm)
                Render.farmLegit = !Render.farmLegit;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
            if(Render.farm){
                mc.thePlayer.rotationPitch = mc.thePlayer.rotationPitch + 1.0f;
                Render.Pitch = Render.Pitch + 1.0f;
            }
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
            if(Render.farm){
                mc.thePlayer.rotationPitch = mc.thePlayer.rotationPitch - 1.0f;
                Render.Pitch = Render.Pitch - 1.0f;
            }
        }

        /// K
        if (keyBindings[1].isPressed()) {

            if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                if (main.getUtils().checkScoreboardString("Jerry's")) {
                    for(Object o: Minecraft.getMinecraft().theWorld.loadedEntityList) {
                        if (o instanceof EntityArmorStand) {
                            if (main.getUtils().checkForSkyblock()) {
                                if (((EntityArmorStand) o).getEquipmentInSlot(4) != null) {
                                    if (((EntityArmorStand) o).getEquipmentInSlot(4).getItem() instanceof ItemSkull) {

                                        String message34 = "";
                                        if (((EntityArmorStand) o).getEquipmentInSlot(4).hasTagCompound()) {
                                            message34 = ((EntityArmorStand) o).getEquipmentInSlot(4).getTagCompound().getCompoundTag("SkullOwner").getTag("Id") + "";
                                        }
                                        if (message34.contains("7732c5e4-1800-3b90-a70f-727d2969254b")) {

                                            if(Minecraft.getMinecraft().thePlayer.getDistanceToEntity( ((EntityArmorStand) o)  ) < 3) {
                                                Minecraft.getMinecraft().theWorld.removeEntity((EntityArmorStand) o);
                                            }

                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return;
            }

            if (main.getUtils().checkScoreboardString("Void Sepulture")) {

                for (Object o : Minecraft.getMinecraft().theWorld.loadedEntityList) {
                    if (!(o instanceof EntityEnderman) && !(o instanceof EntityPlayerSP)) {
                        if (Minecraft.getMinecraft().thePlayer.getDistanceToEntity((Entity) o) < 5) {
                            Minecraft.getMinecraft().theWorld.removeEntity((Entity) o);
                        }
                    }


                }
                return;
            }

            for (Object o : Minecraft.getMinecraft().theWorld.loadedEntityList) {
                if (o instanceof EntityPlayer && !(o instanceof EntityPlayerSP)) {
                    if (Minecraft.getMinecraft().thePlayer.getDistanceToEntity((EntityPlayer) o) < 5) {
                        Minecraft.getMinecraft().theWorld.removeEntity((EntityPlayer) o);
                    }
                }

            }
        }

    }
}
