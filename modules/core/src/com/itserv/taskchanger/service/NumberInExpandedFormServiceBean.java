package com.itserv.taskchanger.service;

import com.itserv.taskchanger.core.NumberInExpandedForm;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service(NumberInExpandedFormService.NAME)
public class NumberInExpandedFormServiceBean implements NumberInExpandedFormService {

    @Inject
    private  NumberInExpandedForm numberInExpandedForm;



    public String getNumber(String number){
        try {
            int num = Integer.parseInt(number);
            return numberInExpandedForm.getNumberInExpandedForm(num);
        }
        catch (NumberFormatException formatException){
            throw new NumberFormatException("Wrong Number");
        }
    }
}