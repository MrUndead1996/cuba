package com.itserv.taskchanger.service;

import com.itserv.taskchanger.core.StringSort;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Arrays;

@Service(StringSortService.NAME)
public class StringSortServiceBean implements StringSortService {

    @Inject
    private  StringSort stringSort;


    public String getSortedString(String words,String parts){
        String[] wordsArray = words.replace(" ","").split(",");
        String[] partsArray = parts.replace(" ","").split(",");
        String[] answer = stringSort.sort(wordsArray,partsArray);
        String result = Arrays.toString(answer);
        return result.substring(1, result.length() - 1);
    }
}