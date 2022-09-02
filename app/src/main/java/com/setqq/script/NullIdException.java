package com.setqq.script;

public class NullIdException extends Exception {
    public NullIdException(String str) {
        super("lua空id异常,脚本中未配置识别id:" + str);
    }
}
