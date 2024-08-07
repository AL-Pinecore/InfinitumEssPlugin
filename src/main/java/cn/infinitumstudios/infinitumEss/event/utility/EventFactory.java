package cn.infinitumstudios.infinitumEss.event.utility;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class EventFactory {
    public static <T> Event<T> createArrayBacked(Class<T> type, Function<T[], T> invokerFactory) {
        List<T> listeners = new ArrayList<>();
        T[] array = (T[]) Array.newInstance(type, 0);

        return new Event<T>() {
            @Override
            public T invoker() {
                return invokerFactory.apply(listeners.toArray(array));
            }

            @Override
            public void register(T listener) {
                listeners.add(listener);
            }
        };
    }
}