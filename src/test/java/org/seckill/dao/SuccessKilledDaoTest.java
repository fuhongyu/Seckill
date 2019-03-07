package org.seckill.dao;

import jdk.nashorn.internal.ir.annotations.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by FHY on 2019/3/3.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit Spring 配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {
    @Resource
    private SuccessKilledDao successKilledDao;

    @Test
    public void insertSuccessKilled() throws Exception {
        successKilledDao.insertSuccessKilled(1001L, 15634408337L);
    }

    @Test
    public void queryByIdWithSeckill() throws Exception {
        SuccessKilled successKilled =  successKilledDao.queryByIdWithSeckill(1001L, 15634408337L);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());
    }
}