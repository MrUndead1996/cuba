package com.itserv.taskchanger.service;

import com.itserv.taskchanger.core.LoadFromFile;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service(SaveLoadService.NAME)
public class LoadServiceBean implements SaveLoadService {

    @Inject
    private LoadFromFile loadFromFile;

    @Override
    public List<String> load(File file) {
        try {
            return loadFromFile.load(file);
        } catch (IOException ioException) {
            throw new RuntimeException("Can not load file. Please, try again");
        }
    }
}