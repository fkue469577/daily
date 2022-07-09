package com.bc.finance.modular.daily.service.impl;

import com.bc.finance.modular.daily.entity.DailyNotes;
import com.bc.finance.modular.daily.mapper.DailyNotesMapper;
import com.bc.finance.modular.daily.service.IDailyNotesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 笔记表 服务实现类
 * </p>
 *
 * @author pcc
 * @since 2022-07-08
 */
@Service
public class DailyNotesServiceImpl extends ServiceImpl<DailyNotesMapper, DailyNotes> implements IDailyNotesService {

}
