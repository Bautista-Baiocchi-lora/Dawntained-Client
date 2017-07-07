package org.bot.util;

public interface Filter<E> {

    public boolean accept(E e);

}