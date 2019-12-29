package com.service;

import com.common.ServerResponse;
import com.pojo.Person;

public interface TrxService {
    ServerResponse buy(Person user, int id, long buyTime);
}
