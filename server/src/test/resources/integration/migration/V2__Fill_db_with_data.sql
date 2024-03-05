INSERT INTO operators(title, inclusion_datetime)
VALUES ('Общество с ограниченной ответственностью "Атомайз", ООО "Атомайз"',
        '2022-02-03 00:00:00+03');

INSERT INTO operators(title, inclusion_datetime)
VALUES ('Публичное акционерное общество "Сбербанк России", ПАО Сбербанк',
        '2022-03-17 00:00:00+03');

INSERT INTO operators(title, inclusion_datetime)
VALUES ('Общество с ограниченной ответственностью "Лайтхаус", ООО "Лайтхаус""',
        '2022-03-17 00:00:00+03');

INSERT INTO operators(title, inclusion_datetime)
VALUES ('АКЦИОНЕРНОЕ ОБЩЕСТВО "АЛЬФА-БАНК", АО "АЛЬФА-БАНК"',
        '2023-02-02 00:00:00+03');

INSERT INTO operators(title, inclusion_datetime)
VALUES ('Общество с ограниченной ответственностью "Системы распределенного реестра", ООО "Системы распределенного реестра"',
        '2023-03-09 00:00:00+03');

INSERT INTO operators(title, inclusion_datetime)
VALUES ('Общество с ограниченной ответственностью "Токены – Цифровые Инвестиции", ООО "Токены"',
        '2023-06-15 00:00:00+03');

INSERT INTO operators(title, inclusion_datetime)
VALUES ('АКЦИОНЕРНЫЙ КОММЕРЧЕСКИЙ БАНК "ЕВРОФИНАНС МОСНАРБАНК" (акционерное общество), АО АКБ "ЕВРОФИНАНС МОСНАРБАНК"',
        '2023-06-15 00:00:00+03');

INSERT INTO operators(title, inclusion_datetime)
VALUES ('Публичное акционерное общество "СПБ Биржа", ПАО "СПБ Биржа"',
        '2023-06-22 00:00:00+03');

INSERT INTO operators(title, inclusion_datetime)
VALUES ('Общество с ограниченной ответственностью "Блокчейн Хаб", ООО "Блокчейн Хаб"',
        '2023-07-27 00:00:00+03');

INSERT INTO operators(title, inclusion_datetime)
VALUES ('Небанковская кредитная организация акционерное общество "Национальный расчетный депозитарий", НКО АО НРД',
        '2023-08-03 00:00:00+03');

-- 6 действующих компаний, с актуальной информацией из отчетов компаний
INSERT INTO companies(title, description, foundation_datetime, revenue, profit, staff)
VALUES ('Общество с ограниченной ответственностью «Цифровые активы»',
        'Компания ведет деятельность по управлению финансово-промышленными группами и является  действующей, ' ||
        'коммерческой. Общее количество направлений деятельности - 4. Организация не имела налоговой задолженности ' ||
        'по состоянию на 01.10.2021',
        '2015-11-24 00:00:00+03', 23155078900000, -9857529700000, 1);

INSERT INTO companies(title, description, foundation_datetime, revenue, profit, staff)
VALUES ('Общество с ограниченной ответственностью «Самолет Плюс ЦФА»',
        'Компания ведет деятельность агенства недвижимости за вознограждение или на договорной основе и является  ' ||
        'действующей, коммерческой. Общее количество направлений деятельнсоти - 4. Организация не имела налоговой ' ||
        'задолженности по состоянию на 01.10.2022. Основным учредителем является компания ООО «Самолет Плюс», ' ||
        'которая разрабатывает компьютерное ПО уже целых 17 лет.',
        '2022-09-05 00:00:00+03', 31475000000, -6000000, 1);

INSERT INTO companies(title, description, foundation_datetime, revenue, profit, staff)
VALUES ('Акционерное общество «ДЖИ-ГРУПП»',
        'Компания ведет деятельность по консультированию по вопросам комерческой деятельности и управления, ' ||
        'является  действующей, коммерческой. Компания занимает первое место в Республике Татарстан по объему ' ||
        'ввода жилья за 2022 год и 34 место по всей России. Организация не имела налоговой задолженности по ' ||
        'состоянию на 01.10.2022',
        '2021-02-19 00:00:00+03', 22736500000, 151100000, 29);

INSERT INTO companies(title, description, foundation_datetime, revenue, profit, staff)
VALUES ('Общество с ограниченной ответственностью «НТС «Градиент»',
        'Компания ведет деятельность по производству парфюмерных и косметических средств, является  ' ||
        'действующей, коммерческой. Общее количество направлений деятельности - 4. Организация не имела ' ||
        'налоговой задолженности по состоянию на 01.10.2021',
        '1995-05-15 00:00:00+03', 2062724900000, 125387000000, 579);

INSERT INTO companies(title, description, foundation_datetime, revenue, profit, staff)
VALUES ('Общество ограниченной ответственностью «Глобал Факторинг Нетворк Рус»',
        'Федеральная факторинговая компания, занимающаяся оперативным финансированием оборотного капитала ' ||
        'предприятий под уступку надежной краткосрочной дебиторской задолженности с использованием юридически ' ||
        'значимого электронного документооборота. Компания входит в топ-30 факторинговых компаний России, в ' ||
        'топ-10 по сделкам с предприятиями малого и среднего бизнеса',
        '2011-03-28 00:00:00+03', 147777500000, 1640400000, 7);

INSERT INTO companies(title, description, foundation_datetime, revenue, profit, staff)
VALUES ('Общество с ограниченной ответственностью «Лизинговая компания Простые решения»',
        'Лизинговая компания, с 2011 года предоставляющая в лизинг широкий спектр имущества: от ' ||
        'легкового транспорта до высокотехнологичного оборудования и недвижимости, в том числе — ' ||
        'финансирование импорта. Организация не имела налоговой задолженности по состоянию на 01.10.2021',
        '2011-08-17 00:00:00+03', 153691500000, 11838300000, 78);

-- 20 актуальных активов, с актуальной информацией (минимум один актив на компанию).
INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('NDM_18',
        'Количество выпускаемых ЦФА: 5 000 штук. Дробление ЦФА и досрочное погашение не предусмотрено. ' ||
        'Каждый ЦФА имеет фиксированную номинальную стоимость в размере 10 000 рублей Российской Федерации. ' ||
        'Выплаты производятся ежемесячно',
        '2023-11-21 00:00:00+03', 5, 18, 1);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('NDM_17',
        'Количество выпускаемых ЦФА: 1 штука. Дробление ЦФА и досрочное погашение не предусмотрено. Каждый ЦФА' ||
        ' имеет фиксированную номинальную стоимость в размере 100 000 000 рублей Российской Федерации. Выплаты ' ||
        'производятся ежемесячно',
        '2023-11-16 00:00:00+03', 4, 18.4, 1);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('NDM_12',
        'Количество выпускаемых ЦФА: 1 штука. Дробление ЦФА и досрочное погашение не предусмотрено. Каждый ЦФА' ||
        ' имеет фиксированную номинальную стоимость в размере 100 000 000 рублей Российской Федерации. Выплаты ' ||
        'производятся ежемесячно',
        '2023-12-10 00:00:00+03', 4, 18.4, 1);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('NDM_13',
        'Количество выпускаемых ЦФА: 50 000 штук. Дробление ЦФА и досрочное погашение не предусмотрено. ' ||
        'Каждый ЦФА имеет фиксированную номинальную стоимость в размере 1000 рублей Российской Федерации. ' ||
        'Выплаты производятся ежемесячно',
        '2023-10-18 00:00:00+03', 6, 14, 1);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('NDM_43',
        'Количество выпускаемых ЦФА: 100 000 штук. Дробление ЦФА и досрочное погашение не предусмотрено. ' ||
        'Каждый ЦФА имеет фиксированную номинальную стоимость в размере 3000 рублей Российской Федерации. ' ||
        'Выплаты производятся ежемесячно',
        '2023-11-02 00:00:00+03', 6, 15, 1);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('NDM_33',
        'Количество выпускаемых ЦФА: 20 000 штук. Дробление ЦФА и досрочное погашение не предусмотрено. ' ||
        'Каждый ЦФА имеет фиксированную номинальную стоимость в размере 4000 рублей Российской Федерации. ' ||
        'Выплаты производятся ежемесячно',
        '2023-09-12 00:00:00+03', 6, 13.5, 1);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('NDM_53',
        'Количество выпускаемых ЦФА: 10 000 штук. Дробление ЦФА и досрочное погашение не предусмотрено. ' ||
        'Каждый ЦФА имеет фиксированную номинальную стоимость в размере 10 000 рублей Российской Федерации. ' ||
        'Выплаты производятся ежемесячно',
        '2023-11-20 00:00:00+03', 6, 15.5, 1);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('NDM_15',
        'Количество выпускаемых ЦФА: 10 000 штук. Дробление ЦФА и досрочное погашение не предусмотрено. ' ||
        'Каждый ЦФА имеет фиксированную номинальную стоимость в размере 10 000 рублей Российской Федерации. ' ||
        'Выплаты производятся ежемесячно',
        '2023-11-21 00:00:00+03', 5, 18, 1);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('NDM_25',
        'Количество выпускаемых ЦФА: 50 000 штук. Дробление ЦФА и досрочное погашение не предусмотрено. ' ||
        'Каждый ЦФА имеет фиксированную номинальную стоимость в размере 20 000 рублей Российской Федерации. ' ||
        'Выплаты производятся ежемесячно',
        '2023-08-11 00:00:00+03', 5, 16.5, 1);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('NDM_5',
        'Количество выпускаемых ЦФА: 30 000 штук. Дробление ЦФА и досрочное погашение не предусмотрено. ' ||
        'Каждый ЦФА имеет фиксированную номинальную стоимость в размере 5000 рублей Российской Федерации. ' ||
        'Выплаты производятся ежемесячно',
        '2023-10-10 00:00:00+03', 5, 17, 1);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('MINETOKEN_53',
        'Лимит привлекаемых Эмитентом денежных средств составляет 150 000 000 рублей. Дробление ЦФА ' ||
        'предусматривается до сотых, досрочное погашение не предусмотрено. ЦФА не имеют фиксированной ' ||
        'номинальной стоимости. ЦФА удостоверяет права обладателя на получение периодических выплат в размере,' ||
        ' равном суммам дивидендов на одну обыкновенную акцию ПАО «ГМК «Норильский никель»',
        '2023-11-24 00:00:00+03', 1, 8.8, 1);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('MINETOKEN_29',
        'Лимит привлекаемых Эмитентом денежных средств составляет 150 000 000 рублей. Дробление ЦФА' ||
        ' предусматривается до сотых, досрочное погашение не предусмотрено. ЦФА не имеют фиксированной ' ||
        'номинальной стоимости. ЦФА удостоверяет права обладателя на получение периодических выплат в размере,' ||
        ' равном суммам дивидендов на одну обыкновенную акцию ПАО «ГМК «Норильский никель»',
        '2023-09-25 00:00:00+03', 1, 9.3, 1);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('MINETOKEN_51',
        'Лимит привлекаемых Эмитентом денежных средств составляет 150 000 000 рублей. Дробление ЦФА' ||
        ' предусматривается до сотых, досрочное погашение не предусмотрено. ЦФА не имеют фиксированной ' ||
        'номинальной стоимости. ЦФА удостоверяет права обладателя на получение периодических выплат в размере,' ||
        ' равном суммам дивидендов на одну обыкновенную акцию ПАО «ГМК «Норильский никель»',
        '2023-11-23 00:00:00+03', 1, 8.9, 1);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('MINETOKEN_17',
        'Лимит привлекаемых Эмитентом денежных средств составляет 150 000 000 рублей. Дробление ЦФА' ||
        ' предусматривается до сотых, досрочное погашение не предусмотрено. ЦФА не имеют фиксированной ' ||
        'номинальной стоимости. ЦФА удостоверяет права обладателя на получение периодических выплат в размере,' ||
        ' равном суммам дивидендов на одну обыкновенную акцию ПАО «ГМК «Норильский никель»',
        '2023-08-17 00:00:00+03', 1, 9.5, 1);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('NDM_9',
        'Количество выпускаемых ЦФА: 510 штук. Дробление ЦФА и досрочное погашение не предусмотрено. ' ||
        'Каждый ЦФА имеет фиксированную номинальную стоимость в размере 99 300 рублей Российской Федерации. ' ||
        'Выплата 1 раз в дату погашения',
        '2023-08-04 00:00:00+03', 3, 10, 1);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('NDM_19',
        'Количество выпускаемых ЦФА: 1000 штук. Дробление ЦФА и досрочное погашение не предусмотрено. ' ||
        'Каждый ЦФА имеет фиксированную номинальную стоимость в размере 80 500 рублей Российской Федерации. ' ||
        'Выплата 1 раз в дату погашения',
        '2023-09-15 00:00:00+03', 3, 12, 1);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('NDM_29',
        'Количество выпускаемых ЦФА: 2000 штук. Дробление ЦФА и досрочное погашение не предусмотрено. ' ||
        'Каждый ЦФА имеет фиксированную номинальную стоимость в размере 15 800 рублей Российской Федерации. ' ||
        'Выплата 1 раз в дату погашения',
        '2023-11-05 00:00:00+03', 3, 13.5, 1);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('NDM_10',
        'Количество выпускаемых ЦФА: 5 штук. Дробление ЦФА до десятых, досрочное погашение не предусмотрено. ' ||
        'Каждый ЦФА имеет фиксированную номинальную стоимость в размере 230 000 рублей Российской Федерации. ' ||
        'Выплата 1 раз в дату погашения',
        '2023-07-24 00:00:00+03', 2, 15, 1);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('NDM_2',
        'Количество выпускаемых ЦФА: 5 штук. Дробление ЦФА до десятых, досрочное погашение не предусмотрено. ' ||
        'Каждый ЦФА имеет фиксированную номинальную стоимость в размере 230 600 рублей Российской Федерации. ' ||
        'Выплата 1 раз в дату погашения',
        '2023-05-11 00:00:00+03', 2, 15, 1);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('NDM_20',
        'Количество выпускаемых ЦФА: 50 штук. Дробление ЦФА до десятых, досрочное погашение не предусмотрено. ' ||
        'Каждый ЦФА имеет фиксированную номинальную стоимость в размере 50 500 рублей Российской Федерации. ' ||
        'Выплата 1 раз в дату погашения',
        '2023-07-12 00:00:00+03', 2, 14.5, 1);


INSERT INTO prices(asset_id, price, set_datetime)
VALUES (1, 1000000, '2023-11-21 00:00:00+03');

INSERT INTO prices(asset_id, price, set_datetime)
VALUES (1, 990000, '2023-11-23 00:00:00+03');

INSERT INTO prices(asset_id, price, set_datetime)
VALUES (2, 10000000, '2023-11-23 00:00:00+03');

INSERT INTO prices(asset_id, price, set_datetime)
VALUES (3, 9900000, '2023-11-10 00:00:00+03');

INSERT INTO prices(asset_id, price, set_datetime)
VALUES (3, 10000000, '2023-12-10 00:00:00+03');

INSERT INTO prices(asset_id, price, set_datetime)
VALUES (4, 100000, '2023-10-18 00:00:00+03');

INSERT INTO prices(asset_id, price, set_datetime)
VALUES (4, 110000, '2023-11-18 00:00:00+03');

INSERT INTO prices(asset_id, price, set_datetime)
VALUES (5, 300000, '2023-11-02 00:00:00+03');

INSERT INTO prices(asset_id, price, set_datetime)
VALUES (5, 305000, '2023-12-02 00:00:00+03');

INSERT INTO prices(asset_id, price, set_datetime)
VALUES (6, 400000, '2023-09-12 00:00:00+03');

INSERT INTO prices(asset_id, price, set_datetime)
VALUES (6, 390000, '2023-10-12 00:00:00+03');

INSERT INTO prices(asset_id, price, set_datetime)
VALUES (6, 395000, '2023-11-12 00:00:00+03');

INSERT INTO prices(asset_id, price, set_datetime)
VALUES (7, 1000000, '2023-11-20 00:00:00+03');

INSERT INTO prices(asset_id, price, set_datetime)
VALUES (8, 1000000, '2023-11-21 00:00:00+03');

INSERT INTO prices(asset_id, price, set_datetime)
VALUES (9, 2000000, '2023-08-11 00:00:00+03');

INSERT INTO prices(asset_id, price, set_datetime)
VALUES (9, 2050000, '2023-09-11 00:00:00+03');

INSERT INTO prices(asset_id, price, set_datetime)
VALUES (9, 2000000, '2023-10-11 00:00:00+03');

INSERT INTO prices(asset_id, price, set_datetime)
VALUES (9, 2100000, '2023-11-11 00:00:00+03');

INSERT INTO prices(asset_id, price, set_datetime)
VALUES (10, 500000, '2023-10-10 00:00:00+03');

INSERT INTO prices(asset_id, price, set_datetime)
VALUES (10, 495000, '2023-11-10 00:00:00+03');

INSERT INTO prices(asset_id, price, set_datetime)
VALUES (11, 1730000, '2023-11-24 00:00:00+03');

INSERT INTO prices(asset_id, price, set_datetime)
VALUES (12, 1590000, '2023-09-25 00:00:00+03');

INSERT INTO prices(asset_id, price, set_datetime)
VALUES (12, 1745000, '2023-10-25 00:00:00+03');

INSERT INTO prices(asset_id, price, set_datetime)
VALUES (12, 1730000, '2023-11-23 00:00:00+03');

INSERT INTO prices(asset_id, price, set_datetime)
VALUES (13, 1735000, '2023-11-23 00:00:00+03');

INSERT INTO prices(asset_id, price, set_datetime)
VALUES (14, 1620000, '2023-08-17 00:00:00+03');

INSERT INTO prices(asset_id, price, set_datetime)
VALUES (14, 1635000, '2023-09-17 00:00:00+03');

INSERT INTO prices(asset_id, price, set_datetime)
VALUES (14, 1640000, '2023-10-17 00:00:00+03');

INSERT INTO prices(asset_id, price, set_datetime)
VALUES (14, 17000, '2023-11-17 00:00:00+03');

INSERT INTO prices(asset_id, price, set_datetime)
VALUES (15, 9930000, '2023-08-04 00:00:00+03');

INSERT INTO prices(asset_id, price, set_datetime)
VALUES (15, 9970000, '2023-09-04 00:00:00+03');

INSERT INTO prices(asset_id, price, set_datetime)
VALUES (15, 9900000, '2023-10-04 00:00:00+03');

INSERT INTO prices(asset_id, price, set_datetime)
VALUES (15, 9980000, '2023-11-04 00:00:00+03');

INSERT INTO prices(asset_id, price, set_datetime)
VALUES (16, 8050000, '2023-09-15 00:00:00+03');

INSERT INTO prices(asset_id, price, set_datetime)
VALUES (16, 8020000, '2023-10-15 00:00:00+03');

INSERT INTO prices(asset_id, price, set_datetime)
VALUES (16, 8060000, '2023-11-15 00:00:00+03');

INSERT INTO prices(asset_id, price, set_datetime)
VALUES (17, 1580000, '2023-11-05 00:00:00+03');

INSERT INTO prices(asset_id, price, set_datetime)
VALUES (17, 1600000, '2023-12-05 00:00:00+03');

INSERT INTO prices(asset_id, price, set_datetime)
VALUES (18, 23000000, '2023-07-24 00:00:00+03');

INSERT INTO prices(asset_id, price, set_datetime)
VALUES (18, 23020000, '2023-08-24 00:00:00+03');

INSERT INTO prices(asset_id, price, set_datetime)
VALUES (18, 22900000, '2023-09-24 00:00:00+03');

INSERT INTO prices(asset_id, price, set_datetime)
VALUES (18, 23090000, '2023-10-24 00:00:00+03');

INSERT INTO prices(asset_id, price, set_datetime)
VALUES (18, 23110000, '2023-11-24 00:00:00+03');

INSERT INTO prices(asset_id, price, set_datetime)
VALUES (19, 23060000, '2023-05-11 00:00:00+03');

INSERT INTO prices(asset_id, price, set_datetime)
VALUES (19, 23090000, '2023-07-11 00:00:00+03');

INSERT INTO prices(asset_id, price, set_datetime)
VALUES (19, 23140000, '2023-09-11 00:00:00+03');

INSERT INTO prices(asset_id, price, set_datetime)
VALUES (19, 23090000, '2023-11-11 00:00:00+03');

INSERT INTO prices(asset_id, price, set_datetime)
VALUES (19, 23095000, '2023-12-11 00:00:00+03');

INSERT INTO prices(asset_id, price, set_datetime)
VALUES (20, 5050000, '2023-07-12 00:00:00+03');

INSERT INTO prices(asset_id, price, set_datetime)
VALUES (20, 5020000, '2023-09-12 00:00:00+03');

INSERT INTO prices(asset_id, price, set_datetime)
VALUES (20, 5040000, '2023-10-12 00:00:00+03');

INSERT INTO prices(asset_id, price, set_datetime)
VALUES (20, 5010000, '2023-11-12 00:00:00+03');

-- Пустой пользователь.
INSERT INTO users(login, email, password)
VALUES ('vspochernin', 'vspochernin@gmail.com', '$2a$10$SGRw6G/5NR5WBIRJjq2HJuVFQSJsfK.dDjPx2pWYDENOWUOXCDoVy');

-- Заполненный пользователь (будет две диверсификации + четыре избранных).
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

-- Диверсификация: комбинированная.
INSERT INTO portfolios(user_id, create_datetime, risk_type_id, amount)
VALUES (1, '2023-12-27 00:00:00+03', 3, 5000000);

-- Диверсификация: низкорискованная.
INSERT INTO portfolios(user_id, create_datetime, risk_type_id, amount)
VALUES (2, '2023-12-28 00:00:00+03', 2, 15000000);

-- Первая: три актива 1, десять активов 3, один актив 4.
INSERT INTO portfolios_assets(portfolio_id, asset_id, count)
VALUES (1, 1, 3);

INSERT INTO portfolios_assets(portfolio_id, asset_id, count)
VALUES (1, 3, 10);

INSERT INTO portfolios_assets(portfolio_id, asset_id, count)
VALUES (1, 4, 1);

-- Вторая: один актив 7, два актива 1, три актива 4.
INSERT INTO portfolios_assets(portfolio_id, asset_id, count)
VALUES (2, 7, 1);

INSERT INTO portfolios_assets(portfolio_id, asset_id, count)
VALUES (2, 1, 2);

INSERT INTO portfolios_assets(portfolio_id, asset_id, count)
VALUES (2, 4, 3);
