package com.flash.sale.dao;

import com.flash.sale.domain.MiaoshaUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface MiaoshaUserDao {

  @Select("select * from miaosha_user where id = #{id}")
  MiaoshaUser getById(@Param("id") long id);

  @Update("update miaosha_user set password = #{password} where id = #{id}")
  void update(MiaoshaUser toBeUpdate);
}
