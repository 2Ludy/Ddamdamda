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

public class CPageRequest {
	private int boardId;
	private int pageNum;
	private int pageSize;
}
