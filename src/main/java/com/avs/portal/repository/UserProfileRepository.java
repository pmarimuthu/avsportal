package com.avs.portal.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.avs.portal.entity.User;
import com.avs.portal.entity.UserProfile;
import com.avs.portal.enums.CasteEnum;
import com.avs.portal.enums.KoththiramEnum;
import com.avs.portal.enums.MaritalStatusEnum;
import com.avs.portal.enums.NatchaththiramEnum;
import com.avs.portal.enums.RaasiEnum;
import com.avs.portal.enums.ReligionEnum;
import com.avs.portal.enums.SubcasteEnum;

public interface UserProfileRepository extends CrudRepository<UserProfile, UUID> {

	List<UserProfile> findAll();
	
	UserProfile findByUser(User user);
	
	List<UserProfile> findByMaritalStatus(MaritalStatusEnum maritalStatus);
	
	List<UserProfile> findByReligion(ReligionEnum religion);
	
	List<UserProfile> findByCaste(CasteEnum caste);
	
	List<UserProfile> findBySubcaste(SubcasteEnum subcaste);
	
	List<UserProfile> findByKoththiram(KoththiramEnum koththiram);
	
	List<UserProfile> findByPlaceOfBirth(String placeOfBirth);
	
	List<UserProfile> findByBirthTimestamp(Timestamp birthTimestamp);
	
	List<UserProfile> findByRaasi(RaasiEnum raasi);
	
	List<UserProfile> findByNatchaththiram(NatchaththiramEnum natchaththiram);
	
}
