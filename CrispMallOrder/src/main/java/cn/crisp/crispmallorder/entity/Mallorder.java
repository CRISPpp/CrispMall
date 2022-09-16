package cn.crisp.crispmallorder.entity;

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
public class Mallorder extends BaseEntity{
    private static final long serialVersionUID = 442353225234L;

    /**
     * 用户id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long num;
    private Long userId;
    private Long productId;
}
