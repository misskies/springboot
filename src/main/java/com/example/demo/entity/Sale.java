package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
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
  @TableName("sys_sale")
@ApiModel(value = "Sale对象", description = "")
public class Sale implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private String salemsg;

    private String name;

    private String type;

      @ApiModelProperty("单价")
      private double iprice;

      @ApiModelProperty("总价")
      private double allprice;

      @ApiModelProperty("数量")
      private Integer num;

    private LocalDateTime createTime;


}
