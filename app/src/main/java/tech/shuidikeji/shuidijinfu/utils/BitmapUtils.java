package tech.shuidikeji.shuidijinfu.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Base64;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapUtils {


    /**
     * 图片转Base64
     */
    public static String BitmapToBase64(Bitmap bitmap) {
        bitmap = YaSuoBitmap(bitmap, 400);
        if (bitmap == null) {
            return "";
        }

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "data:image/jpeg;base64,"+result;

    }

    /**
     * 压缩图片
     */
    public static Bitmap YaSuoBitmap(Bitmap bitmap, int size) {
        if (bitmap == null) {
            return null;
        }
        Bitmap bmp = bitmap;
        //图片大小上限kb
//        int size = 1800;

        // 首先进行一次大范围的压缩
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, output);
        float zoom = (float) Math.sqrt(size * 1024 / (float) output.toByteArray().length); //获取缩放比例

        // 设置矩阵数据
        Matrix matrix = new Matrix();
        matrix.setScale(zoom, zoom);

        // 根据矩阵数据进行新bitmap的创建
        Bitmap resultBitmap = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
        output.reset();
        resultBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);

        // 如果进行了上面的压缩后，依旧大于1000K，就进行小范围的微调压缩
        while (output.toByteArray().length > size * 1024) {
            matrix.setScale(0.8f, 0.8f);//每次缩小 1/10

            resultBitmap = Bitmap.createBitmap(resultBitmap, 0, 0,
                    resultBitmap.getWidth(), resultBitmap.getHeight(), matrix, true);

            output.reset();
            resultBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
        }

        return resultBitmap;
    }

    public static boolean saveBitmapFile(Bitmap bitmap, String filePath) {
        File file=new File(filePath);//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


}
