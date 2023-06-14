package com.xindong.accounting.dataobject.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckResultDTO implements Serializable {

	private String fileUrl;

}
