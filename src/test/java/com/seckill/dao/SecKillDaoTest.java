package com.seckill.dao;

import com.seckill.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by Roll on 2017/10/18.
 * 配置junit和spring整合，为了junit启动时，加载spring IOC容器
 *
 * spring-test
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件的位置
@ContextConfiguration({"classpath:spring/applicationContext.xml"})
public class SecKillDaoTest {

    //注入DAO实现类依赖

    @Resource
    private SecKillDao secKillDao;

    @Test
    public void queryById() throws Exception {
        long id=1000;
        Seckill secKill= (Seckill) secKillDao.queryById(id);
        System.out.println(secKill.getName());
        System.out.println(secKill);

        /**
         *  1000元秒杀iPhone 8
         *  Seckill{
         *  esckillId=0,
         *  name='1000元秒杀iPhone 8',
         *  number=100,
         *  startTime=Tue Oct 17 00:00:00 CST 2017,
         *  endTime=Fri Oct 20 00:00:00 CST 2017,
         *  createTime=Wed Oct 18 09:40:59 CST 2017}
         */
    }

    @Test
    public void queryAll() throws Exception {

        /**
         * List<Seckill> queryAll(int offset, int limit);
         *
         * java 没有保存形参的记录：queryAll(int offset, int limit) -> queryAll(arg0, arg1);
         *
         *  解决方案:
         *      使用MyBatis的@Param注解
         *
         */
        List<Seckill> seckills = secKillDao.queryAll(0,10);
        for (Seckill seckill:seckills) {
            System.out.println(seckill);
        }

    }

    @Test
    public void reduceNumber() throws Exception {

        /**
         *Preparing:
         *      UPDATE seckill SET number = number - 1 WHERE seckill_id = ? AND start_time <= ? AND end_time >= ? AND number > 0;
         ==> Parameters: 1000(Long), 2017-10-18 15:37:30.526(Timestamp), 2017-10-18 15:37:30.526(Timestamp)
         <==    Updates: 1
         */

        Date killTime = new Date();
        int updateCount = secKillDao.reduceNumber(1000, killTime);
        System.out.println(updateCount);

    }



}