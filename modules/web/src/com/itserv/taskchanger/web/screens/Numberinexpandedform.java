package com.itserv.taskchanger.web.screens;

import com.haulmont.cuba.gui.AppConfig;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.export.ByteArrayDataProvider;
import com.haulmont.cuba.gui.export.ExportFormat;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;
import com.itserv.taskchanger.service.NumberInExpandedFormService;
import com.itserv.taskchanger.service.SaveLoadService;

import javax.inject.Inject;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

@UiController("taskchanger_Numberinexpandedform")
@UiDescriptor("NumberInExpandedForm.xml")
public class Numberinexpandedform extends Screen {

    @Inject
    private TextField<String> resultField, numberField;
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
    private Button computeButton, saveButton;
    @Inject
    private Notifications notifications;


    @Subscribe("numberField")
    public void onNumberValueChange(HasValue.ValueChangeEvent<String> event) {
        setNumber(event.getValue());
        buttonsSwitcher();
    }

    @Subscribe("computeButton")
    private void onComputeClick(Button.ClickEvent event) {
        try {
            String answer = service.getNumber(getNumber());
            getResultField().setValue(answer);
        }
        catch (Exception e){
            notifications.create(Notifications.NotificationType.ERROR).withCaption( getNumber() + " is not a number").show();
        }
    }


    @Subscribe("saveButton")
    public void onSaveButtonClick(Button.ClickEvent event) {
        String fileName = getClass().getSimpleName() + ".txt";
        String dataString = getClass().getSimpleName() + "\n" + getNumber();
        byte[] dataBytes = dataString.getBytes(StandardCharsets.UTF_8);
        AppConfig.createExportDisplay(this.getWindow())
                .show(new ByteArrayDataProvider(dataBytes), fileName, ExportFormat.TEXT);

    }


    @Subscribe("upFile")
    public void onUpFileFileUploadSucceed(FileUploadField.FileUploadSucceedEvent event) {
        UUID fileId = upFile.getFileId();
        File file = fileUploadingAPI.getFile(fileId);
        List<String> args = saveLoadService.load(file);
        if (args.size() > 1) {
            getNumberField().setValue(args.get(1));
            onComputeClick(new Button.ClickEvent(getComputeButton()));
        }
        else notifications.create(Notifications.NotificationType.ERROR).withCaption("Wrong file").show();
    }

    private void buttonsSwitcher() {
        if (getNumber() == null){
            getSaveButton().setEnabled(false);
            getComputeButton().setEnabled(false);
        }
        else {
            getSaveButton().setEnabled(true);
            getComputeButton().setEnabled(true);
        }
    }

    public TextField<String> getResultField() {
        return resultField;
    }

    public TextField<String> getNumberField() {
        return numberField;
    }

    public String getNumber() {
        return number;
    }

    public Button getComputeButton() {
        return computeButton;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}