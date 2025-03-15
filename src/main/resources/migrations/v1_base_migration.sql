INSERT INTO public.guest (deleted,full_name,passport) VALUES
	 (false,'Сгенерированный Гость','2222 111111');

INSERT INTO public.hotel_room (cost_per_night, "type") VALUES
	 (1200, 'LUX');

INSERT INTO public.booking ("cost",arrival_date,guest_id,leaving_date,room_id)
	VALUES (1000.0,'2025-03-12',1,'2025-03-15',1)
