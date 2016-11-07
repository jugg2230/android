package Utils;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {


    public static void write(Context context, String fileName, String data) {
        // TODO Auto-generated method stub
        FileOutputStream out = null;
        try {
            out = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            out.write(data.getBytes("UTF-8"));
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void write(Context context, String fileName, InputStream data) {
        // TODO Auto-generated method stub
        FileOutputStream out;

        byte[] buf = new byte[2048];
        try {
            out = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            int read = 0;
            while (read != -1) {
                read = data.read(buf);
                out.write(buf, 0, read);
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean check(Context context, String filename) {
        String[] list = context.getFilesDir().list();
        for (int i = 0; i < list.length; i++) {
            if (list[i].equals(filename)) {
                return true;
            }
        }
        return false;
    }

    public static String read(Context context, String fileName) {
        // TODO Auto-generated method stub
        try {
            FileInputStream fileInputStream = context.openFileInput(fileName);
            byte[] bs = new byte[1024];
            int hasRead = 0;
            StringBuilder stringBuilder = new StringBuilder("");
            while ((hasRead = fileInputStream.read(bs)) > 0) {
                stringBuilder.append(new String(bs, 0, hasRead));
            }
            fileInputStream.close();
            return stringBuilder.toString();
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    public static boolean deleteDirectory(String filePath) {
        boolean flag = false;
        // 如果filePath不以文件分隔符结尾，自动添加文件分隔符
        if (!filePath.endsWith(File.separator)) {
            filePath = filePath + File.separator;
        }
        File dirFile = new File(filePath);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        flag = true;
        File[] files = dirFile.listFiles();
        // 遍历删除文件夹下的所有文件(包括子目录)
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                // 删除子文件
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            } else {
                // 删除子目录
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        return flag;
    }

    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            return file.delete();
        }
        return false;
    }


}
