package com.bc.finance.common.helper;

import com.bc.finance.common.exception.business.BusinessException;
import com.bc.finance.common.utils.StringUtils;
import com.bc.finance.modular.school.entity.UsmScStudents;
import com.bc.finance.modular.school.service.IUsmScClassesService;
import com.bc.finance.modular.school.service.IUsmScStudentsService;
import com.bc.finance.modular.base.service.ISysCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

// 财务导入数据的辅助方法
@Component
public class FinanceHelper {

    @Autowired
    IUsmScStudentsService studentsService;

    @Autowired
    IUsmScClassesService classesService;

    @Autowired
    ISysCompanyService companyService;

    // 检测学生的数据是否正确
    public Map<String, UsmScStudents> checkCorrect(List<String> idCardList) {
        List<UsmScStudents> studentList = studentsService.listByIdcart(idCardList);
        List<String> existsIdCardList = studentList.stream().map(UsmScStudents::getIdcart).collect(Collectors.toList());
        String noExistsIdCards = idCardList.stream().filter(e -> !existsIdCardList.contains(e)).collect(Collectors.joining(","));
        if(StringUtils.isNotBlank(noExistsIdCards)) {
            throw new BusinessException(String.format("导入的数据, 其中身份证号 \"%s\" 没有对应的学生", noExistsIdCards));
        }
        Set<Long> classIdSet = studentList.stream().map(e -> e.getClassId()).collect(Collectors.toSet());
        if(classIdSet.size()!=1) {
            throw new BusinessException("导入的数据, 学生不是来自同一个班级");
        }
        Map<String, UsmScStudents> idCartMapObject = studentList.stream().collect(Collectors.toMap(e -> e.getIdcart(), e -> e));
        return idCartMapObject;
    }
}
