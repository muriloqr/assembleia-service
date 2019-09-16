package com.murilo.assembleia.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.murilo.assembleia.dto.CPFValidatorDTO;

@FeignClient(value = "CPFValidator", url = "https://user-info.herokuapp.com/")
public interface CPFValidatorFeignClient {
	
	@RequestMapping(method = RequestMethod.GET, value = "users/{cpf}")
	CPFValidatorDTO validateCPF(@PathVariable("cpf") String cpf);
}
