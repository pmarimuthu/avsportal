CREATE OR REPLACE FUNCTION public.fn_get_myfamily(given_userid uuid)
 RETURNS text
 LANGUAGE plpgsql
AS $function$
	declare 
		ufm_title int4;
		ufm_record record;
	
		csv_myfamily text := '';
	
	begin
		
		if fn_is_user_exists(given_userid) = false then
			raise notice 'user not found.';
			return csv_myfamily;
		end if;
	
		select ufm1.title into ufm_title from user_family_map_15 ufm1 where ufm1.userid = given_userid;
		raise notice 'user title: %', ufm_title;
	
		if ufm_title > 1 then -- SON | DAUGHTER
			raise notice 'get all unmarried kids';
			for ufm_record in 
				(select ufm2.userid from user_family_map_15 ufm2 where ufm2.title > 1 and ufm2.family_head_id in 
					(select ufm3.family_head_id from user_family_map_15 ufm3 where ufm3.userid = given_userid)
				)
			loop 
				if length(csv_myfamily) > 0 then
					csv_myfamily := csv_myfamily || ', ';
				else 
					csv_myfamily := csv_myfamily || ufm_record.userid;
				end if;
			end loop;
			return csv_myfamily;
		end if;
	
		if ufm_title = 0 then -- Head
			raise notice 'get my spouses';
			for ufm_record in select ufm4.userid from user_family_map_15 ufm4 where ufm4.family_head_id = given_userid and ufm4.title = 1
			loop 
				if length(csv_myfamily) > 0 then
					csv_myfamily := csv_myfamily || ', ';
				else 
					csv_myfamily := csv_myfamily || ufm_record.userid;
				end if;
			end loop;
		elsif ufm_title = 1 then -- Spouse
			raise notice 'get my family-heads';
			for ufm_record in select ufm5.family_head_id from user_family_map_15 ufm5 where ufm5.userid = given_userid
			loop 
				if length(csv_myfamily) > 0 then 
					csv_myfamily := csv_myfamily || ', ';
				else 
					csv_myfamily := csv_myfamily || ufm_record.family_head_id;
				end if;
			end loop;
		end if;
	
		-- **********	
		raise notice 'csv_myfamily: %', csv_myfamily;
				
		return csv_myfamily;

	end;
$function$
;
