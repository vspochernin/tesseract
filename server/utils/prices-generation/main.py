import random
from datetime import datetime, timedelta


def generate_price_insert_script(num_assets, min_price, max_price, start_date_str, num_days):
    start_date = datetime.strptime(start_date_str, "%Y-%m-%d")
    insert_template = "INSERT INTO prices(asset_id, price, set_datetime) VALUES ({asset_id}, {price}, '{date}');"
    scripts = []

    for asset_id in range(1, num_assets + 1):
        price = random.randint(min_price, max_price)
        for day in range(num_days):
            current_date = start_date + timedelta(days=day)
            date_str = current_date.strftime("%Y-%m-%d 00:00:00+03")
            if day != 0:
                price_change_percent = random.uniform(-0.05, 0.05)  # Случайное изменение цены на -5% до +5%
                price *= (1 + price_change_percent)
                price = int(price)
            scripts.append(insert_template.format(asset_id=asset_id, price=price, date=date_str))
        scripts.append("")

    return scripts


# Параметры для генерации скрипта
num_assets = 20  # Количество активов
min_price = 17000  # Минимальная начальная цена (в копейках)
max_price = 10000000 # Максимальная начальная цена (в копейках)
start_date_str = "2023-09-01"  # Начальная дата генерации цен.
num_days = 3775  # Количество дней генерации

# Генерация скрипта
scripts = generate_price_insert_script(num_assets, min_price, max_price, start_date_str, num_days)

# Запись сгенерированного скрипта в файл
file_path = "V3__Fill_db_with_prices.sql"
with open(file_path, "w") as file:
    file.write("\n".join(scripts))
