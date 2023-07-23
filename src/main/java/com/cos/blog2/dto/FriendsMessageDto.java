package com.cos.blog2.dto;

import java.util.List;
import lombok.Data;

@Data
public class FriendsMessageDto {
	
	@Data
	public class Elements{
		public Long id;
		public String uuid;
		public Boolean favorite;
		public String profile_nickname;
		public String profile_thumbnail_image;
		public Boolean allowed_msg;
	}
	
	@Data
	public class FriendsMessageInfo{
		public int total_count;
		public String after_url;
		public int favorite_count;
		public List<Elements> elements;
		//public String before_url;
	}
}
