package com.wanyunfei.demo.Controllers;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.ProjectActivity;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ProjectStart implements ProjectActivity {
    @Nullable
    @Override
    public Object execute(@NotNull Project project, @NotNull Continuation<? super Unit> continuation) {

        @SuppressWarnings("unused")
        GlobalCaretListenerService service = GlobalCaretListenerService.getInstance();

        // 插件启动成功，打印信息
        System.out.println("=================================");
        System.out.println("插件启动成功！");
        System.out.println("项目名称: " + project.getName());
        System.out.println("插件配置正确！");
        System.out.println("=================================");

        return Unit.INSTANCE;
    }
}

