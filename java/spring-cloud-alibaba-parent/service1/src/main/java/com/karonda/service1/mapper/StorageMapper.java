package com.karonda.service1.mapper;

import com.karonda.model.Storage;

public interface StorageMapper {
    Storage selectByCode(String commodityCode);
    void updateByPrimaryKey(Storage storage);
}
