package com.ddam.damda.common.util;

import java.util.List;

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
public class ChatGPTRequest {
	
    private String model = "gpt-4";
    private List<ChatGPTMessage> messages;
    private double temperature = 0.7;

}
