package com.flash.sale.controller;

import com.flash.sale.domain.MiaoshaUser;
import com.flash.sale.redis.RedisService;
import com.flash.sale.service.GoodsService;
import com.flash.sale.service.MiaoshaUserService;
import com.flash.sale.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {

  @Autowired
  MiaoshaUserService userService;

  @Autowired
  RedisService redisService;

  @Autowired
  GoodsService goodsService;

  @RequestMapping(value = "/to_list", produces = "text/html")
  @ResponseBody
  public String list(Model model, MiaoshaUser user) {
    model.addAttribute("user", user);
    //查询商品列表
    List<GoodsVo> goodsList = goodsService.listGoodsVo();
    model.addAttribute("goodsList", goodsList);
    return "goods_list";
  }

  @RequestMapping(value = "/to_detail/{goodsId}", produces = "text/html")
  @ResponseBody
  public String detail(Model model, MiaoshaUser user, @PathVariable("goodsId") long goodsId) {
    model.addAttribute("user", user);

    GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
    model.addAttribute("goods", goods);

    long startAt = goods.getStartDate().getTime();
    long endAt = goods.getEndDate().getTime();
    long now = System.currentTimeMillis();

    int miaoshaStatus = 0;
    int remainSeconds = 0;
    if (now < startAt) {//秒杀还没开始，倒计时
      miaoshaStatus = 0;
      remainSeconds = (int) ((startAt - now) / 1000);
    } else if (now > endAt) {//秒杀已经结束
      miaoshaStatus = 2;
      remainSeconds = -1;
    } else {//秒杀进行中
      miaoshaStatus = 1;
      remainSeconds = 0;
    }
    model.addAttribute("miaoshaStatus", miaoshaStatus);
    model.addAttribute("remainSeconds", remainSeconds);
    return "goods_detail";
  }
}
