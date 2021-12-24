package me.a0g.hyk.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.common.FMLLog;

public class Rotater {

    public static volatile boolean rotating;

    public float yaw;

    public float pitch;

    public int divider;

    public Rotater(Vec3 target, Vec3 from, float divider) {
        this.divider = (int)divider;
        double diffX = target.xCoord - from.xCoord;
        double diffY = target.yCoord - from.yCoord;
        double diffZ = target.zCoord - from.zCoord;
        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        double yaw = MathHelper.atan2(diffZ, diffX) * 180.0D / Math.PI - 90.0D;
        double pitch = -(MathHelper.atan2(diffY, dist) * 180.0D / Math.PI);
        this.yaw = (float)MathHelper.wrapAngleTo180_double(yaw - Minecraft.getMinecraft().thePlayer.rotationYaw) / divider;
        this.pitch = (float)MathHelper.wrapAngleTo180_double(pitch - Minecraft.getMinecraft().thePlayer.rotationPitch) / divider;
    }

    public Rotater(Vec3 target, float divider) {
        this.divider = (int)divider;
        double diffX = target.xCoord - Minecraft.getMinecraft().thePlayer.posX;
        double diffY = target.yCoord - (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight());
        double diffZ = target.zCoord - Minecraft.getMinecraft().thePlayer.posZ;
        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        double yaw = MathHelper.atan2(diffZ, diffX) * 180.0D / Math.PI - 90.0D;
        double pitch = -(MathHelper.atan2(diffY, dist) * 180.0D / Math.PI);

        this.yaw = (float)MathHelper.wrapAngleTo180_double(yaw - Minecraft.getMinecraft().thePlayer.rotationYaw) / divider;
        this.pitch = (float)MathHelper.wrapAngleTo180_double(pitch - Minecraft.getMinecraft().thePlayer.rotationPitch) / divider;
      //  FMLLog.info( "shas: "+MathHelper.wrapAngleTo180_double(Minecraft.getMinecraft().thePlayer.rotationYaw) + " nyshen: " + yaw + " | " + this.yaw + " divided " + this.divider + " bydet " + (MathHelper.wrapAngleTo180_double(Minecraft.getMinecraft().thePlayer.rotationYaw) + this.divider*this.yaw) );
    }

    public Rotater(Vec3 target) {
        this.divider = 20;
        double diffX = target.xCoord - Minecraft.getMinecraft().thePlayer.posX;
        double diffY = target.yCoord - (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight());
        double diffZ = target.zCoord - Minecraft.getMinecraft().thePlayer.posZ;
        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        double yaw = MathHelper.atan2(diffZ, diffX) * 180.0D / Math.PI - 90.0D;
        double pitch = -(MathHelper.atan2(diffY, dist) * 180.0D / Math.PI);
        this.yaw = (float)MathHelper.wrapAngleTo180_double(yaw - Minecraft.getMinecraft().thePlayer.rotationYaw) / 20.0F;
        this.pitch = (float)MathHelper.wrapAngleTo180_double(pitch - Minecraft.getMinecraft().thePlayer.rotationPitch) / 20.0F;
    }

    public static void snapTo(Vec3 target) {
        double diffX = target.xCoord - Minecraft.getMinecraft().thePlayer.posX;
        double diffY = target.yCoord - (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight());
        double diffZ = target.zCoord - Minecraft.getMinecraft().thePlayer.posZ;
        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        double yaw = MathHelper.atan2(diffZ, diffX) * 180.0D / Math.PI - 90.0D;
        double pitch = -(MathHelper.atan2(diffY, dist) * 180.0D / Math.PI);
        Minecraft.getMinecraft().thePlayer.rotationYaw = (float)(Minecraft.getMinecraft().thePlayer.rotationYaw + MathHelper.wrapAngleTo180_double(yaw - Minecraft.getMinecraft().thePlayer.rotationYaw));
        Minecraft.getMinecraft().thePlayer.rotationPitch = (float)(Minecraft.getMinecraft().thePlayer.rotationPitch + MathHelper.wrapAngleTo180_double(pitch - Minecraft.getMinecraft().thePlayer.rotationPitch));
    }

    public Rotater(float yawIn, float pitchIn, float divider) {
        this.divider = (int)divider;
        this.yaw = yawIn / divider;
        this.pitch = pitchIn / divider;
    }

    public Rotater(float yawIn, float pitchIn) {
        this.yaw = yawIn;
        this.pitch = pitchIn;
    }

    public void add() {
        if (Minecraft.getMinecraft().currentScreen != null)
            return;
        //FMLLog.info("Ok");
        Minecraft.getMinecraft().thePlayer.rotationYaw += this.yaw;
        //FMLLog.info(MathHelper.wrapAngleTo180_double(Minecraft.getMinecraft().thePlayer.rotationYaw) + "");
        Minecraft.getMinecraft().thePlayer.rotationPitch += this.pitch;
    }

    public void add(float yawIn, float pitchIn) {
        if (Minecraft.getMinecraft().currentScreen != null)
            return;
        Minecraft.getMinecraft().thePlayer.rotationYaw += yawIn;
        Minecraft.getMinecraft().thePlayer.rotationPitch += pitchIn;
    }

    public void rotate() {
        rotating = true;
        (new Thread(() -> {
            for (int i = 0; i < this.divider; i++) {
                add();
                //FMLLog.info(this.divider+"");
                Utils.sleep(10);
            }
            Utils.sleep(75);
            rotating = false;
        })).start();
    }

    public void rotate(int extraRotation) {
        rotating = true;
        (new Thread(() -> {
            for (int i = 0; i < this.divider + extraRotation; i++) {
                add();
                Utils.sleep(10);
            }
            Utils.sleep(75);
            rotating = false;
        })).start();
    }

}
