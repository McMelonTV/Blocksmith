package ing.boykiss.blocksmith.block;

import ing.boykiss.blocksmith.registry.DynamicRegistry;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import net.minestom.server.tag.Tag;
import org.jetbrains.annotations.NotNull;

public class BlocksmithBlock implements Keyed {
    public static final DynamicRegistry<BlocksmithBlock> BLOCKS = new DynamicRegistry<>();

    public static final Tag<@NotNull String> BLOCKSMITH_BLOCK_ID_RAW_TAG = Tag.String("blocksmith_item_id");
    public static final Tag<@NotNull Key> BLOCKSMITH_BLOCK_ID_TAG = BLOCKSMITH_BLOCK_ID_RAW_TAG.map(Key::key, Key::asString);

    private final Key id;

    private BlocksmithBlock(Key id) {
        this.id = id;
    }

    @Override
    public @NotNull Key key() {
        return id;
    }
}
