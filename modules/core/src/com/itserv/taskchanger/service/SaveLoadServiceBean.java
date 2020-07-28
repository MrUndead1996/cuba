package com.itserv.taskchanger.service;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Query;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.security.app.Authentication;
import com.itserv.taskchanger.core.SaveLoad;
import com.itserv.taskchanger.entity.FileEntity;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service(SaveLoadService.NAME)
public class SaveLoadServiceBean implements SaveLoadService {

    @Inject
    private SaveLoad saveLoad;
    @Inject
    Persistence persistence;
    @Inject
    private Metadata metadata;

    @Override
    public void save(String task, String path, String... args) {
        try (Transaction tx = persistence.createTransaction()) {
            FileEntity fileEntity = metadata.create(FileEntity.class);
            File file = saveLoad.save(task, args);
           /* System.out.println(file.getName());
            System.out.println(file.getAbsolutePath());*/
            fileEntity.setLink(file.getAbsolutePath());
            fileEntity.setFileName(file.getName());
            persistence.getEntityManager().persist(fileEntity);
            tx.commit();
        } catch (IOException ioException) {
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