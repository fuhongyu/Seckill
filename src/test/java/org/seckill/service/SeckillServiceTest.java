package org.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by FHY on 2019/3/4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})
public class SeckillServiceTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> list = seckillService.getSeckillList();
        logger.info("list={}",list);
    }

    @Test
    public void getById() throws Exception {
        long id = 1001;
        Seckill seckill = seckillService.getById(id);
        logger.info("seckill={}", seckill);
    }

    //测试代码完整逻辑，注意重复秒杀逻辑
    @Test
    public void testSeckillLogic() throws Exception {
        long id = 1001;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        if(exposer.isExposed()){
            logger.info("exposer={}",exposer);
            long phone = 14563846382L;
            String md5 = exposer.getMd5();
            try{
                SeckillExecution execution = seckillService.executeSeckill(id, phone, md5);
                logger.info("result={}",execution);
            }catch (RepeatKillException e){
                logger.error(e.getMessage(),e);
            }catch (SeckillCloseException e){
                logger.error(e.getMessage(),e);
            }
        }else{
            //秒杀未开启
            logger.warn("exposer={}", exposer);
        }
    }

    @Test
    public void executeSeckillProcedure(){
        long seckillId = 1003;
        long phone = 15674424380L;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        if(exposer.isExposed()){
            String md5 = exposer.getMd5();
            SeckillExecution execution  = seckillService.executeSeckillProcedure(seckillId,phone,md5);
            logger.info(execution.getStateInfo());
            System.out.println(execution.getStateInfo());
        }
    }



//    @Test
//    public void executeSeckill() throws Exception {
//        long id = 1001;
//        long phone = 14563846382L;
//        String md5 = "e39f6b0434ebd7dcb91ae910e5623de4";
//        try{
//            SeckillExecution execution = seckillService.executeSeckill(id, phone, md5);
//            logger.info("result={}",execution);
//        }catch (RepeatKillException e){
//            logger.error(e.getMessage(),e);
//        }catch (SeckillCloseException e){
//            logger.error(e.getMessage(),e);
//        }
//    }
}