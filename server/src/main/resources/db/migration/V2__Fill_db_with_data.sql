INSERT INTO operators(title, inclusion_datetime)
VALUES ('ООО "ГалактикСофт"', '2022-02-03 00:00:00+03');

INSERT INTO operators(title, inclusion_datetime)
VALUES ('ПАО "КвантБанк"', '2022-03-17 00:00:00+03');

INSERT INTO operators(title, inclusion_datetime)
VALUES ('ООО "ТехноСвет"', '2022-03-17 00:00:00+03');

INSERT INTO operators(title, inclusion_datetime)
VALUES ('АО "ФьючерТек"', '2023-02-02 00:00:00+03');

INSERT INTO operators(title, inclusion_datetime)
VALUES ('ООО "Блокчейн Солюшнс"', '2023-03-09 00:00:00+03');

INSERT INTO operators(title, inclusion_datetime)
VALUES ('ООО "ИнвестТокен"', '2023-06-15 00:00:00+03');

INSERT INTO operators(title, inclusion_datetime)
VALUES ('АКБ "ГлобалФинанс" (АО)', '2023-06-15 00:00:00+03');

INSERT INTO operators(title, inclusion_datetime)
VALUES ('ПАО "ЭксчейнжМаркет"', '2023-06-22 00:00:00+03');

INSERT INTO operators(title, inclusion_datetime)
VALUES ('ООО "КриптоХаб"', '2023-07-27 00:00:00+03');

INSERT INTO operators(title, inclusion_datetime)
VALUES ('НКО "ФинДепозитарий" (АО)', '2023-08-03 00:00:00+03');


INSERT INTO companies(title, description, foundation_datetime, revenue, profit, staff)
VALUES ('ООО "Фантазия Инвест"',
        'Специализируется на разработке и внедрении инновационных финансовых технологий. Имеет широкий спектр инвестиционных проектов в различных отраслях экономики. Полностью соответствует современным требованиям цифровой экономики.',
        '2015-11-24 00:00:00+03', 23155078900000, -9857529700000, 1);

INSERT INTO companies(title, description, foundation_datetime, revenue, profit, staff)
VALUES ('ООО "МегаБилд"',
        'Оказывает полный спектр услуг в области строительства и недвижимости. Известна своими инновационными подходами к строительству и управлению объектами недвижимости.',
        '2022-09-05 00:00:00+03', 31475000000, -6000000, 1);

INSERT INTO companies(title, description, foundation_datetime, revenue, profit, staff)
VALUES ('АО "Эко-Инновации"',
        'Занимается разработкой и реализацией проектов в области экологии и устойчивого развития. Продукция компании помогает снижать воздействие на окружающую среду и повышать эффективность использования ресурсов.',
        '2021-02-19 00:00:00+03', 22736500000, 151100000, 29);

INSERT INTO companies(title, description, foundation_datetime, revenue, profit, staff)
VALUES ('ООО "КосмоТех"',
        'Разрабатывает высокотехнологичное оборудование и программное обеспечение для космической отрасли. Ведет активное сотрудничество с ведущими научными центрами и университетами.',
        '1995-05-15 00:00:00+03', 2062724900000, 125387000000, 579);

INSERT INTO companies(title, description, foundation_datetime, revenue, profit, staff)
VALUES ('ООО "Финансовые Решения"',
        'Предоставляет широкий спектр финансовых услуг для бизнеса, включая факторинг, лизинг и кредитование. Имеет разветвленную сеть филиалов и представительств в крупных городах.',
        '2011-03-28 00:00:00+03', 147777500000, 1640400000, 7);

INSERT INTO companies(title, description, foundation_datetime, revenue, profit, staff)
VALUES ('ООО "ТрансТехЛизинг"',
        'Специализируется на предоставлении в лизинг транспортных средств и спецтехники для крупного и среднего бизнеса. Использует индивидуальный подход к каждому клиенту, предлагая оптимальные условия сотрудничества.',
        '2011-08-17 00:00:00+03', 153691500000, 11838300000, 78);


INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('EcoBond_18',
        'Выпуск экологически чистых облигаций: 5 000 единиц. Без возможности досрочного выкупа. Фиксированная номинальная стоимость 10 000 единиц валюты. Ежемесячные выплаты.',
        '2023-11-21 00:00:00+03', 5, 18, 1);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('TechToken_17',
        'Инновационный технологический токен: ограниченный выпуск в 1 штуку. Не допускается дробление. Фиксированная стоимость 100 000 000 единиц валюты. Периодические выплаты.',
        '2023-11-16 00:00:00+03', 4, 18.4, 2);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('RealEstate_12',
        'Недвижимость premium класса: один объект. Отсутствие возможности досрочного погашения. Фиксированная стоимость 100 000 000 единиц валюты. Регулярные выплаты.',
        '2023-12-10 00:00:00+03', 4, 18.4, 3);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('GreenEnergy_13',
        'Зеленая энергия: выпуск 50 000 единиц. Не предусмотрено дробление. Номинальная стоимость 1 000 единиц валюты. Выплаты осуществляются ежемесячно.',
        '2023-10-18 00:00:00+03', 6, 14, 4);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('InnovativeFund_43',
        'Фонд инноваций: 100 000 единиц. Досрочное погашение и дробление не предусмотрены. Номинальная стоимость 3 000 единиц валюты. Месячные выплаты.',
        '2023-11-02 00:00:00+03', 6, 15, 5);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('StartupShares_33',
        'Акции стартапов: выпуск 20 000 единиц. Без возможности досрочного выкупа и дробления. Номинальная стоимость 4 000 единиц валюты. Ежемесячные выплаты.',
        '2023-09-12 00:00:00+03', 6, 13.5, 6);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('CryptoFuture_53',
        'Криптовалютные фьючерсы: 10 000 единиц. Отсутствие досрочного погашения. Номинальная стоимость 10 000 единиц валюты. Периодические выплаты.',
        '2023-11-20 00:00:00+03', 6, 15.5, 7);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('DigitalGold_15',
        'Цифровое золото: 10 000 единиц. Запрет на дробление и досрочное погашение. Фиксированная стоимость 10 000 единиц валюты. Выплаты осуществляются ежемесячно.',
        '2023-11-21 00:00:00+03', 5, 18, 8);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('SpaceMining_25',
        'Космическая добыча ресурсов: выпуск 50 000 единиц. Без возможности досрочного выкупа. Номинальная стоимость 20 000 единиц валюты. Месячные выплаты.',
        '2023-08-11 00:00:00+03', 5, 16.5, 9);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('AI_Development_5',
        'Разработки в области искусственного интеллекта: 30 000 единиц. Досрочное погашение и дробление не предусмотрены. Номинальная стоимость 5 000 единиц валюты. Ежемесячные выплаты.',
        '2023-10-10 00:00:00+03', 5, 17, 10);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('TokenizedArt_53',
        'Токенизированное искусство: привлечение средств в размере 150 000 000 единиц валюты. Дробление до сотых, без досрочного погашения. Предоставляет права на дивиденды, аналогичные акциям.',
        '2023-11-24 00:00:00+03', 1, 8.8, 1);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('VirtualEstates_29',
        'Виртуальная недвижимость: лимит средств 150 000 000 единиц валюты. Предусмотрено дробление, отсутствует досрочное погашение. Выплаты эквивалентны дивидендам.',
        '2023-09-25 00:00:00+03', 1, 9.3, 2);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('RenewableEnergy_51',
        'Возобновляемая энергия: цель привлечения 150 000 000 единиц валюты. Дробление до сотых, без возможности досрочного погашения. Удостоверяет права на периодические выплаты.',
        '2023-11-23 00:00:00+03', 1, 8.9, 3);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('QuantumComputing_17',
        'Квантовые вычисления: цель финансирования 150 000 000 единиц валюты. Предусмотрено дробление до сотых. Предоставляет права на дивидендные выплаты.',
        '2023-08-17 00:00:00+03', 1, 9.5, 4);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('UrbanDevelopment_9',
        'Развитие городской инфраструктуры: 510 единиц. Отсутствие возможности досрочного погашения. Номинальная стоимость 99 300 единиц валюты. Выплата в конце срока.',
        '2023-08-04 00:00:00+03', 3, 10, 5);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('BiotechInnovations_19',
        'Биотехнологические инновации: 1000 единиц. Запрещено дробление и досрочное погашение. Номинальная стоимость 80 500 единиц валюты. Одноразовая выплата.',
        '2023-09-15 00:00:00+03', 3, 12, 6);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('SmartCity_29',
        'Умный город: 2000 единиц. Досрочное погашение и дробление не предусмотрены. Номинальная стоимость 15 800 единиц валюты. Выплата в конце срока.',
        '2023-11-05 00:00:00+03', 3, 13.5, 7);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('SpaceExploration_10',
        'Исследование космоса: 5 единиц. Дробление до десятых, отсутствие досрочного погашения. Номинальная стоимость 230 000 единиц валюты. Выплата в конце срока.',
        '2023-07-24 00:00:00+03', 2, 15, 8);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('HighTech_2',
        'Высокие технологии: 5 единиц. Допускается дробление до десятых. Номинальная стоимость 230 600 единиц валюты. Однократная выплата в конце периода.',
        '2023-05-11 00:00:00+03', 2, 15, 9);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('EcoProjects_20',
        'Экологические проекты: 50 единиц. Дробление до десятых. Номинальная стоимость 50 500 единиц валюты. Выплата в конце срока.',
        '2023-07-12 00:00:00+03', 2, 14.5, 10);


INSERT INTO users(login, email, password)
VALUES ('vspochernin', 'vspochernin@gmail.com', '$2a$10$SGRw6G/5NR5WBIRJjq2HJuVFQSJsfK.dDjPx2pWYDENOWUOXCDoVy');


INSERT INTO users(login, email, password)
VALUES ('vrazukrantov', 'vrazbusiness@mail.ru', '$2a$10$SGRw6G/5NR5WBIRJjq2HJuVFQSJsfK.dDjPx2pWYDENOWUOXCDoVy');


INSERT INTO users_assets(user_id, asset_id)
VALUES (2, 1);

INSERT INTO users_assets(user_id, asset_id)
VALUES (2, 5);

INSERT INTO users_assets(user_id, asset_id)
VALUES (2, 8);

INSERT INTO users_assets(user_id, asset_id)
VALUES (2, 2);


INSERT INTO diversifications(user_id, create_datetime, risk_type_id, amount)
VALUES (1, '2023-12-27 09:00:00+03', 3, 56432017);


INSERT INTO diversifications(user_id, create_datetime, risk_type_id, amount)
VALUES (2, '2023-12-28 09:00:00+03', 2, 39874008);


INSERT INTO diversifications_assets(diversification_id, asset_id, count)
VALUES (1, 1, 3);

INSERT INTO diversifications_assets(diversification_id, asset_id, count)
VALUES (1, 3, 10);

INSERT INTO diversifications_assets(diversification_id, asset_id, count)
VALUES (1, 4, 1);

INSERT INTO diversifications_assets(diversification_id, asset_id, count)
VALUES (2, 7, 1);

INSERT INTO diversifications_assets(diversification_id, asset_id, count)
VALUES (2, 1, 2);

INSERT INTO diversifications_assets(diversification_id, asset_id, count)
VALUES (2, 4, 3);
