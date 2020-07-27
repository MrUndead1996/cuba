package com.itserv.taskchanger.web.screens;

import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import com.itserv.taskchanger.service.SaveLoadService;
import com.itserv.taskchanger.service.StringSortService;

import javax.inject.Inject;

@UiController("taskchanger_StringSort")
@UiDescriptor("stringSort-screen.xml")
public class StringSort extends Screen {

    @Inject
    private TextField<String> result;
    private String words, parts;
    @Inject
    private Button save, load, compute;

    @Inject
    private  StringSortService service;
    @Inject
    private SaveLoadService saveLoadService;



    @Subscribe("compute")
    public void onComputeClick(Button.ClickEvent event) {
        String answer = service.getSortedString(words,parts);
        result.setValue(answer);
    }

    @Subscribe("words")
    public void onWordsValueChange(HasValue.ValueChangeEvent<String> event) {
        words = event.getValue();
    }

    @Subscribe("parts")
    public void onPartsValueChange(HasValue.ValueChangeEvent<String> event) {
        parts = event.getValue();
    }

    @Subscribe("save")
    public void onSaveClick(Button.ClickEvent event) {
        saveLoadService.save(getClass().getSimpleName(),words,parts);
    }
}