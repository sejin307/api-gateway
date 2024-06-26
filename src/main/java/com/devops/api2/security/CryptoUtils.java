package com.devops.api2.security;

import jakarta.mail.internet.MimeUtility;
import org.springframework.beans.factory.annotation.Value;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * 아이티센그룹 암복호화 모듈
 * CryptoJS.AES.encrypt <===> encrypt(String plainText)
 * CryptoJS.AES.decrypt <===> decrypt(String encryptedStr)
 * KEY,IV값  JS,서버 동일하게 세팅
 * @author 20201110 sejin307
 *
 */
public class CryptoUtils {

    protected static final String CRYPTO_LEGACY_KEY = "CengroupAPK20!@#";
    protected static final String CRYPTO_LEGACY_IV = "cenGroupAPV20#@!";
    protected static final byte[] key = new BigInteger(stringToHex(CRYPTO_LEGACY_KEY),16).toByteArray();
    protected static final byte[] iv  = new BigInteger(stringToHex(CRYPTO_LEGACY_IV),16).toByteArray();

    //private static String plainStr = "admin/admin";


    //암호화
    public static String encrypt(String plainText) throws Exception{
        byte [] encData = null;
        try{
            SecretKey sk = new SecretKeySpec(key, "AES");
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            cipher.init(Cipher.ENCRYPT_MODE, sk ,paramSpec);

            encData = cipher.doFinal(plainText.getBytes("UTF-8"));
        }catch (Exception e) {
            // TODO: handle exception
        }

        return new String (base64Encode(encData));
    }


    //복호화
    public static String decrypt(String encryptedStr){
        String decryptedStr = "";
        try{
            SecretKey sk = new SecretKeySpec(key, "AES");
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            cipher.init(Cipher.DECRYPT_MODE, sk ,paramSpec);

            byte [] decByteData = base64Decode(encryptedStr.getBytes());

            byte[] decData = cipher.doFinal(decByteData);
            decryptedStr = new String(decData,"UTF-8");
        }catch (Exception e) {
            // TODO: handle exception
        }

        return decryptedStr;
    }

    //String -> hex 변환
    public static String stringToHex(String s) {
        String result = "";
        for (int i = 0; i < s.length(); i++) {
            result += String.format("%02X", (int) s.charAt(i));
        }
        return result;
    }


    //BASE64 ENCODE
    public static byte[ ] base64Encode( byte[ ] b ) throws Exception{
        ByteArrayOutputStream baos = new ByteArrayOutputStream( );
        OutputStream b64os = MimeUtility.encode( baos, "base64" );
        b64os.write( b );
        b64os.close( );
        return baos.toByteArray( );
    }

    //BASE64 DECODE
    public static byte[ ] base64Decode( byte[ ] b ) throws Exception{
        ByteArrayInputStream bais = new ByteArrayInputStream( b );
        InputStream b64is = MimeUtility.decode( bais, "base64" );
        byte[ ] tmp = new byte[ b.length ];
        int n = b64is.read( tmp );
        byte[ ] res = new byte[ n ];
        System.arraycopy( tmp, 0, res, 0, n );
        return res;
    }


    public static void main(String[] args) {
        try{
            //String plainStr = "centerr/CENTerr!@#4"; //CENTerr   ex) xrSG7vApBIiKkG%2BXh6wvPBh0y4owT5RnD7asS%2FeIX00%3D%0D%0A
            //String plainStr = "purchase/Purchase!@#4"; //구매   ex) gpVJeolUkIAnRyIRgxQ%2Bnw3ZA8clEMVEqjp0YCvvbfQ%3D%0D%0A
            //String plainStr = "z37cVTlpXxbc+Pte+pzjwbMnSOQMtU5uuEO5cDb/kknAEXJsk/0esEm+jMXLdPX1";
            //String plainStr = "groupware/GroupWare!@#4"; //Groupware ex)xrSG7vApBIiKkG%2BXh6wvPBh0y4owT5RnD7asS%2FeIX00%3D%0D%0A
            String plainStr = "secucen/SecucenIO!@#4"; //V9HNQAeAXTvH0OueWCChrn9xSEGnMHxyYVWlVFpjrDg%3D%0D%0A
            String encryptedStr =  encrypt(plainStr);
            String urlEncodedStr = URLEncoder.encode(encryptedStr);
            //String urlDecodedStr = URLDecoder.decode(urlEncodedStr);
//            String urlDecodedStr = URLDecoder.decode("KI3emM0sC8UILzgjtjxPcUog1KD1ItySOTf8enmBATWDndLk5UDKwOlqBLoZWHJF");

            //yguqGeglouCvlHurxAey4IdvqsRVlrGFtS9VJmdXTZ8%3D%0D%0A
            System.out.println("~~~~~~~~~~~~>urlEncodedStr : " + urlEncodedStr);
            System.out.println("~~~~~~~~~~~~>encryptedStr : " + encryptedStr);
             //String decryptedStr = decrypt(encryptedStr);
//            String decryptedStr = decrypt(urlDecodedStr);

//             System.out.println("~~~~~~~~~~~~> decryptedStr :" + decryptedStr);
        }catch (Exception e) {
            // TODO: handle exception
        }

    }

}

