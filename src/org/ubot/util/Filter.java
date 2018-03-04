package org.ubot.util;

public interface Filter<E> {

	boolean accept(E e);

}