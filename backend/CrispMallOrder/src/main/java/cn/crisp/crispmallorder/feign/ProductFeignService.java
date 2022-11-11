package cn.crisp.crispmallorder.feign;

import cn.crisp.common.R;
import cn.crisp.crispmallproduct.entity.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("CrispMallProduct")
public interface ProductFeignService {
    @GetMapping("/product/{id}")
    public R<Product> getProductById(@PathVariable Long id);

    @PutMapping("/product")
    public R<String> updateProduct(@RequestBody Product product);
}
