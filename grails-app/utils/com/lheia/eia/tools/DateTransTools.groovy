package com.lheia.eia.tools

import com.lheia.eia.config.EiaWorkDayConfig

import java.text.ParseException
import java.text.SimpleDateFormat

/**
 * Created by tianlei on 2018/03/27.
 */
class DateTransTools {
    static String getYearMonth(Date dateD) {
        String str = new java.text.SimpleDateFormat("yyyy年MM月dd日").format(dateD);
        String year = str.substring(0, 4); //取年
        String month = str.substring(str.indexOf("年") + 1, str.indexOf("月")); //取月
//        String day = str.sbstring(str.indexOf("月")+1,str.indexOf("日")); //取日
        return year + month;
    }

    static String getYear(Date dateD) {
        String str = new java.text.SimpleDateFormat("yyyy年MM月dd日").format(dateD);
        String year = str.substring(0, 4); //取年
        return year;
    }

    static String getFormatDateS(Date dateD) {
        String dateF = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dateD);
        return dateF;
    }

    static String getFormatDateS(Date dateD, String format) {
        String dateF = new java.text.SimpleDateFormat(format).format(dateD);
        return dateF;
    }

    static Date getFormatDateD(String dateD) {
        Date dateF = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dateF = sdf.parse(dateD);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return dateF;
    }

    static Date getFormatDateD(String dateD, String pattern) {
        Date dateF = null;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            dateF = sdf.parse(dateD);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return dateF;
    }

    static String getFormatLocalDate(Date UtcDate) {
        def df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"); //世界协调时间转地方时间
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        def localTime = df.parse(UtcDate)
        return localTime
    }

    static final String big = "零壹贰叁肆伍陆柒捌玖";     //大写
    static final String[] units = ["仟佰拾", "角分"];  //单位

    /**
     * 合同金额转换为大写
     * @param contractMoney
     * @return
     */
    static String transformMoney(Double contractMoney) {
        String moneyStr = getDecimalStr(contractMoney)
        String money = transform(moneyStr)
        return money
    }

    /**
     * 双精度浮点数转换成字符串
     */
    static String getDecimalStr(double d) {
        String str = new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP).toString()
        /** 如果结果是整数，则去掉尾巴 */
        if (str.endsWith(".00")) {
            str = str.replace(".00", "")
        }
        return str
    }

    /**
     * 金额转换成大字
     */
    static String transform(String moneyStr) {
        /** 区别整数、小数部分 */
        String[] parts = moneyStr.split("\\.");
        String result = "";

        /** 处理整数部分,若金额太大,不能处理整数部分超过15位的金额！ */
        int length = parts[0].length();
        if (length > 15) {
            return "";
        }
        String intPart = parts[0];

        /** 填充到16位，因为是分4组，每组4个数字 */
        while (intPart.length() < 16) {
            intPart = '0' + intPart;
        }
        /** 共分四组,右起四位一组,例如：0001,2003,0030,3400 */
        String[] groups = new String[4];
        for (int i = 0; i < groups.length; i++) {
            int start = intPart.length() - (i + 1) * 4;
            int end = intPart.length() - i * 4;
            groups[i] = intPart.substring(start, end);
            groups[i] = transformGroup(groups[i]);
        }

        /** 对这四组结果从高位到低位拼接起来，规则：[组4]万[组3]亿[组2]万[组1]元 */
        for (int i = groups.length - 1; i >= 0; i--) {
            /** 第四组：万亿级 */
            if (i == 3) {
                if (!"零".equals(groups[i])) {
                    result += groups[i] + "万";
                }
                /** 第三组：亿级 */
            } else if (i == 2) {
                if (!"零".equals(groups[i])) {
                    result += groups[i] + "亿";
                } else {
                    if (result.length() > 0) {
                        result += "亿";
                    }
                }
                /** 第二组：万级 */
            } else if (i == 1) {
                if (!"零".equals(groups[i])) {
                    result += groups[i] + "万";
                } else if (!groups[i].startsWith("零")) {
                    result += groups[i];
                }
                /** 第一组：千级 */
            } else {
                if (!"零".equals(groups[i]) || result.length() == 0) {
                    result += groups[i];
                }
                result += "元";
            }
        }
        if (!"零元".equals(result) && result.startsWith("零")) {
            /** 最前面的可能出现的“零”去掉 */
            result = result.substring(1, result.length());
        }

        /** 处理小数部分 */
        if (parts.length == 2) {
            /** 小数部分 */
            String decimalPart = parts[1];
            for (int i = 0; i < decimalPart.length(); i++) {
                /** 提取数字，左起 */
                int num = Integer.valueOf(decimalPart.charAt(i) + "");
                /** 数字变大写加上单位 */
                result += big.charAt(num) + "" + units[1].charAt(i);
            }
            /** 去掉"零角"的"角" */
            result = result.replace("零角", "零");
            /** 去掉"零分" */
            result = result.replace("零分", "");
        } else {
            /** 没有小数部分，则加上“整” */
            result += "整";
        }
        return result;
    }

    /**
     * 处理整数部分的组，右起每四位是一组
     * @param group 四位数字字符串
     */
    static String transformGroup(String group) {
        String result = "";
        int length = group.length();
        for (int i = 0; i < length; i++) {
            /** 单个数字，左起 */
            int digit = Integer.valueOf(group.charAt(i).toString());
            /** 单位 */
            String unit = "";
            if (i != length - 1) {
                unit = units[0].charAt(i).toString() + "";
            }
            /** 数字变大写加上单位 */
            result += big.charAt(digit).toString() + unit;
        }

        result = result.replace("零仟", "零");
        result = result.replace("零佰", "零");
        result = result.replace("零拾", "零");

        while (result.contains("零零")) {
            /** 如果有“零零”则变成一个“零” */
            result = result.replace("零零", "零");
        }

        if (!"零".equals(result) && result.endsWith("零")) {
            /** 最未尾的可能出现的“零”去掉 */
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }

    /**
     * 计算工作日
     * @params startDate 开始计算的日期
     * @params endDate 结束计算的日期
     */
    public static int getWorkDays(Date startDate, Date endDate) {
        /** 工作日天数 */
        def workDays = 0
        /** 判断是否是工作日 */
        def ifWorkDay
        Calendar calendar = Calendar.getInstance()
        calendar.setTime(startDate)
        while (calendar.getTime().compareTo(endDate) < 0) {
            calendar.add(Calendar.DAY_OF_MONTH, 1)
            ifWorkDay = EiaWorkDayConfig.findByDay(calendar.getTime())?.ifWorkDay
            if (ifWorkDay == 0) {
                workDays += 1
            }
        }
        return workDays
    }

    /**
     * 计算工作日期
     * @params startDate 开始计算的日期
     * @params workDay 天数
     */
     static Date getWorkDate(Date startDate, int workDayNum) {
   /*      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
         startDate = sdf.parse("2018-08-14")*/
        /** 判断是否是工作日 */
        def ifWorkDay
        Calendar calendar = Calendar.getInstance()
        calendar.setTime(startDate)
        while (workDayNum > 0) {
            calendar.add(Calendar.DAY_OF_MONTH, 1)
            ifWorkDay = EiaWorkDayConfig.findByDay(calendar.getTime())?.ifWorkDay
            if (ifWorkDay == 0) {
                workDayNum --
            }
        }
        return calendar.getTime()
    }

    /**
     * 根据小写数字格式的日期转换成大写格式的日期
     * @param date
     * @return
     */
    static String getUpperDate(String date) {
        String result = "";
        String[] cnDate = "〇一二三四五六七八九".toCharArray();
        String ten = "十";
        String[] dateStr = date.split("-");
        for (int i = 0; i < dateStr.length; i++) {
            for (int j = 0; j < dateStr[i].length(); j++) {
                String charStr = dateStr[i];
                String str = String.valueOf(charStr.charAt(j));
                if (charStr.length() == 2) {
                    if (charStr.equals("10")) {
                        result += ten;
                        break;
                    } else {
                        if (j == 0) {
                            if (charStr.charAt(j) == '1')
                                result += ten;
                            else if (charStr.charAt(j) == '0')
                                result += "";
                            else
                                result += cnDate[Integer.parseInt(str)] + ten;
                        }
                        if (j == 1) {
                            if (charStr.charAt(j) == '0')
                                result += "";
                            else
                                result += cnDate[Integer.parseInt(str)];
                        }
                    }
                } else {
                    result += cnDate[Integer.parseInt(str)];
                }
            }
            if (i == 0) {
                result += "年";
                continue;
            }
            if (i == 1) {
                result += "月";
                continue;
            }
            if (i == 2) {
                result += "日";
                continue;
            }
        }
        return result;
    }
}