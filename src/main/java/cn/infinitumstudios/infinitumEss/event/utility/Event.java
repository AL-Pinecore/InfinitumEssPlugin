package cn.infinitumstudios.infinitumEss.event.utility;

public interface Event<T> {
    T invoker();
    void register(T listener);
}