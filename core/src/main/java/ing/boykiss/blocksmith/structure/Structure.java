package ing.boykiss.blocksmith.structure;

import net.minestom.server.coordinate.BlockVec;
import net.minestom.server.coordinate.Point;
import net.minestom.server.instance.batch.BatchOption;
import net.minestom.server.instance.batch.RelativeBlockBatch;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.generator.GenerationUnit;
import net.minestom.server.instance.generator.UnitModifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

// Note: Negative values wont work
public class Structure {
    private final @NotNull Map<@NotNull BlockVec, @NotNull Block> blocks;
    private @NotNull BlockVec size = BlockVec.ZERO;

    public Structure(@NotNull Map<@NotNull BlockVec, @NotNull Block> blocks, @NotNull BlockVec size) {
        this.blocks = blocks;
        this.size = size;
    }

    public Structure() {
        this.blocks = new HashMap<>();
    }

    public @Nullable Block getBlock(@NotNull BlockVec localPos) {
        return blocks.get(localPos);
    }

    public void setBlock(@NotNull BlockVec localPos, @Nullable Block block) {
        if (block == null) {
            blocks.remove(localPos);
        } else {
            blocks.put(localPos, block);
        }
        recalculateSize();
    }

    public @NotNull RelativeBlockBatch toBatch(BatchOption batchOption) {
        RelativeBlockBatch batch = new RelativeBlockBatch(batchOption);
        blocks.forEach(batch::setBlock);
        return batch;
    }

    public @NotNull RelativeBlockBatch toBatch() {
        return toBatch(new BatchOption());
    }

    private void recalculateSize() {
        int maxX = 0;
        int maxY = 0;
        int maxZ = 0;
        for (BlockVec pos : blocks.keySet()) {
            if (pos.blockX() > maxX) maxX = pos.blockX();
            if (pos.blockY() > maxY) maxY = pos.blockY();
            if (pos.blockZ() > maxZ) maxZ = pos.blockZ();
        }
        size = new BlockVec(maxX + 1, maxY + 1, maxZ + 1);
    }

    public void place(@NotNull GenerationUnit generationUnit, @NotNull BlockVec localPos) {
        Point start = generationUnit.absoluteStart();
        GenerationUnit fork = generationUnit.fork(start, start.add(size));
        UnitModifier forkModifier = fork.modifier();

        blocks.forEach((pos, block) -> forkModifier.setBlock(start.add(pos), block));
    }
}
