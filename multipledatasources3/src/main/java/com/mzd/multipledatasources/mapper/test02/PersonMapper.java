package com.mzd.multipledatasources.mapper.test02;

import com.mzd.multipledatasources.entity.Person;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author zhouyue01
 * @date 2022/8/11
 */
@Mapper
public interface PersonMapper {
    void save(Person person);
}
