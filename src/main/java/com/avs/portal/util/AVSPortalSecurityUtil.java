package com.avs.portal.util;

import com.avs.portal.bean.UserBean;

public final class AVSPortalSecurityUtil {
	
	private AVSPortalSecurityUtil() {
	}

	public static String getJWTToken(UserBean userBean) {
		Logger.info(userBean.toString());
		return "To return JWT Token - Please uncomment the below logic";
		
		/* 
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(RoleEnum.USER.toString());
		
		String secretKey = userBean.getId().toString();
		String token = Jwts
				.builder()
				.setId(userBean.getId().toString())
				.setSubject("AVSPortal")
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 8))) // 8 minutes valid
				.signWith(SignatureAlgorithm.HS512, secretKey.getBytes())
				.compact();
		
		return "Bearer " + token;
		*/
	}
}
