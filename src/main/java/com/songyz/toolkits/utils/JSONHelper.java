package com.songyz.toolkits.utils;

import java.io.InputStream;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;

/**
 * Convert String <--> Object by using Jacson lib.
 * 
 * Pay attention to: Datetime formart is yyyy-MM-dd HH:mm:ss
 * 
 * @author wujin
 * @version V1.0
 */
public class JSONHelper {
    private static ObjectMapper objMapper = new ObjectMapper();
    static {
        objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        objMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objMapper.configure(SerializationFeature.WRITE_ENUMS_USING_INDEX, true);
    }

    public static String toJSON(Object obj) {
        try {
            return objMapper.writeValueAsString(obj);
        }
        catch (Exception exp) {
            throw new RuntimeException("toJSON 异常", exp);
        }
    }

    /**
     * Convert json string to special data type
     * 
     * @param json
     * @param cls
     * @return
     * @throws Exception
     */
    public static <T> T fromJSON(String json, Class<T> cls) {
        try {
            return objMapper.readValue(json, cls);
        }
        catch (Exception exp) {
            throw new RuntimeException("fromJSON 异常", exp);
        }
    }

    /**
     * Convert json data to special data type
     * 
     * @param data
     * @param cls
     * @return
     * @throws Exception
     */
    public static <T> T fromJSON(byte[] data, Class<T> cls) {
        try {
            return objMapper.readValue(data, cls);
        }
        catch (Exception exp) {
            throw new RuntimeException("fromJSON 异常", exp);
        }
    }

    /**
     * Convert json inputStream to special data type
     * 
     * @param in
     * @param element
     * @return
     * @throws Exception
     */
    public static <T> T fromJSON(InputStream in, TypeReference<T> element) {
        try {
            return objMapper.readValue(in, element);
        }
        catch (Exception exp) {
            throw new RuntimeException("fromJSON 异常", exp);
        }
    }

    /**
     * wrap raw json data to List, its element type is T
     * 
     * @param data
     * @param element
     * @return
     * @throws Exception
     */
    public static <T> List<T> fromJSONList(byte[] data, Class<T> element) {
        try {
            JavaType type = TypeFactory.defaultInstance().constructCollectionType(List.class, element);
            return objMapper.readValue(data, type);
        }
        catch (Exception exp) {
            throw new RuntimeException("fromJSONList 异常", exp);
        }
    }

    /**
     * wrap raw json data to Set, its element type is T
     * 
     * @param data
     * @param element
     * @return
     * @throws Exception
     */
    public static <T> Set<T> fromJSONSet(byte[] data, Class<T> element) {
        try {
            JavaType type = TypeFactory.defaultInstance().constructCollectionType(Set.class, element);
            return objMapper.readValue(data, type);
        }
        catch (Exception exp) {
            throw new RuntimeException("fromJSONSet 异常", exp);

        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T fromJSON(String json, TypeReference<T> element) {
        try {
            return (T) objMapper.readValue(json, element);
        }
        catch (Exception exp) {
            throw new RuntimeException("fromJSON 异常", exp);
        }
    }

    /**
     * For generic type, json always convert to LinkedListMap first, so need invoker call this method to
     * convert again
     * 
     * @param obj
     * @param element
     * @return
     * @throws Exception
     */
    public static <T> List<T> convertToList(Object obj, Class<T> element) {
        JavaType type = TypeFactory.defaultInstance().constructCollectionType(List.class, element);
        return objMapper.convertValue(obj, type);
    }

    /**
     * If Object data type is target type, only return this object, Otherwise convert object to special
     * T.
     * 
     * For generic type, json always convert to LinkedListMap first, so need invoker call this method to
     * convert again
     * 
     * @param obj
     * @param element
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static <T> T convertToObject(Object obj, Class<T> element) {
        if (element.isInstance(obj))
            return (T) obj;

        return objMapper.convertValue(obj, element);
    }

    /**
     * Special for complex object If Object data type is target type, only return this object, Otherwise
     * convert object to special T.
     * 
     * For generic type, json always convert to LinkedListMap first, so need invoker call this method to
     * convert again
     * 
     * @param <T>
     * 
     * 
     * @param obj
     * @param element
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static <T> T convertToObject(Object obj, TypeReference<T> element) {
        if (element.getType().getClass().isInstance(obj))
            return (T) obj;

        return (T) objMapper.convertValue(obj, element);
    }
}
