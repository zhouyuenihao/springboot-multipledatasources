package com.mzd.multipledatasources.mapper;

import com.mzd.multipledatasources.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zhouyue01
 * @date 2022/8/11
 */
@Mapper
public interface UserMapper {
    void save(User t);
}
