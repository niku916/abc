package com.dms.service;

import com.dms.dto.DmsDocumentDto;
import com.dms.dto.request.DmsRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface DmsService {

	public DmsDocumentDto getListOfDocFromService(DmsRequestDto dmsRequestDto) throws JsonMappingException, JsonProcessingException;

}
