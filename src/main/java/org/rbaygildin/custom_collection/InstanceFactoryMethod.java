package org.rbaygildin.custom_collection;

@FunctionalInterface
public interface InstanceFactoryMethod<T> {
    T buildInstance();
}
