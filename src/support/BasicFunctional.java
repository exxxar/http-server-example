/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package support;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.ByteMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import javax.imageio.ImageIO;

/**
 *
 * @author Aleks
 */
public class BasicFunctional {
     public final String ERROR_PAGE_404 = "404.html";
     public final String LOGO_ICON = "icon-404.png";
    
     public String md5Custom(String st) {
            MessageDigest messageDigest = null;
            byte[] digest = new byte[0];

            try {
                messageDigest = MessageDigest.getInstance("MD5");
                messageDigest.reset();
                messageDigest.update(st.getBytes());
                digest = messageDigest.digest();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
             }
 
            BigInteger bigInt = new BigInteger(1, digest);
            String md5Hex = bigInt.toString(16);

            while( md5Hex.length() < 32 ){
                md5Hex = "0" + md5Hex;
            }

            return md5Hex;
        }
     
      public String LoadPage(String path) throws FileNotFoundException, IOException
       {
           BufferedReader myfile = new BufferedReader(new FileReader(path));
           String b = null;
           String buf = "";
           while(true){
                b = myfile.readLine();
                buf+=b+"\n";
                if (myfile.ready()==false)
                    break;
            }
            myfile.close();
            return buf;
       }
      
    
    public void SendFile(Socket s,String path) throws IOException
    {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        int in = 0;
        byte byteArray[] = new byte[8192];
        try {
             File f = new File(path);
             if (f.exists())
               {
                bis = new BufferedInputStream(new FileInputStream(path));
                bos = new BufferedOutputStream(s.getOutputStream());

                    while ((in = bis.read(byteArray)) != -1){
                        bos.write(byteArray,0,in);
                     }
                    bis.close();
                    bos.close();
               }
                else
                    if (path.contains("."))
                       SendFile(s, ERROR_PAGE_404);
                  
            
        } catch (Exception e) {
                    bis.close();
                    bos.close();
        }
 }
    
    public  void createQRImage(File qrFile, String qrCodeText, int size,
			String fileType) throws WriterException, IOException {
		// Create the ByteMatrix for the QR-Code that encodes the given String
		Hashtable hintMap = new Hashtable();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		ByteMatrix byteMatrix = qrCodeWriter.encode(qrCodeText,
				BarcodeFormat.QR_CODE, size, size, hintMap);
		// Make the BufferedImage that are to hold the QRCode
		int matrixWidth = byteMatrix.getWidth();
		BufferedImage image = new BufferedImage(matrixWidth, matrixWidth,
				BufferedImage.TYPE_INT_RGB);
		image.createGraphics();

		Graphics2D graphics = (Graphics2D) image.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, matrixWidth, matrixWidth);
		// Paint and save the image using the ByteMatrix
		graphics.setColor(Color.BLACK);

		for (int i = 0; i < matrixWidth; i++) {
			for (int j = 0; j < matrixWidth; j++) {
				if (byteMatrix.get(i, j)==0) {
					graphics.fillRect(i, j, 1, 1);
				}
			}
		}
		ImageIO.write(image, fileType, qrFile);
	}

    
    
   
}
