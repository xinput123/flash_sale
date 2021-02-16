package com.flash.sale.controller;

import com.flash.sale.domain.MiaoshaUser;
import com.flash.sale.result.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

  @RequestMapping("/info")
  public Result<MiaoshaUser> info(MiaoshaUser user) {
    return Result.success(user);
  }

}
