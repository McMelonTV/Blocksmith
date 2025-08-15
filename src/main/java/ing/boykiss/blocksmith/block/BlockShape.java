package ing.boykiss.blocksmith.block;

import lombok.Getter;
import net.minestom.server.instance.block.Block;

public enum BlockShape {
    FULL(Block.NOTE_BLOCK),
    EMPTY(Block.AIR),
    CUSTOM(Block.AIR);

    @Getter
    private final Block baseBlock;

    BlockShape(Block baseBlock) {
        this.baseBlock = baseBlock;
    }
}
