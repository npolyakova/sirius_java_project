import argparse
import requests
from faker import Faker
import random
from concurrent.futures import ThreadPoolExecutor
from datetime import datetime, timedelta

class HotelDataPopulator:
    def __init__(self, endpoint, count=5, delete_all=False):
        self.fake = Faker()
        self.base_url = "http://localhost:8080/api"  # Adjust to your service URL
        self.endpoint = endpoint
        self.count = count
        self.delete_all = delete_all
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
        if self.delete_all:
            self.session.delete(f"{self.base_url}/rooms")
        room_type = random.choice(["LUX", "ECONOM", "STANDART"])
        base_price = 3000 if room_type == "LUX" else 1500
        price_per_night = base_price + random.randint(-200, 500)

        return {
            "type": room_type,
            "costPerNight": price_per_night,
        }

    def _generate_guest_data(self):
        if self.delete_all:
            self.session.delete(f"{self.base_url}/guests")
        return {
            "fullName": self.fake.name(),
            "passport": self.fake.passport_number(),
            "checkIn": str(self.fake.date_between_dates(datetime(2015,1,1), datetime(2025,4,21))),
            "checkOut": str(self.fake.date_between_dates(datetime(2015,1,1), datetime(2026,12,12))),
        }

    def _generate_booking_data(self):
        if self.delete_all:
            self.session.delete(f"{self.base_url}/bookings")
        rooms = self._get_existing_rooms()
        guests = self._get_existing_guests()

        if not rooms:
            self._generate_room_data()
        if not guests:
            self._generate_guest_data()

        check_in_date = self.fake.date_between(start_date='today', end_date='+30d')
        stay_duration = random.randint(1, 14)
        check_out_date = check_in_date + timedelta(days=stay_duration)

        room = random.choice(rooms)
        room_price = room['costPerNight']

        return {
            "roomId": room['id'],
            "guestId": random.choice(guests)['id'],
            "dates": str(check_in_date) + ',' + str(check_out_date),
            "cost": stay_duration * room_price,
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
            results = list(executor.map(lambda d: self._send_create_request(url, d), data))

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
    parser.add_argument("--delete_all", type=bool, default=False, help="Should the data be cleaned")

    args = parser.parse_args()

    try:
        populator = HotelDataPopulator(args.endpoint, args.count, args.delete_all)
        populator.create_data()

    except Exception as e:
        print(f"Error: {str(e)}")
        exit(1)

if __name__ == "__main__":
    main()