package com.qf.v13userservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.v13.api.IUserService;
import com.qf.v13.common.base.BaseServiceImpl;
import com.qf.v13.common.base.IBaseDao;
import com.qf.v13.common.pojo.ResultBean;
import com.qf.v13.entity.TUser;
import com.qf.v13.mapper.TUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author: YangRuiGuang
 * @Date: 2019/6/25 17:31
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<TUser> implements IUserService {

    @Autowired
    private TUserMapper userMapper;

    @Resource(name = "redisTemplate")
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public IBaseDao<TUser> getBaseDao() {
        return userMapper;
    }

    @Override
    public int insertSelective(TUser record) {
        super.insertSelective(record);
        //返回主键信息
        return record.getId().intValue();
    }


    @Override
    public ResultBean checkLogin(TUser user) {

        TUser currnetUser =userMapper.selectByUsername(user.getUsername());

        if(currnetUser != null){
            if(currnetUser.getPassword().equals(user.getPassword())){

                //合法账号
                //操作redis保存凭证 -----》生成JWTToken的方式
                String uuid = UUID.randomUUID().toString();
                String key = new StringBuilder("user:token:").append(uuid).toString();

                //去掉密码信息
                user.setPassword(null);
                redisTemplate.opsForValue().set(key,user);

                //设置有效期
                redisTemplate.expire(key,30, TimeUnit.MINUTES);


//                JwtUtils jwtUtils =new JwtUtils();
//                jwtUtils.setSecretKey("123");
//                jwtUtils.setTtl(30*60*1000);
//                String jwtToken = jwtUtils.createJwtToken(currnetUser.getId().toString(), currnetUser.getUsername());

                //放回信息
                return new ResultBean("200",uuid);
            }
        }
        
        return new ResultBean("404","账号不合法！");
    }

    @Override
    public ResultBean checkIsLogin(String uuid) {
//        try {
//            JwtUtils jwtUtils = new JwtUtils();
//            jwtUtils.setSecretKey("123");
//            Claims claims =jwtUtils.parseJwtToken(uuid);
//            //TODO 刷新凭证有效期
//            return  new ResultBean("200",claims.getSubject());
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResultBean("404",null);
//        }


        //组装key
        String key = new StringBuilder("user:token:").append(uuid).toString();
        //根据key查找redis
        TUser currentUser = (TUser) redisTemplate.opsForValue().get(key);
        if(currentUser != null){
            redisTemplate.expire(key,30,TimeUnit.MINUTES);
            return new ResultBean("200",currentUser);
        }

        return new ResultBean("404",null);
    }

    @Override
    public ResultBean loginOut(String uuid) {

        String key =new StringBuilder("user:token:").append(uuid).toString();
        Boolean delete = redisTemplate.delete(key);
        if(delete) {
            return new ResultBean("200", "删除成功");
        }
        return new ResultBean("404","删除失败");
    }


}
