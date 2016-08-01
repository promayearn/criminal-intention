package com.augmentis.ayp.criminalintention;

/**
 * Created by Chayanit on 8/1/2016.
 */
public class CrimeDbSchema {

    public static final class CrimeTable {
        public static final String NAME = "Crime";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
        }
    }
}
