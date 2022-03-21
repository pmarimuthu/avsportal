package com.avs.portal.repository;

import java.sql.Array;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.avs.portal.entity.User;

public interface UserRepository extends CrudRepository<User, UUID> {

	@Override
	List<User> findAll();
		
	List<User> findAllByIdIn(List<UUID> ids);
	
	List<User> findByPhone(Long phone);
	
	List<User> findByEmail(String email);
	
	@Query(value = "SELECT u FROM User u WHERE u.id = :id OR u.email = :email AND u.phone = :phone")
	List<UUID> findIdByIdOrEmailAndPhone(UUID id, String email, Long phone);
	
	@Query(nativeQuery = true, value = "SELECT id FROM USER_01 u WHERE u.id = :id OR u.email = :email AND u.phone = :phone LIMIT 1")
	List<UUID> findByNativeTheUserByIdOrEmailAndPhone(@Param("id") UUID id, @Param("email") String email, @Param("phone") Long phone);
	
	// --------- Optimized Queries --------- //
	
	@Query(nativeQuery = true, value = "SELECT cast(u.id as varchar) as id FROM user_01 u")
	List<String> findNativeAllId();
	
	@Query(value = "SELECT z FROM User z WHERE z.id = :id OR z.email = :email OR z.phone = :phone")
	List<User> jpqlFindUserByIdOrEmailOrPhone(@Param("id") UUID id, @Param("email") String email, @Param("phone") Long phone);
	
	@Procedure(name = "FN_GET_PARENT", procedureName = "FN_GET_PARENT", value = "FN_GET_PARENT")
	UUID fnGetParent(UUID givenUserId);
	
	@Procedure(name = "FN_GET_SPOUSE", procedureName = "FN_GET_SPOUSE", value = "FN_GET_SPOUSE")
	UUID fnGetSpouse(UUID givenUserId);
	
	@Procedure(name = "FN_GET_CHILD", procedureName = "FN_GET_CHILD", value = "FN_GET_CHILD")
	UUID fnGetChild(UUID givenUserId);
	
	@Procedure(name = "FN_GET_CHILDREN", procedureName = "FN_GET_CHILDREN", value = "FN_GET_CHILDREN")
	String fnGetChildren(UUID givenUserId);
	
	//@Query(nativeQuery = true, value = "select * from fn_get_children(?1)")
	//List<?> fnGetChildren(UUID givenUserId);
	
	//@Procedure(name = "fn_get_children(:given_user_id)")
	//List<UUID> fnGetChildren(@Param("givenUserId") UUID givenUserId);
	
	//@Procedure(procedureName = "fn_get_children")
	//List<UUID> fnGetChildren(UUID givenUserId);
	
	
}
