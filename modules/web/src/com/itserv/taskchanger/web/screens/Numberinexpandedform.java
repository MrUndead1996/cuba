package com.itserv.taskchanger.web.screens;

import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.FileUploadField;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.components.TextInputField;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;
import com.itserv.taskchanger.service.NumberInExpandedFormService;
import com.itserv.taskchanger.service.SaveLoadService;

import javax.inject.Inject;
import java.io.File;
import java.util.List;

@UiController("taskchanger_Numberinexpandedform")
@UiDescriptor("NumberInExpandedForm.xml")
public class Numberinexpandedform extends Screen {

    @Inject
    private TextField<String> result;
    @Inject
    NumberInExpandedFormService service;
    private String number;
    @Inject
    private FileUploadField loadButton;
    @Inject
    private FileUploadingAPI fileUploadingAPI;
    @Inject
    private SaveLoadService saveLoadService;

    @Subscribe("number")
    public void onNumberTextChange(TextInputField.TextChangeEvent event) {
        number = event.getText();
    }

    @Subscribe("computeButton")
    private void onComputeClick(Button.ClickEvent event) {
        String answer = service.getNumber(number);
        result.setValue(answer);
    }

    @Subscribe("loadButton")
    public void onUploadFieldFileUploadSucceed(FileUploadField.FileUploadSucceedEvent event) {
        File file = fileUploadingAPI.getFile(loadButton.getFileId());
        if (file != null) {
           List<String> args = saveLoadService.load(file);
           number = args.get(1);
        }
    }

    @Subscribe("saveButton")
    public void onSaveButtonClick(Button.ClickEvent event) {
        saveLoadService.save(getClass().getSimpleName(),number);
    }
}