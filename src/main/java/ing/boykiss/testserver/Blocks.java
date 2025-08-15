package ing.boykiss.testserver;

import ing.boykiss.blocksmith.block.BlockShape;
import ing.boykiss.blocksmith.block.BlockType;
import ing.boykiss.blocksmith.block.BlocksmithBlock;
import net.kyori.adventure.key.Key;

public class Blocks {
    public static final BlocksmithBlock DEFAULT = BlocksmithBlock.create(Key.key("ns", "default"), BlockShape.FULL, BlockType.DEFAULT);
    public static final BlocksmithBlock DISPLAY = BlocksmithBlock.create(Key.key("ns", "display"), BlockShape.FULL, BlockType.DISPLAY);
}
