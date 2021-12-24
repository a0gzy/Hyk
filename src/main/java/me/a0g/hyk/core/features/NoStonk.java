package me.a0g.hyk.core.features;

import com.google.common.collect.Iterables;
import com.mojang.authlib.properties.Property;
import me.a0g.hyk.Hyk;
import me.a0g.hyk.events.BlockChangeEvent;
import me.a0g.hyk.events.TickEndEvent;
import me.a0g.hyk.utils.RenderUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockSkull;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.util.vector.Vector3f;

import java.awt.*;
import java.util.*;

public class NoStonk {

    private Hyk main = Hyk.getInstance();

    private Minecraft mc = Minecraft.getMinecraft();

    private static HashMap<BlockPos, Block> blockList = new HashMap<>();

    private static BlockPos selectedBlock = null;

    private static BlockPos lastCheckedPosition = null;

    private static HashSet<BlockPos> usedBlocks = new HashSet<>();

    private static float range = 5.0F;

    private static final String witherEssenceSkin = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzRkYjRhZGZhOWJmNDhmZjVkNDE3MDdhZTM0ZWE3OGJkMjM3MTY1OWZjZDhjZDg5MzQ3NDlhZjRjY2U5YiJ9fX0=";

    private static final int essenceSkinHash = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzRkYjRhZGZhOWJmNDhmZjVkNDE3MDdhZTM0ZWE3OGJkMjM3MTY1OWZjZDhjZDg5MzQ3NDlhZjRjY2U5YiJ9fX0=".hashCode();

    private boolean isEnabled() {
        return (main.getUtils().checkForDungeons() && Minecraft.getMinecraft().thePlayer != null && main.getHyConfig().isNoStonk());
    }

    @SubscribeEvent
    public void onTick(TickEndEvent event) {
        if (Minecraft.getMinecraft().thePlayer == null)
            return;
        BlockPos playerPosition = Minecraft.getMinecraft().thePlayer.getPosition();
        if (isEnabled() && (lastCheckedPosition == null || !lastCheckedPosition.equals(playerPosition))) {
            blockList.clear();
            lastCheckedPosition = playerPosition;
            for (int x = playerPosition.getX() - 6; x < playerPosition.getX() + 6; x++) {
                for (int y = playerPosition.getY() - 6; y < playerPosition.getY() + 6; y++) {
                    for (int z = playerPosition.getZ() - 6; z < playerPosition.getZ() + 6; z++) {
                        BlockPos position = new BlockPos(x, y, z);
                        Block block = mc.theWorld.getBlockState(position).getBlock();
                        if (shouldEspBlock(block, position))
                            blockList.put(position, block);
                    }
                }
            }
        }
    }

    public boolean shouldEspBlock(Block block, BlockPos position) {
        if (block instanceof BlockChest || block instanceof BlockLever)
            return true;
        if (block instanceof BlockSkull) {
            TileEntitySkull tileEntity = (TileEntitySkull) mc.theWorld.getTileEntity(position);
            if (tileEntity.getSkullType() == 3) {
                try {
                    Property property = (Property) firstOrNull(tileEntity.getPlayerProfile().getProperties().get("textures"));
                    return (property != null && property.getValue().hashCode() == essenceSkinHash);
                }catch (Exception e){

                }
            }
        }
        return false;
    }

    public static <T> T firstOrNull(Iterable<T> iterable) {
        return (T) Iterables.getFirst(iterable, null);
    }

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        //nostonk
        if (isEnabled()) {
            for (Map.Entry<BlockPos, Block> block : blockList.entrySet()) {
                if (usedBlocks.contains(block.getKey()))
                    continue;
                if (selectedBlock == null) {
                    if (facingBlock(block.getKey(), range))
                        selectedBlock = block.getKey();
                } else if (!facingBlock(selectedBlock, range)) {
                    selectedBlock = null;
                }
                Color color = addAlpha(Color.WHITE, 51);
                if (block.getValue() instanceof net.minecraft.block.BlockSkull)
                    color = addAlpha(Color.BLACK, 51);
                if (block.getValue() instanceof net.minecraft.block.BlockLever)
                    color = addAlpha(Color.LIGHT_GRAY, 51);
                if (block.getValue() instanceof BlockChest && ((BlockChest) block.getValue()).chestType == 1)
                    color = addAlpha(Color.RED, 51);
                if (((BlockPos) block.getKey()).equals(selectedBlock))
                    color = addAlpha(Color.GREEN, 51);
                RenderUtils.highlightBlock(block.getKey(), color, event.partialTicks);
            }
        }

        //ghost blocks
        if (main.getHyConfig().isGhostBlocks() && main.keyBindings[2].isKeyDown()) {
            BlockPos lookingAtPos = mc.thePlayer.rayTrace(mc.playerController.getBlockReachDistance(), 1.0F).getBlockPos();
            if (lookingAtPos != null) {
                Block lookingAtblock = mc.theWorld.getBlockState(lookingAtPos).getBlock();
                if (!isInteractable(lookingAtblock))
                    mc.theWorld.setBlockToAir(lookingAtPos);
            }

        }
    }

    public boolean facingBlock(BlockPos block, float range) {
        float stepSize = 0.15F;
        if (mc.thePlayer != null && mc.theWorld != null) {
            Vector3f position = new Vector3f((float)mc.thePlayer.posX, (float)mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), (float)mc.thePlayer.posZ);
            Vec3 look = mc.thePlayer.getLook(0.0F);
            Vector3f step = new Vector3f((float)look.xCoord, (float)look.yCoord, (float)look.zCoord);
            step.scale(stepSize / step.length());
            for (int i = 0; i < Math.floor((range / stepSize)) - 2.0D; i++) {
                BlockPos blockAtPos = new BlockPos(position.x, position.y, position.z);
                if (blockAtPos.equals(block))
                    return true;
                position.translate(step.x, step.y, step.z);
            }
        }
        return false;
    }



    public static Color addAlpha(Color color, int alpha) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }



    @SubscribeEvent
    public void onInteract(PlayerInteractEvent event) {
        if (isEnabled() && selectedBlock != null && !usedBlocks.contains(selectedBlock)) {
            if (mc.objectMouseOver != null && selectedBlock.equals(mc.objectMouseOver.getBlockPos()))
                return;
            if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR || event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
                usedBlocks.add(selectedBlock);
                mc.thePlayer.setSneaking(false);
                if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory
                                .getCurrentItem(), selectedBlock,
                        EnumFacing.fromAngle(mc.thePlayer.rotationYaw), new Vec3(
                                Math.random(), Math.random(), Math.random())))
                    mc.thePlayer.swingItem();
            }
        }
    }


    @SubscribeEvent
    public void onBlockChange(BlockChangeEvent event) {
        if (mc.theWorld == null || mc.thePlayer == null)
            return;
        if (event.position.distanceSq((Vec3i)mc.thePlayer.getPosition()) > range)
            return;
        if (usedBlocks.contains(event.position))
            return;
        if (!shouldEspBlock(event.newBlock.getBlock(), event.position))
            return;
        blockList.put(event.position, event.newBlock.getBlock());
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        blockList.clear();
        usedBlocks.clear();
        selectedBlock = null;
        lastCheckedPosition = null;
    }

    //AutoCloseChest
    @SubscribeEvent
    public void onDrawBackground(GuiOpenEvent event) {
        if (event.gui instanceof GuiChest && main.getUtils().checkForSkyblock()) {
            String chestName = main.getUtils().getGuiName(event.gui);
            //FMLLog.info(chestName);
            if (main.getHyConfig().isAutoCloseChest() && main.getUtils().checkForDungeons() && chestName.equals("Chest")) {
               // FMLLog.info("da");

               // Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C0DPacketCloseWindow(((ContainerChest)((GuiChest)event.gui).inventorySlots).windowId));
                //event.setCanceled(true);
                //Minecraft.getMinecraft().displayGuiScreen(null);

                mc.thePlayer.closeScreen();
            }
        }
    }

    public static boolean isInteractable(Block block) {
        return (new ArrayList(Arrays.asList((Object[])new Block[] { (Block) Blocks.chest, Blocks.lever, Blocks.trapped_chest, Blocks.wooden_button, Blocks.stone_button, (Block)Blocks.skull }))).contains(block);
    }


}
