package ing.boykiss.blocksmith.item;

import ing.boykiss.blocksmith.registry.DynamicRegistry;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import net.kyori.adventure.text.Component;
import net.minestom.server.component.DataComponent;
import net.minestom.server.component.DataComponentMap;
import net.minestom.server.component.DataComponents;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.tag.Tag;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;

public class BlocksmithItem implements Keyed {
    public static final DynamicRegistry<BlocksmithItem> ITEMS = new DynamicRegistry<>();

    public static final Tag<@NotNull String> BLOCKSMITH_ITEM_ID_RAW_TAG = Tag.String("blocksmith_item_id");
    public static final Tag<@NotNull Key> BLOCKSMITH_ITEM_ID_TAG = BLOCKSMITH_ITEM_ID_RAW_TAG.map(Key::key, Key::asString);

    private final Key id;
    private final Material baseMaterial;
    private final DataComponentMap baseComponents;

    public BlocksmithItem(Key id, Material baseMaterial, DataComponentMap baseComponents) {
        this.id = id;
        this.baseMaterial = baseMaterial;
        this.baseComponents = baseComponents;
    }

    public BlocksmithItem(Key id, Material baseMaterial) {
        this(id, baseMaterial, DataComponentMap.EMPTY);
    }

    public static Optional<BlocksmithItem> fromStack(ItemStack itemStack) {
        return ITEMS.get(itemStack.getTag(BLOCKSMITH_ITEM_ID_TAG));
    }

    public Optional<ItemStack> createStack(int amount) {
        Optional<BlocksmithItem> blocksmithItem = ITEMS.get(id);
        return blocksmithItem.map(item -> item.createStackInternal(amount));
    }

    @SuppressWarnings("unchecked")
    private ItemStack createStackInternal(int amount) {
        ItemStack.Builder builder = ItemStack.builder(baseMaterial);
        builder.amount(amount);
        builder.set(DataComponents.ITEM_NAME, Component.translatable("item." + id.value() + ".name"));
        builder.set(DataComponents.ITEM_MODEL, id.asString());
        baseComponents.entrySet().forEach(e -> builder.set((DataComponent<@NotNull Object>) e.component(), Objects.requireNonNull(e.value())));
        builder.setTag(BLOCKSMITH_ITEM_ID_TAG, id);
        return builder.build();
    }

    @Override
    public @NotNull Key key() {
        return id;
    }
}
