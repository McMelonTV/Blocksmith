package ing.boykiss.testserver;

import ing.boykiss.blocksmith.item.BlocksmithItem;
import net.kyori.adventure.key.Key;
import net.minestom.server.item.Material;

public class Items {
    public static final BlocksmithItem DIA = BlocksmithItem.ITEMS.register(new BlocksmithItem(Key.key("ns", "dia"), Material.DIAMOND));
}
