package com.service.impl;

import com.common.Const;
import com.common.ServerResponse;
import com.dao.ContentMapper;
import com.pojo.Content;
import com.pojo.Person;
import com.service.ContentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {
    @Resource
    private ContentMapper contentMapper;
    @Override
    public ServerResponse delete(Integer id) {
        Integer resultCount = contentMapper.delete(id);
        if (resultCount > 0){
            if (resultCount == 1){
                return ServerResponse.createBySuccess();
            }else{
                return  ServerResponse.createByErrorMessage("好像删除了不止一个产品");
            }
        }else {
            return ServerResponse.createByErrorMessage("删除失败");
        }
    }

    @Override
    public Content getProduct(int id, Person user) {
        Content product = contentMapper.getProduct(id);
        product.setBuyPrice(product.getPrice());
            if (user != null) {
                if (product.getTrxCount() > 0)
                    product.setBuy(true);
            }
        return product;
    }

    @Override
    public List<Content> getProductList(Person user,String type) {
        List<Content> productList = contentMapper.getContentList();
        Iterator<Content> iterator = productList.iterator();
        while(iterator.hasNext()){
            Content product = iterator.next();
            if (product.getTrxCount() > 0){
                product.setBuy(true);
                product.setSell(true);
            }
            if (Const.BUYER.equals(type)){
                if (product.getisBuy()){
                    iterator.remove();
                }
            }
        }
        return productList;
    }

    public List<Content> account(){
        List<Content> contentList = contentMapper.account();
        return contentList;
    }

    public int publicSubmit(Content product){
        BigDecimal price = product.getPrice();
        String title = product.getTitle();
        String image = product.getImage();
        String detail = product.getdetail();
        String summary = product.getsummary();
        product.setId(contentMapper.lastInsertId()+1);
        return contentMapper.insertProduct(price, title, image, detail, summary);
    }

    public Content findProductById(int id){
        return contentMapper.selectById(id);
    }

    public int editSubmit(Content product){
        int id = product.getId();
        String title = product.getTitle();
        String image = product.getImage();
        String detail = product.getdetail();
        String summary = product.getsummary();
        BigDecimal price = product.getPrice();

        return contentMapper.updateProduct(price, title, image, detail, summary, id);
    }
}
