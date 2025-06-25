package com.wanyunfei.demo.Services;

import java.util.*;
import com.ibm.icu.impl.Pair;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.Service;
import com.wanyunfei.demo.Enums.Languages;
import com.wanyunfei.demo.Utils.NativeInterface;

@Service
public final class InputStatus implements IInputStatus {
    private final Map<Pair<Languages, Languages>, Runnable> switchMap = new HashMap<>();

    public InputStatus() {
        for (Languages cur : Languages.values()) {
            for (Languages next : Languages.values()) {
                switchMap.put(Pair.of(cur, next), createSwitchLogic(cur, next));
            }
        }
    }

    private Runnable createSwitchLogic(Languages cur, Languages next) {
        if (cur == Languages.ENGLISH) {
            return NativeInterface::switchInputMethodToEnglish;
        } else if (cur == Languages.CHINESE) {
            return NativeInterface::switchInputMethodToChinese;
        } else if (cur == Languages.OTHER && next == Languages.ENGLISH) {
            return NativeInterface::switchInputMethodToEnglish;
        } else if (cur == Languages.OTHER && next == Languages.CHINESE) {
            return NativeInterface::switchInputMethodToChinese;
        } else {
            return NativeInterface::switchInputMethodToEnglish;
        }
    }

    public Runnable getSwitchMethod(Languages cur, Languages next) {
        return switchMap.getOrDefault(Pair.of(cur, next), () -> {
            throw new IllegalArgumentException("Invalid input method switch");
        });
    }

    public static InputStatus getInstance(){
        return ApplicationManager.getApplication().getService(InputStatus.class);
    }
}
