package com.seckill.dao.cache;

import com.seckill.dao.SecKillDao;
import com.seckill.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Roll on 2017/10/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/applicationContext.xml"})
public class RedisDaoTest {

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private SecKillDao secKillDao;

    private long id = 1001;

    @Test
    public void testSeckill() throws Exception {
        //get and put
        //redis 取数据
        Seckill seckill = redisDao.getSeckill(id);
        if (seckill == null) {
            //为空，从数据库取数据
            seckill = secKillDao.queryById(id);
            if (seckill != null) {
                //放进redis
                String result = redisDao.putSeckill(seckill);
                System.out.println(result);

                seckill = redisDao.getSeckill(id);
                System.out.println(seckill);
            }
        }
    }


}