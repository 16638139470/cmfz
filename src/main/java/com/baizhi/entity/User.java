package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName User
 * @Discription
 * @Author
 * @Date 2019/12/20 14:16
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user2")
public class User implements Serializable {
    @Id
    @ExcelIgnore
    private String id;
    @Excel(name = "用户名")
    private String username;
    private String password;
    private String salt;
    @Excel(name = "法号")
    private String dharma;
    @Excel(name = "省份")
    private String province;
    @Excel(name="城市")
    private String city;
    @Excel(name = "签名")
    private String sign;
    @Excel(name="性别")
    private String sex;
    @Excel(name = "相片",type = 2,width = 20,height = 20)
    private String photo;
    @Excel(name = "状态")
    private String status;
    @Excel(name = "电话")
    private String phone;
    @Excel(name = "师傅")
    private String guruname;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "注册时间",format = "yyyy年MM月dd日")
    private Date create_date;
    private String month;
    private Integer count;
}
