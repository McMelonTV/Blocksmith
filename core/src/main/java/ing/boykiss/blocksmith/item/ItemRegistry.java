package ing.boykiss.blocksmith.item;

import ing.boykiss.blocksmith.registry.DynamicRegistry;
import net.kyori.adventure.key.Key;
import net.minestom.server.component.DataComponentMap;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

import java.util.Optional;

public class ItemRegistry extends DynamicRegistry<ItemDef> {
    /**
     * @throws UnsupportedOperationException if id already exists
     */
    public ItemDef create(Key id, Material baseMaterial, DataComponentMap baseComponents) throws UnsupportedOperationException {
        return this.register(new ItemDef(id, baseMaterial, baseComponents));
    }

    /**
     * @throws UnsupportedOperationException if id already exists
     */
    public ItemDef create(Key id, Material baseMaterial) throws UnsupportedOperationException {
        return create(id, baseMaterial, DataComponentMap.EMPTY);
    }

    public Optional<ItemDef> fromStack(ItemStack itemStack) {
        return this.get(itemStack.getTag(ItemDef.BLOCKSMITH_ITEM_ID_TAG));
    }
}
