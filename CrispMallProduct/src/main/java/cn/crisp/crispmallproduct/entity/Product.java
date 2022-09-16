package cn.crisp.crispmallproduct.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Product extends BaseEntity{
    private static final long serialVersionUID = 442353225233L;

    /**
     * 用户id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long price;
    private String productName;
    private String profile;
    /**
     * 销量
     */
    private Long sale;
    /**
     * 剩余数量
     */
    private Long num;
    private String icon;
}
