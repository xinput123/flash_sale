package com.flash.sale.controller;

import com.flash.sale.domain.User;
import com.flash.sale.redis.RedisService;
import com.flash.sale.redis.UserKey;
import com.flash.sale.result.Result;
import com.flash.sale.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/demo")
public class SampleController {

  @Autowired
  UserService userService;

  @Autowired
  RedisService redisService;

  @RequestMapping("/thymeleaf")
  public String thymeleaf(Model model) {
    model.addAttribute("name", "xinput");
    return "hello";
  }

  @RequestMapping("/db/get")
  @ResponseBody
  public Result<User> dbGet() {
    User user = userService.getById(1);
    return Result.success(user);
  }

  @RequestMapping("/db/tx")
  @ResponseBody
  public Result<Boolean> dbTx() {
    userService.tx();
    return Result.success(true);
  }

  @RequestMapping("/redis/get")
  @ResponseBody
  public Result<User> redisGet() {
    User user = redisService.get(UserKey.getById, "" + 1, User.class);
    return Result.success(user);
  }

  @RequestMapping("/redis/set")
  @ResponseBody
  public Result<Boolean> redisSet() {
    User user = new User();
    user.setId(1);
    user.setName("1111");
    redisService.set(UserKey.getById, "" + 1, user);//UserKey:id1
    return Result.success(true);
  }
}
