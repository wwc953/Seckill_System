package com.seckill.exception;

/**
 * 秒杀关闭异常(运行期异常)
 * <p>
 * Created by Roll on 2017/10/18.
 */
public class SeckillCloseException extends RuntimeException {

    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
