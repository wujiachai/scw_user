package com.offcn.order.service.impl;

import com.offcn.dateutile.AppDateUtils;
import com.offcn.dycommon.response.AppResponse;
import com.offcn.order.emus.OrderStatusEnum;
import com.offcn.order.feign.FindReturnList;
import com.offcn.order.mapper.TOrderMapper;
import com.offcn.order.pojo.TOrder;
import com.offcn.order.pojo.TReturn;
import com.offcn.order.service.OrderService;
import com.offcn.order.vo.req.OrderinfoSubmitvo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl  implements OrderService {
    @Autowired
    private FindReturnList findReturnList;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private TOrderMapper tOrderMapper;

    @Override
    public TOrder saveOrder(OrderinfoSubmitvo orderinfoSubmitvo) {
        //创建订单对象复制属性设置属性
        TOrder tOrder = new TOrder();
        BeanUtils.copyProperties(orderinfoSubmitvo,tOrder);
        String memberid = stringRedisTemplate.opsForValue().get(orderinfoSubmitvo.getAccessToken());
        tOrder.setMemberid(Integer.parseInt(memberid));
        String s = UUID.randomUUID().toString();
        tOrder.setOrdernum(s);//订单编号
        tOrder.setCreatedate(AppDateUtils.getFormatTime());//创建时间
        tOrder.setStatus(OrderStatusEnum.UNPAY.getCode() + "");          //支付状态  未支付
        tOrder.setInvoice(orderinfoSubmitvo.getInvoice().toString());
        //远程服务调用项目回报信息
        AppResponse<List<TReturn>> returnList = findReturnList.findReturnList(orderinfoSubmitvo.getProjectid());
        List<TReturn> data = returnList.getData();

        if(!CollectionUtils.isEmpty(data)){
            //默认取第一个
            TReturn tReturn = data.get(0);
            //计算金额
            Integer money= tReturn.getSupportmoney()*orderinfoSubmitvo.getRtncount()+tReturn.getFreight();
            tOrder.setMoney(money);
        }
        //保存订单
        tOrderMapper.insertSelective(tOrder);
        return tOrder;
    }
}
