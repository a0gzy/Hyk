package me.a0g.hyk.utils;

import net.minecraft.util.EnumChatFormatting;

import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;

public enum SBRarity {
    COMMON("COMMON", EnumChatFormatting.WHITE, new Color(255,255,255)),
    UNCOMMON("UNCOMMON", EnumChatFormatting.GREEN, new Color(85,255,85)),
    RARE("RARE", EnumChatFormatting.BLUE, new Color(85,85,255)),
    EPIC("EPIC", EnumChatFormatting.DARK_PURPLE, new Color(170,0,170)),
    LEGENDARY("LEGENDARY", EnumChatFormatting.GOLD, new Color(255,170,0)),
    MYTHIC("MYTHIC", EnumChatFormatting.LIGHT_PURPLE, new Color(255,85,255)),
    SUPREME("SUPREME", EnumChatFormatting.DARK_RED, new Color(170,0,0)),
    SPECIAL("SPECIAL", EnumChatFormatting.RED, new Color(255,85,85)),
    VERY_SPECIAL("VERY SPECIAL", EnumChatFormatting.RED, new Color(170,0,0));

    private static final SBRarity[] VALUES = Arrays.stream(values()).sorted(Comparator.comparingInt(SBRarity::ordinal)).toArray(size -> new SBRarity[size]);
    private final String name;
    private final EnumChatFormatting baseColor;
    private final Color colorToRender;

    static
    {
        for (SBRarity rarity : values())
        {
            VALUES[rarity.ordinal()] = rarity;
        }
    }

    private SBRarity(String name, EnumChatFormatting baseColor, Color colorToRender)
    {
        this.name = name;
        this.baseColor = baseColor;
        this.colorToRender = colorToRender;
    }

    public String getName()
    {
        return this.name;
    }

    public EnumChatFormatting getBaseColor()
    {
        return this.baseColor;
    }

    public Color getColorToRender()
    {
        return this.colorToRender;
    }

    public static SBRarity byBaseColor(String color)
    {
        for (SBRarity rarity : values())
        {
            if (rarity.baseColor.toString().equals(color))
            {
                return rarity;
            }
        }
        return null;
    }

    public SBRarity getNextRarity()
    {
        return VALUES[(this.ordinal() + 1) % VALUES.length];
    }
}
