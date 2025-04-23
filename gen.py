import argparse
import requests
from faker import Faker
import random
import time
from concurrent.futures import ThreadPoolExecutor
from tqdm import tqdm
from datetime import datetime, timedelta

class HotelDataPopulator:
    def __init__(self, endpoint, count=500):
        self.fake = Faker()
        self.base_url = "http://localhost:8080/api"  # Adjust to your service URL
        self.endpoint = endpoint
        self.count = count
        self.session = requests.Session()

        self.endpoint_config = {
            'rooms': {
                'create_path': '/rooms',
                'data_generator': self._generate_room_data,
                'dependencies': []
            },
            'guests': {
                'create_path': '/guests',
                'data_generator': self._generate_guest_data,
                'dependencies': []
            },
            'bookings': {
                'create_path': '/bookings',
                'data_generator': self._generate_booking_data,
                'dependencies': ['rooms', 'guests']
            }
        }

        if endpoint not in self.endpoint_config:
            raise ValueError(f"Unsupported endpoint: {endpoint}")

    def _generate_room_data(self):
        room_type = random.choice(["LUX", "ECO"])
        base_price = 3000 if room_type == "LUX" else 1500
        price_per_night = base_price + random.randint(-200, 500)

        return {
            "roomType": room_type,
            "pricePerNight": price_per_night,
            "description": self.fake.sentence(),
            "capacity": random.randint(1, 4),
            "floor": random.randint(1, 10),
            "roomNumber": self.fake.unique.bothify('###?')
        }

    def _generate_guest_data(self):
        return {
            "fullName": self.fake.name(),
            "passportNumber": self.fake.unique.bothify('#### ######'),
            "phoneNumber": self.fake.phone_number(),
            "email": self.fake.unique.email(),
            "birthDate": self.fake.date_of_birth(minimum_age=18, maximum_age=80).isoformat()
        }

    def _generate_booking_data(self):
        # Получаем существующие комнаты и гостей
        rooms = self._get_existing_rooms()
        guests = self._get_existing_guests()

        if not rooms:
            self._generate_room_data()
        if not guests:
            self._generate_guest_data()

        # Генерируем даты бронирования
        check_in_date = self.fake.date_between(start_date='today', end_date='+30d')
        stay_duration = random.randint(1, 14)
        check_out_date = check_in_date + timedelta(days=stay_duration)

        # Получаем цену за ночь для выбранной комнаты
        room = random.choice(rooms)
        room_price = room['pricePerNight']

        return {
            "roomId": room['id'],
            "guestId": random.choice(guests)['id'],
            "checkInDate": check_in_date.isoformat(),
            "checkOutDate": check_out_date.isoformat(),
            "totalPrice": stay_duration * room_price,
            "status": random.choice(["confirmed", "pending", "cancelled"]),
            "createdAt": datetime.now().isoformat()
        }

    def _get_existing_rooms(self):
        try:
            response = self.session.get(f"{self.base_url}/rooms")
            if response.status_code == 200:
                return response.json()
            return []
        except Exception as e:
            print(f"Error fetching rooms: {str(e)}")
            return []

    def _get_existing_guests(self):
        try:
            response = self.session.get(f"{self.base_url}/guests")
            if response.status_code == 200:
                return response.json()
            return []
        except Exception as e:
            print(f"Error fetching guests: {str(e)}")
            return []

    def create_data(self, max_workers=10):
        config = self.endpoint_config[self.endpoint]
        url = f"{self.base_url}{config['create_path']}"

        # Проверяем зависимости
        for dep in config['dependencies']:
            dep_populator = HotelDataPopulator(dep, 0)
            existing_data = dep_populator._get_existing_data(dep)

            if not existing_data:
                # Автоматически создаем минимальное количество зависимых сущностей
                create_count = max(10, self.count // 5)
                print(f"Creating {create_count} {dep} records (dependency for {self.endpoint})...")
                dep_populator.count = create_count
                dep_populator.create_data()

        # Генерируем данные
        data = [config['data_generator']() for _ in range(self.count)]

        # Создаем записи с прогресс-баром и параллельными запросами
        with ThreadPoolExecutor(max_workers=max_workers) as executor:
            results = list(tqdm(
                executor.map(lambda d: self._send_create_request(url, d), data),
                total=self.count,
                desc=f"Creating {self.endpoint} records"
            ))

        # Проверяем результаты
        success_count = sum(1 for r in results if r and r.status_code in [200, 201])
        print(f"\nSuccessfully created {success_count}/{self.count} {self.endpoint} records")

    def _get_existing_data(self, endpoint):
        """Универсальный метод для получения существующих данных"""
        if endpoint == 'rooms':
            return self._get_existing_rooms()
        elif endpoint == 'guests':
            return self._get_existing_guests()
        return []

    def _send_create_request(self, url, data):
        try:
            response = self.session.post(url, json=data)
            if response.status_code not in [200, 201]:
                print(f"Error creating record: {response.status_code}, {response.text}")
            return response
        except Exception as e:
            print(f"Request failed: {str(e)}")
            return None

def main():
    parser = argparse.ArgumentParser(description="Populate hotel REST service with test data")
    parser.add_argument("--count", type=int, default=500, help="Number of records to create")
    parser.add_argument("--endpoint", required=True,
                        choices=['rooms', 'guests', 'bookings'],
                        help="API endpoint to populate")

    args = parser.parse_args()

    try:
        populator = HotelDataPopulator(args.endpoint, args.count)

        print(f"Creating {args.count} {args.endpoint} records...")
        start_time = time.time()
        populator.create_data()
        elapsed = time.time() - start_time

        print(f"\nDone! Operation completed in {elapsed:.2f} seconds")
    except Exception as e:
        print(f"Error: {str(e)}")
        exit(1)

if __name__ == "__main__":
    main()