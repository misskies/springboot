package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

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
 * @since 2022-06-19
 */
@Getter
@Setter
  @TableName("sys_menu")
@ApiModel(value = "Menu对象", description = "")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("名称	名称")
      private String name;

      @ApiModelProperty("路径")
      private String path;

      @ApiModelProperty("图标")
      private String icon;

      @ApiModelProperty("描述")
      private String description;
      @ApiModelProperty("父子关系")
      private Integer pid;
      @TableField(exist = false)
      private List<Menu> chilren;

}
