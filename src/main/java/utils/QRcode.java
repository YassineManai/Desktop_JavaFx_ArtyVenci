package utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.pdf417.decoder.ec.ErrorCorrection;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

public class QRcode {
    public static boolean generateQrCode(String text,int id){
        String filePath="C:\\Users\\Hei\\OneDrive\\Documents\\GitHub\\Sprint-Java\\src\\main\\java\\qrcode\\Event_"+id+".png";
        int size=150;
        String fileType="png";
        File myFile=new File(filePath);
        try{
            Map<EncodeHintType,Object> hintMap=new EnumMap<EncodeHintType,Object>(EncodeHintType.class);
            hintMap.put(EncodeHintType.CHARACTER_SET,"UTF-8");
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            QRCodeWriter qrCodeWriter=new QRCodeWriter();
            BitMatrix byteMatrix=qrCodeWriter.encode(text, BarcodeFormat.QR_CODE,size,size,hintMap);
            int cruchifyWidth=byteMatrix.getWidth();
            BufferedImage image=new BufferedImage(cruchifyWidth,cruchifyWidth,BufferedImage.TYPE_INT_RGB);
            image.createGraphics();
            Graphics2D graphics2D=(Graphics2D) image.getGraphics();
            graphics2D.setColor(Color.white);
            graphics2D.fillRect(0,0,cruchifyWidth,cruchifyWidth);
            graphics2D.setColor(Color.black);
            for(int i=0;i<cruchifyWidth;i++){
                for(int j=0;j<cruchifyWidth;j++){
                    if(byteMatrix.get(i,j)){
                        graphics2D.fillRect(i,j,1,1);
                    }
                }
            }
            ImageIO.write(image,fileType,myFile);
        }catch (WriterException e){
            System.out.println(e.getMessage());
            return false;
        }catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }
        System.out.println("You have successfully created a QR code");
        return true;



    }
}
