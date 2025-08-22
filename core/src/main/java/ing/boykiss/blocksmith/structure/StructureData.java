package ing.boykiss.blocksmith.structure;

import ing.boykiss.blocksmith.block.BlockDef;
import ing.boykiss.blocksmith.block.BlockRegistry;
import ing.boykiss.blocksmith.util.Value;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.minestom.server.coordinate.BlockVec;
import net.minestom.server.instance.block.Block;
import org.intellij.lang.annotations.Subst;
import org.javatuples.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Note: Negative values wont work

/**
 * IMPORTANT: This class is not very efficient for regular usage and should therefore only be used for Serialization/Deserialization.
 * For other uses please use the Structure class.
 *
 * @param size             [x,y,z]
 * @param blocks           [[x,y,z,blockIndex,stateIndex,nbtIndex],...]
 * @param namespacePalette ["minecraft","somemod",...]
 * @param blockPalette     [{"oak_leaves":0},{"someblock":1},...]
 * @param statePalette     [[{"waterlogged":true},{"persistent":true}],...]
 * @param nbtPalette       [{__block_data__},...]
 * @see Structure
 */
public record StructureData(
        int @NotNull [] size,
        int @NotNull [] @NotNull [] blocks,
        @NotNull String[] namespacePalette,
        @NotNull Value.KeyValue<Value.IntValue>[] blockPalette,
        @NotNull Value.KeyValue<?>[][] statePalette,
        @NotNull CompoundBinaryTag[] nbtPalette
) {
    public static final int BLOCKDATA_INDEX_X = 0;
    public static final int BLOCKDATA_INDEX_Y = 1;
    public static final int BLOCKDATA_INDEX_Z = 2;
    public static final int BLOCKDATA_INDEX_BLOCK = 3;
    public static final int BLOCKDATA_INDEX_STATE = 4;
    public static final int BLOCKDATA_INDEX_NBT = 5;

    public @NotNull BlockVec getSize() {
        return new BlockVec(size[0], size[1], size[2]);
    }

    public @Nullable Block getBlock(@NotNull BlockVec pos, @Nullable BlockRegistry customBlockRegistry) {
        int[] blockData = getBlockData(pos);
        if (blockData == null) return null;

        return toBlock(blockData, customBlockRegistry);
    }

    public @NotNull List<Pair<@NotNull BlockVec, @NotNull Block>> asBlockList(@Nullable BlockRegistry customBlockRegistry) {
        List<Pair<BlockVec, Block>> list = new ArrayList<>(blocks.length);

        for (int[] blockData : blocks) {
            Block block = toBlock(blockData, customBlockRegistry);
            if (block != null) {
                list.add(Pair.with(getBlockPos(blockData), block));
            }
        }

        return list;
    }

    public @NotNull Map<@NotNull BlockVec, @NotNull Block> asBlockMap(@Nullable BlockRegistry customBlockRegistry) {
        Map<BlockVec, Block> map = new HashMap<>((int) (blocks.length / 0.75 + 1)); // hashmap load factor

        for (int[] blockData : blocks) {
            Block block = toBlock(blockData, customBlockRegistry);
            if (block != null) {
                map.put(getBlockPos(blockData), block);
            }
        }

        return map;
    }

    private @Nullable Block toBlock(int @NotNull [] blockData, @Nullable BlockRegistry customBlockRegistry) {
        Value.KeyValue<Value.IntValue> blockId = blockPalette[blockData[BLOCKDATA_INDEX_BLOCK]];
        @Subst("minecraft") String namespace = namespacePalette[blockId.value().value()];
        @Subst("dirt") String path = blockId.key();
        Key blockKey = Key.key(namespace, path);
        Value.KeyValue<?>[] state = statePalette[blockData[BLOCKDATA_INDEX_STATE]];
        CompoundBinaryTag nbt = nbtPalette[blockData[BLOCKDATA_INDEX_NBT]];

        Map<String, String> stateMap = new HashMap<>();
        for (Value.KeyValue<?> prop : state) {
            stateMap.put(prop.key(), prop.value().toString());
        }

        Block baseBlock = Block.fromKey(blockKey);
        if (baseBlock == null && customBlockRegistry == null) {
            return null;
        } else if (customBlockRegistry != null) {
            return customBlockRegistry.get(blockKey).map(BlockDef::getBlock).map(b -> b.withProperties(stateMap).withNbt(nbt)).orElse(null);
        }

        return baseBlock.withProperties(stateMap).withNbt(nbt);
    }

    private @NotNull BlockVec getBlockPos(int @NotNull [] blockData) {
        return new BlockVec(blockData[BLOCKDATA_INDEX_X], blockData[BLOCKDATA_INDEX_Y], blockData[BLOCKDATA_INDEX_Z]);
    }

    private int @Nullable [] getBlockData(@NotNull BlockVec pos) {
        int[] blockPos = new int[]{pos.blockX(), pos.blockY(), pos.blockZ()};
        for (int[] blockData : blocks) {
            if (blockData[BLOCKDATA_INDEX_X] != blockPos[0]
                    || blockData[BLOCKDATA_INDEX_Y] != blockPos[1]
                    || blockData[BLOCKDATA_INDEX_Z] != blockPos[2])
                continue;
            return blockData;
        }
        return null;
    }
}
