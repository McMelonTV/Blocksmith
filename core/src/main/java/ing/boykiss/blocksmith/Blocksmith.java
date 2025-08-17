package ing.boykiss.blocksmith;

import ing.boykiss.blocksmith.block.BlockRegistry;
import ing.boykiss.blocksmith.item.ItemRegistry;
import ing.boykiss.blocksmith.registry.Registries;

public class Blocksmith implements Registries {
    private final ItemRegistry itemRegistry = new ItemRegistry();
    private final BlockRegistry blockRegistry = new BlockRegistry();

    public Blocksmith() {

    }

    @Override
    public ItemRegistry items() {
        return itemRegistry;
    }

    @Override
    public BlockRegistry blocks() {
        return blockRegistry;
    }
}