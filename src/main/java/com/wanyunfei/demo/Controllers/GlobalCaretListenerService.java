package com.wanyunfei.demo.Controllers;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.Service;
import com.wanyunfei.demo.Utils.ThrottleTask;
import com.intellij.openapi.editor.event.CaretEvent;
import com.wanyunfei.demo.Components.HandleCaret;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.event.CaretListener;
import com.intellij.openapi.editor.EditorFactory;
import org.jetbrains.annotations.NotNull;

@Service(Service.Level.APP)
public final class GlobalCaretListenerService implements Disposable {
    private static final Logger logger = Logger.getInstance(GlobalCaretListenerService.class);
    private final ThrottleTask throttleTask = new ThrottleTask();

    public static GlobalCaretListenerService getInstance() {
        return ApplicationManager.getApplication().getService(GlobalCaretListenerService.class);
    }

    public GlobalCaretListenerService() {
        registerCaretListener();
        logger.info("全局鼠标监听器已注册...");
    }

    // 注册光标监听器
    private void registerCaretListener() {
        EditorFactory.getInstance().getEventMulticaster()
            .addCaretListener(new CaretListener() {
                @Override
                public void caretPositionChanged(@NotNull CaretEvent event) {
                    throttleTask.build(() -> HandleCaret.SwitchInputMethod(event), 1000L);
                    throttleTask.taskRun();
                }
            },
            // 绑定到监听器上，当监听器
            this
        );
    }

    @Override
    public void dispose() {
        // 应用关闭时自动清理
    }
}

