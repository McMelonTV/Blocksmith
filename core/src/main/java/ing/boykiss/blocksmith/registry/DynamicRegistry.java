package ing.boykiss.blocksmith.registry;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import org.javatuples.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class DynamicRegistry<T extends Keyed> extends Registry<T> {
    /**
     * Allows setting (overriding) values of existing keys
     */
    private final boolean allowSet;

    /**
     * Allows removing (unregistering) keys (and their values)
     */
    private final boolean allowRemove;

    /**
     * @param allowSet    Allows setting (overriding) values of existing keys
     * @param allowRemove Allows removing (unregistering) keys (and their values)
     */
    public DynamicRegistry(boolean allowSet, boolean allowRemove) {
        super();
        this.allowSet = allowSet;
        this.allowRemove = allowRemove;
    }

    public DynamicRegistry() {
        this(false, false);
    }

    /**
     * @param initialData The initial data of the registry
     * @param allowSet    Allows setting (overriding) values of existing keys
     * @param allowRemove Allows removing (unregistering) keys (and their values)
     */
    public DynamicRegistry(@NotNull Set<@NotNull T> initialData, boolean allowSet, boolean allowRemove) {
        super(initialData);
        this.allowSet = allowSet;
        this.allowRemove = allowRemove;
    }

    /**
     * @param initialData The initial data of the registry
     */
    public DynamicRegistry(@NotNull Set<@NotNull T> initialData) {
        this(initialData, false, false);
    }

    /**
     * Adds a value to the registry
     *
     * @return Returns the value
     * @throws UnsupportedOperationException if the key already exists
     */
    public T register(@NotNull T value) throws UnsupportedOperationException {
        Key key = value.key();
        if (has(key)) throw new UnsupportedOperationException();
        dataMap.putIfAbsent(key, value);
        return value;
    }

    /**
     * Removes a value from the registry
     *
     * @return Returns the value
     * @throws UnsupportedOperationException if removing is not allowed or the key does not exist
     */
    public T unregister(@NotNull Key key) throws UnsupportedOperationException {
        if (!allowRemove) throw new UnsupportedOperationException();
        if (!has(key)) throw new UnsupportedOperationException();
        return dataMap.remove(key);
    }

    /**
     * Changes the value of an existing key
     *
     * @return Returns the previous and current value
     * @throws UnsupportedOperationException if setting is not allowed or the key does not exist
     */
    public Pair<T, T> set(@NotNull T value) throws UnsupportedOperationException {
        if (!allowSet) throw new UnsupportedOperationException();
        if (!has(value.key())) throw new UnsupportedOperationException();
        return Pair.with(dataMap.put(value.key(), value), value);
    }
}
