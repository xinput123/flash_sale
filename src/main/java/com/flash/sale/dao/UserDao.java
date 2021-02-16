package com.flash.sale.dao;

import com.flash.sale.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

  @Select("select * from user where id = #{id}")
  User getById(@Param("id") int id);

  @Insert("insert into user(id, name)values(#{id}, #{name})")
  int insert(User user);

}
