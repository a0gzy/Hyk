package me.a0g.hyk.gui.buttons;

import me.a0g.hyk.core.Feature;
import me.a0g.hyk.utils.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
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

    public static void drawScaledCustomSizeModalRect(float x, float y, float u, float v, float uWidth, float vHeight, float width, float height, float tileWidth, float tileHeight, boolean linearTexture) {
        if (linearTexture) {
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        }

        float f = 1.0F / tileWidth;
        float f1 = 1.0F / tileHeight;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(x, y + height, 0.0D).tex(u * f, (v + vHeight) * f1).endVertex();
        worldrenderer.pos(x + width, y + height, 0.0D).tex((u + uWidth) * f, (v + vHeight) * f1).endVertex();
        worldrenderer.pos(x + width, y, 0.0D).tex((u + uWidth) * f, v * f1).endVertex();
        worldrenderer.pos(x, y, 0.0D).tex(u * f, v * f1).endVertex();
        tessellator.draw();

        if (linearTexture) {
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        }
    }



    public static void begin2D(int drawType, int color) {

            worldRenderer.begin(drawType, DefaultVertexFormats.POSITION_COLOR);
            GlStateManager.shadeModel(GL11.GL_SMOOTH);

    }

    public static void begin3D(int drawType, int color) {

            worldRenderer.begin(drawType, DefaultVertexFormats.POSITION_COLOR);
            GlStateManager.shadeModel(GL11.GL_SMOOTH);


    }


    public static void end(int color) {

            tessellator.draw();
            GlStateManager.shadeModel(GL11.GL_FLAT);


    }

    public static void drawModalRectWithCustomSizedTexture(float x, float y, float u, float v, float width, float height, float textureWidth, float textureHeight) {
        drawModalRectWithCustomSizedTexture(x, y, u, v, width, height, textureWidth, textureHeight, false);
    }

    /**
     * Draws a textured rectangle at z = 0. Args: x, y, u, v, width, height, textureWidth, textureHeight
     */
    public static void drawModalRectWithCustomSizedTexture(float x, float y, float u, float v, float width, float height, float textureWidth, float textureHeight, boolean linearTexture) {
        if (linearTexture) {
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        }

        float f = 1.0F / textureWidth;
        float f1 = 1.0F / textureHeight;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(x, y + height, 0.0D).tex(u * f, (v + height) * f1).endVertex();
        worldrenderer.pos(x + width, y + height, 0.0D).tex((u + width) * f, (v + height) * f1).endVertex();
        worldrenderer.pos(x + width, y, 0.0D).tex((u + width) * f, v * f1).endVertex();
        worldrenderer.pos(x, y, 0.0D).tex(u * f, v * f1).endVertex();
        tessellator.draw();

        if (linearTexture) {
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        }
    }

    /**
     * Draws a rectangle using absolute coordinates & a color.
     *
     * See {@link DrawUtils#drawRect(double, double, double, double, int)} to use width/height instead.
     */
    public static void drawRectAbsolute(double left, double top, double right, double bottom, int color) {
        drawRectAbsolute(left, top, right, bottom, color, false);
    }

    /**
     * Draws a rectangle using absolute coordinates & a color.
     *
     * See {@link DrawUtils#drawRect(double, double, double, double, int, boolean)} to use width/height instead.
     */
    public static void drawRectAbsolute(double left, double top, double right, double bottom, int color, boolean chroma) {
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
        drawRectInternal(left, top, right - left, bottom - top, color, chroma);
    }

    /**
     * Draws a rectangle using absolute a starting position and a width/height.
     *
     * See {@link DrawUtils#drawRectAbsolute(double, double, double, double, int)} to use absolute coordinates instead.
     */
    public static void drawRect(double x, double y, double w, double h, int color, int rounding) {
        drawRectInternal(x, y, w, h, color, rounding);
    }

    /**
     * Draws a rectangle using absolute a starting position and a width/height.
     *
     * See {@link DrawUtils#drawRectAbsolute(double, double, double, double, int)} to use absolute coordinates instead.
     */
    public static void drawRect(double x, double y, double w, double h, int color) {
        drawRectInternal(x, y, w, h, color, false);
    }

    /**
     * Draws a rectangle using absolute a starting position and a width/height.
     *
     * See {@link DrawUtils#drawRectAbsolute(double, double, double, double, int, boolean)} to use absolute coordinates instead.
     */
    public static void drawRect(double x, double y, double w, double h, int color, boolean chroma) {
        drawRectInternal(x, y, w, h, color, chroma);
    }

    /**
     * Draws a solid color rectangle with the specified coordinates and color (ARGB format). Args: x1, y1, x2, y2, color
     */
    private static void drawRectInternal(double x, double y, double w, double h, int color, boolean chroma) {
        drawRectInternal(x, y, w, h, color);
    }

    private static void drawRectInternal(double x, double y, double w, double h, int color) {
        drawRectInternal(x, y, w, h, color, 0);
    }

    /**
     * Draws a solid color rectangle with the specified coordinates and color (ARGB format). Args: x1, y1, x2, y2, color
     */
    private static void drawRectInternal(double x, double y, double w, double h, int color, int rounding) {
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

    private static void addVertex(double x, double y, int color) {

          //  int colorInt = color.getColorAtPosition((float) x, (float) y);
            worldRenderer.pos(x, y, 0).color(ColorUtils.getRed(color), ColorUtils.getGreen(color), ColorUtils.getBlue(color), ColorUtils.getAlpha(color)).endVertex();

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

        restoreCull();
        restoreTexture();
        restoreBlend();
    }

    /**
     * Draws a solid color rectangle with the specified coordinates and color (ARGB format). Args: x1, y1, x2, y2, color
     */
    public static void drawRectOutline(float x, float y, int w, int h, int thickness, int color, boolean chroma) {
        drawRectOutline(x, y, w, h, thickness, color,false);
    }

    /**
     * Draws a solid color rectangle with the specified coordinates and color (ARGB format). Args: x1, y1, x2, y2, color
     */
    public static void drawRectOutline(float x, float y, int w, int h, int thickness, int color) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

       /* begin2D(GL11.GL_QUADS, color);

            drawSegmentedLineVertical(x - thickness, y, thickness, h, color);
            drawSegmentedLineHorizontal(x - thickness, y - thickness, w + thickness * 2, thickness, color);
            drawSegmentedLineVertical(x + w, y, thickness, h, color);
            drawSegmentedLineHorizontal(x - thickness, y + h, w + thickness * 2, thickness, color);
        } else {*/
            addQuadVertices(x - thickness, y, thickness, h, color);
            addQuadVertices(x - thickness, y - thickness, w + thickness * 2, thickness, color);
            addQuadVertices(x + w, y, thickness, h, color);
            addQuadVertices(x - thickness, y + h, w + thickness * 2, thickness, color);
      //  }

        end(color);

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    /*public static void drawSegmentedLineHorizontal(float x, float y, float w, float h, int color) {
        int segments = (int) (w * ManualChromaManager.getFeatureScale() / 10);
        float length = w / segments;

        for (int segment = 0; segment < segments; segment++) {
            float start = x + length * segment;
            addQuadVertices(start, y, length, h, color);
        }
    }

    public static void drawSegmentedLineVertical(float x, float y, float w, float h, int color) {
        int segments = (int) (h * ManualChromaManager.getFeatureScale() / 10);
        float length = h / segments;

        for (int segment = 0; segment < segments; segment++) {
            float start = y + length * segment;
            addQuadVertices(x, start, w, length, color);
        }
    }*/

    public static void drawText(String text, float x, float y, int color) {
        if (text == null) {
            return;
        }
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;

            fontRenderer.drawString(text, x + 0, y + 0, color, true);

    }

    public static void testDrawText(Feature feature,float scale,Minecraft mc,ButtonLocation buttonLocation){

        float x = feature.getPosTweak().x;
        float y = feature.getPosTweak().y;

        int height = buttonLocation.height;
        int width = buttonLocation.width;


        x = transformXY(x, width, scale);
        y = transformXY(y, height, scale);

        buttonLocation.checkHoveredAndDrawBox(x,x+width,y,y+height,scale);
    }

    public static float transformXY(float xy, int widthHeight, float scale) {
        float minecraftScale = new ScaledResolution(Minecraft.getMinecraft()).getScaleFactor();
        xy -= widthHeight / 2F * scale;
        xy = Math.round(xy * minecraftScale) / minecraftScale;
        return xy / scale;
    }

    public static void drawCenteredText(String text, float x, float y, int color) {
        drawText(text, x - Minecraft.getMinecraft().fontRendererObj.getStringWidth(text) / 2F, y, color);
    }

    public static void printCurrentGLTransformations() {
        FloatBuffer buf = BufferUtils.createFloatBuffer(16);
        GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, buf);
        buf.rewind();
        org.lwjgl.util.vector.Matrix4f mat = new org.lwjgl.util.vector.Matrix4f();
        mat.load(buf);

        float x = mat.m30;
        float y = mat.m31;
        float z = mat.m32;

        float scale = (float) Math.sqrt(mat.m00 * mat.m00 + mat.m01 * mat.m01 + mat.m02 * mat.m02);

        System.out.println("x: " + x);
        System.out.println("y: " + y);
        System.out.println("z: " + z);
        System.out.println("scale: " + scale);
    }

    public static void enableBlend() {
//        previousCullState = GlStateManager.blend.cullFace.currentState;
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
    }

    public static void disableBlend() {
//        previousCullState = GlStateManager.cullState.cullFace.currentState;
        GlStateManager.disableBlend();

    }

    public static void restoreBlend() {

    }

    public static void enableCull() {
        previousCullState = GlStateManager.cullState.cullFace.currentState;
        GlStateManager.enableCull();
    }

    public static void disableCull() {
        previousCullState = GlStateManager.cullState.cullFace.currentState;
        GlStateManager.disableCull();
    }

    public static void restoreCull() {
        if (previousCullState) {
            GlStateManager.enableCull();
        } else {
            GlStateManager.disableCull();
        }
    }

    public static void enableTexture() {
        previousTextureState = GlStateManager.textureState[GlStateManager.activeTextureUnit].texture2DState.currentState;
        GlStateManager.enableTexture2D();
    }

    public static void disableTexture() {
        previousTextureState = GlStateManager.textureState[GlStateManager.activeTextureUnit].texture2DState.currentState;
        GlStateManager.disableTexture2D();
    }

    public static void restoreTexture() {
        if (previousTextureState) {
            GlStateManager.enableTexture2D();
        } else {
            GlStateManager.disableTexture2D();
        }
    }


    private static final FloatBuffer BUF_FLOAT_4 = BufferUtils.createFloatBuffer(4);

    public static void enableOutlineMode() {
        GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL13.GL_COMBINE);
        GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL13.GL_COMBINE_RGB, GL11.GL_REPLACE);
        GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL13.GL_SOURCE0_RGB, GL13.GL_CONSTANT);
        GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL13.GL_OPERAND0_RGB, GL11.GL_SRC_COLOR);
        GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL13.GL_COMBINE_ALPHA, GL11.GL_REPLACE);
        GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL13.GL_SOURCE0_ALPHA, GL11.GL_TEXTURE);
        GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL13.GL_OPERAND0_ALPHA, GL11.GL_SRC_ALPHA);
    }

    public static void outlineColor(int color) {
        BUF_FLOAT_4.put(0, (float)(color >> 16 & 255) / 255.0F);
        BUF_FLOAT_4.put(1, (float)(color >> 8 & 255) / 255.0F);
        BUF_FLOAT_4.put(2, (float)(color & 255) / 255.0F);
        BUF_FLOAT_4.put(3, 1);

        GL11.glTexEnv(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_COLOR, BUF_FLOAT_4);
    }

    public static void disableOutlineMode() {
        GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
        GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL13.GL_COMBINE_RGB, GL11.GL_MODULATE);
        GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL13.GL_SOURCE0_RGB, GL11.GL_TEXTURE);
        GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL13.GL_OPERAND0_RGB, GL11.GL_SRC_COLOR);
        GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL13.GL_COMBINE_ALPHA, GL11.GL_MODULATE);
        GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL13.GL_SOURCE0_ALPHA, GL11.GL_TEXTURE);
        GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL13.GL_OPERAND0_ALPHA, GL11.GL_SRC_ALPHA);
    }

    /**
     * Copied from Render.renderLivingLabel
     *
     * @param str the string to render
     * @param x   offset from the player's render position (eyesight)
     * @param y   offset from the player's render position (eyesight)
     * @param z   offset from the player's render position (eyesight)
     */
    public static void drawTextInWorld(String str, double x, double y, double z) {
        Minecraft mc = Minecraft.getMinecraft();
        FontRenderer fontrenderer = mc.fontRendererObj;
        RenderManager renderManager = mc.getRenderManager();
        float f = 1.6F;
        float f1 = 0.016666668F * f;
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(-f1, -f1, f1);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        int j = fontrenderer.getStringWidth(str) / 2;
        GlStateManager.disableTexture2D();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos(-j - 1, -1, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        worldrenderer.pos(-j - 1, 8, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        worldrenderer.pos(j + 1, 8, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        worldrenderer.pos(j + 1, -1, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, 0, 553648127);
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, 0, -1);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }
}
