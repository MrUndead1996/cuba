package com.itserv.taskchanger.web.screens;

import com.haulmont.cuba.gui.AppConfig;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.FileUploadField;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.export.ByteArrayDataProvider;
import com.haulmont.cuba.gui.export.ExportFormat;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;
import com.itserv.taskchanger.service.SaveLoadService;
import com.itserv.taskchanger.service.StringSortService;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

@UiController("taskchanger_StringSort")
@UiDescriptor("stringSort-screen.xml")
public class StringSort extends Screen {

    @Inject
    private TextField<String> wordsField, partsField, resultField;
    private String words, parts;
    @Inject
    private Button computeButton, saveButton;

    @Inject
    private StringSortService service;
    @Inject
    private SaveLoadService saveLoadService;
    @Inject
    private FileUploadField upFile;
    @Inject
    private FileUploadingAPI fileUploadingAPI;
    @Inject
    private Notifications notifications;


    @Subscribe("computeButton")
    public void onComputeClick(Button.ClickEvent event) {
        if (words != null && parts != null) {
            String answer = service.getSortedString(words, parts);
            getResultField().setValue(answer);
        } else notifications.create(Notifications.NotificationType.ERROR).withCaption("Enter data").show();
    }

    @Subscribe("wordsField")
    public void onWordsValueChange(HasValue.ValueChangeEvent<String> event) {
        setWords(event.getValue());
        buttonsSwitcher();
    }

    @Subscribe("partsField")
    public void onPartsValueChange(HasValue.ValueChangeEvent<String> event) {
         setParts(event.getValue());
         buttonsSwitcher();
    }

    @Subscribe("saveButton")
    public void onSaveClick(Button.ClickEvent event) {
        String filename = getClass().getSimpleName() + ".txt";
        String dataString = getClass().getSimpleName() + "\n" + words + "\n" + parts;
        byte[] dataBytes = dataString.getBytes(StandardCharsets.UTF_8);
        AppConfig.createExportDisplay(this.getWindow())
                .show(new ByteArrayDataProvider(dataBytes), filename, ExportFormat.TEXT);
    }

    @Subscribe("upFile")
    public void onUpFileFileUploadSucceed(FileUploadField.FileUploadSucceedEvent event) {
        UUID fileId = upFile.getFileId();
        File file = fileUploadingAPI.getFile(fileId);
        List<String> args = saveLoadService.load(file);
        if (args.size() > 2) {
            getWordsField().setValue(args.get(1));
            getPartsField().setValue(args.get(2));
            onComputeClick(new Button.ClickEvent(getComputeButton()));
        } else notifications.create(Notifications.NotificationType.ERROR).withCaption("Wrong file").show();
    }

    private void buttonsSwitcher() {
        if (getWords() == null|| getParts() == null){
            getSaveButton().setEnabled(false);
            getComputeButton().setEnabled(false);
        }
        else {
            getSaveButton().setEnabled(true);
            getComputeButton().setEnabled(true);
        }
    }

    //GETTERS
    public TextField<String> getWordsField() {
        return wordsField;
    }

    public TextField<String> getPartsField() {
        return partsField;
    }

    public TextField<String> getResultField() {
        return resultField;
    }

    public Button getComputeButton() {
        return computeButton;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public String getWords() {
        return words;
    }

    public String getParts() {
        return parts;
    }

    //SETTERS

    public void setWords(String words) {
        this.words = words;
    }

    public void setParts(String parts) {
        this.parts = parts;
    }
}