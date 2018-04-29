package com.liang.batchOrder.util;

import com.liang.batchOrder.constants.LanguageConstant;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;

import java.io.*;

/**
 * tesseract for java， ocr（Optical Character Recognition，光学字符识别）
 * 工具类
 * @author wind
 */
public class Tess4jUtils {

    static {
        System.setProperty("jna.debug_load.jna", "true");
        System.setProperty("jna.debug_load", "true");
    }
    /**
     * 从图片中提取文字,默认设置英文字库,使用classpath目录下的训练库
     * @param imageFile
     * @return
     */
    public static String readChar(File imageFile){
        // JNA Interface Mapping
        ITesseract instance = new Tesseract();

        //In case you don't have your own tessdata, let it also be extracted for you
        //这样就能使用classpath目录下的训练库了
        File tessDataFolder = new File("D://tess4j/tessdata");
        //Set the tessdata path
        instance.setDatapath(tessDataFolder.getAbsolutePath());
        //英文库识别数字比较准确
        instance.setLanguage(LanguageConstant.ENG);
        return getOCRText(instance, imageFile);
    }

    /**
     * 从图片中提取文字
     * @param imageFile 图片路径
     * @param dataPath 训练库路径
     * @param language 语言字库
     * @return
     */
    public static String readChar(File imageFile, String dataPath, String language){
        ITesseract instance = new Tesseract();
        instance.setDatapath(dataPath);
        //英文库识别数字比较准确
        instance.setLanguage(language);
        return getOCRText(instance, imageFile);
    }

    /**
     * 识别图片文件中的文字
     * @param instance
     * @param imageFile
     * @return
     */
    private static String getOCRText(ITesseract instance, File imageFile){
        String result = null;
        try {
            result = instance.doOCR(imageFile);
        } catch (TesseractException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static void loadDLL(String libFullName) {
        try {
            String nativeTempDir = System.getProperty("java.io.tmpdir");
            InputStream in = null;
            FileOutputStream writer = null;
            BufferedInputStream reader = null;
            File extractedLibFile = new File(nativeTempDir + File.separator + libFullName);
            if (!extractedLibFile.exists()) {
                try {
                    in = Tesseract.class.getResourceAsStream("/" + libFullName);
                    Tesseract.class.getResource(libFullName);
                    reader = new BufferedInputStream(in);
                    writer = new FileOutputStream(extractedLibFile);
                    byte[] buffer = new byte[1024];
                    while (reader.read(buffer) > 0) {
                        writer.write(buffer);
                        buffer = new byte[1024];
                    }
                    in.close();
                    writer.close();
                    System.load(extractedLibFile.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (in != null) {
                        in.close();
                    }
                    if (writer != null) {
                        writer.close();
                    }
                }
            } else {
                System.load(extractedLibFile.toString());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            File file = new File("D://11111.jpg");

            String ret = readChar(file);

            System.out.println(ret);
            int a = 0;
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        int t = 0;
    }

}