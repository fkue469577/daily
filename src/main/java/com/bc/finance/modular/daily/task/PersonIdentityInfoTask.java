package com.bc.finance.modular.daily.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bc.finance.modular.base.entity.BaseDict;
import com.bc.finance.modular.base.service.IBaseDictService;
import com.bc.finance.modular.daily.entity.PersonIdentityInfo;
import com.bc.finance.modular.daily.service.IPersonIdentityInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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


    @Autowired
    private IBaseDictService baseDictService;
    AtomicInteger ai = new AtomicInteger(0);

//    @Scheduled(cron="00 56 23 * * ?")
    @PostConstruct
    public void run1() throws IOException {

        File file = new File("/Users/chunchengpeng/Downloads/area_format_user.json");
        FileInputStream fis = new FileInputStream(file);
        byte[] bytes = new byte[10240];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int length;
        while((length=fis.read(bytes))>-1) {
            baos.write(bytes, 0, length);
        }
        JSONArray jsonArray = JSON.parseArray(baos.toString("utf-8"));
        List<BaseDict> list = getChildren(jsonArray);

        baseDictService.saveOrUpdateBatch(list);
        System.out.println(new Date());
    }

    public List<BaseDict> getChildren(JSONArray jsonArray) {
        List<BaseDict> list = new ArrayList<>();
        if(jsonArray!=null && jsonArray.size()>0) {
            return jsonArray.stream().flatMap(e -> {
                JSONObject jsonObject = (JSONObject) e;
                BaseDict baseDict = new BaseDict();
                baseDict.setLevel(jsonObject.getInteger("deep"));
                baseDict.setDictCode(jsonObject.getString("ext_id"));
                baseDict.setDictName(jsonObject.getString("name"));
                baseDict.setParentCode(jsonObject.getString("pid"));
                baseDict.setTypeCode("administrative_division");
                baseDict.setTypeName("行政区划");
                baseDict.setSort(ai.getAndAdd(1));
                baseDict.setExtendFile1(jsonObject.getString("pinyin"));
                baseDict.setExtendFile2(jsonObject.getString("id"));
                baseDict.setCreateTime(LocalDateTime.now());
                List<BaseDict> baseDicts = getChildren(jsonObject.getJSONArray("childs"));
                baseDicts.add(baseDict);
                return baseDicts.stream();
            }).collect(Collectors.toList());
        }
        return list;
    }
}