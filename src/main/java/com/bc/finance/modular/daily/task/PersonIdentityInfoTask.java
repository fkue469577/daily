package com.bc.finance.modular.daily.task;

import com.bc.finance.modular.base.entity.BaseDict;
import com.bc.finance.modular.base.service.IBaseDictService;
import com.bc.finance.modular.daily.entity.PersonIdentityInfo;
import com.bc.finance.modular.daily.service.IPersonIdentityInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
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


    @PostConstruct
    public void init() {
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


    @Scheduled(cron="20 */5 * * * ?")
    public void run() {
        // 1. 构造测试数据
        List<PersonIdentityInfo> dataList = new ArrayList<>();
        for (long i = 100001; i <= 100100; i++) { // 生成100条测试数据
            BaseDict ad = getAdministrativeDivision();
            BaseDict nowAd = getAdministrativeDivision();
            String birthday = getBirthday();
            PersonIdentityInfo info = new PersonIdentityInfo();
            info.setPersonNo("PER" + System.currentTimeMillis() + i);
            info.setIdCardNo(String.format("%s%s%s", ad.getDictCode(), birthday.replaceAll("-", ""), String.valueOf(Math.random()*100000000).substring(3, 7))); // 模拟身份证号
            info.setName("测试用户" + i);
            info.setNamePinyin("Ce Shi Yong Hu" + i);
            info.setNameAbbr("CSYH" + i);
            info.setGender(1); // 男
            info.setBirthday(new Date(90, 0, 1)); // 1990-01-01
            info.setAge(34);
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

            log.info(getSurname());

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
        int random = (int) (Math.random() * 34002);
        BaseDict baseDict = administrativeDivisionSort.get(random);
        if(baseDict == null || baseDict.getLevel()!=2){
            return getAdministrativeDivision();
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
}