package com.heitian.ssm.dao;

import com.heitian.ssm.model.Provided;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ChenJiayang on 2017/7/13.
 */

@Repository
public interface ProvidedDao {
    List<Provided> selectBIdByPId(long pid);
}
