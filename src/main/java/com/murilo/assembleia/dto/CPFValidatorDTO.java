package com.murilo.assembleia.dto;

import com.murilo.assembleia.enums.StatusEnum;

public class CPFValidatorDTO {
	
	private StatusEnum status;

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}
}
