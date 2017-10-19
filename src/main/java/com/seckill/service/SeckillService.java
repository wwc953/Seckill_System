package com.seckill.service;

import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.entity.Seckill;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.exception.SeckillException;

import java.util.List;

/**
 * 业务接口：站在"使用者"角度设计接口
 * 三个方面：方法定义粒度、参数、返回类型
 * <p>
 * Created by Roll on 2017/10/18.
 */
public interface SeckillService {

    /**
     * 查询所有秒杀商品
     *
     * @return
     */
    public List<Seckill> getSeckillList();

    /**
     * 查询单个秒杀商品
     *
     * @param seckillId
     * @return
     */
    public Seckill getById(long seckillId);

    /**
     * 输出秒杀接口地址，
     * 否则输出系统时间和秒杀时间
     *
     * @param seckillId
     * @return
     */
    public Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀操作
     *
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException, SeckillCloseException, RepeatKillException;

}
