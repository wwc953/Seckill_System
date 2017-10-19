package com.seckill.service;

import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.entity.Seckill;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Roll on 2017/10/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/applicationContext.xml"})
public class SeckillServiceTest {
    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() throws Exception {

        List<Seckill> list = seckillService.getSeckillList();
        logger.info("list={}", list);

    }

    @Test
    public void getById() throws Exception {

        long id = 1000;
        Seckill seckill = seckillService.getById(id);
        logger.info("seckill={}", seckill);
    }

    @Test
    public void exportSeckillUrl() throws Exception {
        long id = 1000;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        logger.info("exposer={}", exposer);

        /**
         *Exposer{
         * exposed=true,
         * md5='a1ee393a82ff8e286ec34077a1858044',
         * seckillId=1000, now=0, start=0, end=0}
         *
         */
    }

    @Test
    public void executeSeckill() throws Exception {
        long id = 1000;
        long phone = 1706234533L;
        String md5 = "a1ee393a82ff8e286ec34077a1858044";

        try {
            SeckillExecution execution = seckillService.executeSeckill(id, phone, md5);
            logger.info("execution={}", execution);

        } catch (RepeatKillException e) {
            logger.error(e.getMessage());
        } catch (SeckillCloseException e) {
            logger.error(e.getMessage());
        }
    }

}