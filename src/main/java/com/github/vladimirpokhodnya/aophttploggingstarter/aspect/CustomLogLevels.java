package com.github.vladimirpokhodnya.aophttploggingstarter.aspect;

import org.apache.logging.log4j.Level;

public class CustomLogLevels {
    public static final Level MINIMAL = Level.forName("MINIMAL HTTP LOG", 350);
    public static final Level MEDIUM = Level.forName("MEDIUM HTTP LOG", 351);
    public static final Level FULL = Level.forName("FULL HTTP LOG", 352);
}
