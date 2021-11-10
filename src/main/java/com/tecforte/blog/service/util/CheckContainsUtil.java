package com.tecforte.blog.service.util;


import com.tecforte.blog.domain.Entry;
import com.tecforte.blog.domain.enumeration.EmotionEnum;

import com.tecforte.blog.web.rest.errors.BadRequestAlertException;
import java.util.*;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

public final class CheckContainsUtil {
    public static void checkContainsPositive(String title, String content) {
        EnumSet.allOf(EmotionEnum.EmotionEnumPositive.class)
            .forEach(emotion -> {
                String regex = ".*\\b" +emotion.toString()+ "\\b.*";
//                Matcher m = p.matcher(blogTitleUpper);
                if(title.matches(regex)){
                    throw new BadRequestAlertException("Invalid Content", ENTITY_NAME, "invalidContent");
                };
            });

        EnumSet.allOf(EmotionEnum.EmotionEnumPositive.class)
            .forEach(emotion -> {
                String regex = ".*\\b" +emotion.toString()+ "\\b.*";
                if(content.matches(regex)){
                    throw new BadRequestAlertException("Invalid Content", ENTITY_NAME, "invalidContent");
                }
            });
    }


    public static void checkContainsNegative(String title, String content) {
        EnumSet.allOf(EmotionEnum.EmotionEnumNegative.class)
            .forEach(emotion -> {
                String regex = ".*\\b" +emotion.toString()+ "\\b.*";
                if(title.matches(regex)){
                    throw new BadRequestAlertException("Invalid Content", ENTITY_NAME, "invalidContent");
                }
            });

        EnumSet.allOf(EmotionEnum.EmotionEnumNegative.class)
            .forEach(emotion -> {
                String regex = ".*\\b" +emotion.toString()+ "\\b.*";
                if(content.matches(regex)){
                    throw new BadRequestAlertException("Invalid Content", ENTITY_NAME, "invalidContent");
                }
            });
    }
    public static List<Entry> checkContainsKeywords(List<String> keywords, List<Entry> entryList){

        List<Entry> entryListDel = new ArrayList<>();
        for(String keyword: keywords){
            keyword = keyword.toUpperCase();
            String regex = ".*\\b" +keyword+ "\\b.*";

            for(Entry entry: entryList){
                if(entry.getTitle().toUpperCase().matches(regex))
                    entryListDel.add(entry);
                if(entry.getContent().toUpperCase().matches(regex))
                    entryListDel.add(entry);
            }
        }

        return entryListDel;
    }
}
