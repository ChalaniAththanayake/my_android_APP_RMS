//To store Logon  username & selected project title as a static variable. which are use other activities
//Getter & Setter methods use to set static variables values & get static variables values
package com.example.chalaniaththanayake.externaluser;

/**
 * Created by chalaniaththanayake on 1/8/2017.
 */

public class DataHolder{
    private static String username, projectTitle;

    public static String getUsername() {return username;}
    public static void setUsername(String username) {DataHolder.username = username;}

    public static String getProjectTitle() {return projectTitle;}
    public static void setProjectTitle(String projectTitle) {DataHolder.projectTitle = projectTitle;}

}
