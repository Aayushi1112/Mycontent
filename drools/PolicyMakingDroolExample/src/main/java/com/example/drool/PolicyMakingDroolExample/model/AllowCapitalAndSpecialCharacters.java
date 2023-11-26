package com.example.drool.PolicyMakingDroolExample.model;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AllowCapitalAndSpecialCharacters {

	private Integer minSpecialCharacters;

	private Integer minCapitalCharacters;

}
