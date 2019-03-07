package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 配置Spring和junit整合，junit启动时加载Spring IOC容器
 * Spring-test, junit
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit Spring 配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

    //注入Dao实现类依赖

    @Resource
    private SeckillDao seckillDao;

    @Test
    public void queryById() throws Exception {
        long id = 1001;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill.getCreateTime());
    }

    @Test
    public void queryAll() throws Exception {
        List<Seckill> seckills = seckillDao.queryAll(0,100);
        for(Seckill each: seckills){
            System.out.println(each);
        }
    }
    @Test
    public void reduceNumber() throws Exception {
        Date killTime = new Date();
        seckillDao.reduceNumber(1001L, killTime);

    }


}