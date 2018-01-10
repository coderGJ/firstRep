package com.zhisou;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author GuoJun
 * @since  2017-06-19
 * 
 */

public class MyMethod {

    /**
     *
     * 根据luhm算法验证银行卡或信用卡是否合法，
     * 该方法只针对16和19位的银行卡。
     * 1、从卡号最后一位数字开始,偶数位乘以2,如果乘以2的结果是两位数，将结果减去9。
     * 2、把所有数字相加,得到总和。
     * 3、如果信用卡号码是合法的，总和可以被10整除。
     *
     **/
    public boolean luhmBank(String bankCard) {
        String regex = "^[1-9](\\d{15}|\\d{18})$";

        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(bankCard);

        if (match.matches()) {

            int x = bankCard.length() % 2;

            int odd = 0;
            int even = 0;
            for (int i = 0; i < bankCard.length(); i++ ) {
                if (i % 2 == x) {
                    int temp = Integer.valueOf((bankCard.charAt(i) - '0')) << 1;
                    if (temp > 9)
                        temp -= 9;
                    even += temp;
                }
                else {
                    odd += Integer.valueOf((bankCard.charAt(i) - '0'));
                }
            }
            if (logger.isDebugEnabled()) {
                logger.debug("odd:" + odd + "\teven:" + even);
            }
            return ((odd + even) % 10 == 0);

        }
        return false;
    }
    /**
     *
     * 格式化为正整数数字的字符串，给定的length(长度)
     * 不足指定长度的前面补0.
     * 如果传入的数字字符串长度大于等于给定长度，返回原字符串
     * 如果不是数字的字符串，返回原字符串
     * @param length 期望的长度
     * @param num    待格式化的字符串
     *
     **/
    public String formatNum(int length,String num) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]+$");
        if (pattern.matcher(num).matches()) {
            if(num.startWith("-")|| num.startWith("+")){
                String symbol = num.substring(0,1);
                num = num.substring(1);
            }
            if (num.length >= length) {
                return num;
            }
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < length; i++ ) {
                sb.append("0");
            }
            String str = sb.toString() + num;
            return n.substring(str.length() - length);
        }
        return num;
    }
    /**
     *
     * @see org.apache.commons.lang3.math.NumberUtils
     *
     **/
    public static boolean isNumber(String str) {
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        char[] chars = str.toCharArray();
        int sz = chars.length;
        boolean hasExp = false;
        boolean hasDecPoint = false;
        boolean allowSigns = false;
        boolean foundDigit = false;

        int start = (chars[0] == '-') ? 1 : 0;
        if ((sz > start + 1) && (chars[start] == '0') && (chars[(start + 1)] == 'x')) {
            int i = start + 2;
            if (i == sz) {
                return false;
            }

            for (; i < chars.length; ++i) {
                if ((((chars[i] < '0') || (chars[i] > '9'))) && (((chars[i] < 'a') || (chars[i] > 'f')))
                        && (((chars[i] < 'A') || (chars[i] > 'F')))) {
                    return false;
                }
            }
            return true;
        }
        --sz;

        int i = start;

        while ((i < sz) || ((i < sz + 1) && (allowSigns) && (!(foundDigit)))) {
            if ((chars[i] >= '0') && (chars[i] <= '9')) {
                foundDigit = true;
                allowSigns = false;
            } else if (chars[i] == '.') {
                if ((hasDecPoint) || (hasExp)) {
                    return false;
                }
                hasDecPoint = true;
            } else if ((chars[i] == 'e') || (chars[i] == 'E')) {
                if (hasExp) {
                    return false;
                }
                if (!(foundDigit)) {
                    return false;
                }
                hasExp = true;
                allowSigns = true;
            } else if ((chars[i] == '+') || (chars[i] == '-')) {
                if (!(allowSigns)) {
                    return false;
                }
                allowSigns = false;
                foundDigit = false;
            } else {
                return false;
            }
            ++i;
        }
        if (i < chars.length) {
            if ((chars[i] >= '0') && (chars[i] <= '9')) {
                return true;
            }
            if ((chars[i] == 'e') || (chars[i] == 'E')) {
                return false;
            }
            if (chars[i] == '.') {
                if ((hasDecPoint) || (hasExp)) {
                    return false;
                }

                return foundDigit;
            }
            if ((!(allowSigns))
                    && (((chars[i] == 'd') || (chars[i] == 'D') || (chars[i] == 'f') || (chars[i] == 'F')))) {
                return foundDigit;
            }
            if ((chars[i] == 'l') || (chars[i] == 'L')) {
                return ((foundDigit) && (!(hasExp)) && (!(hasDecPoint)));
            }

            return false;
        }

        return ((!(allowSigns)) && (foundDigit));
    }
}
