package generator;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bc.finance.RunApplication;
import com.bc.finance.modular.daily.entity.DailyInterview;
import com.bc.finance.modular.daily.entity.DailyWords;
import com.bc.finance.modular.daily.service.IDailyInterviewService;
import com.bc.finance.modular.daily.service.IDailyWordsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest(classes = RunApplication.class)
public class TestCode {

    @Autowired
    IDailyWordsService wordsService;

    @Test
    public void test() {
        List<DailyWords> ids = wordsService.list(new QueryWrapper<DailyWords>().select("id"));

        long startMillis = System.currentTimeMillis();
        ids.forEach(id->{
            wordsService.getById(id);
        });
        long endMillis = System.currentTimeMillis();
        System.out.println(endMillis - startMillis);
        startMillis = endMillis;
//        IntStream.range(0, ids.size() / 40 + 1).forEach(i->{
//            List<DailyWords> dailyWords = ids.subList((i - 1) * 40, i * 40 > ids.size() ? ids.size() : i * 40);
//        });
        List<DailyWords> dailyWords = wordsService.listByIds(ids.stream().map(DailyWords::getId).collect(Collectors.toList()));
        endMillis = System.currentTimeMillis();
        System.out.println(endMillis - startMillis);
    }

}