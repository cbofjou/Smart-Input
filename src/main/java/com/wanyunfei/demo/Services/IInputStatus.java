package com.wanyunfei.demo.Services;

import com.wanyunfei.demo.Enums.Languages;

public interface IInputStatus {
    Runnable getSwitchMethod(Languages cur, Languages next);
}
