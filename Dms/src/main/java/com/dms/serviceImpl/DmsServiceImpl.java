package com.dms.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dms.dto.DmsDocumentDto;
import com.dms.dto.request.DmsRequestForServiceDto;
import com.dms.service.DmsService;
import com.dms.utils.CommonConstant;
import com.dms.utils.DmsUploadUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.np.dms.dto.ResponseDto;

@Service
public class DmsServiceImpl implements DmsService {
	@Autowired
	private ObjectMapper mapper;
	@Autowired
	private ExternalService externalService;

	@Override
	public DmsDocumentDto getListOfDocFromService(String dmsRequest)
			throws JsonMappingException, JsonProcessingException {
		String data = dmsRequest.replace("%2b", "+");
		String jsonData = DmsUploadUtil.decrypt(data, CommonConstant.TOKEN);
		DmsRequestForServiceDto dmsRequestForServiceDto = readJsondata(jsonData);

		DmsDocumentDto response = externalService.getListofDocToUploadOrUploadeds(dmsRequestForServiceDto);

		return response;
	}

	public DmsRequestForServiceDto readJsondata(String jsonData) throws JsonMappingException, JsonProcessingException {
		DmsRequestForServiceDto dmsRequestForServiceDto = null;

		if (jsonData != null) {
			dmsRequestForServiceDto = mapper.readValue(jsonData, DmsRequestForServiceDto.class);
		}
		return dmsRequestForServiceDto;
	}
}