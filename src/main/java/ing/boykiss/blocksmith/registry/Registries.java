package ing.boykiss.blocksmith.registry;

import ing.boykiss.blocksmith.item.BlocksmithItem;

public final class Registries {
    public static Registry<BlocksmithItem> items() {
        return BlocksmithItem.ITEMS;
    }
}
