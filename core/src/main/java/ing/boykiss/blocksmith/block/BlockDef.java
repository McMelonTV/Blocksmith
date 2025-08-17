package ing.boykiss.blocksmith.block;

import ing.boykiss.blocksmith.block.handler.display.DisplayBlockPlaceHandler;
import lombok.Getter;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import net.minestom.server.instance.block.Block;
import net.minestom.server.tag.Tag;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public final class BlockDef implements Keyed {
    public static final Tag<@NotNull String> BLOCKSMITH_BLOCK_ID_RAW_TAG = Tag.String("blocksmith_block_id");
    public static final Tag<@NotNull Key> BLOCKSMITH_BLOCK_ID_TAG = BLOCKSMITH_BLOCK_ID_RAW_TAG.map(Key::key, Key::asString);

    public static final Tag<@NotNull String> BLOCKSMITH_BLOCK_DISPLAY_ENTITY_UUID_RAW_TAG = Tag.String("blocksmith_block_display_entity_uuid");
    public static final Tag<@NotNull UUID> BLOCKSMITH_BLOCK_DISPLAY_ENTITY_UUID_TAG = BLOCKSMITH_BLOCK_DISPLAY_ENTITY_UUID_RAW_TAG.map(UUID::fromString, UUID::toString);

    private final Key id;
    private final BlockShape shape;
    private final BlockType type;

    @Getter
    private final Block block;

    BlockDef(Key id, BlockShape shape, BlockType type) {
        this.id = id;
        this.shape = shape;
        this.type = type;

        Block block = shape.getBaseBlock();
        if (type == BlockType.SPAWNER) block = Block.SPAWNER;
        if (type == BlockType.DISPLAY) block = block.withHandler(DisplayBlockPlaceHandler.INSTANCE);
        block = block.withTag(BLOCKSMITH_BLOCK_ID_TAG, id);

        this.block = block;
    }

    @Override
    public @NotNull Key key() {
        return id;
    }
}
