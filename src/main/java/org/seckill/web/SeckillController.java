package org.seckill.web;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.dto.SeckillResult;
import org.seckill.entity.Seckill;
import org.seckill.enums.SeckillStateEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by FHY on 2019/3/4.
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeckillService seckillService;

    @RequestMapping(value = "/list",
            method = RequestMethod.GET)
    public String list(Model model){
        List<Seckill> list = seckillService.getSeckillList();
        model.addAttribute("list",list);
        return "list";
    }

    @RequestMapping(value = "/{seckillId}/detail",
            method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId, Model model){
        if(seckillId == null){
            return "redirect:/seckill/list";
        }
        Seckill seckill = seckillService.getById(seckillId);
        if(seckill == null){
            return "forward:/seckill/list";
        }
        model.addAttribute("seckill", seckill);
        return "detail";
    }

    //ajax接口，返回类型json
    @RequestMapping(value = "/{seckillId}/exposer",
            method = RequestMethod.POST,
            produces =  {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId){
        SeckillResult<Exposer> result;
        try{
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<Exposer>(true, exposer);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            result = new SeckillResult<Exposer>(false, e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/{seckillId}/{md5}/execution",
                    method = RequestMethod.POST,
                    produces = {"application/json;charset-UTF-8"})
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
                                                   @PathVariable("md5") String md5,
                                                   @CookieValue(value = "killPhone", required = false) Long phone){
        if(phone == null){
            return new SeckillResult<SeckillExecution>(false, "未注册");
        }
        SeckillResult<SeckillExecution> result;
        try{
            //使用存储过程调用秒杀逻辑，优化时修改的
            SeckillExecution execution = seckillService.executeSeckillProcedure(seckillId, phone, md5);
            return new SeckillResult<SeckillExecution>(true,execution);
        }catch (RepeatKillException e){
            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStateEnum.REPEAT_KILL);
            return new SeckillResult<SeckillExecution>(true, execution);
        }catch (SeckillCloseException e){
            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStateEnum.END);
            return new SeckillResult<SeckillExecution>(true, execution);

        }catch(Exception e){
            logger.error(e.getMessage(), e);
            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStateEnum.INNER_ERROR);
            return new SeckillResult<SeckillExecution>(true, execution);
        }

    }

    //获取系统时间
    @RequestMapping(value = "/time/now", method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Long> time(){
        Date now = new Date();
        return new SeckillResult(true, now.getTime());
    }
}
