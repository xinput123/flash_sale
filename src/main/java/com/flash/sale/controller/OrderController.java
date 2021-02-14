package com.flash.sale.controller;

import com.flash.sale.domain.MiaoshaUser;
import com.flash.sale.domain.OrderInfo;
import com.flash.sale.redis.RedisService;
import com.flash.sale.result.CodeMsg;
import com.flash.sale.result.Result;
import com.flash.sale.service.GoodsService;
import com.flash.sale.service.MiaoshaUserService;
import com.flash.sale.service.OrderService;
import com.flash.sale.vo.GoodsVo;
import com.flash.sale.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/order")
public class OrderController {

  @Autowired
  MiaoshaUserService userService;

  @Autowired
  RedisService redisService;

  @Autowired
  OrderService orderService;

  @Autowired
  GoodsService goodsService;

  @RequestMapping("/detail")
  @ResponseBody
  public Result<OrderDetailVo> info(Model model, MiaoshaUser user,
                                    @RequestParam("orderId") long orderId) {
    if (user == null) {
      return Result.error(CodeMsg.SESSION_ERROR);
    }
    OrderInfo order = orderService.getOrderById(orderId);
    if (order == null) {
      return Result.error(CodeMsg.ORDER_NOT_EXIST);
    }
    long goodsId = order.getGoodsId();
    GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
    OrderDetailVo vo = new OrderDetailVo();
    vo.setOrder(order);
    vo.setGoods(goods);
    return Result.success(vo);
  }

}
