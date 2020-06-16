package cn.betatown.mobile.beitonelibrary.util;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {


    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return true   为空
     * false  不为空
     */
    public static boolean isEmpty(String str) {
        if (str == null || str.length() == 0 || str.equalsIgnoreCase("null") || str.isEmpty() ||
                str.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 邮箱 格式验证
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (isEmpty(email)) {
            return false;
        }
        String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\" +
                ".][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 手机号：手机号码格式验证
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        /*if (isEmpty(mobiles)) {
            return false;
        }
        if (mobiles.length() == 11)
            return true;
        return false;*/
        Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(16[0-9])|(17[0-9])|(18[0," +
                "0-9])|(19[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 手机号：手机号码格式验证
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO2(String mobiles) {
        if (isEmpty(mobiles)) {
            return false;
        }
        Pattern p = Pattern.compile("^(1)\\d{10}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }


    /**
     * is url
     *
     * @param url
     * @return
     */
    public static boolean isUrl(String url) {
        if (isEmpty(url)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(" +
                "([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$");
        return pattern.matcher(url).matches();
    }

    public static boolean checkPassword(String password) {
        if (isEmpty(password)) {
            return false;
        }
        if (password.length() >= 6) {
            return true;
        }
        return false;
    }

    public static boolean checkLicenseCode(String licenseCode) {
        if (!isEmpty(licenseCode) && licenseCode.length() == 18) {
            return true;
        }
        return false;
    }


    public static boolean checkIdentification(String identification) {
        if (isEmpty(identification) || identification.length()
                != 18) {
            return false;
        }

        char[] id = {};

        for (int i = 0; i < identification.length(); i++) {
            id = Arrays.copyOf(id, id.length + 1);
            id[id.length - 1] = identification.charAt(i);
        }

        boolean isFromRight = verForm(identification);
        if (isFromRight) {
            boolean isCoreect = verify(id);
            if (isCoreect) {
                return true;
            }
        }
        return false;
    }

    public static boolean verForm(String num) {
        String reg = "^\\d{15}$|^\\d{17}[0-9Xx]$";
        if (!num.matches(reg)) {
            System.out.println("Format Error!");
            return false;
        }
        return true;
    }

    public static boolean verify(char[] id) {
        int sum = 0;
        int w[] = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
        char[] ch = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
        for (int i = 0; i < id.length - 1; i++) {
            sum += (id[i] - '0') * w[i];
        }
        int c = sum % 11;
        char code = ch[c];
        char last = id[id.length - 1];
        last = last == 'x' ? 'X' : last;
        return last == code;
    }


    public static String formatPhone(String pNumber) {
        if (!TextUtils.isEmpty(pNumber) && pNumber.length() > 6) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < pNumber.length(); i++) {
                char c = pNumber.charAt(i);
                if (i >= 3 && i <= 6) {
                    sb.append('*');
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        }
        return null;
    }


    public static String formatPrice(String price) {
        double price1 = Double.parseDouble(price);
        return formatPrice(price1);
    }


    public static String formatPrice(double price) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(price);
    }


    /**
     * 保留两位小数,进行四舍五入
     *
     * @param d
     * @return
     */
    public static String saveOneBitTwoRound(Double d) {
        BigDecimal bd = new BigDecimal(d);
        Double tem = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
       // return String.valueOf(tem);
        return formatPrice(tem);
    }


}
