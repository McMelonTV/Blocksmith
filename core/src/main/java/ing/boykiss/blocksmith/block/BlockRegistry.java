package ing.boykiss.blocksmith.block;

import ing.boykiss.blocksmith.registry.DynamicRegistry;
import net.kyori.adventure.key.Key;
import net.minestom.server.instance.block.Block;

import java.util.Optional;

public class BlockRegistry extends DynamicRegistry<BlockDef> {
    /**
     * @throws UnsupportedOperationException if id already exists
     */
    public BlockDef create(Key id, BlockShape shape, BlockType type) throws UnsupportedOperationException {
        return this.register(new BlockDef(id, shape, type));
    }

    /**
     * @throws UnsupportedOperationException if id already exists
     */
    public BlockDef create(Key id, BlockShape shape) throws UnsupportedOperationException {
        return create(id, shape, BlockType.NORMAL);
    }

    public Optional<BlockDef> fromBlock(Block block) {
        return this.get(block.getTag(BlockDef.BLOCKSMITH_BLOCK_ID_TAG));
    }
}
