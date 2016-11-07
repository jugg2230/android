package Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import Decoder.BASE64Encoder;


public class ImageUtils {

    private final static String TAG_LOG = ImageUtils.class.getName();

    /**
     * 从资源中取出Bitmap
     *
     * @param act
     * @param resId
     * @return
     */
    public static Bitmap getBitmapFromResources(Activity act, int resId) {
        Resources res = act.getResources();
        return BitmapFactory.decodeResource(res, resId);
    }


    /**
     * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
     *
     * @param map
     * @return
     */
    public static String getImageStr(Bitmap map) {
        byte[] data = null;
        // 读取图片字节数组
        try {
            InputStream in; //= new FileInputStream(imgFilePath);
            map = compressImage(map);
            in = Bitmap2InputStream(map, 100);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
        // 返回Base64编码过的字节数组字符串
    }

    public static String decodeImageForBase64(Bitmap map) {
        byte[] data = null;
        // 读取图片字节数组
        try {
            InputStream in; //= new FileInputStream(imgFilePath);
            in = Bitmap2InputStream(map, 100);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
        // 返回Base64编码过的字节数组字符串
    }

    /**
     * 压缩图片
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 70, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 / 1024 > 36) {    //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            Log.d("OPTIONS", options + "");
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中

        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片

        Log.d(TAG_LOG, "compressImage bitmap getWidth" + bitmap.getWidth());
        Log.d(TAG_LOG, "compressImage bitmap getHeight" + bitmap.getHeight());
        return bitmap;
    }

    private static InputStream Bitmap2InputStream(Bitmap bm, int quality) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        return is;
    }

    public static byte[] bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    public static Bitmap getBitmapFromIntent(Intent data, Context mContext,int x,int y,int size) {
        Uri uri = data.getData();
        try {
            String path = UriUtils.getFilePath(mContext, uri);//针对4.4以前的用这个方法可以获得图片的path路径，部分高版本也用此方法。
            if (path == null) {
                path = UriUtils.getPath(mContext, uri);//针对4.4以后的用这个方法。国内定制的rom有部分不支持此方法。
            }
            BitmapFactory.Options options = new BitmapFactory.Options();  //初始化Bitmap.Options
            options.inJustDecodeBounds = true;    //设置Bitmap为不加载。
            BitmapFactory.decodeFile(path, options); //加载bitmap。但返回值为null
            options.inSampleSize = 1; //压缩比例
            /**
             * 下面方法通过设置  options.inJustDecodeBounds为true可以加载出来Bitmap的高和宽。
             * 同时获取屏幕设置的ImageView的高和宽。
             * 进行比较返回比例
             * 设置options.inSampleSize的比例为返回的比例
             *  设置options.inJustDecodeBounds为false可以加载并返回Bitmap
             *  从而获得了Bitmap
             */
            final int mHeight = options.outHeight;
            final int mWidth = options.outWidth;
            if(x!=0&&y!=0) {
                final double reqHeight = ScreenUtils.getDimenX(mContext, x);
                final double reqWidth = ScreenUtils.getDimenY(mContext, y);
                final int heightRatio = Math.round((float) mHeight / (float) reqHeight);
                final int widthRatio = Math.round((float) mWidth / (float) reqWidth);
                options.inSampleSize = Math.max(heightRatio, widthRatio);
            }else {
                options.inSampleSize=size;
            }
            options.inJustDecodeBounds = false;
            Bitmap mBitmap = BitmapFactory.decodeFile(path, options);
            return mBitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
                             double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }

    //加载Resources图片避免outOfMemoryError
    public static Bitmap decodeResource(Resources res, int id) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        for (options.inSampleSize = 1; options.inSampleSize <= 32; options.inSampleSize++) {
            try {
                bitmap = BitmapFactory.decodeResource(res, id, options);
                Log.d(TAG_LOG, "Decoded successfully for sampleSize " + options.inSampleSize);
                break;
            } catch (OutOfMemoryError outOfMemoryError) {
                // If an OutOfMemoryError occurred, we continue with for loop and next inSampleSize value
                Log.e(TAG_LOG, "outOfMemoryError while reading file for sampleSize " + options.inSampleSize
                        + " retrying with higher value");
            }
        }
        return bitmap;
    }

    //按比例压缩法
    public static Bitmap decodeBitmapByProportion(Context context, Uri uri) {
        String path = UriUtils.getFilePath(context, uri);//针对4.4以前的用这个方法可以获得图片的path路径，部分高版本也用此方法。
        if (path == null) {
            path = UriUtils.getPath(context, uri);//针对4.4以后的用这个方法。国内定制的rom有部分不支持此方法。
        }

        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        for (options.inSampleSize = 1; options.inSampleSize <= 32; options.inSampleSize++) {
            try {
                bitmap = BitmapFactory.decodeFile(path, options);
                Log.d(TAG_LOG, "Decoded successfully for sampleSize " + options.inSampleSize);
                break;
            } catch (OutOfMemoryError outOfMemoryError) {
                // If an OutOfMemoryError occurred, we continue with for loop and next inSampleSize value
                Log.e(TAG_LOG, "outOfMemoryError while reading file for sampleSize " + options.inSampleSize
                        + " retrying with higher value");
            }
        }

        Bitmap bitmapProportion = null;
        try {
//            Log.d(TAG_LOG, "decodeBitmapByProportion bitmap getWidth" + bitmap.getWidth());
//            Log.d(TAG_LOG, "decodeBitmapByProportion bitmap getHeight" + bitmap.getHeight());

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            if( baos.toByteArray().length / 1024>1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
                baos.reset();//重置baos即清空baos
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中
            }
            ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
            BitmapFactory.Options newOpts = new BitmapFactory.Options();
            //开始读入图片，此时把options.inJustDecodeBounds 设回true了
            newOpts.inJustDecodeBounds = true;
            bitmapProportion = BitmapFactory.decodeStream(isBm, null, newOpts);
            newOpts.inJustDecodeBounds = false;
            int w = newOpts.outWidth;
            int h = newOpts.outHeight;
            //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
            float hh = 800f;//这里设置高度为800f
            float ww = 480f;//这里设置宽度为480f
            //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
            int be = 1;//be=1表示不缩放
            if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
                be = (int) (newOpts.outWidth / ww);
            } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
                be = (int) (newOpts.outHeight / hh);
            }
            if (be <= 0)
                be = 1;
            newOpts.inSampleSize = be;//设置缩放比例
            //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
            isBm = new ByteArrayInputStream(baos.toByteArray());
            bitmapProportion = BitmapFactory.decodeStream(isBm, null, newOpts);

        } catch (OutOfMemoryError outOfMemoryError) {
            // If an OutOfMemoryError occurred, we continue with for loop and next inSampleSize value
            Log.e(TAG_LOG, "outOfMemoryError while reading file for sampleSize " + options.inSampleSize
                    + " retrying with higher value");

            return null;
        }

        return compressImage(bitmapProportion);//压缩好比例大小后再进行质量压缩
    }


}
