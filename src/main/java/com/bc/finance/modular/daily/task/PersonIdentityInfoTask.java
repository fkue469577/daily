package com.bc.finance.modular.daily.task;

import cn.hutool.extra.pinyin.PinyinUtil;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Component
public class PersonIdentityInfoTask {

    @Autowired
    private IPersonIdentityInfoService personIdentityInfoService;

    @Autowired
    private IBaseDictService baseDictService;

    private List<BaseDict> ethnicity;
    private Map<String, BaseDict> ethnicityMap;
    private List<BaseDict> administrativeDivision;
    private Map<String, BaseDict> administrativeDivisionMap;
    private Map<Integer, BaseDict> administrativeDivisionSort;
    private Map<String, BaseDict> surnameMap;
    private Map<String, BaseDict> nameMap;
    List<BaseDict> surnames;
    int start = 0;


    AtomicInteger ai = new AtomicInteger(0);
    public List<BaseDict> getChildren(JSONArray jsonArray, String parentName) {
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
                baseDict.setExtendFile3(parentName + baseDict.getDictName());
                baseDict.setCreateTime(LocalDateTime.now());
                List<BaseDict> baseDicts = getChildren(jsonObject.getJSONArray("childs"), baseDict.getExtendFile3());
                baseDicts.add(baseDict);
                return baseDicts.stream();
            }).collect(Collectors.toList());
        }
        return list;
    }
    @PostConstruct
    public void init() throws Exception {

//        File file = new File("/Users/chunchengpeng/Downloads/area_format_user.json");
//        FileInputStream fis = new FileInputStream(file);
//        byte[] bytes = new byte[10240];
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        int length;
//        while((length=fis.read(bytes))>-1) {
//            baos.write(bytes, 0, length);
//        }
//        JSONArray jsonArray = JSON.parseArray(baos.toString("utf-8"));
//        List<BaseDict> list = getChildren(jsonArray, "");
//
//        int page = list.size() / 500;
//        for (int i = 0; i < page; i++) {
//            List<BaseDict> insertList = list.subList(i * 500, Math.min((i + 1) * 500, list.size()));
//            baseDictService.saveBatch(insertList);
//        }
//
//        System.out.println(new Date());
//        if(true) {
//            return;
//        }

        ethnicity = baseDictService.listByTypeCode("ethnicity");
        ethnicityMap = ethnicity.stream().collect(Collectors.toMap(BaseDict::getDictCode, e->e));
        administrativeDivision = baseDictService.listByTypeCode("administrative_division");
        administrativeDivisionMap = administrativeDivision.stream().collect(Collectors.toMap(BaseDict::getDictCode, e->e));
        administrativeDivisionSort = administrativeDivision.stream().collect(Collectors.toMap(BaseDict::getSort, e->e));
        surnames = baseDictService.listByTypeCode("surname");
        surnameMap = baseDictService.listByTypeCode("surname").stream().collect(Collectors.toMap(BaseDict::getDictCode, e->e));
        for (BaseDict baseDict : surnames) {
            baseDict.setExtendFile2(String.valueOf(start));
            baseDict.setExtendFile3(String.valueOf(start+Integer.parseInt(baseDict.getExtendFile1())));
            start += Integer.parseInt(baseDict.getExtendFile1());
        }

        nameMap = baseDictService.listByTypeCode("name").stream().collect(Collectors.toMap(BaseDict::getDictCode, e->e));
        run();
    }


    @Scheduled(cron="20 * * * * ?")
    public void run() throws ParseException {
        // 1. 构造测试数据
        int year = LocalDate.now().getYear();
        List<PersonIdentityInfo> dataList = new ArrayList<>();
        for (long i = 100001; i <= 100100; i++) { // 生成100条测试数据
            BaseDict ad = getAdministrativeDivision();
            BaseDict nowAd = getAdministrativeDivision();
            String birthday = getBirthday();
            String surname = getSurname();
            String name = getName();

            PersonIdentityInfo info = new PersonIdentityInfo();
            info.setPersonNo("PER" + System.currentTimeMillis() + i);
            info.setIdCardNo(String.format("%s%s%s", ad.getDictCode(), birthday.replaceAll("-", ""), String.valueOf(Math.random()).substring(3, 7))); // 模拟身份证号
            info.setName(surname+name);
            info.setNamePinyin(PinyinUtil.getPinyin(name) + " " + PinyinUtil.getPinyin(surname));
            info.setNameAbbr(PinyinUtil.getFirstLetter(info.getName(), "").toUpperCase());
            info.setGender(Integer.parseInt(info.getIdCardNo().substring(info.getIdCardNo().length()-2, info.getIdCardNo().length()-1))/2==0? 2:1); // 男
            info.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(birthday)); // 1990-01-01
            info.setAge(year - Integer.parseInt(birthday.split("-")[0]));
            info.setNation(getEthnicity().getDictName());
            info.setNationCode(getEthnicity().getDictCode());
            info.setNativePlace(ad.getDictName());
            info.setNativePlaceCode(ad.getDictCode());
            info.setHouseholdAddress(ad.getExtendFile3());
            info.setResidenceAddress(nowAd.getExtendFile3());
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


    /**
     * 获取民族
     * @return
     */
    public BaseDict getEthnicity() {
        String random = String.format("%02d", (int) (Math.random() * 300));
        BaseDict baseDict = ethnicityMap.get(random);
        if(baseDict == null){
            baseDict = ethnicityMap.get("01");
        }
        return baseDict;
    }


    /**
     * 获取辖区
     * @return
     */
    public BaseDict getAdministrativeDivision() {
        BaseDict baseDict = null;
        while(baseDict==null || baseDict.getDictCode().length()!=6) {
            int random = (int) (Math.random() * 34002);
            baseDict = administrativeDivisionSort.get(random);
        }
        return baseDict;
    }

    /**
     * 获取出生日期
     */
    public String getBirthday() {
        int startYear = 1950;
        int endYear = 2006;
        LocalDate startDate = LocalDate.of(startYear, 1, 1);
        LocalDate endDate = LocalDate.of(endYear, 12, 31);

        long startEpochDay = startDate.toEpochDay();
        long endEpochDay = endDate.toEpochDay();
        long randomEpochDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay + 1);

        return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.ofEpochDay(randomEpochDay));
    }

    /**
     * 获取姓
     */
    public String getSurname() {
        int random = new Random().nextInt(start);
        for (BaseDict baseDict: surnames) {
            if(random >= Integer.parseInt(baseDict.getExtendFile2()) && random < Integer.parseInt(baseDict.getExtendFile3())){
                return baseDict.getDictName();
            }
        }
        return "李";
    }

    public String getName() {
        if(Math.random()>0.1) {
            BaseDict firstDict = getRandomName();
            while(firstDict==null) {
                firstDict = getRandomName();
            }
            if(firstDict.getDictName().length()==2) {
                return firstDict.getDictName();
            }
            BaseDict secondDict = getRandomName();
            while(secondDict==null || secondDict.getDictName().length()!=1) {
                secondDict = getRandomName();
            }
            return firstDict.getDictName()+secondDict.getDictName();
        }

//        String name = nameMap.get(String.format("%04d", new Random().nextInt(size))).getDictName();
        BaseDict baseDict = getRandomName();
        while(baseDict==null || baseDict.getDictName().length()==2) {
            baseDict = getRandomName();
        }
        return baseDict.getDictName();
    }

    public BaseDict getRandomName() {
        int size = nameMap.size();
        return nameMap.get(String.format("%04d", new Random().nextInt(size)));
    }
}