package com.nexus.utils;

public class AppUtility {
    public static Long generateRandomLongNumber(Long bottomLimit, Long topLimit){
        return bottomLimit + (long) (Math.random() * (topLimit - bottomLimit));
    }

}
