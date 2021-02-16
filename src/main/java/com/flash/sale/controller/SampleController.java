package com.flash.sale.controller;

import com.flash.sale.domain.User;
import com.flash.sale.redis.RedisService;
import com.flash.sale.redis.UserKey;
import com.flash.sale.result.CodeMsg;
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
  private UserService userService;

  @Autowired
  private RedisService redisService;

  @RequestMapping("/hello")
  @ResponseBody
  public Result<String> home() {
    return Result.success("Hello，world");
  }

  @RequestMapping("/error")
  @ResponseBody
  public Result<String> error() {
    return Result.error(CodeMsg.SESSION_ERROR);
  }

  @RequestMapping("/hello/themaleaf")
  public String themaleaf(Model model) {
    model.addAttribute("name", "Joshua");
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
