package cn.crisp.crispmallproduct.controller;

import cn.crisp.common.R;
import cn.crisp.crispmallproduct.entity.Product;
import cn.crisp.crispmallproduct.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api("产品")
@RestController
public class ProductController {
    @Autowired
    ProductService productService;

    @ApiOperation("获取所有产品")
    @GetMapping("/product/")
    public R<List<Product>> getProduct(){
        return R.success(productService.list());
    }
}
