package com.pc.stock.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class UploadForm {
	@Getter @Setter
	private String configName;
	@Getter @Setter
	private String configData;
}
