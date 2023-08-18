package com.xindong.account.converter;


import com.xindong.account.dto.SplitDetailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AccountConverter {

	AccountConverter INSTANCE = Mappers.getMapper(AccountConverter.class);

	List<SplitDetailDTO> toSplitDeatilList(List<SplitDetailDTO> resultList);
}
