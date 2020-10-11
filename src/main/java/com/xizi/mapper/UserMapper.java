package com.xizi.mapper;


import com.xizi.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {

     User queryByName(String name);
}
