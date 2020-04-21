package com.karonda.service1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.karonda.dubboservice.StorageService;
import com.karonda.model.Storage;
import com.karonda.service1.mapper.StorageMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class StorageServiceImpl implements StorageService {

    @Autowired
    private StorageMapper storageMapper;

    @Override
    public void deduct(String commodityCode, int count) {

//        Storage storage = storageMapper.selectByCode(commodityCode);
//        storage.setCount(storage.getCount() - count);
//        storageMapper.updateByPrimaryKey(storage);

        QueryWrapper<Storage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("commodity_code", commodityCode);
        Storage storage = storageMapper.selectOne(queryWrapper);
        storage.setCount(storage.getCount() - count);
        storageMapper.updateById(storage);
    }
}
