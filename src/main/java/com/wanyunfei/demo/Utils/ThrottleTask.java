package com.wanyunfei.demo.Utils;

import java.util.Timer;
import java.util.TimerTask;

public class ThrottleTask {

    private Timer timer;
    private Long delay;
    private Runnable runnable;
    private boolean needWait=false;

    public void build(Runnable runnable, Long delay){
        this.timer=new Timer();
        this.delay=delay;
        this.runnable=runnable;
    }

    public void taskRun(){
        //如果 needWait 为 false,结果取反，表达式为 true。执行 if 语句
        if(!needWait){
            //设置为 true,这样下次就不会再执行
            needWait=true;
            //执行节流方法
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    //执行完成,设置为 false,让下次操作再进入 if 语句中
                    needWait=false;
                    //开启多线程执行 run() 方法
                    runnable.run();
                }
            }, delay);
        }
    }
}