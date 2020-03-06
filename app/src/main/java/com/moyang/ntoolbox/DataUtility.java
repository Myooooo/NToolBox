package com.moyang.ntoolbox;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class DataUtility {

    public static List<ItemNote> ItemNoteList = new ArrayList<>();
    public static List<DateNote> DateNoteList = new ArrayList<>();
    public static List<MoneyNote> MoneyNoteList = new ArrayList<>();
    public static List<EventNote> EventNoteList = new ArrayList<>();
    public static List<String> tags = new ArrayList<>();

    // 哈希表去重
    public static List<String> clearDuplicate(List<String> arrayList){
        return new ArrayList<String>(new HashSet<String>(arrayList));
    }

}
