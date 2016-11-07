package Utils;

import android.content.Context;
import android.util.Base64;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * Created by Dream on 2016/7/12.
 */
public class RSAUtils {

    public static PublicKey KEY;


    public static byte[] decryptByPublicKey( byte[] data) {
        data = Base64.decode(data, Base64.DEFAULT);
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, KEY);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void initPublicKey(Context context) {
        try {
            InputStream is = context.getAssets().open("xingtu.crt");
            InputStreamReader reader = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(reader);
            String s = br.readLine();
            String str = "";
            s = br.readLine();
            while (s.charAt(0) != '-') {
                str += s + "\r";
                s = br.readLine();
            }
            byte[] b = Base64.decode(str, Base64.DEFAULT);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(b);
            KEY = kf.generatePublic(keySpec);

            br.close();
            reader.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}