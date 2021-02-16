package com.flash.sale.service;

import com.flash.sale.dao.OrderDao;
import com.flash.sale.domain.MiaoshaOrder;
import com.flash.sale.domain.MiaoshaUser;
import com.flash.sale.domain.OrderInfo;
import com.flash.sale.redis.OrderKey;
import com.flash.sale.redis.RedisService;
import com.flash.sale.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class OrderService {

  @Autowired
  private OrderDao orderDao;

  @Autowired
  private RedisService redisService;

  public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(long userId, long goodsId) {
    //return orderDao.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
    return redisService.get(OrderKey.getMiaoshaOrderByUidGid, "" + userId + "_" + goodsId, MiaoshaOrder.class);
  }

  public OrderInfo getOrderById(long orderId) {
    return orderDao.getOrderById(orderId);
  }


  @Transactional
  public OrderInfo createOrder(MiaoshaUser user, GoodsVo goods) {
    OrderInfo orderInfo = new OrderInfo();
    orderInfo.setCreateDate(new Date());
    orderInfo.setDeliveryAddrId(0L);
    orderInfo.setGoodsCount(1);
    orderInfo.setGoodsId(goods.getId());
    orderInfo.setGoodsName(goods.getGoodsName());
    orderInfo.setGoodsPrice(goods.getMiaoshaPrice());
    orderInfo.setOrderChannel(1);
    orderInfo.setStatus(0);
    orderInfo.setUserId(user.getId());
    orderDao.insert(orderInfo);
    MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
    miaoshaOrder.setGoodsId(goods.getId());
    miaoshaOrder.setOrderId(orderInfo.getId());
    miaoshaOrder.setUserId(user.getId());
    orderDao.insertMiaoshaOrder(miaoshaOrder);

    redisService.set(OrderKey.getMiaoshaOrderByUidGid, "" + user.getId() + "_" + goods.getId(), miaoshaOrder);

    return orderInfo;
  }

  public void deleteOrders() {
    orderDao.deleteOrders();
    orderDao.deleteMiaoshaOrders();
  }

}
