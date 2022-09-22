package cn.crisp.crispmallproduct.service.impl;

import cn.crisp.crispmallproduct.entity.Product;
import cn.crisp.crispmallproduct.mapper.ProductMapper;
import cn.crisp.crispmallproduct.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
}
