package com.baizhi.entity;

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
 * @ClassName Album
 * @Discription
 * @Author
 * @Date 2019/12/18 20:23
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="album")
public class Album implements Serializable {
    @Id
    private String id;
    private String title;
    private String cover;
    private String author;
    private String beam;
    private Integer score;
    private Integer count;
    private String intro;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_date;
}
