package ing.boykiss.testserver;

import ing.boykiss.blocksmith.block.BlockShape;
import ing.boykiss.blocksmith.block.BlockType;
import ing.boykiss.blocksmith.block.BlocksmithBlock;
import net.kyori.adventure.key.Key;

public class Blocks {
    public static final BlocksmithBlock NORMAL = BlocksmithBlock.create(Key.key("test", "normal"), BlockShape.FULL, BlockType.NORMAL);
    public static final BlocksmithBlock DISPLAY = BlocksmithBlock.create(Key.key("test", "display"), BlockShape.FULL, BlockType.DISPLAY);
    public static final BlocksmithBlock SPAWNER = BlocksmithBlock.create(Key.key("test", "spawner"), BlockShape.FULL, BlockType.SPAWNER);
}
