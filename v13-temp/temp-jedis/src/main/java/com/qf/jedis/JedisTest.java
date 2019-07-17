package com.qf.jedis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * @Author: YangRuiGuang
 * @Date: 2019/6/24 17:01
 */
public class JedisTest {

    @Test
    public void stringTest(){
        Jedis jedis=new Jedis("192.168.231.129",6379);
        jedis.auth("123");
        jedis.set("target","java+redis");
        String target = jedis.get("target");
        System.out.println(target);
    }
}
