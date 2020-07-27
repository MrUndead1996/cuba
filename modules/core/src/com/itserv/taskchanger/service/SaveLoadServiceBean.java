package com.itserv.taskchanger.service;

import com.itserv.taskchanger.core.SaveLoad;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service(SaveLoadService.NAME)
public class SaveLoadServiceBean implements SaveLoadService {

    @Inject
    private SaveLoad saveLoad;

    @Override
    public void save(String task, String... args) {
        try {
            saveLoad.save(task,args);
        }
        catch (IOException ioException){
            throw new RuntimeException("Can not save file. Please, try again");
        }
    }

    @Override
    public List<String> load(File file) {
        try {
            return saveLoad.load(file);
        } catch (IOException ioException) {
            throw new RuntimeException("Can not load file. Please, try again");
        }
    }
}