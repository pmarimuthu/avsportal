package com.avs.portal.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.avs.portal.entity.UserReferral;

public interface UserReferralRepository extends CrudRepository<UserReferral, UUID> {

	List<UserReferral> findAll();
	
	List<UserReferral> findByReferrer(UUID referrer);
	
	List<UserReferral> findByReferee(UUID referee);

	Optional<UserReferral>  findByReferralCode(String referralCode);
	
}
