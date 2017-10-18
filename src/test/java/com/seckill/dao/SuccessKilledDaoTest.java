package com.seckill.dao;

import com.seckill.entity.SuccessKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by Roll on 2017/10/18.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/applicationContext.xml"})
public class SuccessKilledDaoTest {

    @Resource
    private SuccessKilledDao successKilledDao;

    @Test
    public void insertSuccessKilled() throws Exception {
        /**
         *
         *   第一次：
         *      insertCount = 1
         *
         *  第二次：
         *      insertCount = 0
         *
         *  在sql语句中，添加了ignore
         *
         */
        long id = 1000;
        long phone = 1736234533;
        int insertCount = successKilledDao.insertSuccessKilled(id, phone);
        System.out.println("insertCount = "+insertCount);
    }

    @Test
    public void queryByIdWithSeckill() throws Exception {

        long id = 1000;
        long phone = 1736234533;
        SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(id, phone);
        System.out.println(successKilled);

        System.out.println(successKilled.getSeckill());

        /**
         *
         SuccessKilled{
         seckillId=1000,
         userPhone=1736234533,
         state=0,
         createTime=Wed Oct 18 15:46:06 CST 2017}

         Seckill{
         esckillId=0,
         name='1000元秒杀iPhone 8',
         number=100,
         startTime=Wed Oct 18 15:38:14 CST 2017,
         endTime=Fri Oct 20 00:00:00 CST 2017,
         createTime=Wed Oct 18 09:40:59 CST 2017}
         */
    }

}