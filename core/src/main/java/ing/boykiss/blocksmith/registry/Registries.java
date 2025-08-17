package ing.boykiss.blocksmith.registry;

import ing.boykiss.blocksmith.block.BlocksmithBlock;
import ing.boykiss.blocksmith.item.BlocksmithItem;

public final class Registries {
    public static Registry<BlocksmithItem> items() {
        return BlocksmithItem.ITEMS;
    }

    public static Registry<BlocksmithBlock> blocks() {
        return BlocksmithBlock.BLOCKS;
    }
}
