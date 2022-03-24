package com.avs.portal.repository;

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
	
	@Procedure(name = "FN_GET_CHILD", procedureName = "FN_GET_CHILD", value = "FN_GET_CHILD")
	UUID fnGetChild(UUID givenUserId);
	
	// ---------------- PLPGSQL Functions ----------------  //
	/*
	 *  uuid 	fn_auth_user(loginid text, pwd text)
	 *  text 	fn_get_children(given_user_id uuid)
	 *  text[] 	fn_get_grandchildren(given_user_id uuid)
	 *  uuid	fn_get_parent(given_userid uuid)
	 *  text	fn_get_parents(given_userid uuid)
	 *  uuid	fn_get_spouse(given_userid uuid)
	 *  boolean fn_is_user_exists(given_userid uuid)
	 *  
	 */
	
	@Procedure(name = "fn_get_parent", procedureName = "fn_get_parent", value = "fn_get_parent")
	UUID fnGetParent(UUID givenUserId);
	
	@Procedure(name = "fn_is_user_exists", procedureName = "fn_is_user_exists", value = "fn_is_user_exists")
	Boolean fnIsUserExists(UUID givenUserId);
	
	@Procedure(name = "fn_auth_user", procedureName = "fn_auth_user", value = "fn_auth_user")
	UUID fnAuthUser(String loginId, String password);
	
	@Procedure(name = "fn_get_spouse", procedureName = "fn_get_spouse", value = "fn_get_spouse")
	String fnGetSpouse(UUID givenUserId);
	
	@Procedure(name = "fn_get_parents", procedureName = "fn_get_parents", value = "fn_get_parents")
	String fnGetParents(UUID givenUserId);
	
	@Procedure(name = "fn_get_children", procedureName = "fn_get_children", value = "fn_get_children")
	String fnGetChildren(UUID givenUserId);
	
	@Procedure(name = "fn_get_grandchildren", procedureName = "fn_get_grandchildren", value = "fn_get_grandchildren")
	String[] fnGetGrandchildren(UUID givenUserId);
	
	
	
	
}
