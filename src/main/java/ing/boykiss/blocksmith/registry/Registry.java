package ing.boykiss.blocksmith.registry;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public abstract class Registry<T extends Keyed> {
    protected final @NotNull Map<@NotNull Key, @NotNull T> dataMap;

    protected Registry(@NotNull Map<@NotNull Key, @NotNull T> data) {
        dataMap = data;
    }

    protected Registry(@NotNull Set<@NotNull T> data) {
        dataMap = setToMap(data);
    }

    protected Registry() {
        dataMap = new HashMap<>();
    }

    protected static <T extends Keyed> @NotNull Map<@NotNull Key, @NotNull T> setToMap(@NotNull Set<@NotNull T> set) {
        int size = set.size();
        Map<Key, T> dataMap = new HashMap<>(Math.max(16, (int) (size / 0.75f) + 1));
        for (T o : set) {
            dataMap.put(o.key(), o);
        }
        return dataMap;
    }

    public Optional<T> get(@NotNull Key key) {
        T o = dataMap.get(key);
        return o == null ? Optional.empty() : Optional.of(o);
    }

    public @Nullable T getUnsafe(@NotNull Key key) {
        return dataMap.get(key);
    }

    public boolean has(@NotNull Key key) {
        return dataMap.containsKey(key);
    }

    public int size() {
        return dataMap.size();
    }

    public @NotNull Set<@NotNull Key> keys() {
        return dataMap.keySet();
    }

    public @NotNull Collection<@NotNull T> values() {
        return dataMap.values();
    }
}
