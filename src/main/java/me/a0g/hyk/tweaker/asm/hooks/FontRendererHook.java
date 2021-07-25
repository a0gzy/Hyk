package me.a0g.hyk.tweaker.asm.hooks;

import me.a0g.hyk.HypixelKentik;
import me.a0g.hyk.utils.ChromaManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

import java.awt.*;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FontRendererHook {

    private static HypixelKentik main = HypixelKentik.getInstance();
    public static boolean a0g = true;

    public static String changeTextColor(String text) {

        if (text.matches("(.+)?.(MVP§.+) (§.)?a0g(.+)?") && a0g) {  //   §b[MVP§0+§b] a0g joined
            text = text.replaceAll(".(MVP§.+§.). a0g", "§c[§f§ks§6§lGOD§f§ks§c] a0g");
        }
        if (text.contains("a0g") && a0g) {
            String spl = "r";
            if (text.matches("(.+)?§.§.a0g(.+)?")) {
                String[] splited = text.split("a0g");
                if (!splited[0].isEmpty()) {
                    spl = splited[0].charAt(splited[0].length() - 1) + "";
                    String spl2 = "" + splited[0].charAt(splited[0].length() - 3);
                    if( spl.matches("[klmnor]")){
                        spl = "r";
                    }
                    spl = spl2 + "§" + spl;
                }
            }
            else if (text.matches("(.+)?§.a0g(.+)?")) {
                String[] splited = text.split("a0g");
                if (!splited[0].isEmpty()) {
                    spl = splited[0].charAt(splited[0].length() - 1) + "";
                    if( spl.matches("[klmnor]")){
                        spl = "r";
                    }
                }
            }
            text = text.replaceAll("a0g", "§3§oa0g§" + spl);
        }
        if(text.contains("Danreal") && main.getHyConfig().isDaynreal()){
            text = text.replaceAll("Danreal", "");
        }
        /*if (text.matches("(.+)?.(VIP.) (§.)?TechDavo(.+)?")) {  //   §b[MVP§0+§b] a0g joined
            text = text.replaceAll(".(VIP). TechDavo", "§e[§pGAY§e] §aDavo§c♥");
        }
        if(text.contains("TechDavo")){
            text = text.replaceAll("TechDavo", "Davo§c♥");
        }*/
        if(text.contains("LO1D")){
            text = text.replaceAll("LO1D", "Լոիդ");
        }

      //  if (main.getHyConfig().isTransform()) {
            if (main.getHyConfig().isNamechanger() && Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().theWorld != null) {
                String cn = main.getHyConfig().getCustomname();
                if (!cn.isEmpty()) {
                    String mcname = Minecraft.getMinecraft().thePlayer.getName();

                    Pattern mypattern = Pattern.compile(mcname, Pattern.CASE_INSENSITIVE);
                    Pattern mypattern2 = Pattern.compile("(§.)+" + mcname, Pattern.CASE_INSENSITIVE);
                    Matcher matcher = mypattern.matcher(text);
                    Matcher matcher2 = mypattern2.matcher(text);

                    String fname = matcher.replaceAll(mcname);

                    String spl = "r";

                    if (matcher2.find()) {

                        String[] splited = fname.split(mcname);
                        if (!splited[0].isEmpty()) {
                            spl = splited[0].charAt(splited[0].length() - 1) + "";
                            if(splited[0].length() > 4){
                                if(splited[0].charAt(splited[0].length() - 4) == '§' ){
                                    spl = splited[0].charAt(splited[0].length() - 3) + "§" + spl;
                                }
                            }
                        }
                        fname = fname.replaceAll(mcname, cn.replaceAll("&", "§") + "§" + spl);
                    } else {
                        fname = fname.replaceAll(mcname, cn.replaceAll("&", "§") + "§" + spl);
                    }

                    return fname;
                }
            }
       // }

        return text;
    }

    public static void changeColor() {
        if (ChromaManager.isColoringTextChroma()) {
            FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;

            float[] HSB = Color.RGBtoHSB((int)(fontRenderer.red * 255), (int)(fontRenderer.green * 255), (int)(fontRenderer.blue * 255), null);
            int newColor = ChromaManager.getChromaColor(fontRenderer.posX, fontRenderer.posY, HSB, (int)(fontRenderer.alpha * 255));

            float red = (float)(newColor >> 16 & 255) / 255.0F;
            float green = (float)(newColor >> 8 & 255) / 255.0F;
            float blue = (float)(newColor & 255) / 255.0F;

            GlStateManager.color(red, blue, green, fontRenderer.alpha);
        }
    }

    public static void testchroma(String text, boolean shadow){
       // if(main.getHyConfig().isTransform()) {
            FontRenderer fontRendererrr = Minecraft.getMinecraft().fontRendererObj;

            for (int i = 0; i < text.length(); ++i) {
                char c0 = text.charAt(i);

                if (c0 == 167 && i + 1 < text.length()) {
                    int i1 = "0123456789abcdefklmnorz".indexOf(text.toLowerCase(Locale.ENGLISH).charAt(i + 1));

                    if (i1 < 16) {
                        ChromaManager.doneRenderingText();
                        fontRendererrr.randomStyle = false;
                        fontRendererrr.boldStyle = false;
                        fontRendererrr.strikethroughStyle = false;
                        fontRendererrr.underlineStyle = false;
                        fontRendererrr.italicStyle = false;

                        if (i1 < 0 || i1 > 15) {
                            i1 = 15;
                        }

                        if (shadow) {
                            i1 += 16;
                        }

                        int j1 = fontRendererrr.colorCode[i1];
                        fontRendererrr.textColor = j1;
                        GlStateManager.color((float) (j1 >> 16) / 255.0F, (float) (j1 >> 8 & 255) / 255.0F, (float) (j1 & 255) / 255.0F, fontRendererrr.alpha);
                    } else if (i1 == 16) {
                        fontRendererrr.randomStyle = true;
                        //ChromaManager.doneRenderingText();
                    } else if (i1 == 17) {
                        fontRendererrr.boldStyle = true;
                        // ChromaManager.doneRenderingText();
                    } else if (i1 == 18) {
                        fontRendererrr.strikethroughStyle = true;
                        //ChromaManager.doneRenderingText();
                    } else if (i1 == 19) {
                        fontRendererrr.underlineStyle = true;
                        //  ChromaManager.doneRenderingText();
                    } else if (i1 == 20) {
                        fontRendererrr.italicStyle = true;
                        //  ChromaManager.doneRenderingText();
                    } else if (i1 == 21) {
                        ChromaManager.doneRenderingText();
                        fontRendererrr.randomStyle = false;
                        fontRendererrr.boldStyle = false;
                        fontRendererrr.strikethroughStyle = false;
                        fontRendererrr.underlineStyle = false;
                        fontRendererrr.italicStyle = false;


                        GlStateManager.color(fontRendererrr.red, fontRendererrr.blue, fontRendererrr.green, fontRendererrr.alpha);

                    } else if (i1 == 22) {
                        ChromaManager.renderingText();
                    }

                    ++i;
                } else {
                    int j = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000".indexOf(c0);

                    if (fontRendererrr.randomStyle && j != -1) {
                        int k = fontRendererrr.getCharWidth(c0);
                        char c1;

                        while (true) {
                            j = fontRendererrr.fontRandom.nextInt("\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000".length());
                            c1 = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000".charAt(j);

                            if (k == fontRendererrr.getCharWidth(c1)) {
                                break;
                            }
                        }

                        c0 = c1;
                    }

                    float f1 = j == -1 || fontRendererrr.unicodeFlag ? 0.5f : 1f;
                    boolean flag = (c0 == 0 || j == -1 || fontRendererrr.unicodeFlag) && shadow;

                    if (flag) {
                        fontRendererrr.posX -= f1;
                        fontRendererrr.posY -= f1;
                    }

                    float f = fontRendererrr.renderChar(c0, fontRendererrr.italicStyle);

                    if (flag) {
                        fontRendererrr.posX += f1;
                        fontRendererrr.posY += f1;
                    }

                    if (fontRendererrr.boldStyle) {
                        fontRendererrr.posX += f1;

                        if (flag) {
                            fontRendererrr.posX -= f1;
                            fontRendererrr.posY -= f1;
                        }

                        fontRendererrr.renderChar(c0, fontRendererrr.italicStyle);
                        fontRendererrr.posX -= f1;

                        if (flag) {
                            fontRendererrr.posX += f1;
                            fontRendererrr.posY += f1;
                        }

                        ++f;
                    }
                    doDraw(f);
                }
            }

            return;
      //  }
    }

    protected static void doDraw(float f)
    {
        FontRenderer fontRendererrr = Minecraft.getMinecraft().fontRendererObj;
        {
            {

                if (fontRendererrr.strikethroughStyle)
                {
                    Tessellator tessellator = Tessellator.getInstance();
                    WorldRenderer worldrenderer = tessellator.getWorldRenderer();
                    GlStateManager.disableTexture2D();
                    worldrenderer.begin(7, DefaultVertexFormats.POSITION);
                    worldrenderer.pos((double)fontRendererrr.posX, (double)(fontRendererrr.posY + (float)(fontRendererrr.FONT_HEIGHT / 2)), 0.0D).endVertex();
                    worldrenderer.pos((double)(fontRendererrr.posX + f), (double)(fontRendererrr.posY + (float)(fontRendererrr.FONT_HEIGHT / 2)), 0.0D).endVertex();
                    worldrenderer.pos((double)(fontRendererrr.posX + f), (double)(fontRendererrr.posY + (float)(fontRendererrr.FONT_HEIGHT / 2) - 1.0F), 0.0D).endVertex();
                    worldrenderer.pos((double)fontRendererrr.posX, (double)(fontRendererrr.posY + (float)(fontRendererrr.FONT_HEIGHT / 2) - 1.0F), 0.0D).endVertex();
                    tessellator.draw();
                    GlStateManager.enableTexture2D();
                }

                if (fontRendererrr.underlineStyle)
                {
                    Tessellator tessellator1 = Tessellator.getInstance();
                    WorldRenderer worldrenderer1 = tessellator1.getWorldRenderer();
                    GlStateManager.disableTexture2D();
                    worldrenderer1.begin(7, DefaultVertexFormats.POSITION);
                    int l = fontRendererrr.underlineStyle ? -1 : 0;
                    worldrenderer1.pos((double)(fontRendererrr.posX + (float)l), (double)(fontRendererrr.posY + (float)fontRendererrr.FONT_HEIGHT), 0.0D).endVertex();
                    worldrenderer1.pos((double)(fontRendererrr.posX + f), (double)(fontRendererrr.posY + (float)fontRendererrr.FONT_HEIGHT), 0.0D).endVertex();
                    worldrenderer1.pos((double)(fontRendererrr.posX + f), (double)(fontRendererrr.posY + (float)fontRendererrr.FONT_HEIGHT - 1.0F), 0.0D).endVertex();
                    worldrenderer1.pos((double)(fontRendererrr.posX + (float)l), (double)(fontRendererrr.posY + (float)fontRendererrr.FONT_HEIGHT - 1.0F), 0.0D).endVertex();
                    tessellator1.draw();
                    GlStateManager.enableTexture2D();
                }

                fontRendererrr.posX += (float)((int)f);
            }
        }
    }
}
