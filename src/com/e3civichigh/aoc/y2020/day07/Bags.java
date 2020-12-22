package com.e3civichigh.aoc.y2020.day07;

import java.util.HashMap;

public class Bags extends HashMap<String, Bag> {
    // static variable single_instance of type Singleton
    private static Bags single_instance = null;

    // private constructor restricted to this class itself
    private Bags()
    {
    }

    // static method to create instance of Singleton class
    public static Bags getInstance()
    {
        if (single_instance == null)
            single_instance = new Bags();

        return single_instance;
    }
}
