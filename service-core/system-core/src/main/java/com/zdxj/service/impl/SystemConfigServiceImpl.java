package com.zdxj.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zdxj.constant.GlobalConstants;
import com.zdxj.core.service.impl.BaseServiceImpl;
import com.zdxj.mapper.SystemConfigMapper;
import com.zdxj.model.SystemConfigEntity;
import com.zdxj.service.SystemConfigService;

@Service
@Transactional
public class SystemConfigServiceImpl extends BaseServiceImpl<SystemConfigMapper,SystemConfigEntity> implements SystemConfigService {

	public SystemConfigServiceImpl() {
		super(GlobalConstants.SYSTEN_CONFIG_KEY);
	}
}