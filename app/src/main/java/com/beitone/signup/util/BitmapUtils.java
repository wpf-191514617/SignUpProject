package com.beitone.signup.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import cn.betatown.mobile.beitonelibrary.util.StringUtil;

public class BitmapUtils {

    public static String bitmapToBase64WithHeader(String imagePath) {
        if (StringUtil.isEmpty(imagePath))
            return "";
        int lastIndex = imagePath.lastIndexOf(".");
        String type = imagePath.substring(lastIndex + 1, imagePath.length());
        if (type.equals("jpeg") || type.equals("JPEG")) {
            type = "data:image/jpeg;base64,";
        } else if (type.equals("jpg") || type.equals("JPG")) {
            type = "data:image/jpeg;base64,";
        } else if (type.equals("png") || type.equals("PNG")) {
            type = "data:image/png;base64,";
        }
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        String base64 = bitmapToBase64(bitmap);
        return base64;
    }


    /**
     * 根据得到图片字节，获得图片后缀
     *
     * @param photoByte 图片字节
     * @return 图片后缀
     */
    public static String getFileExtendName(byte[] photoByte) {
        String strFileExtendName = ".jpg";
        if (photoByte != null && photoByte.length > 0) {
            if ((photoByte[0] == 71) && (photoByte[1] == 73) && (photoByte[2] == 70)
                    && (photoByte[3] == 56) && ((photoByte[4] == 55) || (photoByte[4] == 57))
                    && (photoByte[5] == 97)) {
                strFileExtendName = ".gif";
            } else if ((photoByte[6] == 74) && (photoByte[7] == 70) && (photoByte[8] == 73)
                    && (photoByte[9] == 70)) {
                strFileExtendName = ".jpg";
            } else if ((photoByte[0] == 66) && (photoByte[1] == 77)) {
                strFileExtendName = ".bmp";
            } else if ((photoByte[1] == 80) && (photoByte[2] == 78) && (photoByte[3] == 71)) {
                strFileExtendName = ".png";
            }
        }
        return strFileExtendName;
    }


    /**
     * bitmap转为base64
     *
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        byte[] bitmapBytes = new byte[0];
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

                baos.flush();
                baos.close();

                bitmapBytes = baos.toByteArray();
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

        String type = getFileExtendName(bitmapBytes);
        if (type.equals(".jpeg") || type.equals(".JPEG")) {
            type = "data:image/jpeg;base64,";
        } else if (type.equals(".jpg") || type.equals(".JPG")) {
            type = "data:image/jpeg;base64,";
        } else if (type.equals(".png") || type.equals(".PNG")) {
            type = "data:image/png;base64,";
        }


        return type + result;
    }

    /**
     * base64转为bitmap
     *
     * @param base64Data
     * @return
     */
    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }



    /**
     * bitmap转为base64
     * @param bitmap
     * @return
     */
    public static String bitmapToBase642(Bitmap bitmap) {

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
        return result;
    }

}
