package com.analizy.cmp.workflow.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wangjian030
 * @since 2021-01-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("flyway_schema_history")
public class SchemaHistory extends Model<SchemaHistory> {

    private static final long serialVersionUID = 1L;

    @TableId("installed_rank")
    private Integer installedRank;
    @Version
    private String version;
    private String description;
    private String type;
    private String script;
    private Integer checksum;
    @TableField("installed_by")
    private String installedBy;
    @TableField("installed_on")
    private Date installedOn;
    @TableField("execution_time")
    private Integer executionTime;
    private Integer success;


    @Override
    protected Serializable pkVal() {
        return this.installedRank;
    }

}
