package com.bc.finance.modular.daily.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("t_person_identity_info")
@ApiModel(value = "PersonIdentityInfo对象", description = "个人身份信息实体类")
public class PersonIdentityInfo {

    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "主键ID（手动输入）", required = true, example = "100001")
    private Long id;

    @TableField("person_no")
    @ApiModelProperty(value = "个人唯一编号", example = "PER20260314001")
    private String personNo;

    @TableField("id_card_no")
    @ApiModelProperty(value = "身份证号码", example = "110101199001011234")
    private String idCardNo;

    @TableField("name")
    @ApiModelProperty(value = "姓名", required = true, example = "张三")
    private String name;

    @TableField("name_pinyin")
    @ApiModelProperty(value = "姓名拼音（全拼）", example = "Zhang San")
    private String namePinyin;

    @TableField("name_abbr")
    @ApiModelProperty(value = "姓名缩写", example = "ZS")
    private String nameAbbr;

    @TableField("gender")
    @ApiModelProperty(value = "性别（1=男，2=女，0=未知）", required = true, example = "1")
    private Integer gender;

    @TableField("birthday")
    @ApiModelProperty(value = "出生日期", required = true, example = "1990-01-01")
    private Date birthday;

    @TableField("age")
    @ApiModelProperty(value = "年龄（计算字段）", example = "34")
    private Integer age;

    @TableField("nation")
    @ApiModelProperty(value = "民族（如：汉族、蒙古族）", required = true, example = "汉族")
    private String nation;

    @TableField("nation_code")
    @ApiModelProperty(value = "民族编码（国标）", example = "01")
    private String nationCode;

    @TableField("marital_status")
    @ApiModelProperty(value = "婚姻状况（1=未婚，2=已婚，3=离异，4=丧偶）", example = "1")
    private Integer maritalStatus;

    @TableField("marital_status_date")
    @ApiModelProperty(value = "婚姻状况变更日期", example = "2020-01-01")
    private Date maritalStatusDate;

    @TableField("native_place")
    @ApiModelProperty(value = "籍贯（省/市/区/街道）", required = true, example = "北京市东城区")
    private String nativePlace;

    @TableField("native_place_code")
    @ApiModelProperty(value = "籍贯行政区划编码", example = "110101")
    private String nativePlaceCode;

    @TableField("household_type")
    @ApiModelProperty(value = "户口类型（1=农业，2=非农业，3=集体户）", example = "2")
    private Integer householdType;

    @TableField("household_register_no")
    @ApiModelProperty(value = "户口本编号", example = "110101001002003004")
    private String householdRegisterNo;

    @TableField("household_address")
    @ApiModelProperty(value = "户籍地址（详细）", required = true, example = "北京市东城区XX街道XX小区1号楼1单元101")
    private String householdAddress;

    @TableField("household_address_code")
    @ApiModelProperty(value = "户籍地址行政区划编码", example = "110101001")
    private String householdAddressCode;

    @TableField("residence_address")
    @ApiModelProperty(value = "现居住地址", required = true, example = "北京市东城区XX街道XX小区1号楼1单元101")
    private String residenceAddress;

    @TableField("residence_address_code")
    @ApiModelProperty(value = "现居地址行政区划编码", example = "110101001")
    private String residenceAddressCode;

    @TableField("residence_type")
    @ApiModelProperty(value = "居住类型（1=自有房，2=租房，3=单位宿舍）", example = "1")
    private Integer residenceType;

    @TableField("residence_start_date")
    @ApiModelProperty(value = "现居地址起始日期", example = "2020-01-01")
    private Date residenceStartDate;

    @TableField("post_code")
    @ApiModelProperty(value = "现居地址邮编", example = "100010")
    private String postCode;

    @TableField("mobile")
    @ApiModelProperty(value = "手机号码", example = "13800138000")
    private String mobile;

    @TableField("phone")
    @ApiModelProperty(value = "固定电话（含区号）", example = "010-88888888")
    private String phone;

    @TableField("email")
    @ApiModelProperty(value = "电子邮箱", example = "zhangsan@example.com")
    private String email;

    @TableField("emergency_contact")
    @ApiModelProperty(value = "紧急联系人姓名", example = "李四")
    private String emergencyContact;

    @TableField("emergency_contact_relation")
    @ApiModelProperty(value = "紧急联系人关系", example = "同事")
    private String emergencyContactRelation;

    @TableField("emergency_contact_mobile")
    @ApiModelProperty(value = "紧急联系人手机号", example = "13900139000")
    private String emergencyContactMobile;

    @TableField("education")
    @ApiModelProperty(value = "学历（1=小学，2=初中，3=高中，4=大专，5=本科，6=硕士，7=博士）", example = "5")
    private Integer education;

    @TableField("education_verify")
    @ApiModelProperty(value = "学历验证状态（0=未验证，1=已验证）", example = "1")
    private Integer educationVerify;

    @TableField("graduation_school")
    @ApiModelProperty(value = "毕业院校", example = "北京大学")
    private String graduationSchool;

    @TableField("graduation_date")
    @ApiModelProperty(value = "毕业日期", example = "2012-06-30")
    private Date graduationDate;

    @TableField("profession")
    @ApiModelProperty(value = "专业", example = "计算机科学与技术")
    private String profession;

    @TableField("degree")
    @ApiModelProperty(value = "学位（1=无，2=学士，3=硕士，4=博士）", example = "2")
    private Integer degree;

    @TableField("political_status")
    @ApiModelProperty(value = "政治面貌（1=群众，2=共青团员，3=党员，4=民主党派，5=无党派）", example = "3")
    private Integer politicalStatus;

    @TableField("political_status_date")
    @ApiModelProperty(value = "政治面貌认定日期", example = "2010-07-01")
    private Date politicalStatusDate;

    @TableField("job_status")
    @ApiModelProperty(value = "就业状态（1=在职，2=失业，3=退休，4=学生，5=自由职业）", example = "1")
    private Integer jobStatus;

    @TableField("company_name")
    @ApiModelProperty(value = "工作单位名称", example = "XX科技有限公司")
    private String companyName;

    @TableField("company_address")
    @ApiModelProperty(value = "工作单位地址", example = "北京市海淀区XX大厦10层")
    private String companyAddress;

    @TableField("company_phone")
    @ApiModelProperty(value = "工作单位电话", example = "010-99999999")
    private String companyPhone;

    @TableField("position")
    @ApiModelProperty(value = "职务", example = "高级工程师")
    private String position;

    @TableField("industry")
    @ApiModelProperty(value = "所属行业", example = "信息技术")
    private String industry;

    @TableField("monthly_income")
    @ApiModelProperty(value = "月收入（元）", example = "20000.00")
    private BigDecimal monthlyIncome;

    @TableField("id_card_issue_org")
    @ApiModelProperty(value = "身份证签发机关", example = "北京市公安局东城分局")
    private String idCardIssueOrg;

    @TableField("id_card_start_date")
    @ApiModelProperty(value = "身份证有效期起始日期", required = true, example = "2020-01-01")
    private Date idCardStartDate;

    @TableField("id_card_end_date")
    @ApiModelProperty(value = "身份证有效期结束日期（长期填9999-12-31）", example = "2030-01-01")
    private Date idCardEndDate;

    @TableField("id_card_photo_front")
    @ApiModelProperty(value = "身份证正面照存储路径", example = "/upload/idcard/front/100001.jpg")
    private String idCardPhotoFront;

    @TableField("id_card_photo_back")
    @ApiModelProperty(value = "身份证背面照存储路径", example = "/upload/idcard/back/100001.jpg")
    private String idCardPhotoBack;

    @TableField("bank_card_no")
    @ApiModelProperty(value = "银行卡号", example = "622202XXXXXXXXXXXX1234")
    private String bankCardNo;

    @TableField("bank_name")
    @ApiModelProperty(value = "开卡银行名称", example = "中国工商银行")
    private String bankName;

    @TableField("bank_branch")
    @ApiModelProperty(value = "开卡支行名称", example = "北京东城支行")
    private String bankBranch;

    @TableField("social_security_no")
    @ApiModelProperty(value = "社保编号", example = "11010119900101123456")
    private String socialSecurityNo;

    @TableField("medical_card_no")
    @ApiModelProperty(value = "医保卡编号", example = "11010119900101123456")
    private String medicalCardNo;

    @TableField("provident_fund_no")
    @ApiModelProperty(value = "公积金编号", example = "11010119900101123456")
    private String providentFundNo;

    @TableField("driving_license_no")
    @ApiModelProperty(value = "驾驶证编号", example = "11010119900101123456")
    private String drivingLicenseNo;

    @TableField("driving_license_type")
    @ApiModelProperty(value = "驾驶证准驾车型", example = "C1")
    private String drivingLicenseType;

    @TableField("passport_no")
    @ApiModelProperty(value = "护照号码（如有）", example = "E12345678")
    private String passportNo;

    @TableField("passport_start_date")
    @ApiModelProperty(value = "护照有效期起始日期", example = "2022-01-01")
    private Date passportStartDate;

    @TableField("passport_end_date")
    @ApiModelProperty(value = "护照有效期结束日期", example = "2032-01-01")
    private Date passportEndDate;

    @TableField("visa_no")
    @ApiModelProperty(value = "签证号码（如有）", example = "V12345678")
    private String visaNo;

    @TableField("visa_valid_date")
    @ApiModelProperty(value = "签证有效期", example = "2025-01-01")
    private Date visaValidDate;

    @TableField("tax_register_no")
    @ApiModelProperty(value = "税务登记号（个人）", example = "91110101MA00000000")
    private String taxRegisterNo;

    @TableField("health_status")
    @ApiModelProperty(value = "健康状况（1=健康，2=良好，3=一般，4=较差）", example = "1")
    private Integer healthStatus;

    @TableField("blood_type")
    @ApiModelProperty(value = "血型", example = "A")
    private String bloodType;

    @TableField("height")
    @ApiModelProperty(value = "身高（米）", example = "1.75")
    private BigDecimal height;

    @TableField("weight")
    @ApiModelProperty(value = "体重（千克）", example = "70.00")
    private BigDecimal weight;

    @TableField("hobby")
    @ApiModelProperty(value = "兴趣爱好", example = "阅读、跑步、编程")
    private String hobby;

    @TableField("language")
    @ApiModelProperty(value = "掌握语言（如：汉语、英语）", example = "汉语、英语")
    private String language;

    @TableField("dialect")
    @ApiModelProperty(value = "方言（如有）", example = "北京话")
    private String dialect;

    @TableField("create_by")
    @ApiModelProperty(value = "创建人", required = true, example = "admin")
    private String createBy;

    @TableField("create_time")
    @ApiModelProperty(value = "创建时间", required = true, example = "2026-03-14 10:00:00")
    private Date createTime;

    @TableField("update_by")
    @ApiModelProperty(value = "更新人", example = "admin")
    private String updateBy;

    @TableField("update_time")
    @ApiModelProperty(value = "更新时间", example = "2026-03-14 11:00:00")
    private Date updateTime;

    @TableField("is_delete")
    @ApiModelProperty(value = "删除标记（0=未删，1=已删）", example = "0")
    private Integer isDelete;

    @TableField("remark")
    @ApiModelProperty(value = "备注信息", example = "无")
    private String remark;
}