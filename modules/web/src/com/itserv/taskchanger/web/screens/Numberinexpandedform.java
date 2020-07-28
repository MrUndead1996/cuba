package com.itserv.taskchanger.web.screens;

import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;
import com.itserv.taskchanger.service.NumberInExpandedFormService;
import com.itserv.taskchanger.service.SaveLoadService;

import javax.inject.Inject;
import java.io.File;
import java.util.List;
import java.util.UUID;

@UiController("taskchanger_Numberinexpandedform")
@UiDescriptor("NumberInExpandedForm.xml")
public class Numberinexpandedform extends Screen {

    @Inject
    private TextField<String> result, numberField;
    @Inject
    private NumberInExpandedFormService service;
    private String number;
    @Inject
    private SaveLoadService saveLoadService;
    @Inject
    private FileUploadingAPI fileUploadingAPI;
    @Inject
    private FileUploadField upFile;
    @Inject
    private Button computeButton;


    @Subscribe("numberField")
    public void onNumberTextChange(TextInputField.TextChangeEvent event) {
        number = event.getText();
    }

    @Subscribe("computeButton")
    private void onComputeClick(Button.ClickEvent event) {
        String answer = service.getNumber(number);
        result.setValue(answer);
    }



    @Subscribe("saveButton")
    public void onSaveButtonClick(Button.ClickEvent event) {
        saveLoadService.save(getClass().getSimpleName(),number);
    }

    @Install(to = "upFile", subject = "validator")
    private void upFileValidator(FileDescriptor fileDescriptor) {
        if (!fileDescriptor.getName().contains(getClass().getSimpleName()))
            throw new ValidationException("wrong");

    }


    @Subscribe("upFile")
    public void onUpFileFileUploadSucceed(FileUploadField.FileUploadSucceedEvent event) {
        UUID fileId = upFile.getFileId();
        File file = fileUploadingAPI.getFile(fileId);
        List<String> args = saveLoadService.load(file);
        args.forEach(System.out::println);
        numberField.setValue(args.get(1));
        number = (args.get(1));
        onComputeClick(new Button.ClickEvent(computeButton));
    }
}