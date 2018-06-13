package org.rbaygildin.custom_collection;

import java.util.HashMap;
import java.util.Map;

/**
 * Словарь с возможностью автоинициализации пары "ключ-значение" в случае
 * ее отсутствия в исходном словаре
 * @param <K> Тип ключа
 * @param <V> Тип значения
 */
public class AutoValueInitMap<K, V> extends HashMap<K, V>{

    private static Map<Class, InstanceFactoryMethod> valueFactories_ = new HashMap<>();
    private Class<V> valueClazz_;

    /**
     * Создание объекта класса с регистрацией типа значения словаря
     * @param valueClazz Класс, хранящий информацию в режиме runtime о значение словаря
     */
    public AutoValueInitMap(Class<V> valueClazz){
        super();
        this.valueClazz_ = valueClazz;
    }

    /**
     * Создание объекта класса с регистрацией типа значения словаря и назначением
     * по умолчанию вместимости словаря
     * @param valueClazz Класс, хранящий информацию в режиме runtime о значение словаря
     */
    public AutoValueInitMap(Class<V> valueClazz, int capacity){
        super(capacity);
        this.valueClazz_ = valueClazz;
    }

    public AutoValueInitMap(Class<V> valueClazz, int capacity, float loadFactor){
        super(capacity, loadFactor);
        this.valueClazz_ = valueClazz;
    }

    public AutoValueInitMap(Class<V> valueClazz, Map<K, V> other){
        super(other);
        this.valueClazz_ = valueClazz;
    }

    /**
     * Зарегестрировать фабрику для автоматической инициализации значения
     * @param clazz Тип значения
     */
    public static void registerInstanceFactory(Class clazz, InstanceFactoryMethod builder){
        valueFactories_.put(clazz, builder);
    }

    /**
     * Удалить фабрику для автоматической инициализации значения
     * @param clazz Тип значения
     */
    public static void removeInstanceFactory(Class clazz){
        valueFactories_.remove(clazz);
    }

    /**
     * Получить значение по ключу
     * @param key Ключ
     * @return Значение
     */
    @Override
    public V get(Object key) {
        if(!containsKey(key)){
            super.put((K) key, (V) valueFactories_.get(valueClazz_).buildInstance());
        }
        return super.get(key);
    }
}
