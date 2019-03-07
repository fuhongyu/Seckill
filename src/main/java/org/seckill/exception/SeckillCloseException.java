package org.seckill.exception;

/**
 * 秒杀关闭异常
 * Created by FHY on 2019/3/4.
 */
public class SeckillCloseException extends RuntimeException{
    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
