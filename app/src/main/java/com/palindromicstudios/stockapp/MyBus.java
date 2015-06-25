package com.palindromicstudios.stockapp;

import com.squareup.otto.Bus;

/**
 * Created by Natan on 6/24/2015.
 */
public class MyBus {

    private static final Bus BUS = new Bus();

    public static Bus getInstance() {
        return BUS;
    }
}
