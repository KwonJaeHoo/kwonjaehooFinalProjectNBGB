package com.sist.nbgb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ReferenceController {
	
	@GetMapping("/reference/referenceList")
	public String reference(Model model)
	{
		return "reference/referenceList";
	}

}
