package com.itserv.taskchanger.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface SaveLoadService {
    String NAME = "taskchanger_SaveLoadService";

     void save(String task, String... args);

     List<String> load(File file);
}