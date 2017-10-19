package com.seckill.exception;

/**
 * 重复秒杀异常(运行期异常)
 *
 * Created by Roll on 2017/10/18.
 */
public class RepeatKillException extends RuntimeException{

    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
