package com.seckill.dao;

import com.seckill.entity.Seckill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Roll on 2017/10/18.
 */
public interface SecKillDao {
    /**
     * 生成测试类：选中接口名，快捷键 Ctrl+Shift+T
     */

    /**
     * 减库存
     *
     * @param seckillId
     * @param killTime
     * @return 如果影响行数>1，表示更新的记录行数
     */
    public int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);


    /**
     * 根据id查询秒杀商品
     *
     * @param seckillId
     * @return
     */
    public Seckill queryById(long seckillId);


    /**
     * 根据偏移量查询秒杀商品列表
     *
     * @param offset
     * @param limit
     * @return
     */
    public List<Seckill> queryAll(@Param("offset") int offset,@Param("limit") int limit);


    /**
     * 存储过程执行秒杀
     *
     * @param paramMap
     */
    public void killByProcedure(Map<String,Object> paramMap);


}
