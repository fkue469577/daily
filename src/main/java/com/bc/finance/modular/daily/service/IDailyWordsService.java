package com.bc.finance.modular.daily.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bc.finance.modular.daily.entity.DailyWords;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 单词表 服务类
 * </p>
 *
 * @author pcc
 * @since 2022-08-23
 */
public interface IDailyWordsService extends IService<DailyWords> {

    List<Map> page(IPage page, Map param);

    boolean save(DailyWords words);

    void insert(DailyWords words);

    void update(DailyWords words);

    DailyWords getByWord(String word);

    void imports(List<DailyWords> words);

    void insertBatch(List<DailyWords> insertList);

    void placementById(String id);

    void oneClickPlacement();

    void oneClickComplete();

    void uncomplete(String id);
}
