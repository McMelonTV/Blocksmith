package testserver;

import ing.boykiss.blocksmith.item.ItemDef;
import ing.boykiss.blocksmith.item.ItemRegistry;
import net.kyori.adventure.key.Key;
import net.minestom.server.item.Material;

public class Items {
    private static final ItemRegistry ITEMS = TestServer.BLOCKSMITH.getItemRegistry();

    public static final ItemDef STICK = ITEMS.create(Key.key("test", "stick"), Material.STICK);
}
