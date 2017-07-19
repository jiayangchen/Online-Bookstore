package com.heitian.ssm.dao;

import com.heitian.ssm.model.Amount;
import org.springframework.stereotype.Repository;

/**
 * Created by ChenJiayang on 2017/7/19.
 */

@Repository
public interface AmountDao {
    void updateAmount(Amount amount);
    Amount getAmountByUId(long uid);
}
