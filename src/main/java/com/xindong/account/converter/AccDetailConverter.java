package com.xindong.account.converter;


import com.xindong.account.dto.AccDetailDTO;
import com.xindong.account.dto.ResultDTO;
import com.xindong.account.entity.AccDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AccDetailConverter {

	AccDetailConverter INSTANCE = Mappers.getMapper(AccDetailConverter.class);

	@Mapping(source = "id", target = "groupId")
	AccDetail toDo(ResultDTO result, Long id);

	List<AccDetailDTO> toAccDetailList(List<AccDetail> accDetails);
}
