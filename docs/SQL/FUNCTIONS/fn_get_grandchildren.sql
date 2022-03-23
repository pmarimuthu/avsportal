CREATE OR REPLACE FUNCTION public.fn_get_grandchildren(given_user_id uuid)
 RETURNS text
 LANGUAGE plpgsql
AS $function$
	declare
		empty_text text := '';
		csv_children_id text;
		arr_children_id text[];
	
		children_id text;
	
		csv_grandchildren_id text;
		arr_grandchildren_id text[];
	
		grandchildren_id text;
		
		-- given_user_email text;
		-- grandchildren_email text;
	
		grandchildrens text[];
	
	begin
		if given_user_id is null then 
			return grandchild_id;
		end if;
	
		-- select u.email into given_user_email from user_01 u where u.id = given_user_id;
		-- raise notice 'To find Grandchildren of % [%]', given_user_id, given_user_email;
	
		csv_children_id = fn_get_children(given_user_id);
		if csv_children_id is null or length(trim(csv_children_id)) = 0 then
			return empty_text;
		end if;
	
		select string_to_array(csv_children_id, ', ') into arr_children_id;	
  		foreach children_id in array arr_children_id loop
  			csv_grandchildren_id = fn_get_children(UUID(children_id));
  		
  			select string_to_array(csv_grandchildren_id, ', ') into arr_grandchildren_id;
	  		foreach grandchildren_id in array arr_grandchildren_id loop				
	  			-- select u.email into grandchildren_email from user_01 u where u.id = uuid(grandchildren_id);
	  			-- raise notice 'grand-child-id: % [%]', grandchildren_id, grandchildren_email;
	  			grandchildrens = array_append(grandchildrens, grandchildren_id);
	  		end loop;

	  	end loop;
	  
	  	-- raise notice 'array: %', grandchildrens;
	  
		return grandchildrens;

	END;
$function$
;
