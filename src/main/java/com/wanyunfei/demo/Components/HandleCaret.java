package com.wanyunfei.demo.Components;

import com.intellij.openapi.editor.event.CaretEvent;
import com.wanyunfei.demo.Enums.Languages;
import com.wanyunfei.demo.Services.InputStatus;
import com.wanyunfei.demo.Utils.JudgeLanguage;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.wanyunfei.demo.Controllers.GlobalCaretListenerService;

public class HandleCaret {
    private static final Logger logger = Logger.getInstance(GlobalCaretListenerService.class);

    // 切换输入法
    public static void SwitchInputMethod(CaretEvent event) {
        // ApplicationManager.getApplication().runReadAction(()->{}) 线程安全的执行读Document或者PSI操作
        ApplicationManager.getApplication().runReadAction(() -> {
            try {
                var editor = event.getEditor();
                // 获取编辑器文档
                var document = editor.getDocument();
                // 获取光标新的的逻辑位置
                int offset = editor.logicalPositionToOffset(event.getNewPosition());

                // 获得前后字符语言
                var curLanguage = Languages.OTHER;
                if(offset < document.getTextLength()) {
                    var curChar = document.getCharsSequence().charAt(offset);
                    curLanguage = JudgeLanguage.judgeLanguage(curChar);
                }
                var nextLanguage = Languages.OTHER;
                if(offset + 1 < document.getTextLength()) {
                    var nextChar = document.getCharsSequence().charAt(offset + 1);
                    nextLanguage = JudgeLanguage.judgeLanguage(nextChar);
                }

                // 切换输入法
                var inputStatus = InputStatus.getInstance();
                var inputMethod = inputStatus.getSwitchMethod(curLanguage, nextLanguage);
                inputMethod.run();
            } catch (IllegalArgumentException e){
                logger.error("切换输入法时出错！" + e.getMessage());
            } catch (Exception e) {
                logger.error("处理鼠标点击事件时出错", e);
            }
        });
    }
}