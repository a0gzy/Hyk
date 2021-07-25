package me.a0g.hyk.events;

import gg.essential.api.utils.GuiUtil;
import me.a0g.hyk.HypixelKentik;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

import java.sql.ResultSet;
import java.util.Objects;

public class EventKey {

    private final HypixelKentik main = HypixelKentik.getInstance();
    private final Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onEvent(InputEvent.KeyInputEvent event) {



        KeyBinding[] keyBindings = main.keyBindings;

        // P
        if (keyBindings[0].isPressed()) {

            GuiUtil.open(Objects.requireNonNull(main.getHyConfig().gui()));
           // ModCore.getInstance().getGuiHandler().open(main.getHyConfig().gui());

        }

        //render info  J
        if(keyBindings[2].isPressed()){


            if(main.getUtils().checkForIsland()){
                Render.farmclicksrender = ! Render.farmclicksrender;
            }

            Render.todesignrender = !Render.todesignrender;

        }

        //farm  H
        if(keyBindings[3].isPressed()){

            if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
                main.getHyConfig().setXr(!main.getHyConfig().isXr());
                Minecraft.getMinecraft().renderGlobal.loadRenderers();
                return;
            }

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
