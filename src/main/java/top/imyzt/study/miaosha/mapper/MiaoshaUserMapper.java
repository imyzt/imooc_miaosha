package top.imyzt.study.miaosha.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import top.imyzt.study.miaosha.domain.MiaoshaUser;

/**
 * @author imyzt
 * @date 2019/3/9 14:10
 * @description MiaoshaUserMapper
 */
public interface MiaoshaUserMapper {

    @Select("SELECT * FROM miaosha.miaosha_user WHERE id = #{id} ")
    MiaoshaUser getById(@Param("id") long id);

}
