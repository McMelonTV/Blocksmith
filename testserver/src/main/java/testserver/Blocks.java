package testserver;

import ing.boykiss.blocksmith.block.BlockDef;
import ing.boykiss.blocksmith.block.BlockRegistry;
import ing.boykiss.blocksmith.block.BlockShape;
import ing.boykiss.blocksmith.block.BlockType;
import net.kyori.adventure.key.Key;

public class Blocks {
    private static final BlockRegistry BLOCKS = TestServer.BLOCKSMITH.getBlockRegistry();

    public static final BlockDef NORMAL = BLOCKS.create(Key.key("test", "normal"), BlockShape.FULL, BlockType.NORMAL);
    public static final BlockDef DISPLAY = BLOCKS.create(Key.key("test", "display"), BlockShape.FULL, BlockType.DISPLAY);
    public static final BlockDef SPAWNER = BLOCKS.create(Key.key("test", "spawner"), BlockShape.FULL, BlockType.SPAWNER);
}
