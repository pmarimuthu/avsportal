package com.avs.portal.repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.avs.portal.entity.UserFamilyMap;
import com.avs.portal.enums.FamilyMemberTitleEnum;
import com.avs.portal.enums.LiveStatusEnum;

public interface UserFamilyMapRepository extends CrudRepository<UserFamilyMap, UUID> {

	@Override
	Collection<UserFamilyMap> findAll();
	
	List<UserFamilyMap> findByParentFamilyHeadId(UUID parentFamilyHeadId);
	
	List<UserFamilyMap> findByFamilyHeadId(UUID familyHeadId);
	
	List<UserFamilyMap> findByTitle(FamilyMemberTitleEnum title);
	
	List<UserFamilyMap> findByLiveStatus(LiveStatusEnum liveStatus);

	List<UserFamilyMap> findDistinctByFamilyHeadIdNotNull();
	
	List<UserFamilyMap> findByTitleAndFamilyHeadId(FamilyMemberTitleEnum title, UUID familyHeadId);	
	
	List<UserFamilyMap> findByTitleAndParentFamilyHeadId(FamilyMemberTitleEnum title, UUID parentFamilyHeadId);
	
	@Query(nativeQuery = true,
			value = "SELECT DISTINCT CAST(ufm.family_head_id AS VARCHAR), ui.firstname, ui.lastname "
					+ "FROM user_family_map_15 ufm "
					+ "inner join user_information_06 ui on ui.userid = ufm.family_head_id "
					+ "WHERE ufm.family_head_id IS NOT null")
	List<String[]> nativeQueryDistinctFamilyHeads();
	
	@Query(nativeQuery = true,
			value = "SELECT DISTINCT CAST(ufm.parent_family_head_id AS VARCHAR), ui.firstname, ui.lastname "
					+ "FROM user_family_map_15 ufm "
					+ "inner join user_information_06 ui on ui.userid = ufm.parent_family_head_id "
					+ "WHERE ufm.parent_family_head_id IS NOT null")
	List<String[]> nativeQueryDistinctParentFamilyHeads();
}
