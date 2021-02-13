package com.flash.sale.redis;

public class MiaoshaUserKey extends BasePrefix {

  public static final int TOKEN_EXPIRE = 3600 * 2;

  private MiaoshaUserKey(int expireSeconds, String prefix) {
    super(expireSeconds, prefix);
  }

  public static MiaoshaUserKey token = new MiaoshaUserKey(TOKEN_EXPIRE, "tk");
}
