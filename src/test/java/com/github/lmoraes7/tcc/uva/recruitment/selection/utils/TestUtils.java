package com.github.lmoraes7.tcc.uva.recruitment.selection.utils;

import org.jeasy.random.EasyRandom;

public final class TestUtils {

    public static <T> T dummyObject(final Class<T> tClass) {
        final EasyRandom easyRandom = new EasyRandom();
        return easyRandom.nextObject(tClass);
    }

}
