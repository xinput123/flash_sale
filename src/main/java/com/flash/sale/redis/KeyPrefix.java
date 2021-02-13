package com.flash.sale.redis;

public interface KeyPrefix {

  int expireSeconds();

  String getPrefix();

}
