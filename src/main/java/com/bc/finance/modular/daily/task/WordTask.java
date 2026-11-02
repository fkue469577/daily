package com.bc.finance.modular.daily.task;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.bc.finance.modular.daily.entity.DailyWords;
import com.bc.finance.modular.daily.service.IDailyWordsService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WordTask {

    @Autowired
    private IDailyWordsService wordsService;

    @Scheduled(cron="0 0 0 * * *")
    public void run() {
        List<DailyWords> wordList0 = wordsService.lambdaQuery()
                .gt(DailyWords::getDays, 7)
                .eq(DailyWords::getCompleted, "0")
                .list();
        wordList0.forEach(e->e.setDays(0));
        wordsService.updateBatchById(wordList0);

        List<DailyWords> wordList1 = wordsService.lambdaQuery()
                .gt(DailyWords::getDays, 0)
                .eq(DailyWords::getCompleted, "0")
                .list();
        wordList1.forEach(e->e.setDays(e.getDays()+1));
        wordsService.updateBatchById(wordList1);

        if((CollectionUtils.isEmpty(wordList0) && CollectionUtils.isEmpty(wordList1)) ||
                (CollectionUtils.isNotEmpty(wordList1) && wordList1.get(0).getDays()==3)) {
            List<DailyWords> wordList2 = wordsService.lambdaQuery()
                    .eq(DailyWords::getDays, 0)
                    .eq(DailyWords::getCompleted, "0")
                    .orderByDesc(DailyWords::getLevel, DailyWords::getId)
                    .last("limit 10")
                    .list();
            wordList2.forEach(e->e.setDays(e.getDays()+1));
            wordsService.updateBatchById(wordList2);
        }

        System.out.println("执行完毕");
    }
}