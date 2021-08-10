package com.avs.portal.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
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
	public List<UserAddressBean> addOrEditUserAddress(UserAddressBean addressBean) {
		if(addressBean == null || addressBean.getUserId() == null)
			return null;
		
		User user = userRepository.findById(addressBean.getUserId()).orElse(null);
		if(user == null)
			return null;
		
		UserAddress userAddress = user.getUserAddresses().stream().filter(address -> (address.getAddressType() != null && address.getAddressType().equals(addressBean.getAddressType()) )).findFirst().orElse(null);
		if(userAddress == null) {
			userAddress = new UserAddress();
			userAddress.setUser(user);
			userAddress.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
		}
		
		userAddress.setAddressLine1(addressBean.getAddressLine1());
		userAddress.setAddressType(addressBean.getAddressType());
		userAddress.setCity(addressBean.getCity());
		userAddress.setState(addressBean.getState());
		userAddress.setCountry(addressBean.getCountry());
		userAddress.setGeoLatitude(addressBean.getGeoLatitude());
		userAddress.setGeoLongitude(addressBean.getGeoLongitude());
		userAddress.setIpAddress(addressBean.getIpAddress());
		userAddress.setPincode(addressBean.getPincode());		
		userAddress.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
		
		user.getUserAddresses().add(userAddress);
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
		
		user.getUserAddresses().remove(userAddress);
		userAddress.setUser(null);
		
		user = userRepository.save(user);
		
		return user.getUserAddresses().stream().map(UserAddress :: toBean).collect(Collectors.toList());
	}

}
