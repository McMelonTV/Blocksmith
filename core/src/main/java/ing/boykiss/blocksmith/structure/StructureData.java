package ing.boykiss.blocksmith.structure;

import ing.boykiss.blocksmith.util.Value;
import net.kyori.adventure.nbt.CompoundBinaryTag;

public class StructureData {
    public int[] size; // [x,y,z]
    public int[][] blocks; // [[x,y,z,blockIndex,stateIndex,nbtIndex],...]
    public String[] namespacePalette; // ["minecraft","somemod",...]
    public Value.KeyValue<Value.IntValue>[] blockPalette; // [{"oak_leaves":0},{"someblock":1},...]
    public Value.KeyValue[][] statePalette; // [[{"waterlogged":true},{"persistent":true}],...]
    public CompoundBinaryTag[] nbtPalette; // [{__block_data__},...]
}
