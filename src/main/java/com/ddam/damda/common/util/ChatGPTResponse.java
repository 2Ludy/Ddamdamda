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
public class ChatGPTResponse {
	
	private List<Choice> choices;
	
	  @Getter @Setter
	    @NoArgsConstructor
	    @AllArgsConstructor
	    public static class Choice {
	        private ChatGPTMessage message;
	        private String finishReason;
	    }

}
