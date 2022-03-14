DROP FUNCTION public.FN_GET_PARENT(given_userid UUID);

CREATE OR REPLACE FUNCTION public.FN_GET_PARENT(given_userid UUID)
 RETURNS UUID
AS $$
	DECLARE
		marital_status INT4;
		parent_id UUID;
		
	BEGIN
		SELECT B.MARITAL_STATUS INTO marital_status FROM USER_01 A INNER JOIN USER_PROFILE_08 B ON A.ID = B.USERID 
		WHERE A.ID = given_userid;
	
		IF marital_status = 0 THEN
			SELECT C.parent_family_head_id INTO parent_id FROM user_family_map_15 C WHERE C.userid = given_userid;
		ELSIF marital_status = 3 THEN
			SELECT C.family_head_id INTO parent_id FROM user_family_map_15 C WHERE C.userid = given_userid;
		END IF;
	
		RETURN parent_id;
	END;
$$

 LANGUAGE plpgsql;