package com.dms.serviceImpl;

import org.springframework.stereotype.Service;
import com.dms.dto.DmsDocumentDto;
import com.dms.dto.request.DmsRequestDto;
import com.dms.dto.request.DmsRequestForServiceDto;
import com.dms.service.DmsService;
import com.dms.utils.CommonConstant;
import com.dms.utils.DmsUploadUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DmsServiceImpl implements DmsService {
	ObjectMapper mapper = new ObjectMapper();
	private DmsRequestForServiceDto dmsRequestForServiceDto;

	@Override
	public DmsDocumentDto getListOfDocFromService(String dmsRequest)
			throws JsonMappingException, JsonProcessingException {
		String data = dmsRequest.replace("%2b", "+");
		String jsonData = DmsUploadUtil.decrypt(data, CommonConstant.TOKEN);
		DmsRequestForServiceDto dmsRequestForServiceDto = readJsondata(jsonData);
		return new DmsDocumentDto();
	}

	public DmsRequestForServiceDto readJsondata(String jsonData) throws JsonMappingException, JsonProcessingException {

		if (jsonData != null) {
			dmsRequestForServiceDto = mapper.readValue(jsonData, DmsRequestForServiceDto.class);
		}
		return dmsRequestForServiceDto;
	}
}