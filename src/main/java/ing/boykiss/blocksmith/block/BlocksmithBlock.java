package ing.boykiss.blocksmith.block;

import ing.boykiss.blocksmith.registry.DynamicRegistry;
import lombok.Getter;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
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
import net.minestom.server.tag.Tag;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

public class BlocksmithBlock implements Keyed {
    public static final DynamicRegistry<BlocksmithBlock> BLOCKS = new DynamicRegistry<>();

    public static final Tag<@NotNull String> BLOCKSMITH_BLOCK_ID_RAW_TAG = Tag.String("blocksmith_block_id");
    public static final Tag<@NotNull Key> BLOCKSMITH_BLOCK_ID_TAG = BLOCKSMITH_BLOCK_ID_RAW_TAG.map(Key::key, Key::asString);

    public static final Tag<@NotNull String> BLOCKSMITH_BLOCK_DISPLAY_ENTITY_UUID_RAW_TAG = Tag.String("blocksmith_block_display_entity_uuid");
    public static final Tag<@NotNull UUID> BLOCKSMITH_BLOCK_DISPLAY_ENTITY_UUID_TAG = BLOCKSMITH_BLOCK_ID_RAW_TAG.map(UUID::fromString, UUID::toString);

    private final Key id;
    private final BlockShape shape;
    private final BlockType type;

    @Getter
    private final Block block;

    private BlocksmithBlock(Key id, BlockShape shape, BlockType type) {
        this.id = id;
        this.shape = shape;
        this.type = type;

        Block block = shape.getBaseBlock().withTag(BLOCKSMITH_BLOCK_ID_TAG, id);
        if (type == BlockType.DISPLAY) block = block.withHandler(DisplayBlockPlaceHandler.INSTANCE);
        this.block = block;
    }

    /**
     * @throws UnsupportedOperationException if id already exists
     */
    public static BlocksmithBlock create(Key id, BlockShape shape, BlockType type) throws UnsupportedOperationException {
        return BLOCKS.register(new BlocksmithBlock(id, shape, type));
    }

    /**
     * @throws UnsupportedOperationException if id already exists
     */
    public static BlocksmithBlock create(Key id, BlockShape shape) throws UnsupportedOperationException {
        return create(id, shape, BlockType.DEFAULT);
    }

    public static Optional<BlocksmithBlock> fromBlock(Block block) {
        return BLOCKS.get(block.getTag(BLOCKSMITH_BLOCK_ID_TAG));
    }

    @Override
    public @NotNull Key key() {
        return id;
    }

    private static class DisplayBlockPlaceHandler implements BlockHandler {
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

            if (!block.hasTag(BLOCKSMITH_BLOCK_ID_TAG)) return;
            Key blocksmithBlockId = block.getTag(BLOCKSMITH_BLOCK_ID_TAG);

            Entity displayEntity = new Entity(EntityType.ITEM_DISPLAY);
            ItemDisplayMeta meta = (ItemDisplayMeta) displayEntity.getEntityMeta();
            ItemStack.Builder builder = ItemStack.builder(Material.STICK);
            builder.itemModel("blocksmith_blocks_" + blocksmithBlockId.asString());
            meta.setItemStack(builder.build());
            meta.setTranslation(new Vec(0.5));
            meta.setScale(Vec.ONE.add(0.0001));
            meta.setBrightness(instance.getBlockLight(blockPos.blockX(), blockPos.blockY(), blockPos.blockZ()) + 1, instance.getSkyLight(blockPos.blockX(), blockPos.blockY(), blockPos.blockZ()) + 1);
            displayEntity.setGlowing(true);
            displayEntity.setInstance(instance, blockPos);

            instance.setBlock(blockPos, block.withTag(BLOCKSMITH_BLOCK_DISPLAY_ENTITY_UUID_TAG, displayEntity.getUuid()).withHandler(DisplayBlockDestroyHandler.INSTANCE));
        }

        @Override
        public @NotNull Key getKey() {
            return Key.key("blocksmith", "display_block_place_handler");
        }
    }

    private static class DisplayBlockDestroyHandler implements BlockHandler {
        public static final DisplayBlockDestroyHandler INSTANCE = new DisplayBlockDestroyHandler();

        private DisplayBlockDestroyHandler() {
            MinecraftServer.getBlockManager().registerHandler(getKey(), () -> DisplayBlockPlaceHandler.INSTANCE);
        }

        @Override
        public void onDestroy(@NotNull Destroy destroy) {
            BlockHandler.super.onDestroy(destroy);
            Instance instance = destroy.getInstance();
            Block block = destroy.getBlock();

            if (!block.hasTag(BLOCKSMITH_BLOCK_DISPLAY_ENTITY_UUID_TAG)) return;
            UUID displayEntityUuid = block.getTag(BLOCKSMITH_BLOCK_DISPLAY_ENTITY_UUID_TAG);

            Entity displayEntity = instance.getEntityByUuid(displayEntityUuid);
            if (displayEntity == null) return;

            displayEntity.remove();
        }

        @Override
        public @NotNull Key getKey() {
            return Key.key("blocksmith", "display_block_destroy_handler");
        }
    }
}
