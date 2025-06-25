package com.wanyunfei.demo.Utils;

import java.io.*;
import java.nio.file.Files;

public class NativeInterface {
    public static native void switchInputMethodToChinese();
    public static native void switchInputMethodToEnglish();

    // 加载native DLL包
    static {
        String dllResource = "/native/nativeInterface.dll";
        try(InputStream in = NativeInterface.class.getResourceAsStream(dllResource)) {
            // 路径：resources/native/nativeInterface.dll
            if (in == null) {
                throw new FileNotFoundException("无法找到 DLL 资源: " + dllResource);
            }
            // 创建临时文件
            File tempDll = Files.createTempFile("nativeInterface", ".dll").toFile();
            tempDll.deleteOnExit();

            try (FileOutputStream out = new FileOutputStream(tempDll)) {
                byte[] buffer = new byte[4096];
                int len;
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
            }

            // 加载 DLL
            System.load(tempDll.getAbsolutePath());

        } catch (IOException e) {
            throw new RuntimeException("加载 nativeInterface.dll 失败", e);
        }
    }
}
