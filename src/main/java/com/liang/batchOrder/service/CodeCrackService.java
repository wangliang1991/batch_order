package com.liang.batchOrder.service;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Created by wangliang.wang on 2018/4/17.
 */
@Service
public class CodeCrackService {

    public String doCracking(File imageFile) {
        if(imageFile == null) {
            return null;
        }

        Tesseract tessreact = new Tesseract();
        tessreact.setDatapath("C:/tessdata");
        try {
            return tessreact.doOCR(imageFile);
        } catch (TesseractException e) {

        }

        return null;
    }
}
