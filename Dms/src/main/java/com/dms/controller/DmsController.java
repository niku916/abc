package com.dms.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dms.dto.DmsDocumentDto;
import com.dms.dto.request.DmsRequestDto;
import com.dms.dto.response.CommonResponse;
import com.dms.dto.response.ResponseStatusDesc;
import com.dms.service.DmsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class DmsController {

	private final DmsService dmsService;

	@PostMapping("/getListOfDocument")
	public CommonResponse<?> getListOfDoc(@RequestBody DmsRequestDto dmsRequestDto)
			throws JsonMappingException, JsonProcessingException {

		DmsDocumentDto listOfDocFromService = dmsService.getListOfDocFromService(dmsRequestDto);
		return new CommonResponse(ResponseStatusDesc.SUCCESS.getValue(), listOfDocFromService, HttpStatus.OK, false);

	}

}
