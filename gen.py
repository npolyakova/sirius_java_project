import psycopg2

def add_booking_to_database():
    try:
        conn = psycopg2.connect(
            dbname="java_app_db",
            user="app_user",
            password="23bjf8Gewb3",
            host="localhost",
            port="5432"
        )

        cursor = conn.cursor()

        guest_data = [
            (False,'Сгенерированный Гость 1','2222 111111'),
            (False,'Сгенерированный Гость 2','3222 111111'),
            (False,'Сгенерированный Гость 3','4222 111111')
        ]

        guest_insert_query = """
        INSERT INTO public.guest (deleted,full_name,passport) 
        VALUES (%s, %s, %s)
        """

        for guest in guest_data:
            cursor.execute(guest_insert_query, guest)

        conn.commit()
        print("Данные успешно добавлены в таблицу guest")

        hotel_room_data = [
            (1200, 'LUX'),
            (800, 'STANDART'),
            (700, 'ECONOM')
        ]

        hotel_room_insert_query = """
        INSERT INTO public.hotel_room (cost_per_night, "type")
        VALUES (%s, %s)
        """

        for hotel_room in hotel_room_data:
            cursor.execute(hotel_room_insert_query, hotel_room)

        conn.commit()
        print("Данные успешно добавлены в таблицу hotel_room")

        booking_data = [
            (1600.0,'2025-03-12',1,'2025-03-15',1),
            (3600.0,'2025-03-12',1,'2025-03-15',1),
            (1400.0,'2025-03-12',1,'2025-03-15',1)
        ]

        booking_insert_query = """
        INSERT INTO public.booking ("cost",arrival_date,guest_id,leaving_date,room_id)
        VALUES (%s, %s, %s)
        """

        for booking in booking_data:
            cursor.execute(hotel_room_insert_query, booking)

        conn.commit()
        print("Данные успешно добавлены в таблицу booking")

    except (Exception, psycopg2.Error) as error:
        print("Ошибка при работе с PostgreSQL:", error)

    finally:
        # Закрытие соединения
        if conn:
            cursor.close()
            conn.close()
            print("Соединение с PostgreSQL закрыто")

# Вызов функции
add_booking_to_database()