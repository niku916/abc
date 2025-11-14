package com.dms.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ResponseStatusDesc {
	SUCCESS("Success"), INFORMATION("Information"), ERROR("Error"), WARNING("Warning"), EXCEPTION("Exception"),
	CONFIRM("Confirm"), FILE("File");

	private final String value;
}
