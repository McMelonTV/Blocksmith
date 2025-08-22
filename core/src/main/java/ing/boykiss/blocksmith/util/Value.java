package ing.boykiss.blocksmith.util;

import org.jetbrains.annotations.NotNull;

public sealed interface Value<T> permits Value.BoolValue, Value.IntValue, Value.StringValue {
    T value();

    String toString();

    record IntValue(Integer value) implements Value<Integer> {
        @Override
        public @NotNull String toString() {
            return value.toString();
        }
    }

    record BoolValue(Boolean value) implements Value<Boolean> {
        @Override
        public @NotNull String toString() {
            return value.toString();
        }
    }

    record StringValue(String value) implements Value<String> {
        @Override
        public @NotNull String toString() {
            return value;
        }
    }

    record KeyValue<T extends Value>(String key, T value) {
    }
}