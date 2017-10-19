package com.seckill.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.seckill.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by Roll on 2017/10/20.
 */
public class RedisDao {

    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    //类似数据库连接池的pool
    private JedisPool jedisPool;

    public RedisDao(String ip, int port) {
        jedisPool = new JedisPool(ip, port);
    }

    private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);

    /**
     * 从Redis中取出Seckill，并进行反序列化
     *
     * @param seckillId
     * @return
     */
    public Seckill getSeckill(long seckillId) {
        //redis操作逻辑
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckill:" + seckillId;
                //内部实现没有序列化
                //get -> byte[] -> 反序列化 -> Object(Seckill)
                //采用自定义序列化
                //protostuff:序列化对象必须包含get、set方法
                byte[] bytes = jedis.get(key.getBytes());
                if (bytes != null) {//获取到
                    Seckill seckill = schema.newMessage();//new了一个空对象，需要传入下面的方法中
                    ProtobufIOUtil.mergeFrom(bytes, seckill, schema);
                    //Seckill被反序列
                    return seckill;
                }

            } finally {
                jedis.close();
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 将Seckill放入redis，并使用protostuff进行序列化
     *
     * @param seckill
     * @return
     */
    public String putSeckill(Seckill seckill) {
        //set -> Object(Seckill) -> 序列化 -> byte[]
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckill:" + seckill.getSeckillId();

                byte[] bytes = ProtobufIOUtil.toByteArray(seckill, schema,
                        LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));

                //超时缓存
                int timeout = 60 * 60;//1小时
                String result = jedis.setex(key.getBytes(), timeout, bytes);
                return result;

            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

}
