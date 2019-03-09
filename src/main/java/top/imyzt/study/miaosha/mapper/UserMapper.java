package top.imyzt.study.miaosha.mapper;

import org.apache.ibatis.annotations.Select;
import top.imyzt.study.miaosha.domain.User;

/**
 * @author imyzt
 * @date 2019/3/8 17:22
 * @description UserMapper
 */
public interface UserMapper {

    @Select("SELECT * FROM user WHERE id = #{id}")
    User findById(Integer id);
}
