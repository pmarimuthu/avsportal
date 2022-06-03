package com.avs.portal.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

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
			return Collections.emptyList();
		
		User userEntity = userRepository.findById(user.getId()).orElse(null);
		if(userEntity == null)
			return Collections.emptyList();
		
		return userEntity.getUserAddresses().stream().map(UserAddress :: toBean).collect(Collectors.toList());
	}

	// READ (ONE)
	public UserAddress getUserAddress(UserAddressBean userAddressBean) {
		if(userAddressBean == null || userAddressBean.getId() == null)
			return null;
		
		return userAddressRepository.findById(userAddressBean.getId()).orElse(null);
	}

	// CREATE (NEW)
	@Transactional
	public List<UserAddressBean> createUserAddress(UserBean userBean, UserAddressBean userAddressBean) {
		if(userBean == null || userAddressBean == null || userAddressBean.getAddressType() == null)
			return Collections.emptyList();
		
		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null)
			return Collections.emptyList();
		
		List<UserAddress> userAddresses = user.getUserAddresses();
		if(userAddresses.stream().filter(userAddress -> userAddress.getAddressType().equals(userAddressBean.getAddressType())).count() != 0) {
			return Collections.emptyList();
		}
		
		UserAddress userAddress = new UserAddress()
				.setAddressLine1(userAddressBean.getAddressLine1())
				.setAddressType(userAddressBean.getAddressType())
				.setCity(userAddressBean.getCity())
				.setCountry(userAddressBean.getCountry())
				.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()))
				.setGeoLatitude(userAddressBean.getGeoLatitude())
				.setGeoLongitude(userAddressBean.getGeoLongitude())
				.setIpAddress(userAddressBean.getIpAddress())
				.setIsDeleted(false)
				.setPincode(userAddressBean.getPincode())
				.setState(userAddressBean.getState())
				.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()))
				.setUsers(Collections.emptyList());
		
		user.getUserAddresses().add(userAddress);
		
		user = userRepository.save(user);
		
		return user.getUserAddresses().stream().map(UserAddress :: toBean).collect(Collectors.toList());
				
	}

	public List<UserAddressBean> attachUserWithUserAddress(UserBean userBean, UserAddressBean userAddressBean) {
		if(userBean == null || userAddressBean == null || userAddressBean.getId() == null || userAddressBean.getAddressType() == null)
			return Collections.emptyList();
		
		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null)
			return Collections.emptyList();
		
		List<UserAddress> userAddresses = user.getUserAddresses();
		if(userAddresses.stream().filter(userAddress -> userAddress.getAddressType().equals(userAddressBean.getAddressType())).count() != 0) {
			return Collections.emptyList();
		}
		
		UserAddress userAddress = userAddressRepository.findById(userAddressBean.getId()).orElse(null);
		if(userAddress == null)
			return Collections.emptyList();
		
		userAddress.getUsers().add(user);
		user.getUserAddresses().add(userAddress);
		
		user = userRepository.save(user);
		
		return user.getUserAddresses().stream().map(UserAddress :: toBean).collect(Collectors.toList());		
		
	}

	public List<UserAddressBean> detachUserFromUserAddress(UserBean userBean, UserAddressBean userAddressBean) {
		if(userBean == null || userAddressBean == null || userAddressBean.getId() == null || userAddressBean.getAddressType() == null)
			return Collections.emptyList();
		
		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null)
			return Collections.emptyList();
		
		List<UserAddress> userAddresses = user.getUserAddresses();
		if(userAddresses.stream().filter(userAddress -> userAddress.getAddressType().equals(userAddressBean.getAddressType())).count() != 1) {
			return Collections.emptyList();
		}
		
		UserAddress userAddress = userAddressRepository.findById(userAddressBean.getId()).orElse(null);
		if(userAddress == null)
			return Collections.emptyList();
		
		userAddress.getUsers().remove(user);
		user.getUserAddresses().remove(userAddress);
		
		user = userRepository.save(user);
		
		return user.getUserAddresses().stream().map(UserAddress :: toBean).collect(Collectors.toList());		
		
	}

	public UserBean updateUserAddress(UserBean userBean, UserAddressBean userAddressBean) {
		if(userBean == null || userAddressBean == null || userAddressBean.getId() == null || userAddressBean.getAddressType() == null)
			return null;

		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null)
			return null;

		List<UserAddress> userAddresses = user.getUserAddresses();
		if(userAddresses
				.stream()
				.filter(userAddress ->
						userAddress.getId().equals(userAddressBean.getId()) 
						&& userAddress.getAddressType().equals(userAddressBean.getAddressType())
				)
				.count() != 1) {			
			return null;
		}

		UserAddress userAddress = userAddresses
				.stream()
				.filter(address ->
						address.getId().equals(userAddressBean.getId()) 
						&& address.getAddressType().equals(userAddressBean.getAddressType())
				)
				.findFirst().orElse(null);
		if(userAddress == null)
			return null;
		
		
		userAddress.setAddressLine1(userAddressBean.getAddressLine1());
		userAddress.setAddressType(userAddressBean.getAddressType());
		userAddress.setCity(userAddressBean.getCity());
		userAddress.setCountry(userAddressBean.getCountry());
		userAddress.setGeoLatitude(userAddressBean.getGeoLatitude());
		userAddress.setGeoLongitude(userAddressBean.getGeoLongitude());
		userAddress.setIpAddress(userAddressBean.getIpAddress());
		userAddress.setIsDeleted(Boolean.FALSE);
		userAddress.setPincode(userAddressBean.getPincode());
		userAddress.setState(userAddressBean.getState());
		userAddress.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));

		user = userRepository.save(user);

		return user.toBean();	

	}

	public UserBean removeUserAddress(UserBean userBean, UserAddressBean userAddressBean) {
		if(userBean == null || userBean.getId() == null || userAddressBean == null || userAddressBean.getAddressType() == null)
			return null;
		
		User user = userRepository.findById(userBean.getId()).orElse(null);
		if(user == null)
			return null;
		
		List<UserAddress> userAddresses = user.getUserAddresses();
		UserAddress userAddress = userAddresses.stream().filter(address -> address.getAddressType().equals(userAddressBean.getAddressType())).findFirst().orElse(null);
		if(userAddress == null)
			return null;
		
		user.getUserAddresses().remove(userAddress);
		userAddress.getUsers().remove(user);
		
		user = userRepository.save(user);
		
		return user.toBean();
	}
	

}
