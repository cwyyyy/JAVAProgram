package com.njts;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.njts.mapper.BuyListMapper;
import com.njts.mapper.ProductMapper;
import com.njts.mapper.UserMapper;
import com.njts.pojo.BuyList;
import com.njts.pojo.Product;
import com.njts.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@SpringBootTest
class RedstarApplicationTests {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private BuyListMapper buyListMapper;

    @Test
    void contextLoads() {
        BuyList buyList = buyListMapper.selectById(1);
        System.out.println(buyList.getBuyUser());
    }

}
