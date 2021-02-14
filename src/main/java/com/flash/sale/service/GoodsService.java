package com.flash.sale.service;

import com.flash.sale.dao.GoodsDao;
import com.flash.sale.domain.MiaoshaGoods;
import com.flash.sale.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsService {

  @Autowired
  GoodsDao goodsDao;

  public List<GoodsVo> listGoodsVo() {
    return goodsDao.listGoodsVo();
  }

  public GoodsVo getGoodsVoByGoodsId(long goodsId) {
    return goodsDao.getGoodsVoByGoodsId(goodsId);
  }

  public void reduceStock(GoodsVo goods) {
    MiaoshaGoods g = new MiaoshaGoods();
    g.setGoodsId(goods.getId());
    goodsDao.reduceStock(g);
  }


}
