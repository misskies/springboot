package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author liu
 * @since 2022-06-21
 */
@Getter
@Setter
  @TableName("sys_remain")
@ApiModel(value = "Remain对象", description = "")
public class Remain implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private String msgtype;

    private String name;

    private String type;

    private Double iprice;

    private Double oprice;

    private Integer inventory;

    private LocalDateTime createTime;


}
