package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName Admin
 * @Discription
 * @Author
 * @Date 2019/12/17 13:22
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="admin1")
public class Admin {
    @Id
    private String id;
    private String username;
    private String password;
    private String nickname;
}
