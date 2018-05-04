package com.example.chalaniaththanayake.resourcemanager;

/**
 * Created by chalaniaththanayake on 1/2/2018.
 */

public class DataHolder{
    private static String data="", username="", skills="";

    public static String getData() {return data;}
    public static void setData(String data) {DataHolder.data = data; }

    public static String getUsername() {return username;}
    public static void setusername(String username) {DataHolder.username = username;}

    public static String getSkills() {return skills;}
    public static void setSkills(String skills) {DataHolder.skills = skills;}
}
