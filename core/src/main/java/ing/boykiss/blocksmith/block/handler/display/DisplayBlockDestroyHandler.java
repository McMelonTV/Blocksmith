package ing.boykiss.blocksmith.block.handler.display;


import ing.boykiss.blocksmith.block.BlockDef;
import net.kyori.adventure.key.Key;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Entity;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.block.BlockHandler;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class DisplayBlockDestroyHandler implements BlockHandler {
    public static final DisplayBlockDestroyHandler INSTANCE = new DisplayBlockDestroyHandler();

    private DisplayBlockDestroyHandler() {
        MinecraftServer.getBlockManager().registerHandler(getKey(), () -> DisplayBlockPlaceHandler.INSTANCE);
    }

    @Override
    public void onDestroy(@NotNull Destroy destroy) {
        BlockHandler.super.onDestroy(destroy);
        Instance instance = destroy.getInstance();
        Block block = destroy.getBlock();

        if (!block.hasTag(BlockDef.BLOCKSMITH_BLOCK_DISPLAY_ENTITY_UUID_TAG)) return;
        UUID displayEntityUuid = block.getTag(BlockDef.BLOCKSMITH_BLOCK_DISPLAY_ENTITY_UUID_TAG);

        Entity displayEntity = instance.getEntityByUuid(displayEntityUuid);
        if (displayEntity == null) return;

        displayEntity.remove();
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("blocksmith", "display_block_destroy_handler");
    }
}