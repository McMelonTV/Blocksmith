package ing.boykiss.blocksmith.registry;

import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class StaticRegistry<T extends Keyed> extends Registry<T> {
    public StaticRegistry(@NotNull Set<@NotNull T> data) {
        super(data);
    }
}
