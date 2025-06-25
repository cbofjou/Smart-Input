package com.wanyunfei.demo.Utils;

import com.wanyunfei.demo.Enums.Languages;
import java.lang.String;

public class JudgeLanguage {
    public static Languages judgeLanguage(char ch) {
        if(isChineseInput(ch)) return Languages.CHINESE;
        else if(isEnglishInput(ch)) return Languages.ENGLISH;
        else return Languages.OTHER;
    }

    // 判断是否为中文输入法产生的字符
    private static boolean isChineseInput(char ch) {
        return isChinese(ch) || isChinesePunctuation(ch);
    }

    // 判断是否为英文输入法产生的字符
    private static boolean isEnglishInput(char ch) {
        return isEnglish(ch) || isEnglishPunctuation(ch) || Character.isDigit(ch);
    }

    // 是否为汉字
    private static boolean isChinese(char ch) {
        int charCode = String.valueOf(ch).codePointAt(0);

        if ((charCode >= 0x4E00 && charCode <= 0x9FFF) ||  // CJK统一汉字
                (charCode >= 0x3400 && charCode <= 0x4DBF) ||  // CJK扩展A
                (charCode >= 0x20000 && charCode <= 0x2A6DF) || // CJK扩展B
                (charCode >= 0x2A700 && charCode <= 0x2B73F) || // CJK扩展C
                (charCode >= 0x2B740 && charCode <= 0x2B81F) || // CJK扩展D
                (charCode >= 0x2B820 && charCode <= 0x2CEAF)) { // CJK扩展E
            return true;
        }

        return (charCode >= 0x3000 && charCode <= 0x303F) ||  // CJK符号和标点
                (charCode >= 0xFF00 && charCode <= 0xFFEF);   // 全角ASCII、全角标点
    }

    // 更精确的中文标点判断
    private static boolean isChinesePunctuation(char ch) {
        String chinesePunctuation = "，。！？；：\"\"''（）【】《》〈〉「」『』、·…—";
        return chinesePunctuation.indexOf(ch) >= 0;
    }

    // 判断字符是否为英文字母
    private static boolean isEnglish(char ch) {
        return (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z');
    }

    // 英文标点
    private static boolean isEnglishPunctuation(char ch) {
        String englishPunctuation = " ,.!?;:\"'()[]<>{}/-_=+*&^%$#@~`|\\";
        return englishPunctuation.indexOf(ch) >= 0;
    }
}
