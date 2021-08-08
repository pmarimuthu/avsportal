package com.avs.portal.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avs.portal.bean.UserAddressBean;
import com.avs.portal.bean.UserBean;
import com.avs.portal.entity.User;
import com.avs.portal.entity.UserAddress;
import com.avs.portal.repository.UserAddressRepository;
import com.avs.portal.repository.UserRepository;

@Service
public class UserAddressService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserAddressRepository userAddressRepository;
	
	// READ (ALL)
	public List<UserAddressBean> getAllUsersAddresses() {
		return userAddressRepository.findAll().stream().map(UserAddress :: toBean).collect(Collectors.toList());
	}

	// READ (USER's)
	public List<UserAddressBean> getUserAddresses(UserBean user) {
		if(user == null || user.getId() == null)
			return null;
		
		User userEntity = userRepository.findById(user.getId()).orElse(null);
		if(userEntity == null)
			return null;
		
		return userEntity.getUserAddresses().stream().map(UserAddress :: toBean).collect(Collectors.toList());
	}

	// CREATE / UPDATE an Address
	public List<UserAddressBean> addOrEditUserAddress(UserAddressBean bean) {
		if(bean == null || bean.getUserId() == null)
			return null;
		
		User user = userRepository.findById(bean.getUserId()).orElse(null);
		if(user == null)
			return null;
		
		UserAddress userAddress = user.getUserAddresses().stream().filter(address -> address.getAddressType().equals(bean.getAddressType())).findFirst().orElse(null);
		if(userAddress == null) {
			userAddress = new UserAddress();
		}
		
		userAddress.setAddressLine1(bean.getAddressLine1());
		userAddress.setAddressType(bean.getAddressType());
		userAddress.setCity(bean.getCity());
		userAddress.setState(bean.getState());
		userAddress.setCountry(bean.getCountry());
		userAddress.setGeoLatitude(bean.getGeoLatitude());
		userAddress.setGeoLongitude(bean.getGeoLongitude());
		userAddress.setIpAddress(bean.getIpAddress());
		userAddress.setPincode(bean.getPincode());
		
		userAddress.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
		userAddress.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		userAddress.setUser(user);	
		user = userRepository.save(user);
		
		return user.getUserAddresses().stream().map(UserAddress :: toBean).collect(Collectors.toList());
	}

	public List<UserAddressBean> deleteUserAddress(UserAddressBean bean) {
		if(bean == null || bean.getId() == null)
			return null;
		
		User user = userRepository.findById(bean.getId()).orElse(null);
		if(user == null)
			return null;
		
		UserAddress userAddress = user.getUserAddresses().stream().filter(address -> address.getAddressType().equals(bean.getAddressType())).findFirst().orElse(null);
		if(userAddress == null)
			return null;
		
		userAddress.setAddressLine1(bean.getAddressLine1());
		userAddress.setAddressType(bean.getAddressType());
		userAddress.setCity(bean.getCity());
		userAddress.setState(bean.getState());
		userAddress.setCountry(bean.getCountry());
		userAddress.setGeoLatitude(bean.getGeoLatitude());
		userAddress.setGeoLongitude(bean.getGeoLongitude());
		userAddress.setIpAddress(bean.getIpAddress());
		userAddress.setPincode(bean.getPincode());
		
		userAddress.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
		userAddress.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		userAddress.setUser(user);
		user.getUserAddresses().add(userAddress);
		
		user = userRepository.save(user);
		
		return user.getUserAddresses().stream().map(UserAddress :: toBean).collect(Collectors.toList());
	}

}
