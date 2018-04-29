package com.liang.batchOrder.service;

import com.baidu.aip.ocr.AipOcr;
import com.liang.batchOrder.bean.BaiDuResultBean;
import com.liang.batchOrder.util.JacksonUtil;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;

/**
 * Created by wangliang.wang on 2018/4/17.
 */
@Service
public class CodeCrackService {

    private static final String APP_ID = "11172925";
    private static final String API_KEY = "Q14GePUudiIcA7utoAM9p2Tg";
    private static final String SECRET_KEY = "4uh9fyGclhVBoeW6OLB0ue5LGoGs3o06";
    AipOcr ORC_CLIENT = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

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

    public String doBaiDuCracking(byte[] imageData) {
        if(imageData == null) {
            return null;
        }
        // 可选：设置网络连接参数
        ORC_CLIENT.setConnectionTimeoutInMillis(2000);
        ORC_CLIENT.setSocketTimeoutInMillis(60000);

        // 调用接口
        JSONObject res = ORC_CLIENT.basicGeneral(imageData, new HashMap<String, String>());
        BaiDuResultBean baiDuResultBean = JacksonUtil.decode(res.toString(), BaiDuResultBean.class);
        System.out.println(res.toString(2));
        return baiDuResultBean.getWords_result().get(0).getWords();
    }
}
