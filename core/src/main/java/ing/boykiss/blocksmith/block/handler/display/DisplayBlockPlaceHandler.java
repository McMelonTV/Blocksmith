package ing.boykiss.blocksmith.block.handler.display;


import ing.boykiss.blocksmith.block.BlockDef;
import net.kyori.adventure.key.Key;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.metadata.display.ItemDisplayMeta;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.block.BlockHandler;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class DisplayBlockPlaceHandler implements BlockHandler {
    public static final DisplayBlockPlaceHandler INSTANCE = new DisplayBlockPlaceHandler();

    private DisplayBlockPlaceHandler() {
        MinecraftServer.getBlockManager().registerHandler(getKey(), () -> INSTANCE);
    }

    @Override
    public void onPlace(@NotNull Placement placement) {
        BlockHandler.super.onPlace(placement);
        Instance instance = placement.getInstance();
        Block block = placement.getBlock();
        Point blockPos = placement.getBlockPosition();

        if (!block.hasTag(BlockDef.BLOCKSMITH_BLOCK_ID_TAG)) return;
        Key blocksmithBlockId = block.getTag(BlockDef.BLOCKSMITH_BLOCK_ID_TAG);

        Entity displayEntity = new Entity(EntityType.ITEM_DISPLAY);
        ItemDisplayMeta meta = (ItemDisplayMeta) displayEntity.getEntityMeta();
        ItemStack.Builder builder = ItemStack.builder(Material.STICK);
        builder.itemModel("blocksmith_blocks_" + blocksmithBlockId.asString());
        meta.setItemStack(builder.build());
        meta.setTranslation(new Vec(0.5));
        meta.setScale(Vec.ONE.add(0.001));
        meta.setBrightnessOverride(255);
        meta.setViewRange(2f);
        meta.setHeight(1f);
        meta.setWidth(1f);
//            displayEntity.setGlowing(true);
        displayEntity.setNoGravity(true);
        displayEntity.setInstance(instance, blockPos);

        CompletableFuture.runAsync(() -> instance.setBlock(blockPos, block.withTag(BlockDef.BLOCKSMITH_BLOCK_DISPLAY_ENTITY_UUID_TAG, displayEntity.getUuid()).withHandler(DisplayBlockDestroyHandler.INSTANCE)));
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("blocksmith", "display_block_place_handler");
    }
}