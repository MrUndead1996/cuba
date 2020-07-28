package com.itserv.taskchanger.core;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

@Component(LoadFromFile.NAME)
public class LoadFromFile {
    public static final String NAME = "taskchanger_SaveLoad";

    /**
     * Load task from file
     *
     * @param file TXT file for loading
     * @return List<String> with task type and field values
     */
    public List<String> load(File file) throws IOException {
        List<String> result = new LinkedList<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        while (reader.ready()) {
            result.add(reader.readLine());     // get args from file
        }
        reader.close();
        return result;
    }


}