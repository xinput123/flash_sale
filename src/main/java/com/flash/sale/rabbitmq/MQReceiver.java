package com.flash.sale.rabbitmq;

import com.flash.sale.domain.MiaoshaOrder;
import com.flash.sale.domain.MiaoshaUser;
import com.flash.sale.redis.RedisService;
import com.flash.sale.service.GoodsService;
import com.flash.sale.service.MiaoshaService;
import com.flash.sale.service.OrderService;
import com.flash.sale.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQReceiver {

  private static Logger log = LoggerFactory.getLogger(MQReceiver.class);

  @Autowired
  private GoodsService goodsService;

  @Autowired
  private OrderService orderService;

  @Autowired
  private MiaoshaService miaoshaService;

  @RabbitListener(queues = MQConfig.MIAOSHA_QUEUE)
  public void receive(String message) {
    log.info("receive message:" + message);
    MiaoshaMessage mm = RedisService.stringToBean(message, MiaoshaMessage.class);
    MiaoshaUser user = mm.getUser();
    long goodsId = mm.getGoodsId();

    GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
    int stock = goods.getStockCount();
    if (stock <= 0) {
      return;
    }
    //判断是否已经秒杀到了
    MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
    if (order != null) {
      return;
    }
    //减库存 下订单 写入秒杀订单
    miaoshaService.miaosha(user, goods);
  }
}
