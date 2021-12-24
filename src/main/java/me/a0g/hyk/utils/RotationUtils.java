package me.a0g.hyk.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Vec3;

public class RotationUtils {

    static boolean working = false;
    static int smoothLookVelocity = 1;

    public static void facePos(Vec3 vector) {
        if ((Minecraft.getMinecraft()).currentScreen == null ||
                (Minecraft.getMinecraft()).currentScreen instanceof net.minecraft.client.gui.GuiIngameMenu || (Minecraft.getMinecraft()).currentScreen instanceof net.minecraft.client.gui.GuiChat) {
            if (working)
                return;
            (new Thread(() -> {
                try {
                    working = true;
                    double diffX = vector.xCoord - (Minecraft.getMinecraft()).thePlayer.posX;
                    double diffY = vector.yCoord - (Minecraft.getMinecraft()).thePlayer.posY;
                    double diffZ = vector.zCoord - (Minecraft.getMinecraft()).thePlayer.posZ;
                    double dist = Math.sqrt(diffX * diffX + diffZ * diffZ);
                    float pitch = (float)-Math.atan2(dist, diffY);
                    float yaw = (float)Math.atan2(diffZ, diffX);
                    pitch = (float)wrapAngleTo180(((pitch * 180.0F) / Math.PI + 90.0D) * -1.0D - (Minecraft.getMinecraft()).thePlayer.rotationPitch);
                    yaw = (float)wrapAngleTo180((yaw * 180.0F) / Math.PI - 90.0D - (Minecraft.getMinecraft()).thePlayer.rotationYaw);
                    for (int i = 0; i < smoothLookVelocity; i++) {
                        (Minecraft.getMinecraft()).thePlayer.rotationYaw += yaw / smoothLookVelocity;
                        (Minecraft.getMinecraft()).thePlayer.rotationPitch += pitch / smoothLookVelocity;
                        Thread.sleep(1L);
                    }
                    working = false;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            })).start();
            return;
        }
    }

    private static double wrapAngleTo180(double angle) {
        angle %= 360.0D;
        while (angle >= 180.0D)
            angle -= 360.0D;
        while (angle < -180.0D)
            angle += 360.0D;
        return angle;
    }
}
