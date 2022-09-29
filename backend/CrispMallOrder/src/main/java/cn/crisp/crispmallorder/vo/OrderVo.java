package cn.crisp.crispmallorder.vo;

import cn.crisp.crispmallorder.entity.Mallorder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderVo extends Mallorder {
    private String username;
    private String productName;
}
