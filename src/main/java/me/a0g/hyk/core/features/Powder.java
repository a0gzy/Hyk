package me.a0g.hyk.core.features;

import me.a0g.hyk.Hyk;
import me.a0g.hyk.events.ReceivePacketEvent;
import me.a0g.hyk.events.TickEndEvent;
import me.a0g.hyk.utils.RenderUtils;
import me.a0g.hyk.utils.Rotater;
import me.a0g.hyk.utils.RotationUtils;
import me.a0g.hyk.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.server.S2APacketParticles;
import net.minecraft.util.*;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.ArrayList;

public class Powder {

    private Hyk main = Hyk.getInstance();

    private long lastworked;
    private static Vec3 closestChest = null;

    private ArrayList<Vec3> solved = new ArrayList<>();
    private ArrayList<BlockPos> broken = new ArrayList<>();

    private static int currentDamage;
    private static BlockPos closestStone;
    private boolean stopHardstone = false;

    @SubscribeEvent
    public void onTick(TickEndEvent event) {
        if(Minecraft.getMinecraft().thePlayer == null && Minecraft.getMinecraft().theWorld == null)
            return;
        if(!main.getHyConfig().isAutoPowder() && !main.getUtils().checkHollows()) {
            currentDamage = 0;
            this.broken.clear();
            return;
        }
        if (!this.stopHardstone) {
            if (this.broken.size() > 10)
                this.broken.clear();
            closestStone = closestStone();
            if (closestStone != null) {
                if (Minecraft.getMinecraft().currentScreen != null)
                    return;
                MovingObjectPosition fake = (Minecraft.getMinecraft()).objectMouseOver;
                fake.hitVec = new Vec3((Vec3i)closestStone);
                EnumFacing enumFacing = fake.sideHit;
                if (currentDamage == 0 && enumFacing != null)
                    (Minecraft.getMinecraft()).thePlayer.sendQueue.addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, closestStone, enumFacing));
                MovingObjectPosition real = (Minecraft.getMinecraft()).objectMouseOver;
                if (real != null && real.entityHit == null)
                    (Minecraft.getMinecraft()).thePlayer.swingItem();
                this.broken.add(closestStone);
            }
        }
    }

    @SubscribeEvent
    public void onParticle(ReceivePacketEvent event) {
        if(Minecraft.getMinecraft().thePlayer == null && Minecraft.getMinecraft().theWorld == null)
            return;
        if(!main.getHyConfig().isAutoPowderChest() || !main.getUtils().checkHollows())//!true and !false
            return;


        if(event.packet instanceof S2APacketParticles && lastworked+6 < System.currentTimeMillis()) {
            lastworked = System.currentTimeMillis();

            S2APacketParticles packet = (S2APacketParticles)event.packet;
            if( packet.getParticleType().equals(EnumParticleTypes.CRIT)){
                Vec3 particlePos = new Vec3(packet.getXCoordinate(), packet.getYCoordinate(), packet.getZCoordinate());
                if (closestChest != null) {
                    this.stopHardstone = true;
                    double dist = closestChest.distanceTo(particlePos);
                    //FMLLog.info(closestChest.xCoord+" " + closestChest.yCoord+" " + closestChest.zCoord);
                    if (dist < 1.0D && particlePos.distanceTo(Minecraft.getMinecraft().thePlayer.getPositionVector()) < 4.5) {
                        //Rotater.snapTo(particlePos);
                        new Rotater(new Vec3(packet.getXCoordinate(), packet.getYCoordinate(), packet.getZCoordinate()), 15.0F).rotate();
                       // working = false;
                       // RotationUtils.facePos(particlePos);
                    }
                }

            }
            //BlockPos playerPosition = Minecraft.getMinecraft().thePlayer.getPosition();
        }

    }

    @SubscribeEvent
    public void renderWorld(RenderWorldLastEvent event) {
        /*if(!main.getUtils().getTabList().contains("a0g"))
            return;*/
        //!false   && !true
        if(main.getHyConfig().isAutoPowder() && main.getUtils().checkHollows()) {

            closestStone = closestStone();
            closestChest = closestChest();
            if (closestStone != null && main.getHyConfig().isAutoPowder())
                RenderUtils.highlightBlock(closestStone, new Color(128, 128, 128, 36), event.partialTicks);
            if (closestChest != null && closestChest.distanceTo(Minecraft.getMinecraft().thePlayer.getPositionVector()) < 6 && main.getHyConfig().isAutoPowderChest()) {
                RenderUtils.highlightBlock(new BlockPos(closestChest.xCoord, closestChest.yCoord, closestChest.zCoord), new Color(255, 128, 0, 51), event.partialTicks);
            } else {
                this.stopHardstone = false;
            }

        }
    }

    @SubscribeEvent
    public void guiDraw(GuiScreenEvent.BackgroundDrawnEvent event) {
        (new Thread(() -> {
            try {
                if (event.gui instanceof GuiChest) {
                    String chestName = main.getUtils().getGuiName(event.gui);
                        if (chestName.contains("Treasure")) {
                            this.solved.add(closestChest);
                            this.stopHardstone = false;
                            Thread.sleep(20L);
                            (Minecraft.getMinecraft()).thePlayer.closeScreen();
                        }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        })).start();
    }

    @SubscribeEvent
    public void clear(WorldEvent.Load event) {
        this.solved.clear();
    }

    private int hardrange = 3;

    private BlockPos closestStone() {
        int r = 6;
        BlockPos playerPos = (Minecraft.getMinecraft()).thePlayer.getPosition();
        playerPos.add(0, 1, 0);
        Vec3 playerVec = (Minecraft.getMinecraft()).thePlayer.getPositionVector();
        Vec3i vec3i = new Vec3i(r, 1 + hardrange, r);
        Vec3i vec3i2 = new Vec3i(r, 0, r);
        ArrayList<Vec3> stones = new ArrayList<>();
        if (playerPos != null)
            for (BlockPos blockPos : BlockPos.getAllInBox(playerPos.add(vec3i), playerPos.subtract(vec3i2))) {
                IBlockState blockState = (Minecraft.getMinecraft()).theWorld.getBlockState(blockPos);
                if (blockState.getBlock() == Blocks.stone && !this.broken.contains(blockPos))
                    stones.add(new Vec3(blockPos.getX() + 0.5D, blockPos.getY(), blockPos.getZ() + 0.5D));
            }
        double smallest = 9999.0D;
        Vec3 closest = null;
        for (Vec3 stone : stones) {
            double dist = stone.distanceTo(playerVec);
            if (dist < smallest) {
                smallest = dist;
                closest = stone;
            }
        }
        if (closest != null && smallest < 5.0D)
            return new BlockPos(closest.xCoord, closest.yCoord, closest.zCoord);
        return null;
    }

    private Vec3 closestChest() {
        int r = 6;
        BlockPos playerPos = (Minecraft.getMinecraft()).thePlayer.getPosition();
        playerPos.add(0, 1, 0);
        Vec3 playerVec = (Minecraft.getMinecraft()).thePlayer.getPositionVector();
        Vec3i vec3i = new Vec3i(r, r, r);
        ArrayList<Vec3> chests = new ArrayList<>();
        if (playerPos != null)
            for (BlockPos blockPos : BlockPos.getAllInBox(playerPos.add(vec3i), playerPos.subtract(vec3i))) {
                IBlockState blockState = (Minecraft.getMinecraft()).theWorld.getBlockState(blockPos);
                if (blockState.getBlock() == Blocks.chest)
                    chests.add(new Vec3(blockPos.getX() + 0.5D, blockPos.getY() + 0.5D, blockPos.getZ() + 0.5D));
            }
        double smallest = 9999.0D;
        Vec3 closest = null;
        for (Vec3 chest : chests) {
            if (!this.solved.contains(chest)) {
                double dist = chest.distanceTo(playerVec);
                if (dist < smallest) {
                    smallest = dist;
                    closest = chest;
                }
            }
        }
        return closest;
    }

}
