package com.example.drool.PolicyMakingDroolExample.controller;

import java.util.ArrayList;
import java.util.List;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.drool.PolicyMakingDroolExample.DroolsConfig;
import com.example.drool.PolicyMakingDroolExample.exceptiom.WrongPasswordException;
import com.example.drool.PolicyMakingDroolExample.model.AllowCapitalAndSpecialCharacters;
import com.example.drool.PolicyMakingDroolExample.model.PasswordPolicyRules;
import com.example.drool.PolicyMakingDroolExample.model.Request;

@RestController
public class PasswordPolicyController {
	@PostMapping("/pass")
	public String createPassword() {
		try {
	
		List<String> globalExceptionMessages = new ArrayList<>();
		PasswordPolicyRules passwordPolicyRules=getPasswordPolicyRulesObject();
		Request request=new Request();
		request.setPassword("abcde22");
		String password="abc";
		KieContainer kieContainer = DroolsConfig.getKieContainer();
		
		KieSession kieSession=kieContainer.newKieSession();
		//kieSession.insert(request);
		kieSession.insert(password);
		kieSession.insert(passwordPolicyRules);
		kieSession.setGlobal("globalExceptionMessages",globalExceptionMessages);
		kieSession.fireAllRules();
		if (!globalExceptionMessages.isEmpty()) {
		    for (String exceptionMessage : globalExceptionMessages) {
		        System.out.println("I am in exception "+  exceptionMessage);
		        throw new WrongPasswordException(exceptionMessage);
		    }
		}
	   
	
	}catch(Exception e) {
		System.out.println("Received exception"+e);
		
	}
		 return "All good";}
	

	private PasswordPolicyRules getPasswordPolicyRulesObject() {
		PasswordPolicyRules passwordPolicyRules=new PasswordPolicyRules();
		passwordPolicyRules.setMaxPasswordLength(10);
		passwordPolicyRules.setMinDigits(2);
		passwordPolicyRules.setMinPasswordLength(6);
		passwordPolicyRules.setAllowCapitalAndSpecialCharacters(new AllowCapitalAndSpecialCharacters(1,3));
		return passwordPolicyRules;

	}
	
	public KieSession getKieSession() {
		KieServices kieServices = KieServices.Factory.get();
		KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
		kieFileSystem.write(ResourceFactory.newClassPathResource("password-rules.drl"));
		KieBuilder kb = kieServices.newKieBuilder(kieFileSystem);
		kb.buildAll();
        KieRepository kieRepository = kieServices.getRepository();
		ReleaseId krDefaultReleaseId = kieRepository.getDefaultReleaseId();
		KieContainer kieContainer = kieServices.newKieContainer(krDefaultReleaseId);
		KieSession kieSession = kieContainer.newKieSession();
		return kieSession;
		}

}
