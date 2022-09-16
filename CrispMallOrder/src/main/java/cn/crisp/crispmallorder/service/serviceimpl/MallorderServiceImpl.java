package cn.crisp.crispmallorder.service.serviceimpl;

import cn.crisp.crispmallorder.entity.Mallorder;
import cn.crisp.crispmallorder.mapper.MallorderMapper;
import cn.crisp.crispmallorder.service.MallorderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class MallorderServiceImpl extends ServiceImpl<MallorderMapper, Mallorder> implements MallorderService {
}
