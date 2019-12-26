package io.leego.mypages.util;

import io.leego.mypages.exception.UnsupportedReturnTypeException;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Yihleego
 */
public final class PaginationCollectionFactory {
    private PaginationCollectionFactory() {
    }

    public static Collection<?> build(Class<?> returnType, Object result, Long total, Integer page, Integer size) {
        if (result == null) {
            return build(returnType, total, page, size);
        }
        Collection<?> collection = (Collection<?>) result;
        if (collection.isEmpty()) {
            return build(returnType, total, page, size);
        }
        if (List.class.isAssignableFrom(returnType)) {
            if (List.class == returnType || ArrayList.class == returnType) {
                return new PaginationArrayList<>(collection, total, page, size);
            } else if (LinkedList.class == returnType) {
                return new PaginationLinkedList<>(collection, total, page, size);
            }
        } else if (Set.class.isAssignableFrom(returnType)) {
            if (Set.class == returnType || HashSet.class == returnType) {
                return new PaginationHashSet<>(collection, total, page, size);
            } else if (LinkedHashSet.class == returnType) {
                return new PaginationLinkedHashSet<>(collection, total, page, size);
            } else if (TreeSet.class == returnType) {
                return new PaginationTreeSet<>(collection, total, page, size);
            }
        } else if (Queue.class.isAssignableFrom(returnType)) {
            if (Queue.class == returnType || ArrayDeque.class == returnType) {
                return new PaginationArrayDeque<>(collection, total, page, size);
            } else if (PriorityQueue.class == returnType) {
                return new PaginationPriorityQueue<>(collection, total, page, size);
            }
        }
        throw new UnsupportedReturnTypeException("Custom type is not supported, please use Java Collection");
    }

    public static Collection<?> build(Class<?> returnType, Long total, Integer page, Integer size) {
        if (List.class.isAssignableFrom(returnType)) {
            if (List.class == returnType || ArrayList.class == returnType) {
                return new PaginationArrayList<>(total, page, size);
            } else if (LinkedList.class == returnType) {
                return new PaginationLinkedList<>(total, page, size);
            }
        } else if (Set.class.isAssignableFrom(returnType)) {
            if (Set.class == returnType || HashSet.class == returnType) {
                return new PaginationHashSet<>(total, page, size);
            } else if (LinkedHashSet.class == returnType) {
                return new PaginationLinkedHashSet<>(total, page, size);
            } else if (TreeSet.class == returnType) {
                return new PaginationTreeSet<>(total, page, size);
            }
        } else if (Queue.class.isAssignableFrom(returnType)) {
            if (Queue.class == returnType || ArrayDeque.class == returnType) {
                return new PaginationArrayDeque<>(total, page, size);
            } else if (PriorityQueue.class == returnType) {
                return new PaginationPriorityQueue<>(total, page, size);
            }
        }
        throw new UnsupportedReturnTypeException("Custom type is not supported, please use Java Collection");
    }

    public static Collection<?> empty(Class<?> returnType) {
        return build(returnType, 0L, 0, 0);
    }

}
