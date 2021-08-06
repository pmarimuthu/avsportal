package com.avs.portal.util;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import com.avs.portal.bean.UserBean;
import com.avs.portal.enums.RoleEnum;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public final class AVSPortalSecurityUtil {

	public static String getJWTToken(UserBean userBean) {
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
	}
}
