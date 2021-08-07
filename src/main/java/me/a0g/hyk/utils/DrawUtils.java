package me.a0g.hyk.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import javax.vecmath.Vector3d;
import java.awt.*;
import java.nio.FloatBuffer;

public class DrawUtils {

    private static final double HALF_PI = Math.PI / 2D;
    private static final double PI = Math.PI;

    private static final Tessellator tessellator = Tessellator.getInstance();
    private static final WorldRenderer worldRenderer = tessellator.getWorldRenderer();

    private static boolean previousTextureState;
    private static boolean previousBlendState;
    private static boolean previousCullState;

    public static void drawRectAbsolute(double left, double top, double right, double bottom, int color) {
        if (left < right) {
            double savedLeft = left;
            left = right;
            right = savedLeft;
        }
        if (top < bottom) {
            double savedTop = top;
            top = bottom;
            bottom = savedTop;
        }
        drawRectInternal(left, top, right - left, bottom - top, color,1);
    }


    private static void drawRectInternal(double x, double y, double w, double h,int color , int rounding) {
        if (rounding > 0) {
            drawRoundedRectangle(x, y, w, h, color, rounding);
            return;
        }

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

        begin2D(GL11.GL_QUADS, color);

        addQuadVertices(x, y, w, h, color);

        end(color);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    private static void addQuadVertices(double x, double y, double w, double h, int color) {
        addQuadVerticesAbsolute(x, y, x + w, y + h, color);
    }

    private static void addQuadVerticesAbsolute(double left, double top, double right, double bottom, int color) {
        addVertex(left, bottom, color);
        addVertex(right, bottom, color);
        addVertex(right, top, color);
        addVertex(left, top, color);
    }

    public static void begin2D(int drawType, int color) {
        worldRenderer.begin(drawType, DefaultVertexFormats.POSITION_COLOR);
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
    }

    public static void end(int color) {
        tessellator.draw();
        GlStateManager.shadeModel(GL11.GL_FLAT);
    }

    private static void drawRoundedRectangle(double x, double y, double w, double h, int color, double rounding) {
        enableBlend();
        disableCull();
        disableTexture();

        double x1, y1, x2, y2;
        begin2D(GL11.GL_QUADS, color);
        // Main vertical rectangle
        x1 = x + rounding;
        x2 = x + w - rounding;
        y1 = y;
        y2 = y + h;
        addVertex(x1, y2, color);
        addVertex(x2, y2, color);
        addVertex(x2, y1, color);
        addVertex(x1, y1, color);

        // Left rectangle
        x1 = x;
        x2 = x + rounding;
        y1 = y + rounding;
        y2 = y + h - rounding;
        addVertex(x1, y2, color);
        addVertex(x2, y2, color);
        addVertex(x2, y1, color);
        addVertex(x1, y1, color);

        // Right rectangle
        x1 = x + w - rounding;
        x2 = x + w;
        y1 = y + rounding;
        y2 = y + h - rounding;
        addVertex(x1, y2, color);
        addVertex(x2, y2, color);
        addVertex(x2, y1, color);
        addVertex(x1, y1, color);
        end(color);

        int segments = 64;
        double angleStep = HALF_PI / (float) segments;

        begin2D(GL11.GL_TRIANGLE_FAN, color);
        // Top left corner
        double startAngle = -HALF_PI;
        double startX = x + rounding;
        double startY = y + rounding;
        addVertex(startX, startY, color);
        for (int segment = 0; segment <= segments; segment++) {
            double angle = startAngle - angleStep * segment;
            addVertex(startX + rounding * Math.cos(angle), startY + rounding * Math.sin(angle), color);
        }
        end(color);

        begin2D(GL11.GL_TRIANGLE_FAN, color);
        // Top right corner
        startAngle = 0;
        startX = x + w - rounding;
        startY = y + rounding;
        addVertex(startX, startY, color);
        for (int segment = 0; segment <= segments; segment++) {
            double angle = startAngle - angleStep * segment;
            addVertex(startX + rounding * Math.cos(angle), startY + rounding * Math.sin(angle), color);
        }
        end(color);

        begin2D(GL11.GL_TRIANGLE_FAN, color);
        // Bottom right corner
        startAngle = HALF_PI;
        startX = x + w - rounding;
        startY = y + h - rounding;
        addVertex(startX, startY, color);
        for (int segment = 0; segment <= segments; segment++) {
            double angle = startAngle - angleStep * segment;
            addVertex(startX + rounding * Math.cos(angle), startY + rounding * Math.sin(angle), color);
        }
        end(color);

        begin2D(GL11.GL_TRIANGLE_FAN, color);
        // Bottom right corner
        startAngle = PI;
        startX = x + rounding;
        startY = y + h - rounding;
        addVertex(startX, startY, color);
        for (int segment = 0; segment <= segments; segment++) {
            double angle = startAngle - angleStep * segment;
            addVertex(startX + rounding * Math.cos(angle), startY + rounding * Math.sin(angle), color);
        }
        end(color);


        disableBlend();
        enableCull();
        enableTexture();


    }

    private static void addVertex(double x, double y, int color) {
        worldRenderer.pos(x, y,0).endVertex();
    }

    public static void enableBlend() {
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
    }

    public static void disableBlend() {
        GlStateManager.disableBlend();
    }

    public static void enableCull() {
        GlStateManager.enableCull();
    }

    public static void disableCull() {
        GlStateManager.disableCull();
    }


    public static void enableTexture() {
        GlStateManager.enableTexture2D();
    }

    public static void disableTexture() {
        GlStateManager.disableTexture2D();
    }


}
