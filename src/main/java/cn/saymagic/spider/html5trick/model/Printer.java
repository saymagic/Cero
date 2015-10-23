package cn.saymagic.spider.html5trick.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by saymagic on 15/10/23.
 */
public class Printer {

    public static String print(Object obj) {
        if (obj == null) {
            printNull();
        }

        if (obj instanceof Map) {
            return printMap((Map) obj);
        } else if (obj instanceof List) {
            return printList((List) obj);
        } else if (obj instanceof Set) {
            return printSet((Set) obj);
        }
        return printAndReturn(obj.toString());
    }

    public static <K, V> String printMap(Map<K, V> map) {
        StringBuilder builder = new StringBuilder();
        printAndReturn("Map " + map.getClass().getSimpleName() + " : ");
        for (Map.Entry<K, V> entry : map.entrySet()) {
            builder.append("key=" + entry.getKey().toString() + " value=" + entry.getValue().toString());
        }
        return printAndReturn(builder.toString());
    }

    public static <E> String printSet(Set<E> set) {
        StringBuilder builder = new StringBuilder();
        printAndReturn("Set " + set.getClass().getSimpleName() + " :");
        for (E e : set) {
            builder.append(" " + e.toString());
        }
        return printAndReturn(builder.toString());
    }

    public static <E> String printList(List<E> list) {
        StringBuilder builder = new StringBuilder();
        printAndReturn("List " + list.getClass().getSimpleName() + " :");
        for (E e : list) {
            builder.append(" " + e.toString());
        }
        return printAndReturn(builder.toString());
    }

    public static <T> boolean isEmpty(T t) {
        if (null == t) {
            return true;
        }
        if (t instanceof String) {
            return t.equals("");
        } else if (t instanceof Map) {
            return ((Map) t).isEmpty();
        } else if (t instanceof List) {
            return ((List) t).isEmpty();
        } else if (t instanceof Set) {
            return ((Set) t).isEmpty();
        }
        return false;
    }

    public static String printNull() {
        System.out.print(" null object");
        return "";
    }

    public static String printAndReturn(String s) {
        if (isEmpty(s)) {
            return printNull();
        }
        System.out.println(s);
        return s;
    }
}
