package com.ddam.damda.images.model;

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

public class Images {
	
	private int id;
	private String file_path;
	private String file_name;
	private String file_type;

}
