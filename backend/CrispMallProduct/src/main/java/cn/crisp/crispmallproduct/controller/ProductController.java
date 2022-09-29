package cn.crisp.crispmallproduct.controller;

import cn.crisp.common.R;
import cn.crisp.crispmallproduct.entity.Product;
import cn.crisp.crispmallproduct.service.ProductService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api("产品")
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @ApiOperation("获取所有产品")
    @GetMapping
    public R<Page> getProduct(int page, int pageSize){
        Page<Product> ret = new Page<>(page, pageSize);
        ret = productService.page(ret);
        return R.success(ret);
    }

    @ApiOperation("根据id获取产品信息")
    @GetMapping("/{id}")
    public R<Product> getProductById(@PathVariable Long id){
        Product ret = productService.getById(id);
        if(ret == null) return R.error("该订单不存在");
        return R.success(ret);
    }
}
