package com.flash.sale.redis;

public interface KeyPrefix {

  public int expireSeconds();

  public String getPrefix();

}
