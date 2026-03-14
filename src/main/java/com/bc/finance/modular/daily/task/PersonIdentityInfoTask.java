package com.bc.finance.modular.daily.task;

import com.bc.finance.modular.daily.entity.PersonIdentityInfo;
import com.bc.finance.modular.daily.service.IPersonIdentityInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class PersonIdentityInfoTask {

    @Autowired
    private IPersonIdentityInfoService personIdentityInfoService;

    @Scheduled(cron="20 */5 * * * ?")
    public void run() throws InterruptedException {
        // 1. 构造测试数据
        List<PersonIdentityInfo> dataList = new ArrayList<>();
        for (long i = 100001; i <= 100100; i++) { // 生成100条测试数据
            PersonIdentityInfo info = new PersonIdentityInfo();
            info.setId(i); // 手动指定主键ID
            info.setPersonNo("PER" + System.currentTimeMillis() + i);
            info.setIdCardNo("11010119900101" + String.format("%04d", i)); // 模拟身份证号
            info.setName("测试用户" + i);
            info.setNamePinyin("Ce Shi Yong Hu" + i);
            info.setNameAbbr("CSYH" + i);
            info.setGender(1); // 男
            info.setBirthday(new Date(90, 0, 1)); // 1990-01-01
            info.setAge(34);
            info.setNation("汉族");
            info.setNativePlace("北京市海淀区");
            info.setHouseholdAddress("北京市海淀区XX街道XX小区");
            info.setResidenceAddress("北京市海淀区XX街道XX小区");
            info.setIdCardStartDate(new Date(2020, 0, 1));
            info.setCreateBy("admin");
            info.setCreateTime(new Date());
            info.setIsDelete(0);

            dataList.add(info);
        }

        // 2. 执行批量插入（每批次50条）
        try {
            personIdentityInfoService.saveBatch(dataList, 50);
            System.out.println("批量插入完成，总成功条数：" + dataList.size());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("批量插入失败：" + e.getMessage());
        }
    }

}