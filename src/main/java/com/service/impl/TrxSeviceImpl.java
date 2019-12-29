package com.service.impl;

import com.common.ServerResponse;
import com.dao.ContentMapper;
import com.dao.TrxMapper;
import com.pojo.Content;
import com.pojo.Person;
import com.service.TrxService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
public class TrxSeviceImpl implements TrxService {

    @Resource
    ContentMapper contentMapper;

    @Resource
    TrxMapper trxMapper;

    @Override
    public ServerResponse buy(Person user, int id, long buyTime) {
        Content product = contentMapper.selectById(id);
        product.setBuyTime(buyTime);
        BigDecimal buyPrice = product.getPrice();
        product.setBuyPrice(buyPrice);
        int isBuy = trxMapper.buy(product.getId(),user.getId(),product.getBuyPrice(), product.getBuyTime());
        if(isBuy>0){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByErrorMessage("购买失败");
    }

}
