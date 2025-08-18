package ing.boykiss.blocksmith.util;

public sealed interface Value<T> permits Value.BoolValue, Value.IntValue, Value.StringValue {
    T value();

    String toString();

    record IntValue(Integer value) implements Value<Integer> {
    }

    record BoolValue(Boolean value) implements Value<Boolean> {
    }

    record StringValue(String value) implements Value<String> {
    }

    record KeyValue<T extends Value>(String key, T value) {
    }
}