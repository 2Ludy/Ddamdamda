package com.ddam.damda.common.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class GCPageRequest {
	private int gnoticeId;
	private int pageNum;
	private int pageSize;
}